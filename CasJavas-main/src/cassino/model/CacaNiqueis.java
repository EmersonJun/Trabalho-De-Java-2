package cassino.model;

public class CacaNiqueis extends Jogo {
    private static final String[] SIMBOLOS = {"7", "00", "4", "2", "X", "Z", "A", "5", "6"};
    
    public CacaNiqueis(Usuario usuario) {
        super(usuario);
    }
    
    @Override
    public String getNome() {
        return "Caça-Níqueis";
    }
    
    @Override
    public ResultadoJogo jogar(double aposta, ParametrosJogo parametros) {
        if (!validarAposta(aposta)) {
            return new ResultadoJogo(false, 0, "Aposta inválida!");
        }
        
        usuario.debitar(aposta);
        int[] rolos = {random.nextInt(SIMBOLOS.length), 
                       random.nextInt(SIMBOLOS.length), 
                       random.nextInt(SIMBOLOS.length)};
        
        String resultado = "[" + SIMBOLOS[rolos[0]] + "] [" + SIMBOLOS[rolos[1]] + "] [" + SIMBOLOS[rolos[2]] + "]";
        DadosCacaNiqueis dadosCaca = new DadosCacaNiqueis(resultado);
        
        if (rolos[0] == rolos[1] && rolos[1] == rolos[2]) {
            double ganho = aposta * 5.0;
            usuario.creditar(ganho);
            return new ResultadoJogo(true, ganho, "JACKPOT! 3 símbolos iguais!", dadosCaca);
        } else if (rolos[0] == rolos[1] || rolos[1] == rolos[2] || rolos[0] == rolos[2]) {
            double ganho = aposta * 2.0;
            usuario.creditar(ganho);
            return new ResultadoJogo(true, ganho, "2 símbolos iguais!", dadosCaca);
        }
        
        return new ResultadoJogo(false, 0, "Nenhuma combinação.", dadosCaca);
    }
}