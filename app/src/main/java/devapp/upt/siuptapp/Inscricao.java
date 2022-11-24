package devapp.upt.siuptapp;

public class Inscricao {
    private int ucId;
    private int alunoId;
    private int incrId;

    public Inscricao(int ucId, int alunoId, int incrId) {
        this.ucId = ucId;
        this.alunoId = alunoId;
        this.incrId = incrId;
    }

    public Inscricao(int ucId, int alunoId) {
        this.ucId = ucId;
        this.alunoId = alunoId;
    }

    public int getUcId() {
        return ucId;
    }

    public void setUcId(int ucId) {
        this.ucId = ucId;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public int getIncrId() {
        return incrId;
    }

    public void setIncrId(int incrId) {
        this.incrId = incrId;
    }
}
