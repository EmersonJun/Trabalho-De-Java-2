package cassino.model;

public class Usuario {
    private String nome;
    private String cpf;
    private String numeroConta;
    private String agencia;
    private double saldo;
    private String senha;
    private LogManager logManager;
    
    public Usuario(String nome, String cpf, String numeroConta, String agencia, double saldo, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.saldo = saldo;
        this.senha = senha;
        this.logManager = new LogManager(nome);
    }
    
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getNumeroConta() { return numeroConta; }
    public String getAgencia() { return agencia; }
    public double getSaldo() { return saldo; }
    public LogManager getLogManager() { return logManager; }
    public String getSenha() { return senha; }
    
    public boolean temSaldoSuficiente(double valor) {
        return saldo >= valor;
    }
    
    public boolean debitar(double valor) {
        if (temSaldoSuficiente(valor)) {
            double saldoAnterior = saldo;
            saldo -= valor;
            logManager.adicionarLog("PERDA", "Aposta", valor, 
                "Aposta realizada", saldoAnterior, saldo);
            return true;
        }
        return false;
    }
    
    public void creditar(double valor) {
        double saldoAnterior = saldo;
        saldo += valor;
        logManager.adicionarLog("GANHO", "Jogo", valor, 
            "Prêmio recebido", saldoAnterior, saldo);
    }
    
    public boolean adicionarSaldo(double valor) {
        if (valor > 0) {
            double saldoAnterior = saldo;
            saldo += valor;
            logManager.adicionarLog("DEPOSITO", "Sistema", valor, 
                "Depósito realizado", saldoAnterior, saldo);
            return true;
        }
        return false;
    }
    
    public boolean retirarSaldo(double valor) {
        if (valor > 0 && valor <= saldo) {
            double saldoAnterior = saldo;
            saldo -= valor;
            logManager.adicionarLog("SAQUE", "Sistema", valor, 
                "Saque realizado", saldoAnterior, saldo);
            return true;
        }
        return false;
    }
}