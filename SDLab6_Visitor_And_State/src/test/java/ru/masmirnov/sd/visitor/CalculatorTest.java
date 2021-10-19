package ru.masmirnov.sd.visitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.masmirnov.sd.visitor.token.Token;
import ru.masmirnov.sd.visitor.tokenizer.ExprTokenizer;
import ru.masmirnov.sd.visitor.visitor.CalcVisitor;
import ru.masmirnov.sd.visitor.visitor.ParserVisitor;
import ru.masmirnov.sd.visitor.visitor.PrintVisitor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

public class CalculatorTest {

    @Test
    public void e2eTest1() throws IOException {
        e2eTest(
                "    \t        2     +    2  *  2      ",
                "NUMBER(2) PLUS NUMBER(2) MULTIPLY NUMBER(2) ",
                "NUMBER(2) NUMBER(2) NUMBER(2) MULTIPLY PLUS ",
                new BigInteger("6")
        );
    }

    @Test
    public void e2eTest2() throws IOException {
        e2eTest(
                "((12 + 34) / (65 - 66)) * 100",
                "OPEN OPEN NUMBER(12) PLUS NUMBER(34) CLOSE DIVIDE OPEN NUMBER(65) MINUS NUMBER(66) CLOSE CLOSE MULTIPLY NUMBER(100) ",
                "NUMBER(12) NUMBER(34) PLUS NUMBER(65) NUMBER(66) MINUS DIVIDE NUMBER(100) MULTIPLY ",
                new BigInteger("-4600")
        );
    }

    @Test
    public void leftAssociationTest() throws IOException {
        e2eTest(
                "100 - 1 - 1",
                "NUMBER(100) MINUS NUMBER(1) MINUS NUMBER(1) ",
                "NUMBER(100) NUMBER(1) MINUS NUMBER(1) MINUS ",
                new BigInteger("98")
        );
    }

    @Test
    public void bigIntegerE2eTest() throws IOException {
        e2eTest(
                "10000000000000000000000000000 + 10000000000000000000000000000",
                "NUMBER(10000000000000000000000000000) PLUS NUMBER(10000000000000000000000000000) ",
                "NUMBER(10000000000000000000000000000) NUMBER(10000000000000000000000000000) PLUS ",
                new BigInteger("20000000000000000000000000000")
        );
    }

    private static void e2eTest(String expr, String expectedTokens, String expectedPolishForm,
                                BigInteger expectedResult) throws IOException {
        InputStream bytes = new ByteArrayInputStream(expr.getBytes());

        List<Token> tokens = new ExprTokenizer().tokenize(bytes);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        new PrintVisitor(out).printExpr(tokens);
        String tokensStr = out.toString();
        Assertions.assertEquals(tokensStr, expectedTokens);

        List<Token> parsed = new ParserVisitor().parse(tokens);
        out = new ByteArrayOutputStream();
        new PrintVisitor(out).printExpr(parsed);
        String parsedStr = out.toString();
        Assertions.assertEquals(parsedStr, expectedPolishForm);

        BigInteger result = new CalcVisitor().calculate(parsed);
        Assertions.assertEquals(result, expectedResult);
    }

}
