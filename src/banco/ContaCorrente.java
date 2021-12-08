package banco;

public class ContaCorrente extends Conta {

    private double limite;

    public ContaCorrente(String nome, String cpf,
                         double rendaMensal,
                         Agencia agencia, double saldo) {

        super(nome, cpf, rendaMensal, agencia, saldo);

        limite = rendaMensal/10;
    }

    @Override
    public double getSaldo() {
        return super.getSaldo() + limite;
    }

}
