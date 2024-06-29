import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class rsa {

  private BigInteger p;
  private BigInteger q;
  private BigInteger n;
  private BigInteger totienteEuler; 
  private BigInteger e;
  private BigInteger d;
  
  public rsa() {
    this.p = BigInteger.probablePrime(1024 / 2, new Random());
    this.q = BigInteger.probablePrime(1024 / 2, new Random());
    this.n = this.p.multiply(this.q);
    this.totienteEuler = (this.p.subtract(BigInteger.ONE).multiply(this.q.subtract(BigInteger.ONE)));
    this.e = generateE();
    this.d = this.e.modInverse(totienteEuler);
  } 

  public BigInteger getP() {
    return p;
  }

  public void setP(BigInteger p) {
    this.p = p;
  }

  public BigInteger getQ() {
    return q;
  }

  public void setQ(BigInteger q) {
    this.q = q;
  }

  public BigInteger getN() {
    return n;
  }

  public void setN(BigInteger n) {
    this.n = n;
  }

  public BigInteger getTotienteEuler() {
    return totienteEuler;
  }

  public void setTotienteEuler(BigInteger totienteEuler) {
    this.totienteEuler = totienteEuler;
  }

  public BigInteger getE() {
    return e;
  }

  public void setE(BigInteger e) {
    this.e = e;
  }

  public BigInteger getD() {
    return d;
  }

  public void setD(BigInteger d) {
    this.d = d;
  }

  public BigInteger generateE() {
    BigInteger e = new BigInteger("11");
    while (e.compareTo(totienteEuler) == -1) {
      if(e.gcd(totienteEuler).equals(BigInteger.ONE)){
        break;
      }

      e = e.nextProbablePrime();
    }

    return e;
  }

  public List<BigInteger> convertListTextToListASCII(String inputString) {
    List<BigInteger> asciiList = new ArrayList<>();

    for (int i = 0; i < inputString.length(); i+=1){
      char character = inputString.charAt(i);
      int asciiValue = (int) character;

      BigInteger asciiBig = BigInteger.valueOf(asciiValue);
      asciiList.add(asciiBig);
    }

    return asciiList;
  }

  public List<String> convertListASCIIToListText(List<String> inputString) {
    List<String> textList = new ArrayList<>();

    for (int i = 0; i < inputString.size(); i+=1){
      int asciiValue = Integer.parseInt(inputString.get(i));

      char character = (char) asciiValue;

      textList.add(String.valueOf(character));
    }

    return textList;
  }

  public String concatListString(List<String> list) {
    StringBuilder sb = new StringBuilder();
    for (String element : list) {
      sb.append(element);
    }

    return sb.toString();
  }

  public List<String> encryptMessage(List<BigInteger> listAscii) {
    List<String> listCrypted = new ArrayList<>();
    for(int i = 0; i < listAscii.size(); i += 1) {
      BigInteger asciiCrypted = listAscii.get(i).modPow(e, n);
      listCrypted.add(asciiCrypted.toString());
    }
    
    return listCrypted;
  }


  public String decryptMessage(List<String> listCrypted) {    
    List<String> listDecrypted = new ArrayList<>();
    for(int i = 0; i < listCrypted.size(); i += 1) {
      BigInteger asciiDecryted = new BigInteger(listCrypted.get(i)).modPow(d, n);
      
      listDecrypted.add(asciiDecryted.toString());
    }
    
    List<String> textList = convertListASCIIToListText(listDecrypted);

    return concatListString(textList);
  }
}
