package cassino.factory;

import cassino.model.*;

public class JogoFactory {
    public static Jogo criarJogo(String nome, Usuario usuario) {
        String nomeFormatado = nome.toLowerCase();

        if (nomeFormatado.equals("roleta")) {
            return new Roleta(usuario);
        }

        if (nomeFormatado.equals("caca-niqueis")) {
            return new CacaNiqueis(usuario);
        }

        throw new IllegalArgumentException("Jogo desconhecido: " + nome);
    }
}
