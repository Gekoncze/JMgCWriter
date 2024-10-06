package cz.mg.c.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CModifier;
import cz.mg.c.entities.types.CPointerType;
import cz.mg.collections.list.List;

public @Service class PointerFormatter {
    private static volatile @Service PointerFormatter instance;

    public static @Service PointerFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new PointerFormatter();
                }
            }
        }
        return instance;
    }

    private PointerFormatter() {
    }

    public @Mandatory String format(@Mandatory List<CPointerType> pointers) {
        StringBuilder builder = new StringBuilder();

        for (CPointerType pointer : pointers) {
            builder.append("*");

            if (pointer.getModifiers().contains(CModifier.CONST)) {
                builder.append(" const ");
            }
        }

        return builder.toString().trim();
    }
}