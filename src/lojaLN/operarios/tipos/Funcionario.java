package lojaLN.operarios.tipos;

import lojaLN.operarios.Operario;

public class Funcionario extends Operario {

    private int nRececoes = 0;
    private int nEntregas = 0;

    public Funcionario(String id, String password) {
        super(id, password);
    }

    public void incNRececoes() {
        ++nRececoes;
    }

    public void incNEntregas() {
        ++nEntregas;
    }



	/**
	 * Descreve as informações de um funcionário
	 */
	public String getInformacoes() {
		return toString();
	}

    @Override
    public String toString() {
        return "Funcionario: " + super.getId() +
                " Número de Receções=" + nRececoes +
                ", Número de Entregas=" + nEntregas;
    }
}