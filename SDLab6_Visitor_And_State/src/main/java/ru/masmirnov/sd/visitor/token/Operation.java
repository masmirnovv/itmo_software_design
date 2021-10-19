package ru.masmirnov.sd.visitor.token;

import ru.masmirnov.sd.visitor.visitor.TokenVisitor;

import java.math.BigInteger;
import java.util.Optional;

public enum Operation implements Token {

    PLUS, MINUS, MULTIPLY, DIVIDE;

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isOpenBrace() {
        return false;
    }

    @Override
    public boolean isOperation() {
        return true;
    }

    @Override
    public int getOperationPriority() {
        switch (this) {
            case PLUS:
            case MINUS:
                return 1;
            case MULTIPLY:
            case DIVIDE:
                return 2;
            default:
                throw new IllegalStateException(
                        "Assertion error: undefined operation " + this
                );
        }
    }

    @Override
    public Optional<BigInteger> getNumber() {
        return Optional.empty();
    }

}
