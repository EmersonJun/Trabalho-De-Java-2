package cassino.controller;

import cassino.view.CasinoView;
import cassino.factory.*;
import java.util.HashMap;
import java.util.Scanner;
import cassino.model.*;

public class CasinoController {
    private HashMap<String, Usuario> usuarios = new HashMap<>();
    private Usuario usuarioLogado;
    private CasinoView view;
    private Roleta roleta;
    private CacaNiqueis cacaNiqueis;
    private Scanner scanner;
    private UsuarioFactory user;
    public CasinoController() {
        view = new CasinoView();
        scanner = new Scanner(System.in);
    }

    private void preCarregarUsuarios() {
    Usuario demo1 = new Usuario("João Demo", "12345678901", "00123", "Agência A", 500.00, "senha1");
    Usuario demo2 = new Usuario("Maria Demo", "98765432100", "00456", "Agência B", 1000.00, "senha2");

    usuarios.put(demo1.getCpf(), demo1);
    usuarios.put(demo2.getCpf(), demo2);

    view.mostrarMensagem("Usuários de demonstração carregados:");
    view.mostrarMensagem("- João Demo / CPF: 12345678901 / Senha: senha1");
    view.mostrarMensagem("- Maria Demo / CPF: 98765432100 / Senha: senha2");
}

    
    public void iniciar() throws InterruptedException {
        view.mostrarBoasVindas();
        preCarregarUsuarios();
        while (true) {
            if (usuarioLogado == null) {
                if (!mostrarMenuLogin()) {
                    view.mostrarMensagem("Obrigado por visitar o Cassino!");
                    break;
                }
            } else {
                if (!executarSessaoCasino()) {
                    continue;
                }
                fazerLogout();
            }
        }
    }
    
    private boolean mostrarMenuLogin() {
        while (true) {
            view.mostrarMensagem("\n" + "=".repeat(40));
            view.mostrarMensagem("        CASSINO - LOGIN");
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
                        return true; 
                    }
                    break;
                case "2":
                    if (criarNovaConta()) {
                        return true; 
                    }
                    break;
                case "3":
                    return false; 
                default:
                    view.mostrarMensagem("Opção inválida! Tente novamente.");
            }
        }
    }
    
    private boolean fazerLogin() {
        view.mostrarMensagem("\n--- LOGIN ---");
        view.mostrarMensagem("Digite seu CPF (ou 'voltar' para retornar): ");
        String cpf = scanner.nextLine().trim();
        
        if (usuarios.containsKey(cpf)) {
            Usuario usuario = usuarios.get(cpf);

            view.mostrarMensagem("Digite sua senha: ");
            String senhaDigitada = scanner.nextLine().trim();

            if (!usuario.getSenha().equals(senhaDigitada)) {
                view.mostrarMensagem("Senha incorreta. Tente novamente.");
                return false;
            }
            usuarioLogado = usuario;

            roleta = (Roleta) JogoFactory.criarJogo("roleta", usuarioLogado);
            cacaNiqueis = (CacaNiqueis) JogoFactory.criarJogo("caca-niqueis", usuarioLogado);

            view.mostrarMensagem("=".repeat(50));
            view.mostrarMensagem("Login realizado com sucesso!");
            view.mostrarMensagem("Bem-vindo de volta, " + usuarioLogado.getNome() + "!");
            view.mostrarMensagem("=".repeat(50));
            return true;
        } else {
            view.mostrarMensagem("CPF não encontrado no sistema.");
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
        
        view.mostrarMensagem("Digite seu CPF: ");
        String cpf = scanner.nextLine().trim();
        
        if (cpf.isEmpty()) {
            view.mostrarMensagem("CPF não pode estar vazio!");
            return false;
        }
        
        view.mostrarMensagem("Digite uma senha para sua conta: ");
        String senha = scanner.nextLine().trim();

        if (senha.isEmpty()) {
            view.mostrarMensagem("senha não pode estar vazia!");
            return false;
        }
        
        if (usuarios.containsKey(cpf)) {
            view.mostrarMensagem("CPF já cadastrado no sistema!");
            view.mostrarMensagem("Use a opção de login ou utilize outro CPF.");
            return false;
        }
        
        view.mostrarMensagem("Digite o número da sua conta bancária: ");
        String numeroConta = scanner.nextLine().trim();
        
        view.mostrarMensagem("Digite o nome da sua agência: ");
        String agencia = scanner.nextLine().trim();
        
        double saldoInicial = obterSaldoInicial();
        
        Usuario novoUsuario = new Usuario(nome, cpf, numeroConta, agencia, saldoInicial, senha);
        usuarios.put(cpf, novoUsuario);
        
        usuarioLogado = novoUsuario;
        roleta = (Roleta) JogoFactory.criarJogo("roleta", usuarioLogado);
        cacaNiqueis = (CacaNiqueis) JogoFactory.criarJogo("caca-niqueis", usuarioLogado);

        view.mostrarMensagem("=".repeat(50));
        view.mostrarMensagem("Conta criada com sucesso!");
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
                    view.mostrarMensagem("Saldo mínimo é R$1.00");
                    continue;
                }
                return saldo;
            } catch (NumberFormatException e) {
                view.mostrarMensagem("Valor inválido! Digite apenas números.");
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
                    fazerLogout();
                    return true; 
                case 4:
                    realizarDeposito();
                    break;
                case 5:
                    realizarSaque();
                    break;
                case 6:
                    mostrarMenuHistorico();
                    break;
                case 7:
                    view.mostrarEstatisticas(usuarioLogado);
                    break;
                default:
                    view.mostrarMensagem("Opção inválida!");
            }
        }
        
        if (usuarioLogado != null && usuarioLogado.getSaldo() < 0.10) {
            view.mostrarMensagem("\nSaldo insuficiente para continuar jogando!");
            view.mostrarMensagem("Saldo mínimo necessário: R$0.10");
            view.mostrarMensagem("Faça um depósito ou entre em contato conosco.");
            usuarioLogado.adicionarSaldo(0.10);
            return false; 
        }
        
        return true;
    }
    
    private void fazerLogout() {
        if (usuarioLogado != null) {
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
        }
    }
    
    private void realizarDeposito() {
        double deposito = view.obterDeposito();
        if (deposito > 0) {
            usuarioLogado.adicionarSaldo(deposito);
            view.mostrarMensagem("Depósito de R$" + String.format("%.2f", deposito) + " realizado com sucesso!");
        } else {
            view.mostrarMensagem("Valor de depósito inválido.");
        }
    }
    
    private void realizarSaque() {
        double saque = view.obterSaque(usuarioLogado.getSaldo());
        if (saque > 0) {
            usuarioLogado.retirarSaldo(saque);
            view.mostrarMensagem("Saque de R$" + String.format("%.2f", saque) + " realizado com sucesso!");
        } else {
            view.mostrarMensagem("Valor de saque inválido ou cancelado.");
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
        
        ParametrosRoleta parametros = view.obterParametrosRoleta();
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
        ParametrosCacaNiqueis parametros = new ParametrosCacaNiqueis();
        ResultadoJogo resultado = cacaNiqueis.jogar(aposta, parametros);
        view.mostrarResultado(resultado);
    }
}