package expression;

public class OrExpression extends Expression {

  public OrExpression(Expression left, Expression right) {
    super(left, right);
  }

  public boolean getValue() {
    return (left.getValue() | right.getValue());
  }

  public String toString() {
    return ("(" + left.toString() + " or " + right.toString() + ")");
  }
}
