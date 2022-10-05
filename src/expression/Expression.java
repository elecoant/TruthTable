package expression;

public abstract class Expression {

  protected Expression left;
  protected Expression right;

  public Expression(Expression left, Expression right) {
    this.left = left;
    this.right = right;
  } 

  public Expression getLeft() {
    return left;
  }

  public Expression getRight() {
    return right;
  }

  public abstract boolean getValue();

  public abstract String toString();
}

