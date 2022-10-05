package expression;

public class VariableExpression extends Expression {

  private boolean value;
  private String name;

  public VariableExpression(String name) {
    super(null, null);
    this.name = name;
  }

  public boolean getValue() {
    return value;
  }

  public void setValue(boolean value) {
    this.value = value;
  }

  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof VariableExpression)) {
      return false;
    }

    return o.toString().equals(name);
  }
}
