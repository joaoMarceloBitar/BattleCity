package Jogo;

import java.util.List;

import javax.swing.SwingUtilities;

public class InimigoAI implements Runnable {

    private boolean running = true;
    private Jogo jogo;

    public InimigoAI(Jogo jogo) {
        this.jogo = jogo;
    }

    public void parar() {
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            jogo.esperarSePausado();

            if (!jogo.isInimigosCongelados()) {
                List<Inimigo> inimigos = jogo.getInimigos();

                for (Inimigo i : inimigos) {
                    if (i.isVivo()) {
                        Direcao d = i.decidirMovimento();
                        synchronized (jogo) {
                            jogo.acaoInimigo(d, i, jogo.getPlayer());

                            if (jogo.getTela() != null) {
                                SwingUtilities.invokeLater(() -> {
                                    jogo.getTela().atualizarTela();
                                });
                            }
                        }
                    }
                } 
            } else {
                if (jogo.getTela() != null) {
                    SwingUtilities.invokeLater(() -> jogo.getTela().atualizarTela());
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}