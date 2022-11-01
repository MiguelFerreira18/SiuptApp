package devapp.upt.siuptapp;

public class Horario {
    private int horId;
    private int numAluno;
    private int codUC;
    private int dia;
    private int horaInicio;
    private int horaFim;
    private String tipo;


    public Horario(int numAluno, int codUC, int dia, int horaInicio, int horaFim, String tipo) {
        this.numAluno = numAluno;
        this.codUC = codUC;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.tipo = tipo;
    }
    public Horario(){
        this.numAluno = 0;
        this.codUC = 0;
        this.dia = 0;
        this.horaInicio = 0;
        this.horaFim = 0;
        this.tipo = "";

    }

    public int getNumAluno() {
        return numAluno;
    }

    public void setNumAluno(int numAluno) {
        this.numAluno = numAluno;
    }

    public int getCodUC() {
        return codUC;
    }

    public void setCodUC(int codUC) {
        this.codUC = codUC;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(int horaFim) {
        this.horaFim = horaFim;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Horario{" +
                "numAluno=" + numAluno +
                ", codUC=" + codUC +
                ", dia=" + dia +
                ", horaInicio=" + horaInicio +
                ", horaFim=" + horaFim +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}