package devapp.upt.siuptapp;

public class UcNotas {

    public String UC;
    public int nota;

    public UcNotas(String UC, int nota) {
        this.UC = UC;
        this.nota = nota;
    }

    public int getNota() {
        return nota;
    }

    public String getUC(){
        return UC;
    }
}
