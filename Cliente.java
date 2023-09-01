import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Scanner entrada = new Scanner(System.in);
        String texto = "";
        // socket do cliente
        Socket cliente = null;
        PrintStream saida = null;

        try{
            cliente = new Socket("127.0.0.1",7000);
            saida = new PrintStream(cliente.getOutputStream());
            do {
                texto = entrada.nextLine();
                saida.println(texto);
            } while(!"sair".equals(texto));
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            entrada.close();
            assert cliente != null;
            cliente.close();
        }
    }
}
