package cassino.model;

public class Usuario {
    private String nome;
    private double saldo;
    
    public Usuario(String nome, double saldoInicial) {
        this.nome = nome;
        this.saldo = saldoInicial;
    }
    
    public String getNome() { return nome; }
    public double getSaldo() { return saldo; }
    
    public boolean temSaldoSuficiente(double valor) {
        return saldo >= valor;
    }
    
    public void debitar(double valor) {
        if (temSaldoSuficiente(valor)) {
            saldo -= valor;
        }
    }
    
    public void creditar(double valor) {
        saldo += valor;
    }
    public void retirarSaldo(double valor) {
    if (valor > 0) {
        saldo -= valor;
    } else {
        System.out.println("Valor inválido para retirada.");
    }
}
public void adicionarSaldo(double valor) {
    if (valor > 0) {
        saldo += valor;
    } else {
        System.out.println("Valor inválido para adicionar saldo.");
    }
}

}
