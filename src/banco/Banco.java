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
        boolean bool = false;
        contas.forEach(c -> {
            if(c.getClass().equals(classe)) {
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
        if(contas.isEmpty()) {
            System.out.println("Não há contas cadastradas no sistema!");
        }
    }

    private static String listarContas() {

        System.out.println("\n1 - Listar contas-corrente\n2 - Listar contas-poupança" +
                "\n3 - Listar contas investimento\n4 - Listar todas as contas" +
                "\n5 - Listar contas com saldo negativo\n6 - Retornar ao menu principal" +
                "\n0 - Encerrar a sessão");

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

    private static void totalInvestido(String cpf) {

        String opcao = "1";
        if(cpf == null) {
            System.out.println("\nTotal do valor investido\n1 - Total de um cliente" +
                    "\n2 - Total de todos os clientes\n3 - Retornar ao menu anterior");
            opcao = scanner.nextLine();
        }
        switch (opcao.hashCode()) {
            case 49:
                System.out.print("Informe o CPF do cliente (apenas números): ");
                cpf = scanner.nextLine();
                if(!cpf.matches("\\d{11}")) {
                    totalInvestido(cpf);
                }
                break;
            case 50: break;
            case 51: return;
            default:
                System.out.println("Opção inválida!");
                totalInvestido(null);
        }

        double total = 0;
        boolean find = false;
        for(Conta c: contas) {
            if(c.getClass().equals(CONTA_POUPANCA) || c.getClass().equals(CONTA_INVESTIMENTO)) {
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
            totalInvestido(null);
        }
        if(!find) {
            System.out.println("O CPF informado não possui conta-investimento nem conta-poupança!");
            totalInvestido(null);
        }
        System.out.println("\nO total investido pelo cliente de CPF "+ cpf +" é de R$ "+
                String.format("%.2f",total));

        totalInvestido(null);
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

        System.out.println("\nEscolha uma opção:\n1 - Criar conta-corrente\n2 - Criar conta-poupança" +
            "\n3 - Criar conta investimento\n4 - Retornar ao menu inicial\n0 - Encerrar a sessão");

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

    private static String alterarDados(Conta c, String nome) {

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
            System.out.println("O valor deve ser um número com no máximo 2 casa decimais!");
            return alterarDados(c, nome);
        }
        c.alterarDadosCadastrais(nome, Double.parseDouble(renda));
        return "";
    }

    private static String operacoes(Conta c, int op) {

        System.out.print("informe o valor " +
                (op == 1 ? "do saque" : op == 2 ? "do depósito" : "da transferência") +
                " ou digite 'sair' para retornar ao menu anterior: ");

        String valor = scanner.nextLine();
        if (valor.equalsIgnoreCase("sair")) {
            return "";
        }
        if (!valor.matches("\\d{1,10}(\\.\\d{1,2})?$")) {
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
            default:
        }
        return "";
    }

    private static String simularInvestimento(Conta c, String rent) {

        if(c.getClass().equals(CONTA_POUPANCA)) {

            if(rent == null) {
                System.out.print("\nInforme a rentabilidade anual da poupança ou digite 'sair' " +
                        "para retornar ao menu anterior: ");
                rent = scanner.nextLine();
                if(rent.equalsIgnoreCase("sair")) {
                    return acessarContas(String.valueOf(c.getConta()));
                }
                if(!rent.matches("\\d{1,10}(\\.\\d{1,2})?$")) {
                    System.out.println("A renda deve ser um número com no máximo 2 casa decimais!" +
                            "\nPor exemplo: 12.22\nOutro exemplo: 12");
                    simularInvestimento(c, null);
                }
                simularInvestimento(c, rent);
            }
            else {
                System.out.print("\nInforme o tempo em meses que você deseja simular: ");
                String tempo = scanner.nextLine();
                if(!tempo.matches("\\d{1,10}")) {
                    System.out.println("O tempo deve ser um número inteiro positivo: ");
                    simularInvestimento(c, rent);
                }
                ((ContaPoupanca) c).rendimento(Integer.parseInt(tempo), Double.parseDouble(rent));
                return acessarContas(String.valueOf(c.getConta()));
            }
        }
        else {
            System.out.println("\nEscolha uma opção para simular um investimento: \n1 - CDB" +
                    "\n2 - LCA\n3 - LCI\n4 - Tesouro Direto\n5 - Retornar ao menu anterior");

            String opcao = scanner.nextLine();
            switch (opcao.hashCode()) {
                case 49:
                    ((ContaInvestimento) c).cdb();
                    return simularInvestimento(c, null);
                case 50:
                    ((ContaInvestimento) c).lca();
                    return simularInvestimento(c, null);
                case 51:
                    ((ContaInvestimento) c).lci();
                    return simularInvestimento(c, null);
                case 52:
                    ((ContaInvestimento) c).tesouroDireto();
                    return simularInvestimento(c, null);
                case 53:
                    return acessarContas(String.valueOf(c.getConta()));
                default:
                    System.out.println("Opção inválida!");
                    return simularInvestimento(c,null);
            }
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
            String invest = !(c.getClass().equals(CONTA_CORRENTE)) ? "\n8 - Simular investimento" : "";
            System.out.println("\n1 - Saque\n2 - Depósito\n3 - Tranferência\n4 - Alterar dados cadastrais" +
                "\n5 - Exibir saldo\n6 - Extrato\n7 - Retornar ao menu anterior"+ invest +"\n0 - Encerrar sessão");

            String opcao = scanner.nextLine();

            switch (opcao.hashCode()) {

                case 49:
                    operacoes(c,1);
                    return acessarContas(conta);
                case 50:
                    operacoes(c,2);
                    return acessarContas(conta);
                case 51:
                    operacoes(c,3);
                    return acessarContas(conta);
                case 52:
                    alterarDados(c,null);
                    return acessarContas(conta);
                case 53:
                    c.saldo();
                    return acessarContas(conta);
                case 54:
                    c.extrato();
                    return acessarContas(conta);
                case 55: return "";
                case 56:
                    if(invest.isEmpty()) {
                        System.out.println("Opção inválida!");
                        acessarContas(conta);
                    }
                    else simularInvestimento(c,null);
                    break;
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
                                 .filter(c -> c.getCpf().equals(cpf))
                                 .collect(Collectors.toList());
        if(list.isEmpty()) {
            System.out.println("CPF não encontrado!");
            return transacoesCliente();
        }
        list.forEach(c -> c.extrato());
        return "";
    }

    private static String menuPrincipal() {

            System.out.println("\nEscolha uma opção:\n1 - Criar conta\n2 - Acessar contas" +
                    "\n3 - Listar contas\n4 - Exibir total investido\n5 - Transações de um cliente" +
                    "\n0 - Encerrar a sessão");

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
                    totalInvestido(null);
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

    }
}
