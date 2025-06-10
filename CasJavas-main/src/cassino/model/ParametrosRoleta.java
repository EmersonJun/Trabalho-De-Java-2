package cassino.model;

public class ParametrosRoleta extends ParametrosJogo {
    private String tipoAposta;
    private int numeroEscolhido;
    private String corEscolhida;
    
    // Construtor para aposta por número
    public ParametrosRoleta(String tipoAposta, int numeroEscolhido) {
        this.tipoAposta = tipoAposta;
        this.numeroEscolhido = numeroEscolhido;
    }
    
    // Construtor para aposta por cor
    public ParametrosRoleta(String tipoAposta, String corEscolhida) {
        this.tipoAposta = tipoAposta;
        this.corEscolhida = corEscolhida;
    }
    
    public String getTipoAposta() { return tipoAposta; }
    public int getNumeroEscolhido() { return numeroEscolhido; }
    public String getCorEscolhida() { return corEscolhida; }
}
