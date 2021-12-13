package br.com.banco;

import br.com.banco.enums.Agencia;
import br.com.banco.tipos_conta.Conta;
import br.com.banco.tipos_conta.ContaCorrente;
import br.com.banco.tipos_conta.ContaInvestimento;
import br.com.banco.tipos_conta.ContaPoupanca;
import br.com.banco.utils.MenuPrincipal;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Conta> contas = new ArrayList<>();

    // INICIALIZANDO DA LISTA DE CONTAS
    static {

        ContaCorrente c1 = (ContaCorrente) new ContaCorrente("Alex Santos",2000,
                Agencia.SAO_JOSE_002).validarCPF("559.247.070-32");
        ContaPoupanca c2 = (ContaPoupanca) new ContaPoupanca("Ana Luíza",3500,
                Agencia.FLORIANOPOLIS_001).validarCPF("638.187.850-90");;
        ContaInvestimento c3 = (ContaInvestimento) new ContaInvestimento("José Rodrigues",
                4400, Agencia.FLORIANOPOLIS_001).validarCPF("201.141.730-95");;
        ContaPoupanca c4 = (ContaPoupanca) new ContaPoupanca("José Rodrigues",4400,
                Agencia.SAO_JOSE_002).validarCPF("201.141.730-95");;

        c1.deposito(1500);
        c1.transferir(c2, 500);
        c1.transferir(c3,700);
        c1.saque(400);
        c2.transferir(c4, 300);

        contas.addAll(List.of(c1,c2,c3,c4));
    }

    public static void main(String[] args) {
        MenuPrincipal.menu(contas);
    }
}
