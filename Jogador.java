public class Jogador extends Personagem {
    int dano;

    public Jogador(int horiz, int verti, Direcao ultimaDirecao) {
        super(horiz, verti, ultimaDirecao);
        this.vida = 3;
        this.dano = 1;
    }

    public void andar(Direcao direcao) {
        if (direcao == Direcao.CIMA) {
            if ((this.verti-1) < 0) {
                System.out.println("Não é possivel avançar");
                return;
            }
            this.verti--;
        }
        if (direcao == Direcao.BAIXO) {
            if ((this.verti+1) > 13) {
                System.out.println("Não é possivel avançar");
                return;
            }
            this.verti++;
        }
        if (direcao == Direcao.DIREITA) {
            if ((this.horiz+1) > 13) {
                System.out.println("Não é possivel avançar");
                return;
            }
            this.horiz++;
        }
        if (direcao == Direcao.ESQUERDA) {
            if ((this.horiz-1) < 0) {
                System.out.println("Não é possivel avançar");
                return;
            }
            this.horiz--;
        }
    }

    public boolean podeAndar() {
        return true;
    }

    public boolean podeQuebrar() {
        return false;
    }
}
