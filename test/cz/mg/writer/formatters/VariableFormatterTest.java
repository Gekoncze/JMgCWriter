
package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.parser.entities.CArray;
import cz.mg.c.parser.entities.CType;
import cz.mg.c.parser.entities.CTypename;
import cz.mg.c.parser.entities.CVariable;
import cz.mg.c.writer.formatters.VariableFormatter;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.tokens.NumberToken;

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

    private void testFormatNamed() {
        CVariable variable = new CVariable(
                new CType(new CTypename("int"), false, new List<>(), new List<>()),
                "foo"
        );

        Assert.assertThatCollections(new List<>("int foo"), variableFormatter.format(variable))
                .withPrintFunction(line -> '"' + line + '"')
                .areEqual();
    }

    private void testFormatAnonymous() {
        CVariable variable = new CVariable(
                new CType(new CTypename("int"), false, new List<>(), new List<>()),
                null
        );

        Assert.assertThatCollections(new List<>("int"), variableFormatter.format(variable))
                .withPrintFunction(line -> '"' + line + '"')
                .areEqual();
    }

    private void testFormatArray() {
        CVariable variable = new CVariable(
                new CType(new CTypename("int"), false, new List<>(), new List<>(
                        new CArray(new List<>(new NumberToken("5",  0)))
                )),
                "foo"
        );

        Assert.assertThatCollections(new List<>("int foo[5]"), variableFormatter.format(variable))
                .withPrintFunction(line -> '"' + line + '"')
                .areEqual();
    }

    private void testFormatMultiLine() {
        // TODO
    }

    private void testFormatFunctionPointer() {
        // TODO
    }
}
