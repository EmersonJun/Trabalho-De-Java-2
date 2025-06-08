package cassino.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class HistoricoTransacoes {
    private List<Transacao> transacoes;
    
    public HistoricoTransacoes() {
        this.transacoes = new ArrayList<>();
    }
    
    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }
    
    public List<Transacao> getTodasTransacoes() {
        return new ArrayList<>(transacoes);
    }
    
    public List<Transacao> getTransacoesPorTipo(TipoTransacao tipo) {
        return transacoes.stream()
                .filter(t -> t.getTipo() == tipo)
                .collect(Collectors.toList());
    }
    
    public List<Transacao> getTransacoesPorData(LocalDate data) {
        return transacoes.stream()
                .filter(t -> t.getDataHora().toLocalDate().equals(data))
                .collect(Collectors.toList());
    }
    
    public double getTotalGastos() {
        return transacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.APOSTA || t.getTipo() == TipoTransacao.RETIRADA)
                .mapToDouble(Transacao::getValor)
                .sum();
    }
    
    public double getTotalGanhos() {
        return transacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.GANHO || t.getTipo() == TipoTransacao.BONUS)
                .mapToDouble(Transacao::getValor)
                .sum();
    }
    
    public double getTotalDepositos() {
        return transacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.DEPOSITO)
                .mapToDouble(Transacao::getValor)
                .sum();
    }
}