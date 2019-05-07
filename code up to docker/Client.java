
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.Serializable;

class Client {
    public static Socket socket;
    public static ObjectOutputStream out;
    public static ObjectInputStream in;
    public static String HOST = "127.0.0.1";
    public static int INIT_PORT = 1150;

    public static void main(String[] args)
            throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
        // init socket with init port
        socket = new Socket(HOST, INIT_PORT);
        // send "start" to get new port
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("Start");

        // get new port to change connection
        in = new ObjectInputStream(socket.getInputStream());
        String newPort = (String) in.readObject();
        System.out.println("New port => " + newPort);

        //// switch to new port
        socket = new Socket(HOST, Integer.parseInt(newPort));
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        out.writeObject("100");
        String data = (String) in.readObject();
        // System.out.println("data => "+data);
        int totalCharactors = data.substring(6, data.length()).length();
        System.out.println("Total charactors => " + totalCharactors);
        System.out.println("Movie name patern => " + data.substring(6, data.length()));
        int leftTime = Integer.parseInt(data.substring(4, 5));
        System.out.println("Remaining round => " + leftTime);
        while (true) {
            try {
                System.out.println("");
                System.out.print("Answer => ");
                String a = (new Scanner(System.in)).nextLine();
                out.writeObject("150:" + a);
                String response = (String) in.readObject();
                leftTime = Integer.parseInt(response.substring(4, 5));
                String ans = response.substring(6, response.length());

                if (response.substring(0, 3).equals("400")) {
                    System.out.println(a + " is wrong -_-");

                    if (leftTime == 4) {
                        System.out.println();
                        System.out.println("   ____________");
                        System.out.println("   |");
                        System.out.println("   |");
                        System.out.println("   |");
                        System.out.println("   |");
                        System.out.println("   |");
                        System.out.println("   |");
                        System.out.println("   |");
                        System.out.println("___|___");
                        System.out.println();
                    }
                    if (leftTime == 3) {
                        System.out.println();
                        System.out.println("   ____________");
                        System.out.println("   |           |   ");
                        System.out.println("   |           *   ");
                        System.out.println("   |         *   * ");
                        System.out.println("   |           *   ");
                        System.out.println("   |");
                        System.out.println("   |");
                        System.out.println("   |");
                        System.out.println("___|___");
                        System.out.println();
                    }
                    if (leftTime == 2) {
                        System.out.println();
                        System.out.println("   ____________");
                        System.out.println("   |           |   ");
                        System.out.println("   |           *   ");
                        System.out.println("   |         *   * ");
                        System.out.println("   |           *   ");
                        System.out.println("   |           *    ");
                        System.out.println("   |           *    ");
                        System.out.println("   |");
                        System.out.println("___|___");
                        System.out.println();
                    }
                    if (leftTime == 1) {
                        System.out.println();
                        System.out.println("   ____________");
                        System.out.println("   |           |   ");
                        System.out.println("   |           *   ");
                        System.out.println("   |         *   * ");
                        System.out.println("   |           *   ");
                        System.out.println("   |           *    ");
                        System.out.println("   |           *    ");
                        System.out.println("   |         *   *  ");
                        System.out.println("___|___    *       *");
                        System.out.println();
                    }
                    System.out.println("Remaining round => " + leftTime);

                }
                if (response.substring(0, 3).equals("200")) {

                    System.out.println(a + " is correct *b*");
                }
                if (response.substring(0, 3).equals("201")) {
                    System.out.println("Movie name is " + ans);
                    break;
                }
                if (response.substring(0, 3).equals("500")) {
                    System.out.println("Movie name is " + ans);
                    break;
                }
                if (response.substring(0, 3).equals("409")) {
                    System.out.println("This charactor is already used , Remaining round => : " + leftTime);
                }
                System.out.println("Movie name is " + ans);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (leftTime == 0) {
            System.out.println();
            System.out.println();
            System.out.println("   ____________");
            System.out.println("   |           |   ");
            System.out.println("   |           *   ");
            System.out.println("   |         *   * ");
            System.out.println("   |           *   ");
            System.out.println("   |     * * * * * * *    ");
            System.out.println("   |           *    ");
            System.out.println("   |         *   *  ");
            System.out.println("___|___    *       *");
            System.out.println();
            System.out.println("YOU LOSE!!");
        } else {
            System.out.println("");
            System.out.println("YOU WIN ^-^");
        }

        out.close();
        in.close();
        socket.close();

    }
}
