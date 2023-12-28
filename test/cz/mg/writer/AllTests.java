package cz.mg.writer;

import cz.mg.annotations.classes.Test;
import cz.mg.writer.formatters.*;

public @Test class AllTests {
    public static void main(String[] args) {
        ExpressionFormatterTest.main(args);
        StructFormatterTest.main(args);
        TokenFormatterTest.main(args);
        TypeFormatterTest.main(args);
        VariableFormatterTest.main(args);
    }
}
