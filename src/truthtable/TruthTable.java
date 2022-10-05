package truthtable;

import truthparser.TruthLexer;
import truthparser.TruthParser;

import java.io.FileNotFoundException;
import java.util.Scanner;

import expression.*;
import truthparser.ParserException;

public class TruthTable {
	public static void main(String[] args) {

		String input = "";

		Scanner sc = new Scanner(System.in);

		System.out.println("Input formula > ");
		input = sc.nextLine();

		sc.close();

		TruthLexer lexer = new TruthLexer(input);
		TruthParser parser = new TruthParser(lexer);

		ExpressionTree t = new ExpressionTree(null, null);

		try {
			t = parser.parseFormula();
		} catch (ParserException e) {
			e.printStackTrace();
		}

		try {
			t.toPdf("truthtable.pdf");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
