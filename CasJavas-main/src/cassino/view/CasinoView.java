package cassino.view;

import cassino.model.*;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.Random;

public class CasinoView {
    private Scanner scanner;
    private DecimalFormat df;
    
    public CasinoView() {
        scanner = new Scanner(System.in);
        df = new DecimalFormat("0.00");
    }
    
    public void mostrarBoasVindas() {
        System.out.println("=".repeat(50));
        System.out.println("          CASINO UNIFICADO - POO/MVC");
        System.out.println("=".repeat(50));
    }
    
    public Usuario criarUsuario() {
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite seu saldo inicial (mínimo R$1.00): R$");
        
        try {
            double saldo = Double.parseDouble(scanner.nextLine());
            if (saldo < 1.00) {
                System.out.println("Saldo mínimo é R$1.00. Definindo saldo para R$100.00");
                saldo = 100.00;
            }
            return new Usuario(nome, saldo);
        } catch (Exception e) {
            System.out.println("Valor inválido! Definindo saldo padrão R$100.00");
            return new Usuario(nome, 100.00);
        }
    }
    
    public void mostrarStatus(Usuario usuario) {
        System.out.println("\nJogador: " + usuario.getNome());
        System.out.println("Saldo atual: R$" + df.format(usuario.getSaldo()));
    }
    
    public int mostrarMenuPrincipal() {
    System.out.println("\n" + "=".repeat(30));
    System.out.println("MENU PRINCIPAL");
    System.out.println("=".repeat(30));
    System.out.println("1. Roleta Colorida");
    System.out.println("2. Caça-Níqueis");
    System.out.println("3. Crash");
    System.out.println("4. Sair");
    System.out.println("5. Depositar saldo");
    System.out.println("6. Retirar saldo");
    System.out.print("Escolha uma opção: ");

    try {
        return Integer.parseInt(scanner.nextLine());
    } catch (Exception e) {
        return -1;
    }
}

    public double obterDeposito() {
    System.out.print("Digite o valor a depositar: R$");
    try {
        double valor = Double.parseDouble(scanner.nextLine());
        return (valor > 0) ? valor : 0;
    } catch (Exception e) {
        return 0;
    }
}

public double obterSaque(double saldoAtual) {
    System.out.print("Digite o valor a sacar (até R$" + df.format(saldoAtual) + "): R$");
    try {
        double valor = Double.parseDouble(scanner.nextLine());
        return (valor > 0 && valor <= saldoAtual) ? valor : 0;
    } catch (Exception e) {
        return 0;
    }
}

    public void adicionarSaldo(Usuario usuario) {
        System.out.print("Digite o valor a adicionar: R$");
        try {
            double valor = Double.parseDouble(scanner.nextLine());
            if (valor > 0) {
                usuario.adicionarSaldo(valor);
                System.out.println("Saldo atualizado: R$" + df.format(usuario.getSaldo()));
            } else {
                System.out.println("Valor inválido. Nenhum valor adicionado.");
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida.");
        }
    }

    public void retirarSaldo(Usuario usuario) {
        System.out.print("Digite o valor a retirar: R$");
        try {
            double valor = Double.parseDouble(scanner.nextLine());
            if (valor > 0) {
                usuario.retirarSaldo(valor);
                System.out.println("Saldo atualizado: R$" + df.format(usuario.getSaldo()));
            } else {
                System.out.println("Valor inválido. Nenhum valor retirado.");
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida.");
        }
    }
    
    public double obterAposta(double saldoMaximo) {
        System.out.print("Digite o valor da aposta (R$0.10 - R$" + df.format(saldoMaximo) + "): R$");
        try {
            double aposta = Double.parseDouble(scanner.nextLine());
            return (aposta >= 0.10 && aposta <= saldoMaximo) ? aposta : -1;
        } catch (Exception e) {
            return -1;
        }
    }

    // Resto da classe segue igual...

    public Object[] obterParametrosRoleta() {
        System.out.println("\nTipo de aposta:");
        System.out.println("1. Número específico (paga 35x)");
        System.out.println("2. Cor (paga 1.8x ou 35x para verde)");
        System.out.print("Escolha: ");
        
        try {
            int tipo = Integer.parseInt(scanner.nextLine());
            if (tipo == 1) {
                System.out.print("Digite o número (0-36): ");
                int numero = Integer.parseInt(scanner.nextLine());
                if (numero >= 0 && numero <= 36) {
                    return new Object[]{"numero", numero};
                }
            } else if (tipo == 2) {
                System.out.println("Escolha a cor:");
                System.out.println("1. Vermelho");
                System.out.println("2. Preto"); 
                System.out.println("3. Verde (0)");
                System.out.print("Opção: ");
                int cor = Integer.parseInt(scanner.nextLine());
                String[] cores = {"", "VERMELHO", "PRETO", "VERDE"};
                if (cor >= 1 && cor <= 3) {
                    return new Object[]{"cor", cores[cor]};
                }
            }
        } catch (Exception e) {}
        
        return null;
    }
    
    public void mostrarAnimacaoRoleta(Roleta roleta) throws InterruptedException {
        System.out.println("\nGirando a roleta...");
        Random random = new Random();
        
        for (int i = 0; i < 15; i++) {
            int temp = random.nextInt(37);
            System.out.print("\r[      " + roleta.formatarNumero(temp) + "      ]");
            Thread.sleep(100 + i * 10);
        }
        System.out.println();
    }
    
    public void mostrarAnimacaoCacaNiqueis() throws InterruptedException {
        System.out.println("\nGirando os rolos...");
        String[] simbolos = {"7", "$", "#", "X", "O", "♥", "♦", "♣", "♠"};
        Random random = new Random();
        
        for (int giro = 0; giro < 10; giro++) {
            System.out.print("\r");
            for (int i = 0; i < 3; i++) {
                String simbolo = simbolos[random.nextInt(simbolos.length)];
                System.out.print("[" + simbolo + "] ");
            }
            Thread.sleep(200);
        }
        System.out.println();
    }
    
    public void mostrarResultado(ResultadoJogo resultado) {
        System.out.println("\n" + "=".repeat(20));
        System.out.println("RESULTADO");
        System.out.println("=".repeat(20));
        
        if (resultado.getDadosExtras() != null) {
            if (resultado.getDadosExtras() instanceof Integer) {
                Roleta roleta = new Roleta(null);
                int numero = (Integer) resultado.getDadosExtras();
                System.out.println("Número sorteado: " + roleta.formatarNumero(numero));
            } else if (resultado.getDadosExtras() instanceof String) {
                System.out.println("Resultado: " + resultado.getDadosExtras());
            }
        }
        
        System.out.println(resultado.getMensagem());
        if (resultado.isGanhou()) {
            System.out.println("Ganho: +R$" + df.format(resultado.getValorGanho()));
        }
    }
    
    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }
    
    public void mostrarDespedida(Usuario usuario) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Obrigado por jogar, " + usuario.getNome() + "!");
        System.out.println("Saldo final: R$" + df.format(usuario.getSaldo()));
        System.out.println("=".repeat(40));
    }
}
