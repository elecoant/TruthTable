package expression;

public class AndExpression extends Expression {

  public AndExpression(Expression left, Expression right) {
    super(left, right);
  }

  public boolean getValue() {
    return (left.getValue() & right.getValue());
  }

  public String toString() {
    return ("(" + left.toString() + " and " + right.toString() + ")");
  }
}
