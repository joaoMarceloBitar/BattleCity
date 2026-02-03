package Jogo;

public class Jogador extends Personagem {
    int dano;
    int vida;
    int pontos;

    public Jogador(int horiz, int verti, Direcao ultimaDirecao) {
        super(horiz, verti, ultimaDirecao);
        this.vida = 3;
        this.dano = 1;
        this.pontos = 0;
    }

    /*
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
        
    public Disparo atirar(Direcao direcaoTiro){

        int xTiro = this.proximoX(direcaoTiro);
        int yTiro = this.proximoY(direcaoTiro);

        Disparo tiro = new Disparo(ultimaDirecao,xTiro,yTiro);
        return tiro;
    }
    */

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

    public void setVida() {
        this.vida = this.vida - 1;
    }

    @Override
    public char getChar() {
        return 'P';
    }
}
