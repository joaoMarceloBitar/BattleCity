package PowerUps;

import Jogo.*;

public class Capacete extends PowerUps{
    private int duracao = 8000;
    
    public Capacete (int horiz, int verti) {
        super(horiz, verti);
    }

    public void invulneravel(Jogo jogo) {
        new Thread(() -> {
            try {
                jogo.getPlayer().setInvulneravel(true);

                Thread.sleep(duracao);

                jogo.getPlayer().setInvulneravel(false);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
