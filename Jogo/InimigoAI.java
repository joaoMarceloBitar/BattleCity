package Jogo;

import java.util.List;

public class InimigoAI implements Runnable {

    private boolean running = true;
    private Jogo jogo;

    public InimigoAI(Jogo jogo) {
        this.jogo = jogo;
    }

    @Override
    public void run() {
        while (running) {
            jogo.esperarSePausado();

            List<Inimigo> inimigos = jogo.getInimigos();

            for (Inimigo i : inimigos) {
                if (i.isVivo()) {
                    Direcao d = i.decidirMovimento();
                    synchronized (jogo) {
                        jogo.acaoInimigo(d, i, jogo.getPlayer());
                    }
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