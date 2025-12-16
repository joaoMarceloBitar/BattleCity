public abstract class Personagem extends Entidade {
    protected Direcao ultimaDirecao;

    public Personagem(int horiz, int verti, Direcao direcao) {
        super(horiz, verti, true);
        this.ultimaDirecao = direcao;
    }

    public void setDirecao(Direcao d) {
        this.ultimaDirecao = d;
    }

    public Disparo disparar(Direcao ultimaDirecao, int x, int y) {
        return new Disparo(ultimaDirecao,x,y);
    }

    public Direcao getUltimaDirecao() {
        return ultimaDirecao;
    }
}
