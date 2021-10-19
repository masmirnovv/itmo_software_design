package ru.masmirnov.sd.visitor.visitor;

import ru.masmirnov.sd.visitor.token.*;

public interface TokenVisitor {

    void visit(NumberToken token);

    void visit(Brace token);

    void visit(Operation token);

}
