import java.util.Scanner;

public class Jogo {
    private Mapa mapa;

    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        int opcao;

        do {
            opcao = menu();

            switch (opcao) {
                case 1:
                    jogo.iniciar();
                    break;
                case 2:
                    jogo.desenhar();
                    break;
                case 3:
                    System.exit(0);
            }
        } while (opcao < 1 && opcao > 3);
    }

    public void iniciar() {
        int min = 1;
        int max = 3;

        int numMapa = min + (int) (Math.random() * ((max - min) + 1));

        mapa = new Mapa("mapa" + numMapa + ".txt");
        Jogador player = geraJogador();
        Inimigo inimigo1 = geraInimigo();
        Inimigo inimigo2 = geraInimigo();
        Entidade base = new Entidade(7, 13);

        gameLoop(player, inimigo1, inimigo2, base, mapa);

        mapa.spawnaPersonagens();

        mapa.executar();
    }

    private void desenhar() {
        mapa.imprimeMapa();
    }

    public static int menu() {
        int opcao;
        Scanner scan = new Scanner(System.in);

        System.out.println("======= MENU =======");
        System.out.println("1 - Jogar");
        System.out.println("2 - Carregar Mapa");
        System.out.println("3 - Sair");

        opcao = scan.nextInt();
        scan.close();
        return opcao;
    }

    public void gameLoop(Jogador player, Inimigo inimigo1, Inimigo inimigo2, Entidade base, Mapa mapa) {
        while (base.vivo = true) {
            //metodo que atualiza o mapa com a loc dos tanqe
            mapa.imprimeMapa();

            Direcao comando = lerEntrada();
            if(comando==Direcao.TIRO){
                //instancia disparo
            }
        }
    }

    public Direcao lerEntrada() {
        char c;
        Direcao comando=null;
        Scanner scan = new Scanner(System.in);

        String entrada = scan.next();
        c = entrada.charAt(0);
        c = Character.toUpperCase(c);
        System.out.println("---CONTROLES---");
        System.out.println("| W: cima     |\n| A: esquerda |\n| S: baixo    |\n| D: direita  |");
        System.out.println("| Q: atirar   |");
        System.out.println("---------------");
        scan.close();
        
        switch (c) {
            case 'W':
                comando = Direcao.CIMA;
            case 'S':
                comando = Direcao.BAIXO;
            case 'A':
                comando = Direcao.ESQUERDA;
            case 'D':
                comando = Direcao.DIREITA;
            case 'Q':
                comando = Direcao.TIRO;
            case 'X':
                System.exit(0);
                return null;
            default:
                System.out.println("entrada inválida\n");
                lerEntrada();

        }
        return comando;
    }

    public Jogador geraJogador() {
        int xPlayer = (int) (10 * (Math.random()));
        Jogador player = new Jogador(xPlayer, 11, Direcao.CIMA);

        return player;
    }

    public Inimigo geraInimigo() {
        int xInimigo = (int) (10 * (Math.random()));
        Inimigo inimigo = new Inimigo(xInimigo, 2, Direcao.BAIXO);

        return inimigo;
    }
}
