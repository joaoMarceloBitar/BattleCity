public class Disparo extends Entidade{
    Direcao direcaoDisparo;

    public Disparo(Direcao direcao, int horiz, int verti){
        super(horiz, verti,true);
        this.destrutivo=false;
        this.direcaoDisparo = direcao;
    }

    @Override
    public char getChar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChar'");
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
}
