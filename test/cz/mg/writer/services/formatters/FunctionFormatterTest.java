
package cz.mg.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.entities.*;
import cz.mg.c.writer.services.formatters.FunctionFormatter;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.tokens.*;
import cz.mg.writer.test.LineValidator;

public @Test class FunctionFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + FunctionFormatterTest.class.getSimpleName() + " ... ");

        FunctionFormatterTest test = new FunctionFormatterTest();
        test.testFormatDeclaration();
        test.testFormatDefinition();
        test.testFormatImplementation();
        test.testNameCannotBeNull();
        test.testOutputCannotBeFunctionPointer();
        test.testFormatSingleLineHeader();
        test.testFormatMultiLineHeader();

        System.out.println("OK");
    }

    private final @Service FunctionFormatter functionFormatter = FunctionFormatter.getInstance();
    private final @Service LineValidator lineValidator = LineValidator.getInstance();

    private void testFormatDeclaration() {
        CFunction function = new CFunction(
            "foo",
            new CType(new CTypename("void"), false, new List<>(), new List<>()),
            new List<>(),
            null
        );

        lineValidator.validate(
            functionFormatter.format(function),
            "void foo()"
        );
    }

    private void testFormatDefinition() {
        CFunction function = new CFunction(
            "foo",
            new CType(new CTypename("void"), false, new List<>(), new List<>()),
            new List<>(),
            new List<>()
        );

        lineValidator.validate(
            functionFormatter.format(function),
            "void foo() {",
            "}"
        );
    }

    private void testFormatImplementation() {
        CFunction function = new CFunction(
            "foo",
            new CType(new CTypename("void"), false, new List<>(), new List<>()),
            new List<>(),
            new List<>(
                new WordToken("return", 0),
                new WordToken("plus", 0),
                new BracketToken("(", 0),
                new WhitespaceToken("\n", 0),
                new WhitespaceToken("    ", 0),
                new NumberToken("1", 0),
                new OperatorToken("+", 0),
                new NumberToken("1", 0),
                new WhitespaceToken("\n", 0),
                new BracketToken(")", 0)
            )
        );

        lineValidator.validate(
            functionFormatter.format(function),
            "void foo() {",
            "    return plus(",
            "        1+1",
            "    )",
            "}"
        );
    }

    private void testNameCannotBeNull() {
        Assert.assertThatCode(() -> {
            CFunction function = new CFunction(
                null,
                new CType(new CTypename("void"), false, new List<>(), new List<>()),
                new List<>(),
                null
            );

            functionFormatter.format(function);
        }).throwsException(IllegalArgumentException.class);
    }

    private void testOutputCannotBeFunctionPointer() {
        Assert.assertThatCode(() -> {
            CFunction function = new CFunction(
                "foo",
                new CType(new CFunction(), false, new List<>(), new List<>()),
                new List<>(),
                null
            );

            functionFormatter.format(function);
        }).throwsException(IllegalArgumentException.class);
    }

    private void testFormatSingleLineHeader() {
        CStruct firstStruct = new CStruct("First", null);
        CStruct secondStruct = new CStruct("Second", null);
        CStruct thirdStruct = new CStruct("Third", null);

        CFunction function = new CFunction(
            "foo",
            new CType(thirdStruct, false, new List<>(), new List<>()),
            new List<>(
                new CVariable(new CType(firstStruct, false, new List<>(), new List<>()), "first"),
                new CVariable(new CType(secondStruct, false, new List<>(), new List<>()), "second")
            ),
            null
        );

        lineValidator.validate(
            functionFormatter.format(function),
            "struct Third foo(struct First first, struct Second second)"
        );
    }

    private void testFormatMultiLineHeader() {
        CStruct firstStruct = new CStruct("First", new List<>());
        CStruct secondStruct = new CStruct("Second", new List<>());
        CStruct thirdStruct = new CStruct("Third", new List<>());

        CFunction function = new CFunction(
            "foo",
            new CType(thirdStruct, false, new List<>(), new List<>()),
            new List<>(
                new CVariable(new CType(firstStruct, false, new List<>(), new List<>()), "first"),
                new CVariable(new CType(secondStruct, false, new List<>(), new List<>()), "second")
            ),
            null
        );

        lineValidator.validate(
            functionFormatter.format(function),
            "struct Third {",
            "} foo(",
            "    struct First {",
            "    } first,",
            "    struct Second {",
            "    } second",
            ")"
        );
    }
}