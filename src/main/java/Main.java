import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public final static int PORT = 8989;

    public static void main(String[] args) {
        CategoriesСalculation categoriesСalculation = new CategoriesСalculation();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept(); PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    System.out.println("Connection accepted");
                    categoriesСalculation.addToBasket(in);
                    out.println("{\"maxCategory\": " + categoriesСalculation.loadFromTSV(new File("categories.tsv")) + " }");
                }
            }
        } catch (IOException e) {
            System.out.println("Server have some problems");
            e.printStackTrace();
        }
    }
}
