public abstract class Personagem extends Entidade {
    protected Direcao ultimaDirecao;

    public Personagem(int horiz, int verti, Direcao direcao, char caractere) {
        super(horiz, verti, 3, true, caractere);
        this.ultimaDirecao = direcao;
    }

    @Override
    public boolean podeAtravessar() {
        return false;
    }

    public void setDirecao(Direcao d) {
        this.ultimaDirecao = d;
    }

    public Disparo disparar() {
        return new Disparo(getHoriz(), getVerti(), ultimaDirecao);
    }

    public Direcao getUltimaDirecao() {
        return ultimaDirecao;
    }

    public void andar(Direcao d) {
        switch (d) {
            case CIMA:
                setVerti(getVerti() - 1);
                break;
            case BAIXO:
                setVerti(getVerti() + 1);
                break;
            case ESQUERDA:
                setHoriz(getHoriz() - 1);
                break;
            case DIREITA:
                setHoriz(getHoriz() + 1);;
                break;
            default:
                break;
        }
    }

}
