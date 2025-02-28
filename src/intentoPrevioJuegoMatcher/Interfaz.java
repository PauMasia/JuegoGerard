package intentoPrevioJuegoMatcher;

import javax.swing.*;
import java.awt.*;

public class Interfaz {

    public static void main(String[] args) {
        // Crear el marco principal
        JFrame frame = new JFrame("Pantalla para 2 Jugadores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Panel principal que contiene los paneles de los jugadores
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 20, 0)); // 1 fila, 2 columnas, con espacio entre columnas

        // Crear panel para el Jugador 1
        JPanel player1Panel = createPlayerPanel("Jugador 1");
        mainPanel.add(player1Panel);

        // Crear panel para el Jugador 2
        JPanel player2Panel = createPlayerPanel("Jugador 2");
        mainPanel.add(player2Panel);

        // Agregar el panel principal al marco
        frame.add(mainPanel, BorderLayout.CENTER);

        // Hacer visible el marco
        frame.setVisible(true);
    }

    private static JPanel createPlayerPanel(String playerName) {
        // Crear panel del jugador
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());

        // Etiqueta para el nombre del jugador
        JLabel playerLabel = new JLabel(playerName, SwingConstants.CENTER);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerPanel.add(playerLabel, BorderLayout.NORTH);

        // Crear la cuadricula 4x4
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 4, 5, 5)); // 4 filas, 4 columnas, con espacio entre celdas

        // Agregar botones a la cuadricla,
//        for (int i = 0; i < 16;) {
            JButton button = new JButton();

            MatcherTable matcherTable = new MatcherTable();
            matcherTable.addPairs();
            for (String [] a:matcherTable.table ){
                for (String e: a){
                    button.setText(e);

                }
            
//            matcherTable.;

            button.setPreferredSize(new Dimension(50, 50)); // Tamaño preferido para los botones
            gridPanel.add(button);
        }

        playerPanel.add(gridPanel, BorderLayout.CENTER);

        return playerPanel;
    }
}
