package expression;

public class ConstantExpression extends Expression {

  private boolean value;

  public ConstantExpression(boolean value) {
    super(null, null);
    this.value = value;
  }

  public boolean getValue() {
    return value;
  }

  public String toString() {
    return Boolean.toString(value);
  }
}
