package cassino.model;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

public class LogManager {
    private List<LogEntry> logs;
    private DecimalFormat df;
    private String nomeArquivo;
    
    public LogManager(String nomeUsuario) {
        this.logs = new ArrayList<>();
        this.df = new DecimalFormat("0.00");
        this.nomeArquivo = "logs_" + nomeUsuario.replaceAll("[^a-zA-Z0-9]", "_") + ".txt";
        criarArquivoSeNaoExistir();
    }
    
    private void criarArquivoSeNaoExistir() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo, true))) {
            java.io.File arquivo = new java.io.File(nomeArquivo);
            if (arquivo.length() == 0) {
                writer.println("=".repeat(80));
                writer.println("CASINO UNIFICADO - LOG DE TRANSAÇÕES");
                writer.println("Arquivo criado em: " + java.time.LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                writer.println("=".repeat(80));
                writer.println();
            }
        } catch (IOException e) {
        }
    }
    
    public void adicionarLog(String tipo, String jogo, double valor, String descricao, 
                           double saldoAnterior, double saldoAtual) {
        LogEntry entry = new LogEntry(tipo, jogo, valor, descricao, saldoAnterior, saldoAtual);
        logs.add(entry);
        salvarLogNoArquivo(entry);
    }
    
    private void salvarLogNoArquivo(LogEntry entry) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo, true))) {
            writer.println(entry.toString());
            writer.flush();
        } catch (IOException e) {
        }
    }
    
    public List<LogEntry> getTodosLogs() {
        return new ArrayList<>(logs);
    }
    
    public List<LogEntry> getLogsPorTipo(String tipo) {
        return logs.stream()
                  .filter(log -> log.getTipo().equals(tipo))
                  .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public double getTotalGanhos() {
        return logs.stream()
                  .filter(log -> log.getTipo().equals("GANHO"))
                  .mapToDouble(LogEntry::getValor)
                  .sum();
    }
    
    public double getTotalPerdas() {
        return logs.stream()
                  .filter(log -> log.getTipo().equals("PERDA"))
                  .mapToDouble(LogEntry::getValor)
                  .sum();
    }
    
    public double getTotalDepositos() {
        return logs.stream()
                  .filter(log -> log.getTipo().equals("DEPOSITO"))
                  .mapToDouble(LogEntry::getValor)
                  .sum();
    }
    
    public double getTotalSaques() {
        return logs.stream()
                  .filter(log -> log.getTipo().equals("SAQUE"))
                  .mapToDouble(LogEntry::getValor)
                  .sum();
    }
    
    public String getEstatisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(40)).append("\n");
        sb.append("ESTATÍSTICAS DO JOGADOR\n");
        sb.append("=".repeat(40)).append("\n");
        sb.append("Total de transações: ").append(logs.size()).append("\n");
        sb.append("Total ganho: R$").append(df.format(getTotalGanhos())).append("\n");
        sb.append("Total perdido: R$").append(df.format(getTotalPerdas())).append("\n");
        sb.append("Total depositado: R$").append(df.format(getTotalDepositos())).append("\n");
        sb.append("Total sacado: R$").append(df.format(getTotalSaques())).append("\n");
        
        double lucroLiquido = getTotalGanhos() - getTotalPerdas();
        sb.append("Lucro/Prejuízo líquido: R$").append(df.format(lucroLiquido));
        sb.append(lucroLiquido >= 0 ? " (LUCRO)" : " (PREJUÍZO)").append("\n");
        sb.append("=".repeat(40));
        
        return sb.toString();
    }
    
    public void salvarEstatisticasNoArquivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo, true))) {
            writer.println();
            writer.println("=".repeat(80));
            writer.println("ESTATÍSTICAS FINAIS - " + java.time.LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            writer.println("=".repeat(80));
            writer.println("Total de transações: " + logs.size());
            writer.println("Total ganho: R$" + df.format(getTotalGanhos()));
            writer.println("Total perdido: R$" + df.format(getTotalPerdas()));
            writer.println("Total depositado: R$" + df.format(getTotalDepositos()));
            writer.println("Total sacado: R$" + df.format(getTotalSaques()));
            
            double lucroLiquido = getTotalGanhos() - getTotalPerdas();
            writer.println("Lucro/Prejuízo líquido: R$" + df.format(lucroLiquido) + 
                          (lucroLiquido >= 0 ? " (LUCRO)" : " (PREJUÍZO)"));
            writer.println("=".repeat(80));
            writer.println();
        } catch (IOException e) {
        }
    }
    
    public String getNomeArquivo() {
        return nomeArquivo;
    }
}