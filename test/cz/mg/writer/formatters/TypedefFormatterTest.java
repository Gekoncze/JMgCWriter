package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.entities.*;
import cz.mg.c.writer.formatters.TypedefFormatter;
import cz.mg.collections.list.List;
import cz.mg.writer.test.LineValidator;

public @Test class TypedefFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + TypedefFormatterTest.class.getSimpleName() + " ... ");

        TypedefFormatterTest test = new TypedefFormatterTest();
        test.testFormatTypename();
        test.testFormatStruct();
        test.testFormatFunctionPointer();

        System.out.println("OK");
    }

    private final @Service TypedefFormatter typedefFormatter = TypedefFormatter.getInstance();
    private final @Service LineValidator lineValidator = LineValidator.getInstance();

    private void testFormatTypename() {
        CType type = new CType(new CTypename("int"), false, new List<>(new CPointer()), new List<>());
        CTypedef typedef = new CTypedef("IntPtr", type);

        lineValidator.validate(
            typedefFormatter.format(typedef),
            "typedef int* IntPtr"
        );
    }

    private void testFormatStruct() {
        CVariable variable = new CVariable(new CType(new CTypename("int"), false, new List<>(), new List<>()), "i");
        CStruct struct = new CStruct("MyStruct_T", new List<>(variable));
        CType type = new CType(struct, false, new List<>(), new List<>());
        CTypedef typedef = new CTypedef("MyStruct", type);

        lineValidator.validate(
            typedefFormatter.format(typedef),
            "typedef struct MyStruct_T {",
            "    int i;",
            "} MyStruct"
        );
    }

    private void testFormatFunctionPointer() {
        CFunction function = new CFunction(
            "FuncPtr",
            new CType(new CTypename("void"), false, new List<>(), new List<>()),
            new List<>(),
            null
        );

        CType type = new CType(function, false, new List<>(new CPointer()), new List<>());
        CTypedef typedef = new CTypedef("FuncPtr", type);

        lineValidator.validate(
            typedefFormatter.format(typedef),
            "typedef void (* FuncPtr)()"
        );
    }
}