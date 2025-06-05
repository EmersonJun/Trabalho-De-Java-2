package cassino.controller;
import cassino.view.CasinoView;
import cassino.model.*;

public class CasinoController {
    private Usuario usuario;
    private CasinoView view;
    private Roleta roleta;
    private CacaNiqueis cacaNiqueis;
    private Crash crash;
    
    public CasinoController() {
        view = new CasinoView();
    }
    
    public void iniciar() throws InterruptedException {
        view.mostrarBoasVindas();
        usuario = view.criarUsuario();
        
        roleta = new Roleta(usuario);
        cacaNiqueis = new CacaNiqueis(usuario);
        crash = new Crash(usuario);
        
        while (usuario.getSaldo() >= 0.10) {
            view.mostrarStatus(usuario);
            int opcao = view.mostrarMenuPrincipal();
            
            switch (opcao) {
    case 1:
        jogarRoleta();
        break;
    case 2:
        jogarCacaNiqueis();
        break;
    case 3:
        jogarCrash();
        break;
    case 4:
        view.mostrarDespedida(usuario);
        return;
    case 5:
        double deposito = view.obterDeposito();
        usuario.adicionarSaldo(deposito);
        view.mostrarMensagem("Depósito realizado com sucesso!");
        break;
    case 6:
        double saque = view.obterSaque(usuario.getSaldo());
        if (saque > 0) {
            usuario.retirarSaldo(saque);
            view.mostrarMensagem("Saque realizado com sucesso!");
        } else {
            view.mostrarMensagem("Saque inválido ou cancelado.");
        }
        break;
    default:
        view.mostrarMensagem("Opção inválida!");
}

        
        view.mostrarMensagem("Saldo insuficiente para continuar!");
        view.mostrarDespedida(usuario);
    }
}
    
    private void jogarRoleta() throws InterruptedException {
        double aposta = view.obterAposta(usuario.getSaldo());
        if (aposta == -1) {
            view.mostrarMensagem("Aposta inválida!");
            return;
        }
        
        Object[] parametros = view.obterParametrosRoleta();
        if (parametros == null) {
            view.mostrarMensagem("Parâmetros inválidos!");
            return;
        }
        
        view.mostrarAnimacaoRoleta(roleta);
        ResultadoJogo resultado = roleta.jogar(aposta, parametros);
        view.mostrarResultado(resultado);
    }
    
    private void jogarCacaNiqueis() throws InterruptedException {
        double aposta = view.obterAposta(usuario.getSaldo());
        if (aposta == -1) {
            view.mostrarMensagem("Aposta inválida!");
            return;
        }
        
        view.mostrarAnimacaoCacaNiqueis();
        ResultadoJogo resultado = cacaNiqueis.jogar(aposta);
        view.mostrarResultado(resultado);
    }
    
    private void jogarCrash() {
        double aposta = view.obterAposta(usuario.getSaldo());
        if (aposta == -1) {
            view.mostrarMensagem("Aposta inválida!");
            return;
        }
        
        ResultadoJogo resultado = crash.jogar(aposta);
        view.mostrarResultado(resultado);
    }
}