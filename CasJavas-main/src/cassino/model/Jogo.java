package cassino.model;
import java.util.Random;

public abstract class Jogo {
    protected Usuario usuario;
    protected Random random;
    
    public Jogo(Usuario usuario) {
        this.usuario = usuario;
        this.random = new Random();
    }
    
    public abstract ResultadoJogo jogar(double aposta, Object... parametros);
    public abstract String getNome();
    
    protected boolean validarAposta(double aposta) {
        return aposta >= 0.10 && usuario.temSaldoSuficiente(aposta);
    }
}