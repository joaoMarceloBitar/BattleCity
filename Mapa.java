import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Mapa {
    protected char[][] grid = new char[13][13];

    public Mapa(String caminhoArquivo) {
        carregaMapa(caminhoArquivo);
    }

    public void carregaMapa(String caminhoArquivo) {
        try (FileReader reader = new FileReader(caminhoArquivo)) {

            int c;
            int linha = 0;
            int coluna = 0;

            while ((c = reader.read()) != -1) {

                char ch = (char) c;

                if (ch == '\n' || ch == '\r')
                    continue;

                grid[linha][coluna] = ch;
                coluna++;

                if (coluna == 13) {
                    coluna = 0;
                    linha++;
                }

                if (linha == 13)
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void imprimeMapa() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    public void atualizarInimigos() {
        char[][] novoGrid = new char[13][13];

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                novoGrid[i][j] = grid[i][j];
            }
        }

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                if (grid[i][j] == 'I') {
                    Direcao d = Direcao.random();

                    int y = 0;
                    int x = 0;

                    switch (d) {
                        case CIMA:
                            y = -1;
                            break;
                        case BAIXO:
                            y = 1;
                            break;
                        case ESQUERDA:
                            x = -1;
                            break;
                        case DIREITA:
                            x = 1;
                            break;
                    }

                    int novoI = i + y;
                    int novoJ = j + x;

                    if (novoI < 0 || novoI >= 13 || novoJ < 0 || novoJ >= 13) {
                        continue;
                    }

                    if (grid[novoI][novoJ] == '_') {
                        novoGrid[novoI][novoJ] = 'I';
                        novoGrid[i][j] = '_';
                    }
                }
            }
        }
        grid = novoGrid;
    }

    public void atualizarJogador(char c) {
        int y = 0, x = 0;
        int i = 0, j = 0;

        switch (c) {
            case 'w':
                y = -1;
                break;
            case 's':
                y = 1;
                break;
            case 'a':
                x = -1;
                break;
            case 'd':
                x = 1;                
                break;
        }

        for (i = 0; i < 13; i++) {
            for (j = 0; j < 13; j++) {
                if (grid[i][j] == 'J') {
                    break;
                }
            }
        }

        int novoI = i + y;
        int novoJ = j + x;

        if (novoI < 0 || novoI >= 13 || novoJ < 0 || novoJ >= 13) {
            return;
        }

        if (grid[novoI][novoJ] == '_') {
                grid[novoI][novoJ] = 'J';
                grid[i][j] = '_';
        }
    }

    public void lerEntrada() {
        char c;
        Scanner scan = new Scanner (System.in);

        String entrada = scan.next();
        c = entrada.charAt(0);

        if (c == 'w' || c == 's' || c == 'a' || c == 'd') {
            atualizarJogador(c);
        } else if (c == 'q') {
            disparar();
        } else if (c == 'x') {
            System.exit(0);
        } else {
            System.out.println("entrada inválida\n");
            scan.close();
            return;
        }
        scan.close();
    }

    public void colisoes() {

    }

    public boolean fimMapa() {

    }
    
    public void disparar() {

    }

    public void spawnaPersonagens() {
        Random random = new Random();

        int linhaPlayer = grid.length - 1;
        int colunaPlayer;

        do {
            colunaPlayer = random.nextInt(grid[0].length);
        } while (grid[linhaPlayer][colunaPlayer] != '_');

        grid[linhaPlayer][colunaPlayer] = 'P';

        int linhaInimigo = 0;

        for (int i = 0; i < 4; i++) {
            int colunaInimigo;

            do {
                colunaInimigo = random.nextInt(grid[0].length);
            } while (grid[linhaInimigo][colunaInimigo] != '_');

            grid[linhaInimigo][colunaInimigo] = 'I';
        }
    }

    public void executar() {
        boolean rodando = true;

        while (rodando) {
            imprimeMapa();
            atualizarInimigos();
            lerEntrada();
            colisoes();
            rodando = fimMapa();
        }
    }
}
