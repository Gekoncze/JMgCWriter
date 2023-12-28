package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CPointer;
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

    public @Mandatory String format(@Mandatory List<CPointer> pointers) {
        StringBuilder builder = new StringBuilder();

        for (CPointer pointer : pointers) {
            builder.append("*");

            if (pointer.isConstant()) {
                builder.append(" const ");
            }
        }

        return builder.toString().trim();
    }
}
