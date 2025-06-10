package cassino.model;

public class ResultadoJogo {
    private boolean ganhou;
    private double valorGanho;
    private String mensagem;
    private DadosExtras dadosExtras;
    private DadosJogo dadosJogo; 
    
    public ResultadoJogo(boolean ganhou, double valorGanho, String mensagem) {
        this.ganhou = ganhou;
        this.valorGanho = valorGanho;
        this.mensagem = mensagem;
        this.dadosExtras = null;
        this.dadosJogo = null;
    }
    
    public ResultadoJogo(boolean ganhou, double valorGanho, String mensagem, DadosExtras dadosExtras) {
        this(ganhou, valorGanho, mensagem);
        this.dadosExtras = dadosExtras;
    }
    
    public ResultadoJogo(boolean ganhou, double valorGanho, String mensagem, DadosJogo dadosJogo) {
        this(ganhou, valorGanho, mensagem);
        this.dadosJogo = dadosJogo;
    }
    
    public boolean isGanhou() { return ganhou; }
    public double getValorGanho() { return valorGanho; }
    public String getMensagem() { return mensagem; }
    public DadosExtras getDadosExtras() { return dadosExtras; }
    public DadosJogo getDadosJogo() { return dadosJogo; }
    
    public void setDadosExtras(DadosExtras dadosExtras) {
        this.dadosExtras = dadosExtras;
    }
    
    public void setDadosJogo(DadosJogo dadosJogo) {
        this.dadosJogo = dadosJogo;
    }
}