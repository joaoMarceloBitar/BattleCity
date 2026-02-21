/*package Jogo;

public class Spawner implements Runnable {

    private boolean running = true;
    private Jogo jogo;

    public Spawner(Jogo jogo) {
        this.jogo = jogo;
    }

    @Override
    public void run() {

        while (running) {
            jogo.esperarSePausado();
            try {
                Thread.sleep(5000);
                jogo.spawnInimigo();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
*/