package ru.masmirnov.sd.visitor;

import ru.masmirnov.sd.visitor.token.Token;
import ru.masmirnov.sd.visitor.tokenizer.ExprTokenizer;
import ru.masmirnov.sd.visitor.visitor.*;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class CalculatorTerminal {

    public static void main(String[] args) {
        System.out.println(
                "Type  calc <expression>  to calculate an expression,\n" +
                "Or    exit               to halt the program"
        );
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            switch (scanner.next().toLowerCase()) {
                case "calc":
                    try {
                        String expr = scanner.nextLine();

                        InputStream is = new ByteArrayInputStream(expr.getBytes());
                        List<Token> tokens = new ExprTokenizer().tokenize(is);
                        System.out.print("Tokens: ");
                        new PrintVisitor(System.out).printExpr(tokens);
                        System.out.println();

                        List<Token> parsed = new ParserVisitor().parse(tokens);
                        System.out.print("RPN:    ");
                        new PrintVisitor(System.out).printExpr(parsed);
                        System.out.println();

                        System.out.println("Result: " + new CalcVisitor().calculate(parsed));
                    } catch (RuntimeException | IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "exit":
                    System.out.println("Bye");
                    System.exit(0);
                default:
                    System.out.println("Unknown command");
            }
        }
    }

}
