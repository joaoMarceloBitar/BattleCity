import java.util.Scanner;

public class Jogo {
    private Mapa mapa;
    private Jogador player;

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

        int numMapa = min + (int)(Math.random() * ((max - min) + 1));

        mapa = new Mapa("mapa" + numMapa + ".txt");

        mapa.spawnaPersonagens();

        mapa.executar();
    }

    private void desenhar() {
        mapa.imprimeMapa();
    }

    public static int menu () {
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
}
