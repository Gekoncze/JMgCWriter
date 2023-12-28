package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.writer.formatters.ExpressionFormatter;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.tokens.NumberToken;
import cz.mg.tokenizer.entities.tokens.OperatorToken;
import cz.mg.tokenizer.entities.tokens.WordToken;

public @Test class ExpressionFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + ExpressionFormatterTest.class.getSimpleName() + " ... ");

        ExpressionFormatterTest test = new ExpressionFormatterTest();
        test.testFormatEmpty();
        test.testFormatSingle();
        test.testFormatMultiple();
        test.testFormatWords();
        test.testFormatNumbers();

        System.out.println("OK");
    }

    private final @Service ExpressionFormatter expressionFormatter = ExpressionFormatter.getInstance();

    private void testFormatEmpty() {
        Assert.assertEquals(
            "",
            expressionFormatter.format(new List<>())
        );
    }

    private void testFormatSingle() {
        Assert.assertEquals(
            "foo",
            expressionFormatter.format(new List<>(new WordToken("foo", 0)))
        );
    }

    private void testFormatMultiple() {
        Assert.assertEquals(
            "1+1",
            expressionFormatter.format(new List<>(
                new NumberToken("1", 0),
                new OperatorToken("+", 0),
                new NumberToken("1", 0)
            ))
        );
    }

    private void testFormatWords() {
        Assert.assertEquals(
            "foo bar",
            expressionFormatter.format(new List<>(
                new WordToken("foo", 0),
                new WordToken("bar", 0)
            ))
        );
    }

    private void testFormatNumbers() {
        Assert.assertEquals(
            "1 2",
            expressionFormatter.format(new List<>(
                new WordToken("1", 0),
                new WordToken("2", 0)
            ))
        );
    }
}
