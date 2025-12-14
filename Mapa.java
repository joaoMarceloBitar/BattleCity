import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Mapa {
    private static final int TAM = 13;

    protected char[][] mapaBase = new char[TAM][TAM];
    protected char[][] grid = new char[TAM][TAM];

    public Mapa(String caminhoArquivo) {
        carregaMapa(caminhoArquivo);
        resetarMapa();
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

                mapaBase[linha][coluna] = ch;
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

    public void resetarMapa() {
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                grid[i][j] = mapaBase[i][j];
            }
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

    public void colisoes() {

    }

    public boolean fimMapa() {
        return true;
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
            colisoes();
            rodando = fimMapa();
        }
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

}