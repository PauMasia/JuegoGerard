import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Servidor iniciado. Esperando clientes...");

        Socket clientSocket1 = serverSocket.accept();
        System.out.println("Cliente 1 conectado, esperando al cliente 2");
        Socket clientSocket2 = serverSocket.accept();
        System.out.println("Cliente 2 conectado.");

        PrintWriter out1 = new PrintWriter(clientSocket1.getOutputStream(), true);
        BufferedReader in1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));

        PrintWriter out2 = new PrintWriter(clientSocket2.getOutputStream(), true);
        BufferedReader in2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));

        int secretNumber = new Random().nextInt(100) + 1;
        System.out.println("Número secreto generado: " + secretNumber);

        String guess1, guess2;
        int guessNum1, guessNum2;
        boolean gameWon = false;

        while (!gameWon) {
            // Cli 1
            out1.println("Tu turno. Adivina el número:");
            guess1 = in1.readLine();
            guessNum1 = Integer.parseInt(guess1);

            if (guessNum1 == secretNumber) {
                out1.println("¡Correcto! ¡Has ganado!");
                out2.println("El Cliente 1 ha ganado. El número era: " + secretNumber);
                gameWon = true;
            } else if (guessNum1 < secretNumber) {
                out1.println("Demasiado bajo.");
                out2.println("El Cliente 1 ha adivinado demasiado bajo.");
            } else {
                out1.println("Demasiado alto.");
                out2.println("El Cliente 1 ha adivinado demasiado alto.");
            }

            if (gameWon) break;

            // Cliente 2
            out2.println("Tu turno. Adivina el número:");
            guess2 = in2.readLine();
            guessNum2 = Integer.parseInt(guess2);

            if (guessNum2 == secretNumber) {
                out2.println("¡Correcto! ¡Has ganado!");
                out1.println("El Cliente 2 ha ganado. El número era: " + secretNumber);
                gameWon = true;
            } else if (guessNum2 < secretNumber) {
                out2.println("Demasiado bajo.");
                out1.println("El Cliente 2 ha adivinado demasiado bajo.");
            } else {
                out2.println("Demasiado alto.");
                out1.println("El Cliente 2 ha adivinado demasiado alto.");
            }
        }

        serverSocket.close();
    }
}