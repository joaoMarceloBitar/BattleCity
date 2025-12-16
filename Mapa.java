import java.io.FileReader;
import java.io.IOException;

public class Mapa {
    private static final int TAM = 13;
    private Entidade[][] grid;
    private Base base;

    public Mapa(String caminhoArquivo) {
        grid = new Entidade[TAM][TAM];
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

                switch (ch) {
                    case '#':
                        grid[linha][coluna] = new BlocoAco(linha, coluna);
                        break;
                    case '%':
                        grid[linha][coluna] = new BlocoTijolo(linha, coluna);
                        break;
                    case 'B':
                        grid[linha][coluna] = new Base(linha, coluna);
                        grid[linha][coluna] = base;                        
                        break;
                    default:
                        grid[linha][coluna] = null;
                        break;
                }
                coluna++;
                if (coluna == TAM) {
                    coluna = 0;
                    linha++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetarMapa() {
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                if (grid[i][j] == null) {
                    grid[i][j] = new Vazio(i, j);
                }
            }
        }
    }

    public Base getBase() {
        return base;
    }


    public void imprimeMapa(Jogador player, Inimigo i1, Inimigo i2) {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {

                if (i == player.getVerti() && j == player.getHoriz()) {
                    System.out.print(player.getCaractere());
                } 
                else if ((i == i1.getVerti() && j == i1.getHoriz()) || i == i2.getVerti() && j == i2.getHoriz()) {
                    System.out.print('I');
                } 
                else {
                    System.out.print(grid[i][j].getCaractere());
                }
            }
            System.out.println();
        }
    }

    public void imprimeMapa() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                System.out.print(grid[i][j].getCaractere());
            }
            System.out.println();
        }
    }

    public boolean podeMover(Personagem p, Direcao d) {
    int novoX = p.getHoriz();
    int novoY = p.getVerti();

    switch (d) {
        case CIMA:
            novoY--;
            break;
        case BAIXO:
            novoY++;
            break;
        case ESQUERDA:
            novoX--;
            break;
        case DIREITA:
            novoX++;
            break;
        default:
            return false;
    }

    if (novoX < 0 || novoX >= TAM || novoY < 0 || novoY >= TAM) {
        return false;
    }

    return grid[novoY][novoX].podeAtravessar();
}


    /*
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
    */

    public boolean disparar (int y, int x, Direcao direcao, Jogador player, Inimigo i1, Inimigo i2) {
        int dy = 0;
        int dx = 0;

        switch (direcao) {
            case CIMA:
                dx = -1;
                break;
            case BAIXO:
                dx = 1;
                break;
            case ESQUERDA:
                dy = -1;
                break;
            case DIREITA:
                dy = 1;
                break;
            default:
                break;
        }

        x += dx;
        y += dy;

        while (x >= 0 && x < 13 && y >= 0 && y < 13) {
           
            if (i1.taVivo() && i1.getHoriz() == x && i1.getVerti() == y) {
                i1.atingido();
                return false;
            }

            if (i2.taVivo() && i2.getHoriz() == x && i2.getVerti() == y) {
                i2.atingido();
                return false;
            }

            Entidade alvo = grid[y][x];

            if (alvo instanceof Vazio) {
                x += dx;
                y += dy;
                continue;
            }

            alvo.atingido();
            
            if (!alvo.taVivo()) {
                grid[y][x] = new Vazio(x, y);

                if (alvo instanceof Base) {
                    return true;
                }
            }

            break;

        }
        return false;
    }
}