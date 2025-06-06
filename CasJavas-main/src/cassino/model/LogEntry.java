package cassino.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {
    private LocalDateTime timestamp;
    private String tipo; // "GANHO", "PERDA", "DEPOSITO", "SAQUE"
    private String jogo; // "Roleta", "Caça-Níqueis", "Crash", "Sistema"
    private double valor;
    private String descricao;
    private double saldoAnterior;
    private double saldoAtual;
    
    public LogEntry(String tipo, String jogo, double valor, String descricao, 
                   double saldoAnterior, double saldoAtual) {
        this.timestamp = LocalDateTime.now();
        this.tipo = tipo;
        this.jogo = jogo;
        this.valor = valor;
        this.descricao = descricao;
        this.saldoAnterior = saldoAnterior;
        this.saldoAtual = saldoAtual;
    }
    
    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getTipo() { return tipo; }
    public String getJogo() { return jogo; }
    public double getValor() { return valor; }
    public String getDescricao() { return descricao; }
    public double getSaldoAnterior() { return saldoAnterior; }
    public double getSaldoAtual() { return saldoAtual; }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("[%s] %s - %s: R$%.2f (%s) | Saldo: R$%.2f -> R$%.2f",
            timestamp.format(formatter), tipo, jogo, Math.abs(valor), 
            descricao, saldoAnterior, saldoAtual);
    }
}
