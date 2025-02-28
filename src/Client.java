import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in);

        String message;
        while ((message = in.readLine()) != null) {
            System.out.println(message);
            if (message.contains("Adivina el número:")) {
                out.println(scanner.nextLine());
            }
            if (message.contains("¡Has ganado!") || message.contains("ha ganado")) {
                break;
            }
        }

        socket.close();
        scanner.close();
    }
}