package cassino.model;

public class ParametrosRoleta  implements ParametrosJogo{
    private String tipoAposta;
    private int numeroEscolhido;
    private String corEscolhida;
    
    public ParametrosRoleta(String tipoAposta, int numeroEscolhido) {
        this.tipoAposta = tipoAposta;
        this.numeroEscolhido = numeroEscolhido;
    }
    
    public ParametrosRoleta(String tipoAposta, String corEscolhida) {
        this.tipoAposta = tipoAposta;
        this.corEscolhida = corEscolhida;
    }
    
    public String getTipoAposta() { return tipoAposta; }
    public int getNumeroEscolhido() { return numeroEscolhido; }
    public String getCorEscolhida() { return corEscolhida; }
}
