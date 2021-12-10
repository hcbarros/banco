package banco;

public class ContaCorrente extends Conta {

    private double limite;

    public ContaCorrente(String nome, double rendaMensal, Agencia agencia) {
        super(nome, rendaMensal, agencia);
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
