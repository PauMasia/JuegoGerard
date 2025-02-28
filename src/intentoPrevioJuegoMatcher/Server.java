package intentoPrevioJuegoMatcher;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(9999);
             var exe = Executors.newVirtualThreadPerTaskExecutor();
        ) {
            System.out.println("Esperando a los jugadores");
            Socket cli1 = server.accept();
            cli1.getOutputStream().write("Conectado, esperando jugador 2".getBytes());
            System.out.println("Jugador 1 conectado, esperando al jugador 2");
            Socket cli2 = server.accept();
            cli1.getOutputStream().write("Iniciando partida".getBytes());
            cli2.getOutputStream().write("Iniciando partida".getBytes());

            MatcherTable table1 = new MatcherTable();
            MatcherTable table2 = new MatcherTable();

            exe.submit(() -> {
                table1.addPairs();
                table1.aviso(cli1);
            });
            exe.submit(() -> {
                table2.addPairs();
                table1.aviso(cli2);
            });
            AtomicInteger cont = new AtomicInteger();
            //dentro de el trywith
            //var reader1 = new BufferedReader(new InputStreamReader(cli1.getInputStream()));
            //var reader2 = new BufferedReader(new InputStreamReader(cli2.getInputStream()))

                try(OutputStream out1 = cli1.getOutputStream(); OutputStream out2 = cli2.getOutputStream();){
                    while (true) {
                        System.out.println("Que comience el juego");
                        if (cont.get() % 2 == 0) {
//                        String raw= cli1.getInputStream().toString();
//                            out1.write("\nTu turno".getBytes());
                            out2.write("\nTurno del jugador 1".getBytes());

                            table1.mostrarPlayerTabla(cli1);

                            table1.tryToHit(cli1);


                        } else {
                            out1.write("\nTurno del jugador 2".getBytes());
                            out1.flush();

                            out2.write("\nTu turno".getBytes());
                            out2.flush();
                            table2.mostrarTabla(cli2);

                            table2.tryToHit(cli2);
                        }

                        //Dar victoria
                        if (table1.table.equals(table1.playerTable)) {
                            out1.write("\nGanaste".getBytes());
                            out2.write("\nPerdiste".getBytes());
                            break;
                        }else if (table2.table.equals(table2.playerTable)) {
                            out1.write("\nPerdiste".getBytes());
                            out2.write("\nGanaste".getBytes());
                            break;
                        }
                        cont.getAndIncrement();
                    }
                } catch (IOException e) {
                    System.out.println("Error en el input");
                    e.printStackTrace();
                }


        } catch (IOException a) {
            a.printStackTrace();
        }
    }
}

