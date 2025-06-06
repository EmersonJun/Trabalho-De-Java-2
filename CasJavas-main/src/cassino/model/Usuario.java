package cassino.model;

public class Usuario {
    private String nome;
    private String cpf;
    private String numeroConta;
    private String agencia;
    private double saldo;
    private LogManager logManager; // ADICIONAR ESTA LINHA
    
    public Usuario(String nome, String cpf, String numeroConta, String agencia, double saldo) {
        this.nome = nome;
        this.cpf = cpf;
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.saldo = saldo;
        this.logManager = new LogManager(nome); // PASSAR O NOME PARA O LOG MANAGER
    }
    
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getNumeroConta() { return numeroConta; }
    public String getAgencia() { return agencia; }
    public double getSaldo() { return saldo; }
    public LogManager getLogManager() { return logManager; } // GETTER PARA LOG MANAGER
    
    public boolean temSaldoSuficiente(double valor) {
        return saldo >= valor;
    }
    
    public void debitar(double valor) {
        if (temSaldoSuficiente(valor)) {
            double saldoAnterior = saldo;
            saldo -= valor;
            // Log da perda/aposta
            logManager.adicionarLog("PERDA", "Aposta", valor, 
                "Aposta realizada", saldoAnterior, saldo);
        }
    }
    
    public void creditar(double valor) {
        double saldoAnterior = saldo;
        saldo += valor;
        // Log do ganho
        logManager.adicionarLog("GANHO", "Jogo", valor, 
            "Prêmio recebido", saldoAnterior, saldo);
    }
    
    public void adicionarSaldo(double valor) {
        if (valor > 0) {
            double saldoAnterior = saldo;
            saldo += valor;
            // Log do depósito
            logManager.adicionarLog("DEPOSITO", "Sistema", valor, 
                "Depósito realizado", saldoAnterior, saldo);
        } else {
            System.out.println("Valor inválido para adicionar saldo.");
        }
    }
    
    public void retirarSaldo(double valor) {
        if (valor > 0 && valor <= saldo) {
            double saldoAnterior = saldo;
            saldo -= valor;
            // Log do saque
            logManager.adicionarLog("SAQUE", "Sistema", valor, 
                "Saque realizado", saldoAnterior, saldo);
        } else {
            System.out.println("Valor inválido para retirada.");
        }
    }
}