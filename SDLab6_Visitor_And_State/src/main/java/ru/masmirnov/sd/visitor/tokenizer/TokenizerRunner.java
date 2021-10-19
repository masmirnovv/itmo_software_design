package ru.masmirnov.sd.visitor.tokenizer;

import ru.masmirnov.sd.visitor.token.Token;
import ru.masmirnov.sd.visitor.visitor.CalcVisitor;
import ru.masmirnov.sd.visitor.visitor.ParserVisitor;
import ru.masmirnov.sd.visitor.visitor.PrintVisitor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TokenizerRunner {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String expr = scanner.nextLine();
            ExprTokenizer et = new ExprTokenizer();

            List<Token> tokens = et.tokenize(new ByteArrayInputStream(expr.getBytes()));
            new PrintVisitor(System.out).printExpr(tokens);
            List<Token> parsed = new ParserVisitor().parse(tokens);
            new PrintVisitor(System.out).printExpr(parsed);
            System.out.println(new CalcVisitor().calculate(parsed));
        }
    }

}
