package cassino.model;

import java.util.Random;

public abstract class Jogo {
    protected Usuario usuario;
    protected Random random;
    
    public Jogo(Usuario usuario) {
        this.usuario = usuario;
        this.random = new Random();
    }
    
    public abstract String getNome();
    public abstract ResultadoJogo jogar(double aposta, ParametrosJogo parametros);
    
    protected boolean validarAposta(double aposta) {
        return aposta > 0 && aposta <= usuario.getSaldo();
    }
}