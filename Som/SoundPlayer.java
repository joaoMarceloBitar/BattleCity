package Som;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {

    private Clip clipAtual;
    private String caminhoAtual;

    // para a música atual e toca uma nova em loop
    public void tocar(String caminho) {
        if (clipAtual != null && clipAtual.isRunning() && caminho.equals(caminhoAtual)) {
            return;
        }
        parar();

        try {
            URL url = getClass().getResource(caminho);
            if (url == null) {
                System.out.println("Arquivo de som não encontrado: " + caminho);
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            clipAtual = AudioSystem.getClip();
            clipAtual.open(audio);
            this.caminhoAtual = caminho;
            clipAtual.loop(Clip.LOOP_CONTINUOUSLY); // toca em loop infinito
            clipAtual.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Erro ao tocar som: " + e.getMessage());
        }
    }

    public void parar() {
        if (clipAtual != null && clipAtual.isRunning()) {
            clipAtual.stop();
            clipAtual.close();
        }
        clipAtual = null;
        caminhoAtual = null;
    }

    public boolean estaTocando() {
        return clipAtual != null && clipAtual.isRunning();
    }
}