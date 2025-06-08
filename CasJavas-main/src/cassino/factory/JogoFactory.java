
package cassino.factory;

import cassino.model.*;

public class JogoFactory {
    public static Jogo criarJogo(String nome, Usuario usuario) {
        switch (nome.toLowerCase()) {
            case "roleta":
                return new Roleta(usuario);
            case "caca-niqueis":
                return new CacaNiqueis(usuario);
                 default:
                throw new IllegalArgumentException("Jogo desconhecido: " + nome);
        }
    }
}


