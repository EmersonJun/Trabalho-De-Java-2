package cassino.model;

public class Roleta extends Jogo {
    private static final int[] NUMEROS_VERMELHOS = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    
    public Roleta(Usuario usuario) {
        super(usuario);
    }
    
    @Override
    public String getNome() {
        return "Roleta Colorida";
    }
    
    @Override
    public ResultadoJogo jogar(double aposta, Object... parametros) {
        if (!validarAposta(aposta)) {
            return new ResultadoJogo(false, 0, "Aposta inválida!");
        }
        
        String tipoAposta = (String) parametros[0];
        usuario.debitar(aposta);
        
        int numeroSorteado = random.nextInt(37);
        
        if ("numero".equals(tipoAposta)) {
            int numeroEscolhido = (Integer) parametros[1];
            if (numeroEscolhido == numeroSorteado) {
                double ganho = aposta * 35;
                usuario.creditar(ganho);
                return new ResultadoJogo(true, ganho, "PARABÉNS! Acertou o número!", numeroSorteado);
            }
        } else if ("cor".equals(tipoAposta)) {
            String corEscolhida = (String) parametros[1];
            String corSorteada = getCorNumero(numeroSorteado);
            if (corEscolhida.equals(corSorteada)) {
                double multiplicador = "VERDE".equals(corEscolhida) ? 35 : 1.8;
                double ganho = aposta * multiplicador;
                usuario.creditar(ganho);
                return new ResultadoJogo(true, ganho, "PARABÉNS! Acertou a cor!", numeroSorteado);
            }
        }
        
        return new ResultadoJogo(false, 0, "Não foi dessa vez!", numeroSorteado);
    }
    
    private String getCorNumero(int numero) {
        if (numero == 0) return "VERDE";
        for (int n : NUMEROS_VERMELHOS) {
            if (n == numero) return "VERMELHO";
        }
        return "PRETO";
    }
    
    public String formatarNumero(int numero) {
        String cor = getCorNumero(numero);
        String RESET = "\u001B[0m";
        String FUNDO_VERMELHO = "\u001B[41m";
        String FUNDO_VERDE = "\u001B[42m";
        String FUNDO_BRANCO = "\u001B[47m";
        String TEXTO_PRETO = "\u001B[30m";
        
        switch (cor) {
            case "VERMELHO":
                return FUNDO_VERMELHO + TEXTO_PRETO + String.format("%02d", numero) + RESET;
            case "PRETO":
                return FUNDO_BRANCO + TEXTO_PRETO + String.format("%02d", numero) + RESET;
            case "VERDE":
                return FUNDO_VERDE + TEXTO_PRETO + String.format("%02d", numero) + RESET;
            default:
                return String.format("%02d", numero);
        }
    }
}