public abstract class Personagem extends Entidade {
    protected Direcao ultimaDirecao;

    public Personagem(int horiz, int verti, Direcao direcao) {
        super(horiz, verti, true);
        this.ultimaDirecao = direcao;
    }

    public void setDirecao(Direcao d) {
        this.ultimaDirecao = d;
    }

    public Disparo disparar() {
        return new Disparo(ultimaDirecao);
    }

    public Direcao getUltimaDirecao() {
        return ultimaDirecao;
    }
}
