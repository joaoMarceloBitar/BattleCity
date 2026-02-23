package Jogo;

public class PM extends Personagem implements Atingivel {
    private boolean parado = true;

    public PM (int horiz, int verti, Direcao ultimaDirecao) {
        super(horiz, verti, ultimaDirecao);
        this.vivo = true;
    }

    public void setParado(boolean estado) {
        this.parado = estado;
    }

    public boolean getParado() {
        return this.parado;
    }

    public Direcao decidirMovimento(Jogador player) {
        if (this.getParado()) return null;

        return Direcao.randomica(); 
    }


    @Override
    public char getChar() {
        return 'M';
    }

    @Override
    public char getCharAtingido() {
        throw new UnsupportedOperationException("Unimplemented method 'getCharAtingido'");
    }
}
