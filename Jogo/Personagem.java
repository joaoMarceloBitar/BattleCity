package Jogo;

public abstract class Personagem extends Entidade {
    protected Direcao ultimaDirecao;
    protected int oldX;
    protected int oldY;

    public Personagem(int horiz, int verti, Direcao direcao) {
        super(horiz, verti, true);
        this.oldX = horiz;
        this.oldY = verti;
        this.ultimaDirecao = direcao;
    }

    public void setDirecao(Direcao d) {
        this.ultimaDirecao = d;
    }

    public Disparo disparar(Direcao ultimaDirecao, int x, int y) {
        return new Disparo(ultimaDirecao,x,y);
    }

    public Direcao getUltimaDirecao() {
        return ultimaDirecao;
    }

    public int getOldX() { return oldX; }
    public int getOldY() { return oldY; }

    public void andar(Direcao direcao) {
        this.oldX = this.horiz;
        this.oldY = this.verti;

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
}
