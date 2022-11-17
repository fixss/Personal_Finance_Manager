import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public final static int port = 8989;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                System.out.println("Connection accepted");
                final String name = in.readLine();
                out.println(String.format("Hi %s, your port is %d", name, clientSocket.getPort()));
            }
        } catch (IOException e) {
            System.out.println("Server have some problems");
            e.printStackTrace();
        }
    }
}
