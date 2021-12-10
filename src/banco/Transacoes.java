package banco;

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
        System.out.println("Saque realizado com sucesso!");
    }

    public void deposito(double valor) {
        String result = "\nDepósito ocorrido em " + getData() +
                        "\nValor do depósito: R$ "+ valor+
                        "\nNúmero da conta: "+ conta.getConta() +
                        "\nAgência: "+ conta.getAgencia().name();
        add(result);
        System.out.println("Depósito realizado com sucesso!");
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
        System.out.println("Transferência realizada com sucesso!");
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
