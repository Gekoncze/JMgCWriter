package cz.mg.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.writer.services.formatters.ExpressionFormatter;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.tokens.*;
import cz.mg.writer.test.LineValidator;

public @Test class ExpressionFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + ExpressionFormatterTest.class.getSimpleName() + " ... ");

        ExpressionFormatterTest test = new ExpressionFormatterTest();
        test.testFormatEmpty();
        test.testFormatSingle();
        test.testFormatMultiple();
        test.testFormatWords();
        test.testFormatNumbers();
        test.testFormatSingleLine();
        test.testFormatMultiLine();

        System.out.println("OK");
    }

    private final @Service ExpressionFormatter expressionFormatter = ExpressionFormatter.getInstance();
    private final @Service LineValidator lineValidator = LineValidator.getInstance();

    private void testFormatEmpty() {
        Assert.assertEquals(
            "",
            expressionFormatter.formatSingleLine(new List<>())
        );
    }

    private void testFormatSingle() {
        Assert.assertEquals(
            "foo",
            expressionFormatter.formatSingleLine(new List<>(new WordToken("foo", 0)))
        );
    }

    private void testFormatMultiple() {
        Assert.assertEquals(
            "1+1",
            expressionFormatter.formatSingleLine(new List<>(
                new NumberToken("1", 0),
                new OperatorToken("+", 0),
                new NumberToken("1", 0)
            ))
        );
    }

    private void testFormatWords() {
        Assert.assertEquals(
            "foo bar",
            expressionFormatter.formatSingleLine(new List<>(
                new WordToken("foo", 0),
                new WordToken("bar", 0)
            ))
        );
    }

    private void testFormatNumbers() {
        Assert.assertEquals(
            "1 2",
            expressionFormatter.formatSingleLine(new List<>(
                new WordToken("1", 0),
                new WordToken("2", 0)
            ))
        );
    }

    private void testFormatSingleLine() {
        Assert.assertEquals(
            "( )",
            expressionFormatter.formatSingleLine(new List<>(
                new BracketToken("(", 0),
                new WhitespaceToken("\n", 0),
                new BracketToken(")", 0)
            ))
        );
    }

    private void testFormatMultiLine() {
        lineValidator.validate(
            expressionFormatter.formatMultiLine(new List<>(
                new BracketToken("(", 0),
                new WhitespaceToken("\n", 0),
                new BracketToken(")", 0)
            )),
            "(",
            ")"
        );
    }
}
