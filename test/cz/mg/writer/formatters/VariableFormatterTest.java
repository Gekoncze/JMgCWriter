
package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.parser.entities.CArray;
import cz.mg.c.parser.entities.CType;
import cz.mg.c.parser.entities.CTypename;
import cz.mg.c.parser.entities.CVariable;
import cz.mg.c.writer.formatters.VariableFormatter;
import cz.mg.collections.list.List;
import cz.mg.tokenizer.entities.tokens.NumberToken;
import cz.mg.writer.test.LineValidator;

public @Test class VariableFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + VariableFormatterTest.class.getSimpleName() + " ... ");

        VariableFormatterTest test = new VariableFormatterTest();
        test.testFormatNamed();
        test.testFormatAnonymous();
        test.testFormatArray();
        test.testFormatMultiLine();
        test.testFormatFunctionPointer();

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

    private void testFormatMultiLine() {
        // TODO
    }

    private void testFormatFunctionPointer() {
        // TODO
    }
}
