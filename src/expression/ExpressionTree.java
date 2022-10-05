package expression;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

public class ExpressionTree {

  private String name;
  private Expression root;
  private List<VariableExpression> variables;

  public ExpressionTree(String name, Expression root) {
    this.name = name;
    this.root = root;
    variables = new ArrayList<VariableExpression>();
    fetchVariables(root);
  }

  private void fetchVariables(Expression expr) {
    if (expr == null) {
      return;
    }

    if (expr instanceof VariableExpression && !variables.contains(expr)) {
      variables.add((VariableExpression) expr);
      return;
    }

    fetchVariables(expr.getLeft());
    fetchVariables(expr.getRight());
  }

  public String getEvalutation() {
    String s = "";
    int n = (int) Math.pow(2, variables.size());

    // header
    for (VariableExpression v : variables) {
      s += v + "\t";
    }
    s += name + "\n";

    for (int i = 0; i < n; i++) {
      setValuations(i);
      s += getValuations() + root.getValue() + "\n";
    }

    return s;
  }

  public void toPdf(String filename) throws FileNotFoundException {
    PdfWriter writer = new PdfWriter(filename);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    document.add(new Paragraph(toString()));

    int nbVar = variables.size();
    int rows = (int) Math.pow(2, nbVar);
    int cols = nbVar + 1;

    Table table = new Table(cols);
    Cell cell;

    // Header
    for (VariableExpression v : variables) {
      cell = new Cell().add(new Paragraph(v.toString()));
      cell.setBackgroundColor(WebColors.getRGBColor("#c8c8c8"));
      table.addCell(cell);
    }

    cell = new Cell().add(new Paragraph(name));
    cell.setBackgroundColor(WebColors.getRGBColor("#c8c8c8"));
    table.addCell(cell);

    // Contents
    for (int i = 0; i < rows; i++) {
      setValuations(i);
      for (VariableExpression v : variables) {
        table.addCell(new Cell().add(new Paragraph(Boolean.toString(v.getValue()))));
      }
      table.addCell(new Cell().add(new Paragraph(Boolean.toString(root.getValue()))));
    }

    document.add(table);

    document.close();

    System.out.println("PDF created successfully.");
  }

  private void setValuations(int iteration) {
    int index = 0;
    for (VariableExpression v : variables) {
      v.setValue((iteration & (int) (Math.pow(2, index))) != 0);
      ++index;
    }
  }

  private String getValuations() {
    String s = "";

    for (VariableExpression v : variables) {
      s += v.getValue() + "\t";
    }

    return s;
  }

  public String toString() {
    return name + ": " + root.toString();
  }
}
