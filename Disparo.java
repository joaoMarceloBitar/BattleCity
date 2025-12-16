public class Disparo {
    private int horiz;
    private int verti;
    private Direcao direcaoDisparo;
    private boolean ativo;

    public Disparo(int horiz, int verti, Direcao direcao) {
        this.horiz = horiz;
        this.verti = verti;
        this.direcaoDisparo = direcao;
        this.ativo = true;
    }

    public boolean taAtivo() {
        return ativo;
    }

    public void mover() {
        switch(direcaoDisparo) {
            case CIMA:
                horiz--;
                break;
            case BAIXO:
                horiz++;
                break;
            case DIREITA:
                verti++;
                break;
            case ESQUERDA:
                verti--;
                break;
            default:
                break;
        }
    }

    public int getHoriz() {
        return horiz;
    }

    public int getVerti() {
        return verti;
    }

    public void desativar() {
        ativo = false;
    }
}
