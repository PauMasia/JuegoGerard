package intentoPrevioJuegoMatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        while (true){
            try (Socket socket = new Socket("localhost", 9999);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                 Scanner sc = new Scanner(System.in);
            ) {

                String serverMessage;
                while (!((serverMessage = in.readLine()).equals("end"))) {
                    System.out.println(serverMessage);

                    if (serverMessage.contains("Tu turno")) {
                        System.out.println(serverMessage);
                        String coord1 = sc.nextLine();
                        writer.println(coord1);
                        writer.flush();
                        String coord2 = sc.nextLine();
                        writer.println(coord2);
                        writer.flush();
                    } else if (serverMessage.contains("FelAicidades")) {
                        break;
                    }
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());;
            }catch (Exception a){
                System.out.println("Intentando reconectar...");
            }
        }
    }
}