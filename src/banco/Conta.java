package banco;

public class Conta {

    private String nome;
    private String cpf;
    private double rendaMensal;
    private int conta;
    private Agencia agencia;
    private double saldo;
    private static int sequencial;

    public Conta() { }

    public Conta(String nome, String cpf,
                 double rendaMensal, Agencia agencia,
                 double saldo) {

        this.nome = nome;
        validarCPF(cpf);
        this.rendaMensal = rendaMensal;
        this.conta = ++sequencial;
        this.agencia = agencia;
        this.saldo = saldo;
    }


    public void saque(double valor) {
        saldo -= valor;
    }

    public void deposito(double valor) {
        saldo += valor;
    }

    public void saldo() {
        System.out.println("Saldo: R$ "+saldo);
    }

    public void extrato() {

    }

    public void transferir(Conta c, double valor) {
        saque(valor);
        if(c != null) {
            c.deposito(valor);
        }
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public static int getSequencial() {
        return sequencial;
    }

    public static void setSequencial(int sequencial) {
        Conta.sequencial = sequencial;
    }

    public void validarCPF(String cpf) {
        if (!cpf.matches("\\d{11}")) {
            throw new RuntimeException("O CPF deve conter apenas 11 dígitos!");
        }
        for(int i = 9; i < 11; i++) {
            int soma = 0;
            int desc = i + 1;
            for (int j = 0; j < i; j++) {
                soma += (cpf.charAt(j) - '0') * (desc--);
            }
            int result = (soma % 11) < 2 ? 0 : (11 - (soma % 11));
            int digito = cpf.charAt(i) - '0';
            if (digito != result) {
                throw new RuntimeException("CPF inválido!");
            }
        }
        this.cpf = cpf;
    }



}
