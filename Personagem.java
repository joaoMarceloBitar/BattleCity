public abstract class Personagem{
    protected int vida;
    protected Direcao ultimaDirecao;

    public void setDirecao(Direcao d) {
        this.ultimaDirecao = d;
    }

    public Direcao getUltimaDirecao() {
        return ultimaDirecao;
    }
}