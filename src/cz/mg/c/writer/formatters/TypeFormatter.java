package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.*;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

public @Service class TypeFormatter implements CEntityFormatter<CType> {
    private static volatile @Service TypeFormatter instance;

    public static @Service TypeFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new TypeFormatter();
                    instance.structFormatter = StructFormatter.getInstance();
                    instance.unionFormatter = UnionFormatter.getInstance();
                    instance.enumFormatter = EnumFormatter.getInstance();
                    instance.pointerFormatter = PointerFormatter.getInstance();
                    instance.functionPointerFormatter = FunctionPointerFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service StructFormatter structFormatter;
    private @Service UnionFormatter unionFormatter;
    private @Service EnumFormatter enumFormatter;
    private @Service PointerFormatter pointerFormatter;
    private @Service FunctionPointerFormatter functionPointerFormatter;

    private TypeFormatter() {
    }

    public @Mandatory List<String> format(@Mandatory CType type) {
        CTypename typename = type.getTypename();
        if (typename.getClass().equals(CTypename.class)) {
            return formatTypename(type);
        } else if (typename instanceof CStruct) {
            return formatStruct(type);
        } else if (typename instanceof CUnion) {
            return formatUnion(type);
        } else if (typename instanceof CEnum) {
            return formatEnum(type);
        } else if (typename instanceof CFunction) {
            return formatFunctionPointer(type);
        } else {
            throw new UnsupportedOperationException("Unsupported type " + typename.getClass().getSimpleName() + ".");
        }
    }

    private @Mandatory List<String> formatTypename(@Mandatory CType type) {
        List<String> lines = new List<>(type.getTypename().getName());
        addConst(lines, type);
        addPointers(lines, type);
        return lines;
    }

    private @Mandatory List<String> formatStruct(@Mandatory CType type) {
        List<String> lines = structFormatter.format((CStruct) type.getTypename());
        addConst(lines, type);
        addPointers(lines, type);
        return lines;
    }

    private @Mandatory List<String> formatUnion(@Mandatory CType type) {
        List<String> lines = unionFormatter.format((CUnion) type.getTypename());
        addConst(lines, type);
        addPointers(lines, type);
        return lines;
    }

    private @Mandatory List<String> formatEnum(@Mandatory CType type) {
        List<String> lines = enumFormatter.format((CEnum) type.getTypename());
        addConst(lines, type);
        addPointers(lines, type);
        return lines;
    }

    private @Mandatory List<String> formatFunctionPointer(@Mandatory CType type) {
        return functionPointerFormatter.format(type);
    }

    private void addConst(@Mandatory List<String> lines, @Mandatory CType type) {
        ListItem<String> lineItem = lines.getFirstItem();
        lineItem.set(addConst(lineItem.get(), type));
    }

    private @Mandatory String addConst(@Mandatory String line, @Mandatory CType type) {
        StringBuilder builder = new StringBuilder(line);

        if (type.isConstant()) {
            builder.insert(0, " const ");
        }

        return builder.toString().trim();
    }

    private void addPointers(@Mandatory List<String> lines, @Mandatory CType type) {
        ListItem<String> lineItem = lines.getLastItem();
        lineItem.set(addPointers(lineItem.get(), type));
    }

    private @Mandatory String addPointers(@Mandatory String line, @Mandatory CType type) {
        return line + pointerFormatter.format(type.getPointers());
    }
}
