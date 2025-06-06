package cassino.controller;
import cassino.view.CasinoView;

import java.util.HashMap;
import java.util.Scanner;
import java.util.List;

import cassino.model.*;

public class CasinoController {
    private HashMap<String, Usuario> usuarios = new HashMap<>();
    private Usuario usuarioLogado;
    private CasinoView view;
    private Roleta roleta;
    private CacaNiqueis cacaNiqueis;
    private Crash crash;
    private Scanner scanner;
    
    public CasinoController() {
        view = new CasinoView();
        scanner = new Scanner(System.in);
    }
    
    public void iniciar() throws InterruptedException {
        view.mostrarBoasVindas();
        
        // Loop principal do sistema
        while (true) {
            if (usuarioLogado == null) {
                // Se não há usuário logado, mostrar menu de login
                if (!mostrarMenuLogin()) {
                    // Se usuário escolheu sair, encerrar aplicação
                    view.mostrarMensagem("Obrigado por visitar o Casino Unificado!");
                    break;
                }
            } else {
                // Se há usuário logado, mostrar menu principal do casino
                if (!executarSessaoCasino()) {
                    // Se usuário fez logout, continuar para próxima iteração
                    continue;
                }
                // Se retornou false por saldo insuficiente, fazer logout automático
                fazerLogout();
            }
        }
    }
    
    private boolean mostrarMenuLogin() {
        while (true) {
            view.mostrarMensagem("\n" + "=".repeat(40));
            view.mostrarMensagem("        CASINO UNIFICADO - LOGIN");
            view.mostrarMensagem("=".repeat(40));
            view.mostrarMensagem("1. Fazer Login");
            view.mostrarMensagem("2. Criar Nova Conta");
            view.mostrarMensagem("3. Sair do Sistema");
            view.mostrarMensagem("=".repeat(40));
            view.mostrarMensagem("Escolha uma opção: ");
            
            String opcao = scanner.nextLine().trim();
            
            switch (opcao) {
                case "1":
                    if (fazerLogin()) {
                        return true; // Login bem-sucedido
                    }
                    break;
                case "2":
                    if (criarNovaConta()) {
                        return true; // Conta criada e login automático
                    }
                    break;
                case "3":
                    return false; // Sair do sistema
                default:
                    view.mostrarMensagem("Opção inválida! Tente novamente.");
            }
        }
    }
    
    private boolean fazerLogin() {
        view.mostrarMensagem("\n--- LOGIN ---");
        view.mostrarMensagem("Digite seu CPF (ou 'voltar' para retornar): ");
        String cpf = scanner.nextLine().trim();
        
        if ("voltar".equalsIgnoreCase(cpf)) {
            return false;
        }
        
        if (usuarios.containsKey(cpf)) {
            usuarioLogado = usuarios.get(cpf);
            
            // Recriar objetos dos jogos para o usuário logado
            roleta = new Roleta(usuarioLogado);
            cacaNiqueis = new CacaNiqueis(usuarioLogado);
            crash = new Crash(usuarioLogado);
            
            view.mostrarMensagem("=".repeat(50));
            view.mostrarMensagem("Login realizado com sucesso!");
            view.mostrarMensagem("Bem-vindo de volta, " + usuarioLogado.getNome() + "!");
            view.mostrarMensagem("=".repeat(50));
            return true;
        } else {
            view.mostrarMensagem("❌ CPF não encontrado no sistema.");
            view.mostrarMensagem("Verifique o CPF ou crie uma nova conta.");
            return false;
        }
    }
    
    private boolean criarNovaConta() {
        view.mostrarMensagem("\n--- CRIAR NOVA CONTA ---");
        
        view.mostrarMensagem("Digite seu nome completo: ");
        String nome = scanner.nextLine().trim();
        
        if (nome.isEmpty()) {
            view.mostrarMensagem("Nome não pode estar vazio!");
            return false;
        }
        
        view.mostrarMensagem("Digite seu CPF (apenas números): ");
        String cpf = scanner.nextLine().trim();
        
        if (cpf.isEmpty()) {
            view.mostrarMensagem("CPF não pode estar vazio!");
            return false;
        }
        
        if (usuarios.containsKey(cpf)) {
            view.mostrarMensagem("❌ CPF já cadastrado no sistema!");
            view.mostrarMensagem("Use a opção de login ou utilize outro CPF.");
            return false;
        }
        
        view.mostrarMensagem("Digite o número da sua conta bancária: ");
        String numeroConta = scanner.nextLine().trim();
        
        view.mostrarMensagem("Digite o nome da sua agência: ");
        String agencia = scanner.nextLine().trim();
        
        double saldoInicial = obterSaldoInicial();
        
        // Criar novo usuário
        Usuario novoUsuario = new Usuario(nome, cpf, numeroConta, agencia, saldoInicial);
        usuarios.put(cpf, novoUsuario);
        
        // Fazer login automático
        usuarioLogado = novoUsuario;
        roleta = new Roleta(usuarioLogado);
        cacaNiqueis = new CacaNiqueis(usuarioLogado);
        crash = new Crash(usuarioLogado);
        
        view.mostrarMensagem("=".repeat(50));
        view.mostrarMensagem("✅ Conta criada com sucesso!");
        view.mostrarMensagem("Bem-vindo ao Casino Unificado, " + nome + "!");
        view.mostrarMensagem("Seu saldo inicial é: R$" + String.format("%.2f", saldoInicial));
        view.mostrarMensagem("=".repeat(50));
        
        return true;
    }
    
    private double obterSaldoInicial() {
        while (true) {
            view.mostrarMensagem("Digite seu saldo inicial (mínimo R$1.00): R$");
            try {
                double saldo = Double.parseDouble(scanner.nextLine().trim());
                if (saldo < 1.00) {
                    view.mostrarMensagem("⚠️  Saldo mínimo é R$1.00");
                    continue;
                }
                return saldo;
            } catch (NumberFormatException e) {
                view.mostrarMensagem("❌ Valor inválido! Digite apenas números.");
            }
        }
    }
    
    private boolean executarSessaoCasino() throws InterruptedException {
        while (usuarioLogado != null && usuarioLogado.getSaldo() >= 0.10) {
            view.mostrarStatus(usuarioLogado);
            int opcao = view.mostrarMenuPrincipal();
            
            switch (opcao) {
                case 1:
                    jogarRoleta();
                    break;
                case 2:
                    jogarCacaNiqueis();
                    break;
                case 3:
                    jogarCrash();
                    break;
                case 4:
                    // Sair/Logout
                    fazerLogout();
                    return true; // Retorna para o menu de login
                case 5:
                    realizarDeposito();
                    break;
                case 6:
                    realizarSaque();
                    break;
                case 7:
                    mostrarMenuHistorico();
                    break;
                case 8:
                    view.mostrarEstatisticas(usuarioLogado);
                    break;
                default:
                    view.mostrarMensagem("Opção inválida!");
            }
        }
        
        if (usuarioLogado != null && usuarioLogado.getSaldo() < 0.10) {
            view.mostrarMensagem("\n⚠️  Saldo insuficiente para continuar jogando!");
            view.mostrarMensagem("Saldo mínimo necessário: R$0.10");
            view.mostrarMensagem("Faça um depósito ou entre em contato conosco.");
            return false; // Forçar logout
        }
        
        return true;
    }
    
    private void fazerLogout() {
        if (usuarioLogado != null) {
            // Salvar estatísticas finais no arquivo de log
            usuarioLogado.getLogManager().salvarEstatisticasNoArquivo();
            
            view.mostrarMensagem("\n" + "=".repeat(50));
            view.mostrarMensagem("Logout realizado com sucesso!");
            view.mostrarMensagem("Até logo, " + usuarioLogado.getNome() + "!");
            view.mostrarMensagem("Saldo final: R$" + String.format("%.2f", usuarioLogado.getSaldo()));
            view.mostrarMensagem("Seus dados foram salvos no arquivo: " + 
                                usuarioLogado.getLogManager().getNomeArquivo());
            view.mostrarMensagem("=".repeat(50));
            
            usuarioLogado = null;
            roleta = null;
            cacaNiqueis = null;
            crash = null;
        }
    }
    
    private void realizarDeposito() {
        double deposito = view.obterDeposito();
        if (deposito > 0) {
            usuarioLogado.adicionarSaldo(deposito);
            view.mostrarMensagem("✅ Depósito de R$" + String.format("%.2f", deposito) + " realizado com sucesso!");
        } else {
            view.mostrarMensagem("❌ Valor de depósito inválido.");
        }
    }
    
    private void realizarSaque() {
        double saque = view.obterSaque(usuarioLogado.getSaldo());
        if (saque > 0) {
            usuarioLogado.retirarSaldo(saque);
            view.mostrarMensagem("✅ Saque de R$" + String.format("%.2f", saque) + " realizado com sucesso!");
        } else {
            view.mostrarMensagem("❌ Valor de saque inválido ou cancelado.");
        }
    }
    
    private void mostrarMenuHistorico() {
        boolean continuar = true;
        while (continuar) {
            int opcao = view.mostrarMenuHistorico();
            
            switch (opcao) {
                case 1:
                    view.mostrarHistoricoFiltrado(usuarioLogado, "TODOS");
                    break;
                case 2:
                    view.mostrarHistoricoFiltrado(usuarioLogado, "GANHO");
                    break;
                case 3:
                    view.mostrarHistoricoFiltrado(usuarioLogado, "PERDA");
                    break;
                case 4:
                    view.mostrarHistoricoFiltrado(usuarioLogado, "DEPOSITO");
                    break;
                case 5:
                    view.mostrarHistoricoFiltrado(usuarioLogado, "SAQUE");
                    break;
                case 6:
                    continuar = false;
                    break;
                default:
                    view.mostrarMensagem("Opção inválida!");
            }
        }
    }
    
    private void jogarRoleta() throws InterruptedException {
        double aposta = view.obterAposta(usuarioLogado.getSaldo());
        if (aposta == -1) {
            view.mostrarMensagem("Aposta inválida!");
            return;
        }
        
        Object[] parametros = view.obterParametrosRoleta();
        if (parametros == null) {
            view.mostrarMensagem("Parâmetros inválidos!");
            return;
        }
        
        view.mostrarAnimacaoRoleta(roleta);
        ResultadoJogo resultado = roleta.jogar(aposta, parametros);
        view.mostrarResultado(resultado);
    }
    
    private void jogarCacaNiqueis() throws InterruptedException {
        double aposta = view.obterAposta(usuarioLogado.getSaldo());
        if (aposta == -1) {
            view.mostrarMensagem("Aposta inválida!");
            return;
        }
        
        view.mostrarAnimacaoCacaNiqueis();
        ResultadoJogo resultado = cacaNiqueis.jogar(aposta);
        view.mostrarResultado(resultado);
    }
    
    private void jogarCrash() {
        double aposta = view.obterAposta(usuarioLogado.getSaldo());
        if (aposta == -1) {
            view.mostrarMensagem("Aposta inválida!");
            return;
        }
        
        ResultadoJogo resultado = crash.jogar(aposta);
        view.mostrarResultado(resultado);
    }
}