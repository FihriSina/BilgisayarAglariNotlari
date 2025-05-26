package uppercaseandplus;
/**
 *
 * @author fihri
 */
import java.io.*;
import java.net.*;

public class UpperCaseServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is running...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    String receivedText = in.readLine();
                    System.out.println("Received: " + receivedText);

                    String upperCaseText = receivedText.toUpperCase();
                    out.println(upperCaseText);
                    System.out.println("Sent: " + upperCaseText);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


