package ru.masmirnov.sd.visitor.token;

import ru.masmirnov.sd.visitor.visitor.TokenVisitor;

import java.math.BigInteger;
import java.util.Optional;

public enum Brace implements Token {

    OPEN, CLOSE;

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isOperation() {
        return false;
    }

    @Override
    public int getOperationPriority() {
        throw new IllegalStateException(
                "Not an operation: " + this
        );
    }

    @Override
    public boolean isOpenBrace() {
        return this == OPEN;
    }

    @Override
    public Optional<BigInteger> getNumber() {
        return Optional.empty();
    }

}
