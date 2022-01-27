package br.com.banco;

import br.com.banco.tipos_conta.Conta;
import br.com.banco.utils.FileHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Transacoes extends ArrayList<String> {

    private Conta conta;

    public Transacoes(Conta conta) {
        this.conta = conta;
    }


    public void saque(double valor) {
        String result = "\nSaque ocorrido em " + getData() +
                        "\nValor do saque: R$ "+ valor +
                        "\nNúmero da conta: "+ conta.getConta() +
                        "\nAgência: "+ conta.getAgencia().name();
        add(result);
        FileHandler.gravarTransacoes(conta, result);
        System.out.println("\nSaque realizado com sucesso!");
    }

    public void deposito(double valor) {
        String result = "\nDepósito ocorrido em " + getData() +
                        "\nValor do depósito: R$ "+ valor+
                        "\nNúmero da conta: "+ conta.getConta() +
                        "\nAgência: "+ conta.getAgencia().name();
        add(result);
        FileHandler.gravarTransacoes(conta, result);
        System.out.println("\nDepósito realizado com sucesso!");
    }

    public void depositoOutraConta(Conta origem, double valor) {
        String result = "\nDepósito ocorrido em " + getData() +
                "\nValor do depósito: R$ " + String.format("%.2f", valor) +
                "\nConta de origem: \n\t\t- agência -> " + origem.getAgencia().name() +
                "\n\t\t- conta -> " + origem.getConta() +
                "\n\t\t- nome do titular -> " + origem.getNome() +
                "\n\t\t- CPF -> " + formatarCFP(origem.getCpf()) +
                "\nConta de destino: \n\t\t- agência -> " + conta.getAgencia().name() +
                "\n\t\t- conta -> " + conta.getConta() +
                "\n\t\t- nome do titular -> " + conta.getNome() +
                "\n\t\t- CPF -> " + formatarCFP(conta.getCpf());

        FileHandler.gravarTransacoes(conta, result);
        add(result);
    }

    public void transferencia(Conta dest, double valor) {
        String result = "\nTransferência ocorrida em " + getData() +
                "\nValor da transferência: R$ " + String.format("%.2f", valor) +
                "\nConta de origem: \n\t\t- agência -> " + conta.getAgencia().name() +
                "\n\t\t- conta -> " + conta.getConta() +
                "\n\t\t- nome do titular -> " + conta.getNome() +
                "\n\t\t- CPF -> " + formatarCFP(conta.getCpf()) +
                "\nConta de destino: \n\t\t- agência -> " + dest.getAgencia().name() +
                "\n\t\t- conta -> " + dest.getConta() +
                "\n\t\t- nome do titular -> " + dest.getNome() +
                "\n\t\t- CPF -> " + formatarCFP(dest.getCpf());

        add(result);
        FileHandler.gravarTransacoes(conta, result);
        System.out.println("\nTransferência realizada com sucesso!");
    }

    private String getData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    private String formatarCFP(String cpf) {
        return cpf.substring(0,3)+"."+
                cpf.substring(3,6)+"."+
                cpf.substring(6,9)+"-"+cpf.substring(9,11);
    }

}
