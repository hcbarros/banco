package banco;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta {

    private String nome;
    private String cpf;
    private double rendaMensal;
    private int conta;
    private Agencia agencia;
    private double saldo;
    private Transacoes transacoes;
    private static int sequencial;


    public Conta(String nome, String cpf,
                 double rendaMensal, Agencia agencia,
                 double saldo) {

        this.nome = nome;
        //validarCPF(cpf);
        this.rendaMensal = rendaMensal;
        this.conta = ++sequencial;
        this.agencia = agencia;
        this.saldo = saldo;
        this.transacoes = new Transacoes(this);
    }

    public Conta validarCPF(String cpf) {
        if (!cpf.matches("\\d{11}")) {
            System.out.println("O CPF deve conter apenas 11 dígitos!");
            return null;
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
                System.out.println("CPF inválido!");
                return null;
            }
        }
        this.cpf = cpf;
        return this;
    }


    public double saque(double valor) {
        if((getSaldo() - valor) <= 0) {
            System.out.println("Você não possui saldo suficiente!");
            return 0;
        }
        saldo -= valor;
        transacoes.saque(valor);
        return valor;
    }

    public void deposito(double valor) {
        saldo += valor;
        transacoes.deposito(valor);
    }

    public void saldo() {
        System.out.println("Saldo: R$ "+saldo);
    }

    public void extrato() {
        transacoes.forEach(System.out::println);
    }

    public void transferir(Conta c, double valor) {
        double quantia = saque(valor);
        if(c != null && quantia > 0) {
            transacoes.transferencia(c, valor);
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

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<String> getExtrato() {
        return transacoes;
    }

}
