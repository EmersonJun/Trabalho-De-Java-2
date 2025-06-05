package cassino.model;

public class ResultadoJogo {
    private boolean ganhou;
    private double valorGanho;
    private String mensagem;
    private Object dadosExtras;
    
    public ResultadoJogo(boolean ganhou, double valorGanho, String mensagem) {
        this.ganhou = ganhou;
        this.valorGanho = valorGanho;
        this.mensagem = mensagem;
    }
    
    public ResultadoJogo(boolean ganhou, double valorGanho, String mensagem, Object dadosExtras) {
        this(ganhou, valorGanho, mensagem);
        this.dadosExtras = dadosExtras;
    }
    
    // Getters
    public boolean isGanhou() { return ganhou; }
    public double getValorGanho() { return valorGanho; }
    public String getMensagem() { return mensagem; }
    public Object getDadosExtras() { return dadosExtras; }
}