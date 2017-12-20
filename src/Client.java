import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;

public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int PORT  = 65533;

    public static void main(String arg[]) throws Exception {
        System.out.println("running client");
        ArrayList<Integer> inputIntArray = new ArrayList<>();
        ArrayList<Integer> primeIntArray = new ArrayList<>();
        String userInt;

        DataOutputStream outToServer = null;
        BufferedReader userInput = null;
        BufferedReader fromServer = null;

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            Socket clientSocket = new Socket(inetAddress, PORT);

            System.out.println("Socket created!");
            //output stream TO socket
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.flush();
            boolean running = true;

            userInput = new BufferedReader(new InputStreamReader(System.in));
            while (running) {
                System.out.println("enter integer (! to close): ");
                userInt = userInput.readLine();
                if (!userInt.equals("!")) {
                    inputIntArray.add( Integer.parseInt(userInt) );
                } else {
                    running = false;
                }
            } // end of while loop

            System.out.println("loop exited");

            //after loop is exited, send array elements to server
            for (int i = 0; i < inputIntArray.size(); i++) {
                outToServer.writeInt(inputIntArray.get(i));
            }

            //get from server
            fromServer = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            primeIntArray.add(fromServer.read());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outToServer != null) {
                    outToServer.close();
                }
                if (userInput != null) {
                    userInput.close();
                }
                if (fromServer != null) {
                    fromServer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("Client finished!");

    }
}
