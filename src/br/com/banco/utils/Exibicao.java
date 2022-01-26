package br.com.banco.utils;

import br.com.banco.tipos_conta.Conta;
import br.com.banco.tipos_conta.ContaCorrente;
import br.com.banco.tipos_conta.ContaInvestimento;
import br.com.banco.tipos_conta.ContaPoupanca;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Exibicao {

    private static Scanner scanner = new Scanner(System.in);
    private static boolean find = false;

    public static String listarContas(List<Conta> contas) {

        System.out.println("\n1 - Listar contas-corrente\n2 - Listar contas-poupança" +
                "\n3 - Listar contas investimento\n4 - Listar todas as contas" +
                "\n5 - Listar contas com saldo negativo\n6 - Retornar ao menu principal" +
                "\n9 - Encerrar a sessão");

        String opcao = scanner.nextLine();
        switch (opcao.hashCode()) {
            case 49:
                listarContas(contas, ContaCorrente.class, false);
                break;
            case 50:
                listarContas(contas, ContaPoupanca.class, false);
                break;
            case 51:
                listarContas(contas, ContaInvestimento.class, false);
                break;
            case 52:
                listarContas(contas, Conta.class, false);
                break;
            case 53:
                listarContas(contas, Conta.class, true);
                break;
            case 54:
                return "";
            case 57:
                System.out.println("Volte sempre! Até a proxima.");
                return "9";
            default:
                System.out.println("Opção inválida!");
        }

        return listarContas(contas);
    }


    private static void listarContas(List<Conta> contas, Class<?> classe, boolean ehNegativo) {
        contas.forEach(c -> {
            if(c.getClass().equals(classe)) {
                System.out.println(c);
                Gravador.carregarArquivo(c, false);
                find = true;
            }
            else if(c.getClass().getSuperclass().equals(classe)) {
                if(ehNegativo && c.getSaldo() < 0) {
                    System.out.println(c);
                    Gravador.carregarArquivo(c, false);
                    find = true;
                }
                else if(!ehNegativo) {
                    System.out.println(c);
                    Gravador.carregarArquivo(c, false);
                    find = true;
                }
            }
        });
        if(contas.isEmpty()) {
            System.out.println("Não há contas cadastradas no sistema!");
        }
        else if(!find) {
            System.out.println("Não houve ocorrências para sua pesquisa!");
        }
        find = false;
    }


    public static String totalInvestido(List<Conta> contas, String cpf) {

        String opcao = "1";
        if(cpf == null) {
            System.out.println("\nTotal do valor investido\n1 - Total de um cliente" +
                    "\n2 - Total de todos os clientes\n3 - Retornar ao menu anterior");
            opcao = scanner.nextLine();
        }
        switch (opcao.hashCode()) {
            case 49:
                System.out.print("Informe o CPF do cliente ou digite '0' para retornar: ");
                cpf = scanner.nextLine();
                if(cpf.equals("0")) {
                    return totalInvestido(contas,null);
                }
                else if(!Conta.ehCpfValido(cpf, true)) {
                    System.out.println("CPF inválido!");
                    return totalInvestido(contas,null);
                }
                break;
            case 50: break;
            case 51: return "";
            default:
                System.out.println("Opção inválida!");
                return totalInvestido(contas,null);
        }

        double total = 0;
        boolean find = false;
        for(Conta c: contas) {
            if(c.getClass().equals(ContaPoupanca.class) || c.getClass().equals(ContaInvestimento.class)) {
                if(cpf != null && cpf.equals(c.getCpf())) {
                    total += c.getSaldo();
                    find = true;
                }
                else if(cpf == null) {
                    total += c.getSaldo();
                }
            }
        }
        if(cpf == null) {
            System.out.println("\nO total investido de todos os clientes é de R$ "+
                    String.format("%.2f",total));
            return totalInvestido(contas,null);
        }
        if(!find) {
            System.out.println("\nO CPF informado não possui conta-investimento nem conta-poupança!");
            return totalInvestido(contas,null);
        }
        System.out.println("\nO total investido pelo cliente de CPF "+ cpf +" é de R$ "+
                String.format("%.2f",total));

        return totalInvestido(contas,null);
    }


    public static String transacoesCliente(List<Conta> contas) {
        System.out.print("\nInforme o CPF do cliente " +
                "ou digite 'sair' para retornar ao menu anterior: ");
        final String cpf = scanner.nextLine();
        if(cpf.equalsIgnoreCase("sair")) {
            return "";
        }
        if(!Conta.ehCpfValido(cpf, true)) {
            System.out.println("CPF inválido!");
            return transacoesCliente(contas);
        }
        List<Conta> list = contas.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .collect(Collectors.toList());
        if(list.isEmpty()) {
            System.out.println("CPF não encontrado!");
            return transacoesCliente(contas);
        }
        list.forEach(c -> {
            c.extrato();
            Gravador.carregarArquivo(c,true);
        });
        return "";
    }

}
