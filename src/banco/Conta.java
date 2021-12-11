package banco;

import java.lang.reflect.Method;
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


    public Conta(String nome, double rendaMensal, Agencia agencia) {
        this.nome = nome;
        this.rendaMensal = rendaMensal;
        this.agencia = agencia;
        this.saldo = 0.0;
    }

    public Conta validarCPF(String cpf) {
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
        this.conta = ++sequencial;
        this.transacoes = new Transacoes(this);
        return this;
    }

    private double saque(double valor, boolean ehTransferencia) {
        if(valor <= 0) {
            System.out.println("Informe um valor positivo!");
            return 0;
        }
        if((saldo - valor) <= 0) {
            try {
                Method m = this.getClass().getMethod("getLimite");
                double limite = (double) m.invoke(this);
                if((saldo + limite) - valor < 0) {
                    System.out.println("Você não possui saldo suficiente!");
                    return 0;
                }
            }catch(Exception e) {
                System.out.println("Você não possui saldo suficiente!");
                return 0;
            }
        }
        saldo -= valor;
        if(!ehTransferencia) {
            transacoes.saque(valor);
        }
        return valor;
    }

    public double saque(double valor) {
        return saque(valor, false);
    }

    private void deposito(double valor, boolean ehTransferencia) {
        if(valor <= 0) {
            System.out.println("Informe um valor positivo!");
            return;
        }
        saldo += valor;
        if(!ehTransferencia) {
            transacoes.deposito(valor);
        }
    }

    public void deposito(double valor) {
        deposito(valor, false);
    }

    public void saldo() {
        System.out.println("Saldo: R$ "+saldo);
    }

    public void extrato() {
        if(transacoes.isEmpty()) {
            System.out.println("A conta de número "+conta+" ainda não possui movimentação!");
            return;
        }
        transacoes.forEach(System.out::println);
    }

    public void transferir(Conta c, double valor) {
        if(valor <= 0) {
            System.out.println("Informe um valor positivo!");
            return;
        }
        if(c == null) {
            return;
        }
        double quantia = saque(valor, true);
        if(c != null && quantia > 0) {
            transacoes.transferencia(c, valor);
            c.deposito(valor,true);
        }
    }

    public void alterarDadosCadastrais(String nome, double rendaMensal) {
        this.nome = nome;
        this.rendaMensal = rendaMensal;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public double getRendaMensal() {
        return rendaMensal;
    }

    public int getConta() {
        return conta;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public Transacoes getTransacoes() {
        return transacoes;
    }

    public String toString() {
        return "\nTipo da conta: "+getClass().getSimpleName() +
                "\nTitular: "+ getNome() +
                "\nAgência: "+ getAgencia() +
                "\nNúmero da conta: "+ getConta() +
                "\nSaldo: "+ getSaldo();
    }
}
