package banco;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Banco {

    private static List<Conta> contas = new ArrayList<>();
    private static final Class<?> CONTA = Conta.class;
    private static final Class<?> CONTA_CORRENTE = ContaCorrente.class;
    private static final Class<?> CONTA_POUPANCA = ContaPoupanca.class;
    private static final Class<?> CONTA_INVESTIMENTO = ContaInvestimento.class;
    private static Scanner scanner = new Scanner(System.in);


    private static void listarContas(Class<?> classe, boolean ehNegativo) {
        contas.forEach(c -> {
            if(c.getClass().isAssignableFrom(classe)) {
                System.out.println(c);
            }
            else if(c.getClass().getSuperclass().equals(classe)) {
                if(ehNegativo && c.getSaldo() < 0) {
                    System.out.println(c);
                }
                else if(!ehNegativo) {
                    System.out.println(c);
                }
            }
        });
    }


    private double totalInvestido(String cpf) {

        return contas.stream().filter(c -> c.getClass().equals(CONTA_POUPANCA) ||
                        c.getClass().equals(CONTA_INVESTIMENTO)).map(c -> {
                            return cpf == null ? c.getSaldo() :
                                    cpf.equals(c.getCpf()) ? c.getSaldo() : 0;
                        }).reduce(0.0, (a, b) -> a + b);
    }

    private static void transacoesCliente(String cpf) {
        contas.forEach(c -> {
            if(c.getCpf().equals(cpf)) {
                c.extrato();
            }
        });
    }

    private static String criarConta(Class<?> classe, String nome, String renda, String ag, String cpf) {

        if(nome == null) {
            System.out.print("Informe o nome do titular: ");
            nome = scanner.nextLine();
            return criarConta(classe, nome, renda, ag, cpf);
        }
        else if(renda == null) {
            System.out.print("Informe a renda do titular: ");
            renda = scanner.nextLine();
            if(!renda.matches("\\d{1,10}(\\.\\d{1,2})?$")) {
                System.out.println("A renda deve ser um número com no máximo 2 casa decimais!");
                renda = null;
            }
            return criarConta(classe, nome, renda, ag, cpf);
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
            return criarConta(classe, nome, renda, ag, cpf);
        }
        else if(cpf == null){
                System.out.print("Informe o CPF do titular (apenas números): ");
                cpf = scanner.nextLine();
                if (!cpf.matches("\\d{11}")) {
                    System.out.println("O CPF deve conter apenas 11 dígitos!");
                    return criarConta(classe, nome, renda, ag, null);
                }
                else {
                    if(classe.getSimpleName().equals("ContaCorrente")) {
                        ContaCorrente c = (ContaCorrente) new ContaCorrente(nome, Double.parseDouble(renda),
                                ag.equals("1") ? Agencia.FLORIANOPOLIS_001 : Agencia.SAO_JOSE_002)
                                .validarCPF(cpf);
                        if(c == null) return criarConta(classe, nome, renda, ag,null);
                        else contas.add(c);
                    }
                    else if(classe.getSimpleName().equals("ContaPoupanca")) {
                        ContaPoupanca c = (ContaPoupanca) new ContaPoupanca(nome, Double.parseDouble(renda),
                                ag.equals("1") ? Agencia.FLORIANOPOLIS_001 : Agencia.SAO_JOSE_002)
                                .validarCPF(cpf);
                        if(c == null) return criarConta(classe, nome, renda, ag, null);
                        else contas.add(c);
                    }
                    else {
                        ContaInvestimento c = (ContaInvestimento) new ContaInvestimento(nome, Double.parseDouble(renda),
                                ag.equals("1") ? Agencia.FLORIANOPOLIS_001 : Agencia.SAO_JOSE_002)
                                .validarCPF(cpf);
                        if(c == null) return criarConta(classe, nome, renda, ag, null);
                        else contas.add(c);
                    }
                }
        }

        System.out.println("\n"+classe.getSimpleName() + " criada com sucesso!");
        return "";
    }

    private static String opcapCriarConta() {

        System.out.println("\nEscolha uma opção:");
        System.out.println("1 - Criar conta-corrente");
        System.out.println("2 - Criar conta-poupança");
        System.out.println("3 - Criar conta investimento");
        System.out.println("4 - Retornar ao menu inicial");
        System.out.println("0 - Encerrar a sessão");

        String opcao = scanner.nextLine();
        switch (opcao.hashCode()) {

            case 49:
                opcao = criarConta(CONTA_CORRENTE,null,null,null,null);
                break;
            case 50:
                opcao = criarConta(CONTA_POUPANCA,null,null,null,null);
                break;
            case 51:
                opcao = criarConta(CONTA_INVESTIMENTO,null,null,null,null);
                break;
            case 52: break;
            case 48:
                System.out.println("Volte sempre! Até a proxima.");
                break;
            default:
                System.out.println("Opção inválida!");
        }

        return (opcao.equals("4") || opcao.equals("0")) ?
                opcao : opcapCriarConta();
    }


    public static void main(String[] args) {



        ContaCorrente corrente = (ContaCorrente) new ContaCorrente("Henrique",2000,
                            Agencia.SAO_JOSE_002).validarCPF("02657226401");

        ContaPoupanca poupanca = (ContaPoupanca) new ContaPoupanca("Henrique",1500,
                            Agencia.SAO_JOSE_002).validarCPF("02657226400");;


        String opcao = "";
        while(!opcao.equals("0")) {

            System.out.println("Escolha uma opção:");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Acessar contas");
            System.out.println("3 - Listar contas");
            System.out.println("4 - Exibir total investido");
            System.out.println("5 - Transações de um cliente");
            System.out.println("0 - Encerrar a sessão");



            opcao = scanner.nextLine();

            switch (opcao.hashCode()) {

                case 49:
                    opcao = opcapCriarConta();
                    break;

                case 50:

                    break;
                case 51:

                    break;

                case 52:

                    break;

                case 53:

                    break;

                case 48:

                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        }

//        System.out.println("2424242".matches("\\d+$"));
//        System.out.println("8565".matches("\\d{1,4}"));
//        System.out.println("242.42".matches("\\d+(\\.\\d{1,2})?$"));
//
//        poupanca.rendimento(4, 5);
//
//        contas.add(corrente);
//        contas.add(poupanca);
//
//        corrente.saque(500);
//        poupanca.transferir(corrente,500);
//
//        listarContas(CONTA, false);




    }
}
