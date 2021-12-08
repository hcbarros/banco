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
    private List<String> extrato;
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
        this.extrato = new ArrayList<>();
    }


    public double saque(double valor, boolean ehTransferencia) {
        if((getSaldo() - valor) < 0) {
            System.out.println("Você não possui saldo suficiente!");
            return 0;
        }
        saldo -= valor;
        if(!ehTransferencia) {
            transacao(null, valor, true);
        }
        return valor;
    }

    public void deposito(double valor) {
        saldo += valor;
        transacao(null, valor, false);
    }

    public void saldo() {
        System.out.println("Saldo: R$ "+saldo);
    }

    public void extrato() {
        extrato.forEach(System.out::println);
    }

    public void transferir(Conta c, double valor) {
        double quantia = saque(valor, true);
        if(c != null && quantia > 0) {
            transacao(c, valor, true);
            c.deposito(valor);
        }
    }

    public void transacao(Conta c, double valor, boolean ehSaque) {
        String result = (ehSaque ? "Saque" : "Depósito") +" ocorrido em " + getData() +
                        "\nValor do "+(ehSaque ? "saque: " : "depósito: ")+ valor;
        if(c != null) {
            result = "\nTransferência ocorrida em " + getData() +
                    "Valor da transferência: R$ " + String.format("%.2f", valor) +
                    "Conta de origem: \n\t\t- agência ->" + agencia.name() +
                    "\n- conta -> " + conta +
                    "\n- nome -> " + nome +
                    "\n- CPF -> " + cpf +
                    "Conta de destino: \n\t\tagência ->" + c.agencia.name() +
                    "\n- conta -> " + c.conta +
                    "\n- nome -> " + c.nome +
                    "\n- CPF -> " + c.cpf;
        }
        extrato.add(result);
    }

    public String getData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
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
        return extrato;
    }

    public String formatarCFP(String cpf) {
        return cpf.substring(0,3)+"."+
                cpf.substring(3,6)+"."+
                cpf.substring(6,9)+"-"+cpf.substring(9,11);
    }

    public void validarCPF(String cpf) {
        cpf = cpf.replace("-","")
                 .replace(".","");
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
