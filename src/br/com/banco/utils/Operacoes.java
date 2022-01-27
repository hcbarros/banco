package br.com.banco.utils;

import br.com.banco.tipos_conta.Conta;
import br.com.banco.tipos_conta.ContaInvestimento;
import br.com.banco.tipos_conta.ContaPoupanca;

import java.util.List;
import java.util.Scanner;

public class Operacoes {

    private static Scanner scanner = new Scanner(System.in);


    public static String operar(List<Conta> contas, Conta c, int op, String valor) {

        if(valor == null) {
            System.out.print("informe o valor " +
                    (op == 1 ? "do saque" : op == 2 ? "do depósito" : "da transferência") +
                    " ou digite 'sair' para retornar ao menu anterior: ");

            valor = scanner.nextLine();
            if (valor.equalsIgnoreCase("sair")) {
                return "";
            }
            if (!valor.matches("\\d{1,10}(\\.\\d{1,2})?$")) {
                System.out.println("A valor deve ser um número com no máximo 2 casas decimais!" +
                        "\nPor exemplo: 12.22\nOutro exemplo: 155");
                return operar(contas, c, op,null);
            }
        }
        switch (op) {
            case 1:
                c.saque(Double.parseDouble(valor));
                break;
            case 2:
                c.deposito(Double.parseDouble(valor));
                break;
            case 3: {
                System.out.print("Informe o número da conta de destino " +
                        "ou digite 'sair' para retornar ao menu anterior: ");
                String conta = scanner.nextLine();
                if (conta.equalsIgnoreCase("sair")) {
                    return "";
                }
                if (!conta.matches("\\d{1,10}")) {
                    System.out.println("Opção inválida!");
                    return operar(contas, c, op, valor);
                }
                final int num = Integer.parseInt(conta);
                Conta co = contas.stream().filter(cont -> cont.getConta() == num)
                        .findFirst().orElse(null);
                if (co == null) {
                    System.out.println("Conta inexistente!");
                    return operar(contas, c, op, valor);
                }
                c.transferir(co, Double.parseDouble(valor));
                FileHandler.gravarConta(co);
                break;
            }
            default:
        }
        FileHandler.gravarConta(c);
        return "";
    }


    public static String alterarDados(Conta c, String nome) {

        if(nome == null) {
            System.out.println("Alteração de dados cadastrais");
            System.out.print("Informe o novo nome do titular ou " +
                    "digite 'sair' para retornar ao menu anterior: ");

            nome = scanner.nextLine();
            if (nome.equalsIgnoreCase("sair")) {
                return "";
            }
            if (nome.isEmpty() || nome.isBlank()) {
                return alterarDados(c, null);
            }
        }
        System.out.print("informe a nova renda mensal do titular " +
                "ou digite 'sair' para retornar ao menu anterior: ");

        String renda = scanner.nextLine();
        if (renda.equalsIgnoreCase("sair")) {
            return "";
        }
        if (!renda.matches("\\d{1,10}(\\.\\d{1,2})?$")) {
            System.out.println("A renda deve ser um número com no máximo 2 casas decimais!" +
                    "\nPor exemplo: 12.22\nOutro exemplo: 155");
            return alterarDados(c, nome);
        }
        c.alterarDadosCadastrais(nome, Double.parseDouble(renda));
        FileHandler.gravarConta(c);
        return "";
    }


    public static String simularInvestimento(List<Conta> contas, Conta c, String rent) {

        if (c.getClass().equals(ContaPoupanca.class)) {

            if (rent == null) {
                System.out.print("\nInforme a rentabilidade anual da poupança ou digite 'sair' " +
                        "para retornar ao menu anterior: ");
                rent = scanner.nextLine();
                if (rent.equalsIgnoreCase("sair")) {
                    return Acesso.acessarContas(contas, String.valueOf(c.getConta()));
                }
                if (!rent.matches("\\d{1,10}(\\.\\d{1,2})?$")) {
                    System.out.println("A rentabilidade deve ser um número com no máximo 2 casas decimais!" +
                            "\nPor exemplo: 12.22\nOutro exemplo: 155");
                    return simularInvestimento(contas, c, null);
                }
                return simularInvestimento(contas, c, rent);
            } else {
                System.out.print("\nInforme o tempo em meses que você deseja simular: ");
                String tempo = scanner.nextLine();
                if (!tempo.matches("\\d{1,10}")) {
                    System.out.println("O tempo deve ser um número inteiro positivo: ");
                    return simularInvestimento(contas, c, rent);
                }
                ((ContaPoupanca) c).rendimento(Integer.parseInt(tempo), Double.parseDouble(rent));
                return Acesso.acessarContas(contas, String.valueOf(c.getConta()));
            }
        } else {
            System.out.println("\nEscolha uma opção para simular um investimento: \n1 - CDB" +
                    "\n2 - LCA\n3 - LCI\n4 - Tesouro Direto\n5 - Retornar ao menu anterior");

            String opcao = scanner.nextLine();
            switch (opcao.hashCode()) {
                case 49:
                    ((ContaInvestimento) c).cdb();
                    return simularInvestimento(contas, c, null);
                case 50:
                    ((ContaInvestimento) c).lca();
                    return simularInvestimento(contas, c, null);
                case 51:
                    ((ContaInvestimento) c).lci();
                    return simularInvestimento(contas, c, null);
                case 52:
                    ((ContaInvestimento) c).tesouroDireto();
                    return simularInvestimento(contas, c, null);
                case 53:
                    return Acesso.acessarContas(contas, String.valueOf(c.getConta()));
                default:
                    System.out.println("Opção inválida!");
                    return simularInvestimento(contas, c, null);
            }
        }
    }

}
