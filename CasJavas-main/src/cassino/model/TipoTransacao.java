package cassino.model;

public enum TipoTransacao {
    DEPOSITO("Deposito"),
    RETIRADA("Retirada"),
    APOSTA("Aposta"),
    GANHO("Ganho"),
    BONUS("Bonus");
    
    private final String descricao;
    
    TipoTransacao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}