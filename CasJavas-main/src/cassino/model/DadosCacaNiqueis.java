package cassino.model;

public class DadosCacaNiqueis extends DadosJogo {
    private String resultado;
    
    public DadosCacaNiqueis(String resultado) {
        this.resultado = resultado;
    }
    
    public String getResultado() { return resultado; }
}