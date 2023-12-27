package cz.mg.writer;

import cz.mg.annotations.classes.Test;
import cz.mg.writer.formatters.TokenFormatterTest;
import cz.mg.writer.formatters.TypeFormatterTest;

public @Test class AllTests {
    public static void main(String[] args) {
        TokenFormatterTest.main(args);
        TypeFormatterTest.main(args);
    }
}
