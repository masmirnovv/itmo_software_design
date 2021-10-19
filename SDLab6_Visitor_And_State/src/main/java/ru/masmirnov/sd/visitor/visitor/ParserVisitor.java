package ru.masmirnov.sd.visitor.visitor;

import ru.masmirnov.sd.visitor.token.*;

import java.util.*;

public class ParserVisitor implements TokenVisitor {

    private List<Token> out;

    private LinkedList<Token> stack;

    public List<Token> parse(List<Token> tokens) {
        out = new ArrayList<>();
        stack = new LinkedList<>();

        for (Token token : tokens) {
            token.accept(this);
        }

        while (!stack.isEmpty()) {
            Token curToken = stack.removeLast();
            if (!curToken.isOperation()) {
                throw new IllegalStateException(
                        "Parse error: wrong balanced braces"
                );
            }
            out.add(curToken);
        }

        return out;
    }


    @Override
    public void visit(NumberToken token) {
        out.add(token);
    }

    @Override
    public void visit(Brace token) {
        switch (token) {
            case OPEN:
                stack.addLast(token);
                break;
            case CLOSE:
                Token curToken;
                while (!stack.isEmpty() && !(curToken = stack.removeLast()).isOpenBrace()) {
                    out.add(curToken);
                    if (stack.isEmpty()) {
                        throw new IllegalStateException(
                                "Parse error: wrong balanced braces (no open braces defined for some close one)"
                        );
                    }
                }
                break;
        }
    }

    @Override
    public void visit(Operation token) {
        while (!stack.isEmpty()
                && stack.getLast().isOperation()
                && stack.getLast().getOperationPriority() >= token.getOperationPriority()) {
            out.add(stack.removeLast());
        }
        stack.addLast(token);
    }

}
