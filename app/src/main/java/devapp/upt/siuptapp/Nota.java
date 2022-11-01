package devapp.upt.siuptapp;

public class Nota {
    private int notaId;
    private int numAluno;
    private int codUC;
    private int nota;

    public Nota(int notaId, int numAluno, int codUC, int nota) {
        this.notaId = notaId;
        this.numAluno = numAluno;
        this.codUC = codUC;
        this.nota = nota;
    }

    public Nota(int numAluno, int codUC, int nota) {
        this.numAluno = numAluno;
        this.codUC = codUC;
        this.nota = nota;
    }

    public Nota() {
        this.notaId = 0;
        this.numAluno = 0;
        this.codUC = 0;
        this.nota = 0;
    }


    public int getNotaId() {
        return notaId;
    }

    public void setNotaId(int notaId) {
        this.notaId = notaId;
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

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "notaId=" + notaId +
                ", numAluno=" + numAluno +
                ", codUC=" + codUC +
                ", nota=" + nota +
                '}';
    }
}