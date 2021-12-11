package banco;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    private static String listarContas() {

        System.out.println("\n1 - Listar contas-corrente");
        System.out.println("2 - Listar contas-poupança");
        System.out.println("3 - Listar contas investimento");
        System.out.println("4 - Listar todas as contas");
        System.out.println("5 - Listar contas com saldo negativo");
        System.out.println("6 - Retornar ao menu principal");
        System.out.println("0 - Encerrar a sessão");

        String opcao = scanner.nextLine();
        switch (opcao.hashCode()) {
            case 49:
                listarContas(CONTA_CORRENTE, false);
                break;
            case 50:
                listarContas(CONTA_POUPANCA, false);
                break;
            case 51:
                listarContas(CONTA_INVESTIMENTO, false);
                break;
            case 52:
                listarContas(CONTA, false);
                break;
            case 53:
                listarContas(CONTA, true);
                break;
            case 54:
                return "";
            case 48:
                System.out.println("Volte sempre! Até a proxima.");
                return "0";
            default:
                System.out.println("Opção inválida!");
        }

        return listarContas();
    }

    private static void totalInvestido() {

        System.out.println("\nTotal do valor investido");
        System.out.println("1 - Total de um cliente");
        System.out.println("2 - Total de todos os clientes");
        System.out.println("3 - Retornar ao menu anterior");

        String cpf = null;
        String opcao = scanner.nextLine();
        switch (opcao.hashCode()) {
            case 49:
                System.out.print("Inform o CPF do cliente (apenas números): ");
                cpf = scanner.nextLine();
                break;
            case 50: break;
            case 51: return;
            default:
                System.out.println("Opção inválida!");
                totalInvestido();
        }

        double total = 0;
        for(Conta c: contas) {
            if(c.getClass().equals(CONTA_POUPANCA) || c.getClass().equals(CONTA_INVESTIMENTO)) {
                if(cpf != null && cpf.equals(c.getCpf())) {
                    total += c.getSaldo();
                }
                else if(cpf == null) {
                    total += c.getSaldo();
                }
            }
        }
        if(cpf == null) {
            System.out.println("O total investido de todos os clientes é de R$ "+
                    String.format("%.2f",total));
            totalInvestido();
        }
        System.out.println("O total investido pelo cliente de CPF "+ cpf +" é de R$ "+
                String.format("%.2f",total));

        totalInvestido();
    }

    private static String criarConta(Class<?> classe, String nome, String renda, String ag, String cpf) {

        if(nome == null) {
            System.out.print("Informe o nome do titular: ");
            nome = scanner.nextLine();
            if(nome.isBlank() || nome.isEmpty()) nome = null;

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

    private static String opcaoCriarConta() {

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
                opcao : opcaoCriarConta();
    }

    private static String operacoes(Conta c, int op) {

        String valor = "";
        if(op == 4) {
            System.out.println("Alteração de dados cadastrais");
            System.out.print("informe o novo nome do titular ou " +
                    "digite 'sair' para retornar ao menu anterior: ");
        }
        else {
            System.out.print("informe o valor " +
                    (op == 1 ? "do saque" : op == 2 ? "do depósito" : "da transferência") +
                    " ou digite 'sair' para retornar ao menu anterior: ");
        }
        valor = scanner.nextLine();
        if (valor.equalsIgnoreCase("sair")) {
            return "";
        }
        if ((valor.isEmpty() || valor.isBlank()) && op == 4) {
            return operacoes(c, op);
        }
        if (!valor.matches("\\d{1,10}(\\.\\d{1,2})?$") && op != 4) {
            System.out.println("O valor deve ser um número com no máximo 2 casa decimais!");
            return operacoes(c, op);
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
                    return operacoes(c, op);
                }
                final int num = Integer.parseInt(conta);
                Conta co = contas.stream().filter(cont -> cont.getConta() == num)
                        .findFirst().orElse(null);
                if (co == null) {
                    System.out.println("Conta inexistente!");
                    return operacoes(c, op);
                }
                c.transferir(co, Double.parseDouble(valor));
                break;
            }
            case 4: {
                System.out.print("informe a nova renda mensal do titular " +
                        "ou digite 'sair' para retornar ao menu anterior: ");

                final String nome = valor;
                valor = scanner.nextLine();
                if (!valor.matches("\\d{1,10}(\\.\\d{1,2})?$") && op != 4) {
                    System.out.println("O valor deve ser um número com no máximo 2 casa decimais!");
                    return operacoes(c, op);
                }
                c.alterarDadosCadastrais(nome, Double.parseDouble(valor));
                break;
            }
            default:
        }
        return "";
    }

    private static String acessarContas(String conta) {

        if(conta == null) {
            System.out.print("Informe o número da conta ou digite 'sair' para retornar ao menu anterior: ");
            conta = scanner.nextLine();
            if(conta.equalsIgnoreCase("sair")) {
                return "";
            }
            if (!conta.matches("\\d{1,10}")) {
                System.out.println("Opsção inválida!");
                conta = null;
            }
            return acessarContas(conta);
        }
        else {
            final int num = Integer.parseInt(conta);
            Conta c = contas.stream().filter(co -> co.getConta() == num)
                            .findFirst().orElse(null);
            if(c == null) {
                System.out.println("Conta inexistente!");
                return acessarContas(null);
            }
            System.out.println("\n1 - Saque");
            System.out.println("2 - Depósito");
            System.out.println("3 - Tranferência");
            System.out.println("4 - Alterar dados cadastrais");
            System.out.println("5 - Exibir saldo");
            System.out.println("6 - Extrato");
            System.out.println("7 - Retornar ao menu anterior");
            System.out.println("0 - Encerrar sessão");

            String opcao = scanner.nextLine();

            switch (opcao.hashCode()) {

                case 49:
                    operacoes(c,1);
                    acessarContas(conta);
                    break;
                case 50:
                    operacoes(c,2);
                    acessarContas(conta);
                    break;
                case 51:
                    operacoes(c,3);
                    acessarContas(conta);
                    break;
                case 52:
                    operacoes(c,4);
                    acessarContas(conta);
                    break;
                case 53:
                    c.saldo();
                    acessarContas(conta);
                    break;
                case 54:
                    c.extrato();
                    acessarContas(conta);
                    break;
                case 55: return "";
                case 48:
                    System.out.println("Volte sempre! Até a próxima.");
                    return "0";
                default:
                    System.out.println("Opção inválida!");
            }
        }
        return "";
    }

    private static String transacoesCliente() {
        System.out.print("\nInforme o CPF do cliente (apenas números) " +
                "ou digite 'sair' para retornar ao menu anterior: ");
        final String cpf = scanner.nextLine();
        if(cpf.equalsIgnoreCase("sair")) {
            return "";
        }
        List<Conta> list = contas.stream()
                .filter(c -> c.equals(cpf)).collect(Collectors.toList());
        if(list.isEmpty()) {
            System.out.println("CPF não encontrado!");
            return transacoesCliente();
        }
        list.forEach(c -> c.extrato());
        return "";
    }

    private static String menuPrincipal() {

            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Acessar contas");
            System.out.println("3 - Listar contas");
            System.out.println("4 - Exibir total investido");
            System.out.println("5 - Transações de um cliente");
            System.out.println("0 - Encerrar a sessão");

            String opcao = scanner.nextLine();

            switch (opcao.hashCode()) {
                case 49:
                    opcao = opcaoCriarConta();
                    break;
                case 50:
                    opcao = acessarContas(null);
                    break;
                case 51:
                    opcao = listarContas();
                    break;
                case 52:
                    totalInvestido();
                    break;
                case 53:
                    opcao = transacoesCliente();
                    break;
                case 48:
                    System.out.println("Volte sempre! Até a proxima.");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        return opcao.equals("0") ? "" : menuPrincipal();
    }


    public static void main(String[] args) {



//        ContaCorrente corrente = (ContaCorrente) new ContaCorrente("Henrique",2000,
//                            Agencia.SAO_JOSE_002).validarCPF("02657226401");
//
//        ContaPoupanca poupanca = (ContaPoupanca) new ContaPoupanca("Henrique",1500,
//                            Agencia.SAO_JOSE_002).validarCPF("02657226400");;

        menuPrincipal();


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
