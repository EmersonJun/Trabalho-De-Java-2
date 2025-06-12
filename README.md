# 🎰 Cassino Virtual em Java (MVC + OOP)

Envolvidos no projeto:
Emerson da Silveira Dutra Júnior RGM: 38765306

Taynara Piloneto Mafra RGM: 39470253
 
 João Carlos Fava Filho  RGM: 38517809

 Jaques de Oliveira Vasconcelos: RGM:38774372

 Kayo Henrique Batista Goncalves: RGM: 39186288


Bem-vindo ao **Cassino Virtual**!  
Este projeto em **Java** simula uma experiência de cassino no console, com suporte a jogos como **Roleta Colorida** e **Caça-Níqueis**, além de gerenciamento completo de usuários, transações e estatísticas.

---

## 🧩 Tecnologias Utilizadas

- ☕ Java 11+
- 🎮 Console/Terminal (modo texto)
- 📁 Arquitetura MVC
- 🧠 Programação Orientada a Objetos (POO)
- 💾 Registro de logs e estatísticas em arquivos `.txt`

---

## 🎯 Funcionalidades

- 👤 Cadastro e login de usuários com CPF e senha
- 💸 Apostas reais com controle de saldo
- 🎰 Jogos implementados:
  - **Roleta Colorida** (aposta por número ou cor)
  - **Caça-Níqueis** (com simulação animada)
- 🏦 Funções bancárias:
  - Depósito e saque com validação
- 📊 Histórico de transações:
  - Ganhos, perdas, apostas, depósitos e saques
- 📝 Estatísticas detalhadas de desempenho
- 📂 Logs persistentes por usuário (`logs_nomeUsuario.txt`)

---

## 📁 Estrutura do Projeto

cassino/
├── model/ → Lógica dos jogos, usuários e transações
├── view/ → Interface de console (CasinoView)
├── controller/ → Fluxo principal da aplicação
├── factory/ → Criação de jogos e usuários
└── Casino.java → Classe principal (main)


---

## 🔐 Segurança e Validações

- CPF com 11 dígitos numéricos
- Senha mínima de 4 caracteres
- Validação de saldo mínimo para apostas (≥ R$0,10)
- Limites de saque e depósitos seguros
- Logs imutáveis gravados em disco com histórico completo

---

## 💾 Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seunome/cassino-java.git
   
## 🧠 Aprendizados
Este projeto reforça conceitos como:

Estruturação em camadas (MVC)

Herança, encapsulamento, polimorfismo e abstração

Tratamento de erros e validação de entrada

Registro e persistência de dados locais

Organização e modularização de código em Java

## 📌 Observações
O sistema é executado inteiramente via console (sem interface gráfica).

Os arquivos de log são salvos automaticamente após logout.

Ideal para fins didáticos e acadêmicos.
