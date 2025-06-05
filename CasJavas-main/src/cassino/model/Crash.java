package cassino.model;

public class Crash extends Jogo {
    public Crash(Usuario usuario) {
        super(usuario);
    }
    
    @Override
    public String getNome() {
        return "Crash";
    }
    
    @Override
    public ResultadoJogo jogar(double aposta, Object... parametros) {
        if (!validarAposta(aposta)) {
            return new ResultadoJogo(false, 0, "Aposta inválida!");
        }
        
        usuario.debitar(aposta);
        double crashPoint = 1.00 + random.nextDouble() * 9.99;
        
        // Simula o multiplicador de saída (para demonstração)
        double multiplicadorSaida = 1.0 + random.nextDouble() * 3.0;
        
        if (multiplicadorSaida < crashPoint) {
            double ganho = aposta * multiplicadorSaida;
            usuario.creditar(ganho);
            return new ResultadoJogo(true, ganho, 
                "Você sacou em " + String.format("%.2f", multiplicadorSaida) + "x", 
                new double[]{multiplicadorSaida, crashPoint});
        } else {
            return new ResultadoJogo(false, 0, 
                "CRASH! Multiplicador chegou a " + String.format("%.2f", crashPoint) + "x", 
                new double[]{multiplicadorSaida, crashPoint});
        }
    }
}