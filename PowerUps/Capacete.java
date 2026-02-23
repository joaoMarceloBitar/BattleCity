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

                javax.swing.SwingUtilities.invokeLater(() -> {
                    if (jogo.getTela() != null) {
                        jogo.getTela().atualizarTela();
                    }
                });

                Thread.sleep(duracao);

                jogo.getPlayer().setInvulneravel(false);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
