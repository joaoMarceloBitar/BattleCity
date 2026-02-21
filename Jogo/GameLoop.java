package Jogo;

import javax.swing.SwingUtilities;

public class GameLoop implements Runnable {

    private boolean running = true;
    private Jogo jogo;

    public GameLoop(Jogo jogo) {
        this.jogo = jogo;
    }

    public void parar() {
        this.running = false;
    }

    @Override
    public void run() {
        final int FPS = 30;
        final long frameTime = 1_000_000_000L / FPS;

        while (running) {
            jogo.esperarSePausado();
            long start = System.nanoTime();

            synchronized (jogo) {
                jogo.update();
            }

            SwingUtilities.invokeLater(() -> {
                if (jogo.getTela() != null) {
                    jogo.getTela().atualizarTela();
                }
            });

            long duration = System.nanoTime() - start;
            long sleep = (frameTime - duration) / 1_000_000;
            if (sleep > 0) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}