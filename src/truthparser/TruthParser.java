package truthparser;

import java.util.ArrayList;
import java.util.List;

import expression.*;
import truthparser.TruthLexer.Token;
import truthparser.TruthLexer.Operator;

public class TruthParser {

  private TruthLexer lexer;
  private TruthLexer.Token token;

  private final ConstantExpression falseExpression;
  private final ConstantExpression trueExpression;
  private List<VariableExpression> variables;

  public TruthParser(TruthLexer lexer) {
    this.lexer = lexer;
    token = Token.NULL;

    falseExpression = new ConstantExpression(false);
    trueExpression = new ConstantExpression(true);

    variables = new ArrayList<VariableExpression>();
  }

  private void checkTokenIs(Token t) throws ParserException {
    if (token != t) {
      throw new ParserException(
          "expected '" + t.label + "' but got '" + token.label + "' instead (" + lexer.getPosition() + ")");
    }
  }

  private void checkTokenIs(Token[] tarr) throws ParserException {
    for (Token t : tarr) {
      if (token == t) {
        return;
      }
    }

    String msg = "expected {";

    for (Token t : tarr) {
      msg += "'" + t.label + "',";
    }

    msg += "} but got '" + token.label + "' instead (" + lexer.getPosition() + ")";
    throw new ParserException(msg);
  }

  public VariableExpression addVariable(String varname) throws ParserException {
    VariableExpression var = new VariableExpression(varname);
    for (VariableExpression v : variables) {
      if (v.equals(var)) {
        return v;
      }
    }

    if (variables.size() < 8) {
      variables.add(var);
    } else {
      throw new ParserException("cannot take more than 8 propositional variables as input");
    }

    return var;
  }

  public ExpressionTree parseFormula() throws ParserException {
    // prop -> ID COLON expr SEMICOLON
    String name = "";
    token = lexer.getToken();
    checkTokenIs(Token.ID);
    name = lexer.getLastString();

    token = lexer.getToken();
    checkTokenIs(Token.COLON);

    Expression expr = parseExpression();

    token = lexer.getToken();
    checkTokenIs(Token.SEMICOLON);

    return new ExpressionTree(name, expr);
  }

  private Expression parseExpression() throws ParserException {
    // expr -> ID
    // | CONSTANT
    // | notexpr
    // | '(' binexpr ')'

    token = lexer.getToken();

    checkTokenIs(new Token[] { Token.BOPEN, Token.ID, Token.CONSTANT, Token.OPERATOR });

    switch (token) {
      case CONSTANT:
        if (lexer.getLastConstant()) {
          return trueExpression;
        }
        return falseExpression;
      case ID:
        return addVariable(lexer.getLastString());
      case OPERATOR:
        return parseUnaryExpression();
      case BOPEN:
        Expression expr = parseBinaryExpression();
        token = lexer.getToken();
        checkTokenIs(Token.BCLOSE);
        return expr;
      default:
        return null;
    }
  }

  private Expression parseUnaryExpression() throws ParserException {
    // notexpr -> OPERATOR expr

    Operator operator = lexer.getLastOperator();
    Expression expr = parseExpression();
    switch (operator) {
      case NOT:
        return new NotExpression(expr);
      default:
        throw new ParserException("missing left expression (" + lexer.getPosition() + ")");
    }
  }

  private Expression parseBinaryExpression() throws ParserException {
    // binexpr -> expr OPERATOR expr
    Expression leftExpression = parseExpression();
    token = lexer.getToken();
    checkTokenIs(Token.OPERATOR);
    Operator operator = lexer.getLastOperator();
    Expression righExpression = parseExpression();

    switch (operator) {
      case AND:
        return new AndExpression(leftExpression, righExpression);
      case OR:
        return new OrExpression(leftExpression, righExpression);
      case XOR:
        return new XorExpression(leftExpression, righExpression);
      case IMPLIES:
        return new ImplicationExpression(leftExpression, righExpression);
      case EQUALS:
        return new EquivalenceExpression(leftExpression, righExpression);
      case NOT:
        throw new ParserException("extraneous left expression (" + lexer.getPosition() + ")");
      case NULL:
      default:
        throw new ParserException("unhandled null operator");
    }
  }
}
