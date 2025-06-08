package cassino.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transacao {
    private LocalDateTime dataHora;
    private TipoTransacao tipo;
    private double valor;
    private double saldoAnterior;
    private double saldoAtual;
    private String descricao;
    
    public Transacao(TipoTransacao tipo, double valor, double saldoAnterior, double saldoAtual, String descricao) {
        this.dataHora = LocalDateTime.now();
        this.tipo = tipo;
        this.valor = valor;
        this.saldoAnterior = saldoAnterior;
        this.saldoAtual = saldoAtual;
        this.descricao = descricao;
    }
    
    public LocalDateTime getDataHora() { return dataHora; }
    public TipoTransacao getTipo() { return tipo; }
    public double getValor() { return valor; }
    public double getSaldoAnterior() { return saldoAnterior; }
    public double getSaldoAtual() { return saldoAtual; }
    public String getDescricao() { return descricao; }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("[%s] %s - R$ %.2f | Saldo: R$ %.2f → R$ %.2f | %s",
                dataHora.format(formatter),
                tipo.getDescricao(),
                valor,
                saldoAnterior,
                saldoAtual,
                descricao);
    }
}