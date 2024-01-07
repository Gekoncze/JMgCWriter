
package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.entities.*;
import cz.mg.c.writer.formatters.VariableFormatter;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.tokens.NumberToken;
import cz.mg.tokenizer.entities.tokens.OperatorToken;
import cz.mg.writer.test.LineValidator;

public @Test class VariableFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + VariableFormatterTest.class.getSimpleName() + " ... ");

        VariableFormatterTest test = new VariableFormatterTest();
        test.testFormatNamed();
        test.testFormatAnonymous();
        test.testFormatArray();
        test.testFormatArrays();
        test.testFormatMultiLine();
        test.testFormatFunctionPointer();
        test.testFunctionPointerNameMustMatch();

        System.out.println("OK");
    }

    private final @Service VariableFormatter variableFormatter = VariableFormatter.getInstance();
    private final @Service LineValidator lineValidator = LineValidator.getInstance();

    private void testFormatNamed() {
        CVariable variable = new CVariable(
            new CType(new CTypename("int"), false, new List<>(), new List<>()),
            "foo"
        );

        lineValidator.validate(
            variableFormatter.format(variable),
            "int foo"
        );
    }

    private void testFormatAnonymous() {
        CVariable variable = new CVariable(
            new CType(new CTypename("int"), false, new List<>(), new List<>()),
            null
        );

        lineValidator.validate(
            variableFormatter.format(variable),
            "int"
        );
    }

    private void testFormatArray() {
        CVariable variable = new CVariable(
            new CType(new CTypename("int"), false, new List<>(), new List<>(
                new CArray(new List<>(new NumberToken("5",  0)))
            )),
            "foo"
        );

        lineValidator.validate(
            variableFormatter.format(variable),
            "int foo[5]"
        );
    }

    private void testFormatArrays() {
        CVariable variable = new CVariable(
            new CType(new CTypename("int"), false, new List<>(), new List<>(
                new CArray(new List<>(new NumberToken("2",  0))),
                new CArray(new List<>(new NumberToken("3", 0), new OperatorToken("*", 0), new NumberToken("3", 0)))
            )),
            "foo"
        );

        lineValidator.validate(
            variableFormatter.format(variable),
            "int foo[2][3*3]"
        );
    }

    private void testFormatMultiLine() {
        CStruct struct = new CStruct(null, new List<>(
            new CVariable(new CType(new CTypename("int"), false, new List<>(), new List<>()), "i")
        ));

        CType type = new CType(
            struct,
            true,
            new List<>(new CPointer(true)),
            new List<>(new CArray(new List<>(new NumberToken("3", 0))))
        );

        CVariable variable = new CVariable(type, "foo");

        lineValidator.validate(
            variableFormatter.format(variable),
            "const struct {",
            "    int i;",
            "}* const foo[3]"
        );
    }

    private void testFormatFunctionPointer() {
        CFunction function = new CFunction(
            "foo",
            new CType(new CTypename("void"), false, new List<>(), new List<>()),
            new List<>(),
            null
        );

        CType type = new CType(function, false, new List<>(new CPointer()), new List<>(new CArray()));
        CVariable variable = new CVariable(type, "foo");

        lineValidator.validate(
            variableFormatter.format(variable),
            "void (* foo[])()"
        );
    }

    private void testFunctionPointerNameMustMatch() {
        Assert.assertThatCode(() -> {
            CFunction function = new CFunction(
                "foo",
                new CType(new CTypename("void"), false, new List<>(), new List<>()),
                new List<>(),
                null
            );

            CType type = new CType(function, false, new List<>(new CPointer()), new List<>());
            CVariable variable = new CVariable(type, "bar");

            variableFormatter.format(variable);
        }).throwsException(IllegalArgumentException.class);
    }
}