package cz.mg.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.entities.CArray;
import cz.mg.c.writer.services.formatters.ArrayFormatter;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.tokens.NumberToken;
import cz.mg.tokenizer.entities.tokens.OperatorToken;

public @Test class ArrayFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + ArrayFormatterTest.class.getSimpleName() + " ... ");

        ArrayFormatterTest test = new ArrayFormatterTest();
        test.testFormatEmpty();
        test.testFormatSingle();
        test.testFormatMultiple();
        test.testFormatExpression();

        System.out.println("OK");
    }

    private final @Service ArrayFormatter arrayFormatter = ArrayFormatter.getInstance();

    private void testFormatEmpty() {
        Assert.assertEquals(
            "",
            arrayFormatter.format(new List<>())
        );
    }

    private void testFormatSingle() {
        Assert.assertEquals(
            "[]",
            arrayFormatter.format(new List<>(new CArray()))
        );
    }

    private void testFormatMultiple() {
        Assert.assertEquals(
            "[][]",
            arrayFormatter.format(new List<>(new CArray(), new CArray()))
        );
    }

    private void testFormatExpression() {
        Assert.assertEquals(
            "[1+2]",
            arrayFormatter.format(new List<>(new CArray(new List<>(
                new NumberToken("1", 0),
                new OperatorToken("+", 0),
                new NumberToken("2", 0)
            ))))
        );
    }
}