package banco;

import java.io.Console;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleInfo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Transacoes extends ArrayList<String> {

    private Conta conta;

    public Transacoes(Conta conta) {
        this.conta = conta;

        
    }


    public void saque(double valor) {

        String result = "Saque ocorrido em " + getData() +
                        "\nValor do saque: R$ "+ valor;
        add(result);
    }

    public void deposito(double valor) {
        String result = "Depósito ocorrido em " + getData() +
                        "\nValor do depósito: R$ "+ valor;
        add(result);
    }

    public void transferencia(Conta dest, double valor) {
        String result = "\nTransferência ocorrida em " + getData() +
                "Valor da transferência: R$ " + String.format("%.2f", valor) +
                "Conta de origem: \n\t\t- agência ->" + conta.getAgencia().name() +
                "\n- conta -> " + conta.getConta() +
                "\n- nome -> " + conta.getNome() +
                "\n- CPF -> " + formatarCFP(conta.getCpf()) +
                "Conta de destino: \n\t\tagência ->" + dest.getAgencia().name() +
                "\n- conta -> " + dest.getConta() +
                "\n- nome -> " + dest.getNome() +
                "\n- CPF -> " + formatarCFP(dest.getCpf());

        add(result);
    }

    public String getData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    public String formatarCFP(String cpf) {
        return cpf.substring(0,3)+"."+
                cpf.substring(3,6)+"."+
                cpf.substring(6,9)+"-"+cpf.substring(9,11);
    }

    private void investimento() {

    }

}
