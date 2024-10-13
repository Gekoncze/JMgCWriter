package cz.mg.c.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.*;
import cz.mg.collections.map.Map;
import cz.mg.collections.pair.Pair;

public @Service class FileEntityFormatters {
    private static volatile @Service FileEntityFormatters instance;

    public static @Service FileEntityFormatters getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new FileEntityFormatters();
                    instance.formatters = new Map<>(
                        new Pair<>(CStruct.class, StructFormatter.getInstance()),
                        new Pair<>(CUnion.class, UnionFormatter.getInstance()),
                        new Pair<>(CEnum.class, EnumFormatter.getInstance()),
                        new Pair<>(CFunction.class, FunctionFormatter.getInstance()),
                        new Pair<>(CVariable.class, VariableFormatter.getInstance()),
                        new Pair<>(CTypedef.class, TypedefFormatter.getInstance())
                        // TODO - add directive formatters
                    );
                }
            }
        }
        return instance;
    }

    private @Service Map<Class<? extends CEntity>, EntityFormatter<? extends CEntity>> formatters;

    private FileEntityFormatters() {
    }

    @SuppressWarnings("unchecked")
    public <E extends CEntity> @Mandatory EntityFormatter<E> get(@Mandatory E entity) {
        EntityFormatter<CEntity> formatter = (EntityFormatter<CEntity>) formatters.getOptional(entity.getClass());
        if (formatter != null) {
            return (EntityFormatter<E>) formatter;
        } else {
            throw new UnsupportedOperationException(
                "No formatter found for " + entity.getClass().getSimpleName() + "."
            );
        }
    }
}