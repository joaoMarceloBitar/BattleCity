package Jogo;

public class BlocoCaixa extends Entidade {

    private boolean playerCima = false; // true quando o player está neste quadrante

    public BlocoCaixa(int horiz, int verti) {
        super(horiz, verti, false); // indestrutível
    }

    public boolean isPlayerCima() {
        return playerCima;
    }

    public void setPlayerCima(boolean playerCima) {
        this.playerCima = playerCima;
    }

    @Override
    public char getChar() {
        return 'C';
    }
}