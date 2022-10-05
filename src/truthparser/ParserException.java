package truthparser;

public class ParserException extends Exception {
  public ParserException(String msg) {
    super(msg);
    System.err.println(msg);
    System.exit(0);
  }
}
