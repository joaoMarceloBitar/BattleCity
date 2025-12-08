public class Jogo {
    private Mapa mapa;
    private Jogador player;

    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        jogo.iniciar();
        jogo.desenhar();
    }

    public void iniciar() {
        int min = 1;
        int max = 3;

        int numMapa = min + (int)(Math.random() * ((max - min) + 1));

        mapa = new Mapa("mapa" + numMapa + ".txt");
    }

    private void desenhar() {
        mapa.imprimeMapa();
    }
}
