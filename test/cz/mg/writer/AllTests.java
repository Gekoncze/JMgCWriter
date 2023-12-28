package cz.mg.writer;

import cz.mg.annotations.classes.Test;
import cz.mg.writer.formatters.ExpressionFormatterTest;
import cz.mg.writer.formatters.TokenFormatterTest;
import cz.mg.writer.formatters.TypeFormatterTest;
import cz.mg.writer.formatters.VariableFormatterTest;

public @Test class AllTests {
    public static void main(String[] args) {
        ExpressionFormatterTest.main(args);
        TokenFormatterTest.main(args);
        TypeFormatterTest.main(args);
        VariableFormatterTest.main(args);
    }
}
