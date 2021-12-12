package br.com.banco.utils;

import br.com.banco.tipos_conta.Conta;
import br.com.banco.tipos_conta.ContaCorrente;

import java.util.Scanner;
import java.util.List;

public class Acesso {

    private static Scanner scanner = new Scanner(System.in);


    public static String acessarContas(List<Conta> contas, String conta) {

        if (conta == null) {
            System.out.print("Informe o número da conta ou digite 'sair' para retornar ao menu anterior: ");
            conta = scanner.nextLine();
            if (conta.equalsIgnoreCase("sair")) {
                return "";
            }
            if (!conta.matches("\\d{1,10}")) {
                System.out.println("Opsção inválida!");
                conta = null;
            }
            return acessarContas(contas, conta);
        } else {
            final int num = Integer.parseInt(conta);
            Conta c = contas.stream().filter(co -> co.getConta() == num)
                    .findFirst().orElse(null);
            if (c == null) {
                System.out.println("Conta inexistente!");
                return acessarContas(contas, null);
            }
            String invest = !(c.getClass().equals(ContaCorrente.class)) ? "\n8 - Simular investimento" : "";
            System.out.println("\n1 - Saque\n2 - Depósito\n3 - Tranferência\n4 - Alterar dados cadastrais" +
                    "\n5 - Exibir saldo\n6 - Extrato\n7 - Retornar ao menu anterior" + invest + "\n9 - Encerrar sessão");

            String opcao = scanner.nextLine();
            switch (opcao.hashCode()) {
                case 49:
                    Operacoes.operar(contas, c, 1, null);
                    return acessarContas(contas, conta);
                case 50:
                    Operacoes.operar(contas, c, 2, null);
                    return acessarContas(contas, conta);
                case 51:
                    Operacoes.operar(contas, c, 3, null);
                    return acessarContas(contas, conta);
                case 52:
                    Operacoes.alterarDados(c, null);
                    return acessarContas(contas, conta);
                case 53:
                    c.saldo();
                    return acessarContas(contas, conta);
                case 54:
                    c.extrato();
                    return acessarContas(contas, conta);
                case 55:
                    return "";
                case 56:
                    if (invest.isEmpty()) {
                        System.out.println("Opção inválida!");
                        return acessarContas(contas, conta);
                    } else {
                        return Operacoes.simularInvestimento(contas, c, null);
                    }
                case 57:
                    System.out.println("Volte sempre! Até a próxima.");
                    return "9";
                default:
                    System.out.println("Opção inválida!");
                    return acessarContas(contas, conta);
            }
        }
    }

}