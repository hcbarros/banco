package br.com.banco.utils;

import br.com.banco.tipos_conta.Conta;

import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

    private static Scanner scanner = new Scanner(System.in);


    public static String menu(List<Conta> contas) {

        System.out.println("\nEscolha uma opção:\n1 - Criar conta\n2 - Acessar contas" +
                "\n3 - Listar contas\n4 - Exibir total investido\n5 - Transações de um cliente" +
                "\n9 - Encerrar a sessão");

        String opcao = scanner.nextLine();
        switch (opcao.hashCode()) {
            case 49:
                opcao = GeradorConta.opcaoCriarConta(contas);
                break;
            case 50:
                opcao = Acesso.acessarContas(contas,null);
                break;
            case 51:
                opcao = Exibicao.listarContas(contas);
                break;
            case 52:
                opcao = Exibicao.totalInvestido(contas,null);
                break;
            case 53:
                opcao = Exibicao.transacoesCliente(contas);
                break;
            case 57:
                System.out.println("Volte sempre! Até a proxima.");
                break;
            default:
                System.out.println("Opção inválida!");
        }

        return opcao.equals("9") ? "" : menu(contas);
    }

}
