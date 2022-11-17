package devapp.upt.siuptapp;

public class Model {
    private String prato;
    private int dia;

    public Model(String prato, int dia) {
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
