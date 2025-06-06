package cassino.model;

public class ResultadoJogo {
    private boolean ganhou;
    private double valorGanho;
    private String mensagem;
    private DadosExtras dadosExtras;
    private Object dadosJogo; // Campo adicional para dados específicos do jogo
    
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
    
    // Construtor adicional para dados específicos do jogo (números da roleta, símbolos, etc.)
    public ResultadoJogo(boolean ganhou, double valorGanho, String mensagem, Object dadosJogo) {
        this(ganhou, valorGanho, mensagem);
        this.dadosJogo = dadosJogo;
    }
    
    // Getters
    public boolean isGanhou() { 
        return ganhou; 
    }
    
    public double getValorGanho() { 
        return valorGanho; 
    }
    
    public String getMensagem() { 
        return mensagem; 
    }
    
    public DadosExtras getDadosExtras() {
        return dadosExtras;
    }
    
    public Object getDadosJogo() {
        return dadosJogo;
    }
    
    // Setters (caso necessário)
    public void setDadosExtras(DadosExtras dadosExtras) {
        this.dadosExtras = dadosExtras;
    }
    
    public void setDadosJogo(Object dadosJogo) {
        this.dadosJogo = dadosJogo;
    }
}