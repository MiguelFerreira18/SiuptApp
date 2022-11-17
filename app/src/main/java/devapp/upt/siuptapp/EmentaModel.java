package devapp.upt.siuptapp;

public class EmentaModel {
    private String prato;
    private int dia;

    public EmentaModel(String prato, int dia) {
        this.prato = prato;
        this.dia = dia;
    }

    public String getPrato() {
        return prato;
    }

    public void setPrato(String prato) {
        this.prato = prato;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

}
