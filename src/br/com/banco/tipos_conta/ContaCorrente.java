package br.com.banco.tipos_conta;

import br.com.banco.enums.Agencia;


public class ContaCorrente extends Conta {

    private double limite;

    public ContaCorrente(String nome, double rendaMensal, Agencia agencia) {
        super(nome, rendaMensal, agencia);
        limite = (rendaMensal > 0) ? (rendaMensal/10) : 0;
    }


    @Override
    public void alterarDadosCadastrais(String nome, double rendaMensal) {
        limite = (rendaMensal > 0) ? (rendaMensal/10) : 0;
        super.alterarDadosCadastrais(nome, rendaMensal);
    }

    public double getLimite() {
        return limite;
    }

    public String toString() {
        return super.toString() +
                "\nLimite (cheque especial): " + limite;
    }

}
