package expression;

public class EquivalenceExpression extends Expression {

  public EquivalenceExpression(Expression left, Expression right) {
    super(left, right);
  }

  public boolean getValue() {
    return (left.getValue() == right.getValue());
  }

  public String toString() {
    return ("(" + left.toString() + " <-> " + right.toString() + ")");
  }
}
