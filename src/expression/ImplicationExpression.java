package expression;

public class ImplicationExpression extends Expression {

  public ImplicationExpression(Expression left, Expression right) {
    super(left, right);
  }

  public boolean getValue() {
    return (!left.getValue() | right.getValue());
  }

  public String toString() {
    return ("(" + left.toString() + " -> " + right.toString() + ")");
  }
}
