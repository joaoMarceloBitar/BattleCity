package Jogo;

public class Jogador extends Personagem {
    int dano;
    int vida;
    int pontos;
    String nome;
    private boolean invulneravel = false;

    public Jogador(int horiz, int verti, Direcao ultimaDirecao) {
        super(horiz, verti, ultimaDirecao);
        this.vida = 3;
        this.dano = 1;
        this.pontos = 0;
        this.nome = "Jogador";
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getX() {
        return this.horiz;
    }

    public int getY() {
        return this.verti;
    }

    public void setX(int x) {
        this.horiz = x;
    }

    public void setY(int y) {
        this.verti = y;
    }

    public boolean podeQuebrar() {
        return false;
    }

    public int getVida() {
        return this.vida;
    }

    public int getPontos() {
        return this.pontos;
    }

    public void setVida(int vida) {
        this.vida = vida - 1;
    }

    public void setInvulneravel(boolean estado) {
        this.invulneravel = estado;
    }

    public boolean getInvulneravel() {
        return this.invulneravel;
    }

    @Override
    public char getChar() {
        return 'P';
    }
}
