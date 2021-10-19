package ru.masmirnov.sd.visitor.visitor;

import ru.masmirnov.sd.visitor.token.*;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CalcVisitor implements TokenVisitor {

    private LinkedList<Token> stack;

    public BigInteger calculate(List<Token> expr) {
        stack = new LinkedList<>();

        for (Token token : expr) {
            token.accept(this);
        }

        if (stack.size() != 1) {
            throw new IllegalStateException(
                    "Calculation exception: expression size > 1 after calculation"
            );
        }
        return popNumberFromStack();
    }


    @Override
    public void visit(NumberToken token) {
        stack.addLast(token);
    }

    @Override
    public void visit(Brace token) {
        throw new IllegalStateException(
                "Assertion error: no braces should be in Reverse Polish Notation"
        );
    }

    @Override
    public void visit(Operation token) {
        BigInteger arg1 = popNumberFromStack();
        BigInteger arg2 = popNumberFromStack();
        switch (token) {
            case PLUS:
                stack.addLast(new NumberToken(arg2.add(arg1)));
                break;
            case MINUS:
                stack.addLast(new NumberToken(arg2.subtract(arg1)));
                break;
            case MULTIPLY:
                stack.addLast(new NumberToken(arg2.multiply(arg1)));
                break;
            case DIVIDE:
                stack.addLast(new NumberToken(arg2.divide(arg1)));
                break;
        }
    }

    private BigInteger popNumberFromStack() {
        if (stack.isEmpty()) {
            throw new IllegalStateException(
                    "Calculation exception: empty stack (a number expected)"
            );
        }
        Optional<BigInteger> num = stack.removeLast().getNumber();
        if (num.isEmpty()) {
            throw new IllegalStateException(
                    "Calculation exception: not a number at the stack: " + this
            );
        }
        return num.get();
    }

}
