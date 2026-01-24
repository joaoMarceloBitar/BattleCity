public class Disparo extends Entidade{
    Direcao direcaoDisparo;

    public Disparo(Direcao direcao, int horiz, int verti){
        super(horiz, verti,true);
        this.destrutivo=false;
        this.direcaoDisparo = direcao;
    }

    @Override
    public char getChar() {
        return 'O';
    }

    public void move(){
        switch (direcaoDisparo) {
            case CIMA:
                verti--;
                break;
            case BAIXO:
                verti++;
                break;
            case ESQUERDA:
                horiz--;
                break;
            case DIREITA:
                horiz++;
            default:
                break;
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
}
