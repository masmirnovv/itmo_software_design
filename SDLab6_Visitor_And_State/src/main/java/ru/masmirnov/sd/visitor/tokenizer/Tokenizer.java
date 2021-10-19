package ru.masmirnov.sd.visitor.tokenizer;

import ru.masmirnov.sd.visitor.token.Token;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface Tokenizer {

    List<Token> tokenize(InputStream is) throws IOException;

}
