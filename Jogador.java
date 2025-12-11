public class Jogador extends Personagem {
    int dano;

    public Jogador(int horiz,int verti, Direcao ultimaDirecao) {
        super(horiz,verti,ultimaDirecao);
        this.vida = 3;
        this.dano = 1;
    }

    public boolean podeAndar() {
        return true;
    }

    public boolean podeQuebrar() {
        return false;
    }
}
