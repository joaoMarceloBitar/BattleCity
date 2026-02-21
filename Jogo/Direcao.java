package Jogo;

public enum Direcao {
    CIMA,
    BAIXO,
    ESQUERDA,
    DIREITA,
    TIRO;


public static Direcao randomica() {
    int dirOp = (int) (Math.random() * 5);
    switch (dirOp) {
        case 0: return CIMA;
        case 1: return BAIXO;
        case 2: return ESQUERDA;
        case 3: return TIRO;
        default: return DIREITA;
    }
}

}
