package intentoPrevioJuegoMatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MatcherTable {
    String[][] table = new String[4][4];
    ;
    String[][] playerTable = new String[4][4];
    String[] symbols = {"\uD83D\uDC36",
            "\uD83D\uDC31",
            "\uD83D\uDC30",
            "\uD83D\uDC38",
            "\uD83E\uDD81",
            "\uD83D\uDC22",
            "\uD83D\uDC26",
            "\uD83D\uDC2D"
    };

    //Constructor
    public MatcherTable() {
    }

    //Añade parejas en orden y muestra las posiciones
    public void addPairs() {
        int[] coord;
        for (String sym : symbols) {
            for (int count = 0; count <= 1; count++) {
                coord = getCoordinates();
//                System.out.println(coord[0] + ", " + coord[1]);
                table[coord[0]][coord[1]] = sym;
            }
        }
    }

    //Coge cordenadas aleatorias, sin que coincidan con existentes
    public int[] getCoordinates() {
        int x;
        int y;

        x = getPosition();
        y = getPosition();

        if (checkPosition(x, y)) {
//            System.out.println(x + ", " + y);
            return getCoordinates();
        }
        return new int[]{x, y};
    }

    //Genera un numero de posicion aleatorio 0-3
    public int getPosition() {
        int res = (int) (Math.random() * 4);
//        System.out.println(res);
        return res;
    }

    // Comprueba que la posicion este ocupada
    public boolean checkPosition(int x, int y) {
//        System.out.println(table[x][y] == null);
        if (table[x][y] == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        String res = "";
        for (String[] a : table) {
            res += "\n-------------------\n";
            for (String e : a) {
                res += e + " | ";

            }
        }
        res += "\n*********************************";
        return res;
    }

    //Solo println de toda la tabla
    public void mostrarTabla(Socket cli) throws IOException {
        for (String[] a : table) {
            System.out.print("\n-------------------\n");
            for (String e : a) {
                System.out.print(e + " | ");

            }
        }
        System.out.print("\n*********************************");
    }
    //Mostrar la tabla oculta para cada cliente
    public void mostrarPlayerTabla(Socket cli) throws IOException {
        for (String[] a : playerTable) {
            cli.getOutputStream().write("\n-------------------\n".getBytes());
            cli.getOutputStream().flush();
            for (String e : a) {
                if (e == null) {
                    cli.getOutputStream().write("  | ".getBytes());
                    cli.getOutputStream().flush();
                } else {
                    cli.getOutputStream().write((e + " | ").getBytes());
                    cli.getOutputStream().flush();
                }
            }
        }
        cli.getOutputStream().write("\n*********************************".getBytes());
        cli.getOutputStream().flush();
    }

    public Boolean tryToHit(Socket cli) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(cli.getInputStream()));
        cli.getOutputStream().write("\nIntroduce las coordenadas de tu primer intento (formato: x,y):\n".getBytes());
        cli.getOutputStream().flush();

        String raw1 = sc.readLine();
        String raw2 = "";
        String regex = "^[0-3],[0-3]$";

        if (raw1.matches(regex)) {
            raw2 = sc.readLine();
            if (!raw2.matches(regex)) {
                System.out.println("negro");
                aviso(cli);
                tryToHit(cli);
            } else if ((raw1.equals(raw2))) {
                System.out.println("Las posiciones son iguales, no puede ser");
                tryToHit(cli);

            }
        } else {
            System.out.println("negro2");
            aviso(cli);
            tryToHit(cli);
        }

//        if (raw1.matches(regex)) {
//            System.out.println("Si");
//        }


        int[] pos1 = rellenar(raw1);
        int[] pos2 = rellenar(raw2);
//
        if (checkPositionIgual(pos1, pos2)) {
            playerTable[pos1[0]][pos1[1]] = table[pos1[0]][pos1[1]];
            playerTable[pos2[0]][pos2[1]] = table[pos2[0]][pos2[1]];
            mostrarPlayerTabla(cli);
            return true;
        } else {
            System.out.println("Has fallado");
            return false;
        }
    }

    //Verificar si los dibujos de las posiciones es el mismo o no
    public boolean checkPositionIgual(int[] x, int[] y) {
        System.out.println(table[x[0]][x[1]].equals(table[y[0]][y[1]]));
        if (table[x[0]][x[1]].equals(table[y[0]][y[1]])) {
            return false;
        } else {
            return true;
        }
    }


    //Funcion para el metodo de arriba, exclusivamente, sacar el array de las posiciones
    public int[] rellenar(String raw) {
        int[] position = new int[2];

        int cont = 0;
        try {

            for (String txt : raw.split(",")) {
                position[cont] = Integer.parseInt(txt);
                cont++;
            }

        } catch (ArrayIndexOutOfBoundsException a) {
            System.out.println(a.getMessage());
        }
        return position;
    }

    //Aviso de formato
    public void aviso(Socket cli)  {
        try {
            cli.getOutputStream().write("\nRecuerda que la posicion debe indicarse en el siguiente formato 2,3".getBytes());
            cli.getOutputStream().flush();
        }catch (IOException a){
            System.out.println("error aviso");
        }
    }
}
