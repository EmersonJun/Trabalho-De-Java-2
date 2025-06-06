package cassino.model;

public class DadosExtras {
    private String tipoBonus;
    private int multiplicador;
    
    public DadosExtras(String tipoBonus, int multiplicador) {
        this.tipoBonus = tipoBonus;
        this.multiplicador = multiplicador;
    }

    public String getTipoBonus() {
        return tipoBonus;
    }

    public int getMultiplicador() {
        return multiplicador;
    }
}
