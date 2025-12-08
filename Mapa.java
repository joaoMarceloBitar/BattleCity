import java.io.FileReader;
import java.io.IOException;

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
}
