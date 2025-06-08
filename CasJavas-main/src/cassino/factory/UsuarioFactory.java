package cassino.factory;

import cassino.model.*;
import java.util.HashMap;
import java.util.Map;

public class UsuarioFactory {
    private static Map<String, Usuario> usuariosCriados = new HashMap<>();
    

    public static Usuario criarUsuario(String nome, String cpf, String numeroConta, 
                                     String agencia, double saldo, String senha) {

        if (!validarParametros(nome, cpf, numeroConta, agencia, saldo, senha)) {
            return null;
        }
        
        if (usuariosCriados.containsKey(cpf)) {
            System.out.println("Usuário com CPF " + cpf + " já existe!");
            return null;
        }
        
        nome = nome.trim();
        cpf = cpf.trim();
        numeroConta = numeroConta.trim();
        agencia = agencia.trim();
        senha = senha.trim();
        
        if (saldo < 1.00) {
            saldo = 100.00;
            System.out.println("Saldo ajustado para o mínimo de R$100.00");
        }
        
        Usuario novoUsuario = new Usuario(nome, cpf, numeroConta, agencia, saldo, senha);
        
        usuariosCriados.put(cpf, novoUsuario);
        
        System.out.println("Usuário criado com sucesso: " + nome);
        return novoUsuario;
    }
    
    public static Usuario criarUsuario(String nome, String cpf, String numeroConta, 
                                     String agencia, String senha) {
        return criarUsuario(nome, cpf, numeroConta, agencia, 100.00, senha);
    }
    
    public static Usuario criarUsuarioDemo(String nome, String cpf) {
        return criarUsuario(nome, cpf, "12345-6", "Agencia Demo", 1000.00, "123456");
    }
    
    public static Usuario criarUsuarioDemo(String nome, String cpf, String senha) {
        return criarUsuario(nome, cpf, "12345-6", "Agencia Demo", 1000.00, senha);
    }
    
    public static Usuario obterUsuario(String cpf) {
        return usuariosCriados.get(cpf);
    }
    
    public static boolean usuarioExiste(String cpf) {
        return usuariosCriados.containsKey(cpf);
    }
    
    public static Usuario autenticarUsuario(String cpf, String senha) {
        Usuario usuario = usuariosCriados.get(cpf);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }
        return null;
    }
    
    public static boolean removerUsuario(String cpf) {
        if (usuariosCriados.containsKey(cpf)) {
            usuariosCriados.remove(cpf);
            return true;
        }
        return false;
    }
    
    public static String[] obterTodosCPFs() {
        return usuariosCriados.keySet().toArray(new String[0]);
    }
    
    public static int getTotalUsuarios() {
        return usuariosCriados.size();
    }
    
    public static void limparRegistros() {
        usuariosCriados.clear();
    }
    
    public static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }
        
        String cpfLimpo = cpf.trim().replaceAll("[^0-9]", "");
        return cpfLimpo.length() == 11 && cpfLimpo.matches("\\d{11}");
    }
    
    private static boolean validarParametros(String nome, String cpf, String numeroConta, 
                                           String agencia, double saldo, String senha) {
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Nome não pode estar vazio!");
            return false;
        }
        
        if (cpf == null || cpf.trim().isEmpty()) {
            System.out.println("CPF não pode estar vazio!");
            return false;
        }
        
        if (!validarCPF(cpf)) {
            System.out.println("CPF deve ter 11 dígitos!");
            return false;
        }
        
        if (numeroConta == null || numeroConta.trim().isEmpty()) {
            System.out.println("Número da conta não pode estar vazio!");
            return false;
        }
        
        if (agencia == null || agencia.trim().isEmpty()) {
            System.out.println("Agência não pode estar vazia!");
            return false;
        }
        
        if (senha == null || senha.trim().isEmpty()) {
            System.out.println("Senha não pode estar vazia!");
            return false;
        }
        
        if (senha.trim().length() < 4) {
            System.out.println("Senha deve ter pelo menos 4 caracteres!");
            return false;
        }
        
        if (saldo < 0) {
            System.out.println("Saldo não pode ser negativo!");
            return false;
        }
        
        return true;
    }
}