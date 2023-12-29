package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.*;
import cz.mg.collections.components.Capacity;
import cz.mg.collections.list.List;
import cz.mg.collections.map.Map;
import cz.mg.collections.pair.Pair;

public @Service class CMainEntityFormatters {
    private static volatile @Service CMainEntityFormatters instance;

    public static @Service CMainEntityFormatters getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new CMainEntityFormatters();
                    instance.formatters = new Map<>(
                        new Capacity(50),
                        new Pair<>(CStruct.class, StructFormatter.getInstance()),
                        new Pair<>(CUnion.class, UnionFormatter.getInstance()),
                        new Pair<>(CEnum.class, EnumFormatter.getInstance()),
                        new Pair<>(CFunction.class, FunctionFormatter.getInstance()),
                        new Pair<>(CVariable.class, VariableFormatter.getInstance()),
                        new Pair<>(CTypedef.class, TypedefFormatter.getInstance())
                    );
                }
            }
        }
        return instance;
    }

    private @Service Map<Class<? extends CEntity>, CEntityFormatter<? extends CEntity>> formatters;

    private CMainEntityFormatters() {
    }

    public @Mandatory List<String> format(@Mandatory CEntity entity) {
        return getFormatter(entity).format(entity);
    }

    @SuppressWarnings("unchecked")
    private @Mandatory CEntityFormatter<CEntity> getFormatter(@Mandatory CEntity entity) {
        CEntityFormatter<CEntity> formatter = (CEntityFormatter<CEntity>) formatters.getOptional(entity.getClass());
        if (formatter != null) {
            return formatter;
        } else {
            throw new UnsupportedOperationException(
                "No formatter found for " + entity.getClass().getSimpleName() + "."
            );
        }
    }
}
