package cz.mg.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.entities.*;
import cz.mg.c.writer.services.formatters.*;
import cz.mg.test.Assert;

public @Test class MainEntityFormattersTest {
    public static void main(String[] args) {
        System.out.print("Running " + MainEntityFormattersTest.class.getSimpleName() + " ... ");

        MainEntityFormattersTest test = new MainEntityFormattersTest();
        test.testGet();

        System.out.println("OK");
    }

    private final @Service FileEntityFormatters formatters = FileEntityFormatters.getInstance();

    private void testGet() {
        Assert.assertEquals(StructFormatter.class, formatters.get(new CStruct()).getClass());
        Assert.assertEquals(UnionFormatter.class, formatters.get(new CUnion()).getClass());
        Assert.assertEquals(EnumFormatter.class, formatters.get(new CEnum()).getClass());
        Assert.assertEquals(FunctionFormatter.class, formatters.get(new CFunction()).getClass());
        Assert.assertEquals(VariableFormatter.class, formatters.get(new CVariable()).getClass());
        Assert.assertEquals(TypedefFormatter.class, formatters.get(new CTypedef()).getClass());

        Assert.assertThatCode(() -> {
            formatters.get(new CEntity() {});
        }).throwsException(UnsupportedOperationException.class);
    }
}