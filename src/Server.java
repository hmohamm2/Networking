import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static int PORT  = 65533;

    public static void main(String arg[]) throws Exception {
        ArrayList<Integer> fromClientArray = new ArrayList<>();
        ArrayList<Integer> toClientArray = new ArrayList<>();

        ServerSocket welcomeSocket = null;
        Socket connectionSocket = null;
        DataOutputStream sendToClient = null;
        BufferedReader fromClient = null;
        try {
            welcomeSocket = new ServerSocket(PORT);

            System.out.println("Socket created!");

            while (true) {
                connectionSocket = welcomeSocket.accept();

                System.out.println("socket accessed" + connectionSocket);

                fromClient = new BufferedReader(
                        new InputStreamReader(connectionSocket.getInputStream()));

                fromClientArray.add(fromClient.read());


                sendToClient = new DataOutputStream(
                        connectionSocket.getOutputStream());
                sendToClient.flush();
                //sort out prime numbers to send back to client, use a method
                toClientArray = sortPrime(fromClientArray);

                for (int index = 0; index < toClientArray.size(); index++) {
                    sendToClient.writeInt(toClientArray.get(index));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                if (welcomeSocket != null) {
                    welcomeSocket.close();
                }
                if (connectionSocket != null) {
                    connectionSocket.close();
                }
                if (sendToClient != null) {
                    sendToClient.close();
                }
                if (fromClient != null) {
                    fromClient.close();
                }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    System.out.println("Server finished");
    }

    public static ArrayList<Integer> sortPrime(ArrayList<Integer> integerList) {
        ArrayList<Integer> primeArray = new ArrayList<>();

        for (int index = 0; index < integerList.size(); index++) {
            int n = integerList.get(index);
            //second for loop to test if prime
            for (int i = 2; i < n; i++) {
                if ( (n % i) > 0 ) {
                    primeArray.add(n);
                }
            } // end second for loop
        } // end first for loop

        return primeArray;

    }

}
