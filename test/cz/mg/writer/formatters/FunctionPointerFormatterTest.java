
package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.parser.entities.*;
import cz.mg.c.writer.formatters.FunctionPointerFormatter;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.writer.test.LineValidator;

public @Test class FunctionPointerFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + FunctionPointerFormatterTest.class.getSimpleName() + " ... ");

        FunctionPointerFormatterTest test = new FunctionPointerFormatterTest();
        test.testFormatSimple();
        test.testFormatArray();
        test.testTypeMustBePointer();
        test.testTypeMustBeFunction();
        test.testTypeCannotBeConst();
        test.testFunctionCannotHaveImplementation();
        test.testNameCannotBeNull();
        test.testOutputCannotBeFunctionPointer();
        test.testFormatSingleLineHeader();
        test.testFormatMultiLineHeader();

        System.out.println("OK");
    }

    private final @Service FunctionPointerFormatter functionPointerFormatter = FunctionPointerFormatter.getInstance();
    private final @Service LineValidator lineValidator = LineValidator.getInstance();

    private void testFormatSimple() {
        CFunction function = new CFunction(
            "foo",
            new CType(new CTypename("void"), false, new List<>(), new List<>()),
            new List<>(),
            null
        );

        CType type = new CType(function, false, new List<>(new CPointer()), new List<>());

        lineValidator.validate(
            functionPointerFormatter.format(type),
            "void (* foo)()"
        );
    }

    private void testFormatArray() {
        CFunction function = new CFunction(
            "foo",
            new CType(new CTypename("void"), false, new List<>(), new List<>()),
            new List<>(),
            null
        );

        CType type = new CType(function, false, new List<>(new CPointer()), new List<>(new CArray()));

        lineValidator.validate(
            functionPointerFormatter.format(type),
            "void (* foo[])()"
        );
    }

    private void testTypeMustBePointer() {
        Assert.assertThatCode(() -> {
            CFunction function = new CFunction(
                "bar",
                new CType(new CTypename("void"), false, new List<>(), new List<>()),
                new List<>(),
                null
            );

            CType type = new CType(function, false, new List<>(), new List<>());

            functionPointerFormatter.format(type);
        }).throwsException(IllegalArgumentException.class);
    }

    private void testTypeMustBeFunction() {
        Assert.assertThatCode(() -> {
            CType type = new CType(new CStruct(), false, new List<>(new CPointer()), new List<>());
            functionPointerFormatter.format(type);
        }).throwsException(IllegalArgumentException.class);
    }

    private void testTypeCannotBeConst() {
        Assert.assertThatCode(() -> {
            CFunction function = new CFunction(
                "bar",
                new CType(new CTypename("void"), false, new List<>(), new List<>()),
                new List<>(),
                null
            );

            CType type = new CType(function, true, new List<>(new CPointer()), new List<>());

            functionPointerFormatter.format(type);
        }).throwsException(IllegalArgumentException.class);
    }

    private void testFunctionCannotHaveImplementation() {
        Assert.assertThatCode(() -> {
            CFunction function = new CFunction(
                "bar",
                new CType(new CTypename("void"), false, new List<>(), new List<>()),
                new List<>(),
                new List<>()
            );

            CType type = new CType(function, false, new List<>(new CPointer()), new List<>());

            functionPointerFormatter.format(type);
        }).throwsException(IllegalArgumentException.class);
    }

    private void testNameCannotBeNull() {
        Assert.assertThatCode(() -> {
            CFunction function = new CFunction(
                null,
                new CType(new CTypename("void"), false, new List<>(), new List<>()),
                new List<>(),
                null
            );

            CType type = new CType(function, false, new List<>(new CPointer()), new List<>());

            functionPointerFormatter.format(type);
        }).throwsException(IllegalArgumentException.class);
    }

    private void testOutputCannotBeFunctionPointer() {
        Assert.assertThatCode(() -> {
            CFunction function = new CFunction(
                "bar",
                new CType(new CFunction(), false, new List<>(), new List<>()),
                new List<>(),
                null
            );

            CType type = new CType(function, false, new List<>(new CPointer()), new List<>());

            functionPointerFormatter.format(type);
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

        CType type = new CType(function, false, new List<>(new CPointer()), new List<>());

        lineValidator.validate(
            functionPointerFormatter.format(type),
            "struct Third (* foo)(struct First first, struct Second second)"
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

        CType type = new CType(function, false, new List<>(new CPointer()), new List<>());

        lineValidator.validate(
            functionPointerFormatter.format(type),
            "struct Third {",
            "} (* foo)(",
            "    struct First {",
            "    } first,",
            "    struct Second {",
            "    } second",
            ")"
        );
    }
}
