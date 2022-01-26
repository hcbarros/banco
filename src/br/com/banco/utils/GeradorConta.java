package br.com.banco.utils;

import br.com.banco.enums.Agencia;
import br.com.banco.tipos_conta.Conta;
import br.com.banco.tipos_conta.ContaCorrente;
import br.com.banco.tipos_conta.ContaInvestimento;
import br.com.banco.tipos_conta.ContaPoupanca;

import java.util.List;
import java.util.Scanner;

public class GeradorConta {

    private static Scanner scanner = new Scanner(System.in);


    public static String opcaoCriarConta(List<Conta> contas) {

        System.out.println("\nEscolha uma opção:\n1 - Criar conta-corrente\n2 - Criar conta-poupança" +
                "\n3 - Criar conta investimento\n4 - Retornar ao menu inicial\n9 - Encerrar a sessão");

        String opcao = scanner.nextLine();
        switch (opcao.hashCode()) {

            case 49:
                opcao = criarConta(contas, ContaCorrente.class,null,null,null,null);
                break;
            case 50:
                opcao = criarConta(contas, ContaPoupanca.class,null,null,null,null);
                break;
            case 51:
                opcao = criarConta(contas, ContaInvestimento.class,null,null,null,null);
                break;
            case 52: return "4";
            case 57:
                System.out.println("Volte sempre! Até a proxima.");
                return "9";
            default:
                System.out.println("Opção inválida!");
        }

        return opcaoCriarConta(contas);
    }


    private static String criarConta(List<Conta> contas, Class<?> classe,
                                     String nome, String renda, String ag, String cpf) {

        if(nome == null) {
            System.out.print("Informe o nome do titular: ");
            nome = scanner.nextLine();
            if(nome.isBlank() || nome.isEmpty()) nome = null;

            return criarConta(contas, classe, nome, renda, ag, cpf);
        }
        else if(renda == null) {
            System.out.print("Informe a renda do titular: ");
            renda = scanner.nextLine();
            if(!renda.matches("\\d{1,10}(\\.\\d{1,2})?$")) {
                System.out.println("A renda deve ser um número com no máximo 2 casas decimais!" +
                        "\nPor exemplo: 12.22\nOutro exemplo: 155");
                renda = null;
            }
            return criarConta(contas, classe, nome, renda, ag, cpf);
        }
        else if(ag == null) {
            System.out.print("Informe a agência: " +
                    "\n1 - FLORIANOPOLIS" +
                    "\n2 - SÃO JOSÉ\n");
            ag = scanner.nextLine();
            if(!ag.equals("1") && !ag.equals("2")) {
                System.out.println("Opção inválida");
                ag = null;
            }
            return criarConta(contas, classe, nome, renda, ag, cpf);
        }
        else if(cpf == null){
            System.out.print("Informe o CPF do titular: ");
            cpf = scanner.nextLine();
            if (!Conta.ehCpfValido(cpf, true)) {
                System.out.println("CPF inválido!");
                return criarConta(contas, classe, nome, renda, ag, null);
            }
            try {
                Conta c = (Conta) classe.getDeclaredConstructor(
                        String.class, double.class, Agencia.class, String.class)
                        .newInstance(nome, Double.parseDouble(renda),
                                ag.equals("1") ? Agencia.FLORIANOPOLIS_001 : Agencia.SAO_JOSE_002, cpf);
                contas.add(c);
                Gravador.gravarConta(c);
            }
            catch (Exception ex) {
                System.out.println("Erro: "+ex.getMessage());
            }
        }
        System.out.println("\n"+classe.getSimpleName() + " criada com sucesso!");
        return "";
    }

}

