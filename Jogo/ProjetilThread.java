package Jogo;

import javax.swing.SwingUtilities;

public class ProjetilThread implements Runnable {

    private boolean running = true;
    private Jogo jogo;
    private final int INTERVALO_MS = 200;

    public ProjetilThread(Jogo jogo) {
        this.jogo = jogo;
    }

    public void parar() {
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            jogo.esperarSePausado();
            synchronized (jogo) {
                jogo.moveDisparos();
                if (jogo.getTela() != null) {
                    SwingUtilities.invokeLater(() -> jogo.getTela().atualizarDisparo());
                }
            }
            try {
                Thread.sleep(INTERVALO_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}