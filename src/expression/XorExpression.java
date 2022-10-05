package expression;

public class XorExpression extends Expression {

  public XorExpression(Expression left, Expression right) {
    super(left, right);
  }

  public boolean getValue() {
    return (left.getValue() ^ right.getValue());
  }

  public String toString() {
    return ("(" + left.toString() + " xor " + right.toString() + ")");
  }
}
