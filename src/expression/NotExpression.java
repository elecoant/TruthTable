package expression;

public class NotExpression extends Expression {

  public NotExpression(Expression expr) {
    super(expr, null);
  }

  public boolean getValue() {
    return (!left.getValue());
  }

  public String toString() {
    return ("not " + left.toString());
  }
}
