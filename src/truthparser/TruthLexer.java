package truthparser;

public class TruthLexer {

  private String inputString;

  private int inputIndex;
  private char lastChar;
  private String lastString;
  private Operator lastOperator;
  private boolean lastConstant;
  private boolean eof;

  public TruthLexer(String inputString) {
    this.inputString = inputString;
    inputIndex = 0;
    lastChar = 0;
    lastString = "";
    lastOperator = Operator.NULL;
    lastConstant = false;
    eof = false;
  }

  public static enum Token {
    BOPEN("("),
    BCLOSE(")"),
    COLON(":"),
    SEMICOLON(";"),
    OPERATOR("operator"),
    CONSTANT("constant"),
    ID("id"),
    EOF("end-of-file"),
    NULL("null");

    public final String label;

    private Token(String label) {
      this.label = label;
    }
  }

  public static enum Operator {
    NOT,
    AND,
    OR,
    XOR,
    IMPLIES,
    EQUALS,
    NULL
  }

  private void readChar() {
    if (eof) {
      return;
    }

    if (inputIndex < inputString.length()) {
      lastChar = inputString.charAt(inputIndex);
      inputIndex++;
    }
    else {
      eof = true;
    }
  }

  private boolean isWhitespace() {
    return (Character.toString(lastChar).isBlank());
  }

  public Token readString() throws ParserException {
    while (!eof && Character.isAlphabetic(lastChar)) {
      lastString += lastChar;
      readChar();
    }

    if (lastString.isEmpty()) {
      throw new ParserException("unexpected character '" + lastChar + "'");
    }

    switch (lastString) {
      case "not":
        lastOperator = Operator.NOT;
        return Token.OPERATOR;
      case "and":
        lastOperator = Operator.AND;
        return Token.OPERATOR;
      case "or":
        lastOperator = Operator.OR;
        return Token.OPERATOR;
      case "xor":
        lastOperator = Operator.XOR;
        return Token.OPERATOR;
      case "implies":
        lastOperator = Operator.IMPLIES;
        return Token.OPERATOR;
      case "equals":
        lastOperator = Operator.EQUALS;
        return Token.OPERATOR;
      case "true":
        lastConstant = true;
        return Token.CONSTANT;
      case "false":
        lastConstant = false;
        return Token.CONSTANT;
      default:
        return Token.ID;
    }
  }

  public Token getToken() throws ParserException {
    if (lastString.isEmpty()) {
      readChar();
    } else {
      lastString = "";
    }

    // Consume whitespaces
    while (!eof && isWhitespace()) {
      readChar();
    }

    if (eof) {
      return Token.EOF;
    }

    switch (lastChar) {
      case '(':
        return Token.BOPEN;
      case ')':
        return Token.BCLOSE;
      case ':':
        return Token.COLON;
      case ';':
        return Token.SEMICOLON;
      default:
        return readString();
    }
  }

  public String getLastString() {
    return lastString;
  }

  public Operator getLastOperator() {
    return lastOperator;
  }

  public boolean getLastConstant() {
    return lastConstant;
  }

  public int getPosition() {
    return inputIndex;
  }
}
