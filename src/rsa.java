import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class rsa {

  public static BigInteger p = BigInteger.probablePrime(1024 / 2, new Random());
  public static BigInteger q = BigInteger.probablePrime(1024 / 2, new Random());
  public static BigInteger n = p.multiply(q);
  public static BigInteger totienteEuler = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))); //  φ(n) = (p - 1) * (q - 1);
  public static BigInteger e = generateE();
  public static BigInteger d = e.modInverse(totienteEuler);
  public static BigInteger privateKey;


  public static BigInteger generateE() {
    BigInteger e = new BigInteger("11");
    while (e.compareTo(totienteEuler) == -1) {
      if(e.gcd(totienteEuler).equals(BigInteger.ONE)){
        break;
      }

      e = e.nextProbablePrime();
    }

    return e;
  }

  public static List<BigInteger> convertListTextToListASCII(String inputString) {
    List<BigInteger> asciiList = new ArrayList<>();

    for (int i = 0; i < inputString.length(); i+=1){
      char character = inputString.charAt(i);
      int asciiValue = (int) character;

      BigInteger asciiBig = BigInteger.valueOf(asciiValue);
      asciiList.add(asciiBig);
    }

    return asciiList;
  }

  public static List<String> convertListASCIIToListText(List<String> inputString) {
    List<String> textList = new ArrayList<>();

    for (int i = 0; i < inputString.size(); i+=1){
      int asciiValue = Integer.parseInt(inputString.get(i));

      char character = (char) asciiValue;

      textList.add(String.valueOf(character));
    }

    return textList;
  }

  public static String concatListString(List<String> list) {
    StringBuilder sb = new StringBuilder();
    for (String element : list) {
      sb.append(element);
    }

    return sb.toString();
  }

  public static List<String> encryptMessage(List<BigInteger> listAscii) {
    List<String> listCrypted = new ArrayList<>();
    for(int i = 0; i < listAscii.size(); i += 1) {
      BigInteger asciiCrypted = listAscii.get(i).modPow(e, n);
      listCrypted.add(asciiCrypted.toString());
    }
    
    return listCrypted;
  }


  public static String decryptMessage(List<String> listCrypted) {    
    List<String> listDecrypted = new ArrayList<>();
    for(int i = 0; i < listCrypted.size(); i += 1) {
      BigInteger asciiDecryted = new BigInteger(listCrypted.get(i)).modPow(d, n);
      
      listDecrypted.add(asciiDecryted.toString());
    }
    
    List<String> textList = convertListASCIIToListText(listDecrypted);

    return concatListString(textList);
  }


  public static void main(String[] args) {
    
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("Digite a mensagem a ser criptografada: ");
    String nome = scanner.nextLine();
    System.out.println(String.format("Mensagem original: %s", nome));
    
    
    System.out.println("--------------------------------");
    List<BigInteger> listASCII = convertListTextToListASCII(nome);
    List<String> cryptedMessage = encryptMessage(listASCII);
    System.out.println(String.format("Mensagem criptografada: %s", concatListString(cryptedMessage)));
    
    System.out.println("--------------------------------");
    String nAsString = n.toString();
    System.out.println(String.format("Chave pública: (%s, %s)", nAsString, e.toString()));
    
    System.out.println("--------------------------------");
    System.out.println(String.format("Chave privada: (%s, %s)", nAsString, d.toString()));
    
    System.out.println("--------------------------------");

    System.out.println(String.format("Mensagem descriptografada: %s", decryptMessage(cryptedMessage)));

    scanner.close();
  }

}
