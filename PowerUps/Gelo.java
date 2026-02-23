package PowerUps;

import Jogo.*;

public class Gelo extends PowerUps {
    
    public Gelo(int horiz, int verti) {
        super(horiz, verti);
    }

    public void congela(Jogo jogo) {
        new Thread(() -> {
            try {
                jogo.setInimigosCongelados(true);

                javax.swing.SwingUtilities.invokeLater(() -> {
                    if (jogo.getTela() != null) {
                        jogo.getTela().atualizarTela();
                    }
                });
                Thread.sleep(3000);

                jogo.setInimigosCongelados(false);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}

