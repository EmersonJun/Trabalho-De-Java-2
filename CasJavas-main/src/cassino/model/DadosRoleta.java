package cassino.model;

public class DadosRoleta extends DadosJogo {
    private int numeroSorteado;
    
    public DadosRoleta(int numeroSorteado) {
        this.numeroSorteado = numeroSorteado;
    }
    
    public int getNumeroSorteado() { return numeroSorteado; }
}
