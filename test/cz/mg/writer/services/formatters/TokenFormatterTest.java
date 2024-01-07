package cz.mg.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.writer.services.formatters.TokenFormatter;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.tokens.*;

public @Test class TokenFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + TokenFormatterTest.class.getSimpleName() + " ... ");

        TokenFormatterTest test = new TokenFormatterTest();
        test.testFormatWord();
        test.testFormatNumber();
        test.testFormatBracket();
        test.testFormatOperator();
        test.testFormatSeparator();
        test.testFormatSingleQuote();
        test.testFormatDoubleQuote();
        test.testFormatSingleLineComment();
        test.testFormatMultiLineComment();

        System.out.println("OK");
    }

    private final @Service TokenFormatter tokenFormatter = TokenFormatter.getInstance();

    private void testFormatWord() {
        Assert.assertEquals(
            "foo",
            tokenFormatter.format(new WordToken("foo", 0))
        );
    }

    private void testFormatNumber() {
        Assert.assertEquals(
            "12",
            tokenFormatter.format(new NumberToken("12", 0))
        );

        Assert.assertEquals(
            "-7",
            tokenFormatter.format(new NumberToken("-7", 0))
        );
    }

    private void testFormatBracket() {
        Assert.assertEquals(
            "(",
            tokenFormatter.format(new NumberToken("(", 0))
        );

        Assert.assertEquals(
            "]",
            tokenFormatter.format(new NumberToken("]", 0))
        );
    }

    private void testFormatOperator() {
        Assert.assertEquals(
            "*",
            tokenFormatter.format(new OperatorToken("*", 0))
        );

        Assert.assertEquals(
            "+=",
            tokenFormatter.format(new OperatorToken("+=", 0))
        );
    }

    private void testFormatSeparator() {
        Assert.assertEquals(
            ",",
            tokenFormatter.format(new SeparatorToken(",", 0))
        );
    }

    private void testFormatSingleQuote() {
        Assert.assertEquals(
            "'abc'",
            tokenFormatter.format(new SingleQuoteToken("abc", 0))
        );

        Assert.assertEquals(
            "'a\\\nc'",
            tokenFormatter.format(new SingleQuoteToken("a\\\nc", 0))
        );
    }

    private void testFormatDoubleQuote() {
        Assert.assertEquals(
            "\"abc\"",
            tokenFormatter.format(new DoubleQuoteToken("abc", 0))
        );

        Assert.assertEquals(
            "\"a\\\nc\"",
            tokenFormatter.format(new DoubleQuoteToken("a\\\nc", 0))
        );
    }

    private void testFormatSingleLineComment() {
        Assert.assertEquals(
            "//foo",
            tokenFormatter.format(new SingleLineCommentToken("foo", 0))
        );

        Assert.assertEquals(
            "// foo",
            tokenFormatter.format(new SingleLineCommentToken(" foo", 0))
        );

        Assert.assertThatCode(() -> {
            tokenFormatter.format(new SingleLineCommentToken("abc\ndef", 0));
        }).throwsException(IllegalArgumentException.class);
    }

    private void testFormatMultiLineComment() {
        Assert.assertEquals(
            "/*foo*/",
            tokenFormatter.format(new MultiLineCommentToken("foo", 0))
        );

        Assert.assertEquals(
            "/* foo */",
            tokenFormatter.format(new MultiLineCommentToken(" foo ", 0))
        );
    }
}
