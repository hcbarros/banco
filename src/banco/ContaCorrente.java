package banco;

public class ContaCorrente extends Conta {

    private double limite;

    public ContaCorrente(String nome, String cpf,
                         double rendaMensal,
                         Agencia agencia, double saldo) {

        super(nome, cpf, rendaMensal, agencia, saldo);

        limite = rendaMensal > 0 ? (rendaMensal/10) : 0;
    }

    @Override
    public double getSaldo() {
        return super.getSaldo() + limite;
    }

    public double getLimite() {
        return limite;
    }

}
