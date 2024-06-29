import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        rsa RSA = new rsa();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a mensagem a ser criptografada: ");
        String nome = scanner.nextLine();
        System.out.println(String.format("Mensagem original: %s", nome));
        
        System.out.println("--------------------------------");
        List<BigInteger> listASCII = RSA.convertListTextToListASCII(nome);
        List<String> cryptedMessage = RSA.encryptMessage(listASCII);
        System.out.println(String.format("Mensagem criptografada: %s", RSA.concatListString(cryptedMessage)));
        
        System.out.println("--------------------------------");
        String nAsString = RSA.getN().toString();
        System.out.println(String.format("Chave pública: (%s, %s)", nAsString, RSA.getE().toString()));
        
        System.out.println("--------------------------------");
        System.out.println(String.format("Chave privada: (%s, %s)", nAsString, RSA.getD().toString()));
        
        System.out.println("--------------------------------");

        System.out.println(String.format("Mensagem descriptografada: %s", RSA.decryptMessage(cryptedMessage)));
      
        scanner.close();
    }
}
