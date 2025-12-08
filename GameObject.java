public abstract class GameObject {
    protected int x;
    protected int y;
    protected char simbolo;

    public GameObject(int x,int y){
         
    }

    public char getSimbolo() {
        return simbolo;
    }

    public abstract boolean podeAndar();
    public abstract boolean podeQuebrar();
}
