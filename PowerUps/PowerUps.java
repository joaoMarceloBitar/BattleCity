package PowerUps;

import Jogo.*;

public class PowerUps extends Entidade {
    private boolean pegavel = true;

    public PowerUps(int horiz, int verti) {
        super(horiz, verti, false);
        this.vivo = true;
    }

    public void setPegavel(boolean pego) {
        this.pegavel = false;
    }

    public boolean getPegavel() {
        return this.pegavel;
    }

    public static PowerUps getPowerUps (int horiz, int verti, Jogo jogo) {
        PowerUps p;
        int random = (int)(Math.random() * (jogo.getNivelAtual() + 3));

        switch (random) {
            case 1:
                p = new Capacete(horiz, verti);
                break;
            case 2:
                p = new Gelo(horiz, verti);
                break;
            case 3:
                p = new Kit(horiz, verti);
                break;
            default:
                p = null;
                break;
        }
        return p;
    }

    @Override
    public char getChar() {
        throw new UnsupportedOperationException("Unimplemented method 'getChar'");
    }
}