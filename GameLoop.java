public class GameLoop implements Runnable {

    private boolean running=true;

    @Override
    public void run() {
        while(running) {
            atualizarJogo();
            renderizarJogo();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void atualizarJogo() {
        // Lógica de atualização do jogo
    }
    private void renderizarJogo() {
        // Lógica de renderização do jogo
    }
    
}
