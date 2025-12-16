public class Jogador extends Personagem {
    int dano;

    public Jogador(int horiz, int verti, Direcao ultimaDirecao) {
        super(horiz, verti, ultimaDirecao);
        this.vida = 3;
        this.dano = 1;
    }

    public void andar(Direcao direcao) {
        if (direcao == Direcao.CIMA) {
            this.verti--;
            ultimaDirecao = Direcao.CIMA;
        }
        if (direcao == Direcao.BAIXO) {
            this.verti++;
            ultimaDirecao = Direcao.BAIXO;
        }
        if (direcao == Direcao.DIREITA) {
            this.horiz++;
            ultimaDirecao = Direcao.DIREITA;
        }
        if (direcao == Direcao.ESQUERDA) {
            this.horiz--;
            ultimaDirecao = Direcao.ESQUERDA;
        }
    }

    public int proximoX(Direcao d) {
        if (d == Direcao.DIREITA)
            return horiz + 1;
        if (d == Direcao.ESQUERDA)
            return horiz - 1;
        return horiz;
    }

    public int proximoY(Direcao d) {
        if (d == Direcao.CIMA)
            return verti - 1;
        if (d == Direcao.BAIXO)
            return verti + 1;

        return verti;
    }

    public boolean podeQuebrar() {
        return false;
    }

    public Disparo atirar(Direcao direcaoTiro){

        int xTiro = this.proximoX(direcaoTiro);
        int yTiro = this.proximoY(direcaoTiro);

        Disparo tiro = new Disparo(ultimaDirecao,xTiro,yTiro);
        return tiro;
    }

    @Override
    public char getChar() {
        return 'P';
    }
}
