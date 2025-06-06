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
    
    public void imprimirHistorico() {
        System.out.println("=== HISTÓRICO DE TRANSAÇÕES ===");
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação encontrada.");
            return;
        }
        
        transacoes.forEach(System.out::println);
        
        System.out.println("\n=== RESUMO ===");
        System.out.printf("Total Depositado: R$ %.2f%n", getTotalDepositos());
        System.out.printf("Total Gasto: R$ %.2f%n", getTotalGastos());
        System.out.printf("Total Ganho: R$ %.2f%n", getTotalGanhos());
        System.out.printf("Saldo Liquido: R$ %.2f%n", getTotalDepositos() + getTotalGanhos() - getTotalGastos());
    }
}