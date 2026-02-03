package Main;

import Interface.*;
import Jogo.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Jogo jogo = new Jogo();

        SwingUtilities.invokeLater(() -> {
            TelaInicial menu = new TelaInicial(jogo);
            menu.setVisible(true);
        });
    }
}