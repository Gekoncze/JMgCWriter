package cz.mg.c.writer.services.formatters.maps;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.*;
import cz.mg.c.entities.types.CArrayType;
import cz.mg.c.entities.types.CBaseType;
import cz.mg.c.entities.types.CPointerType;
import cz.mg.c.writer.services.formatters.*;
import cz.mg.collections.map.Map;
import cz.mg.collections.pair.Pair;

public @Service class TypeFormatters {
    private static volatile @Service TypeFormatters instance;

    public static @Service TypeFormatters getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new TypeFormatters();
                    instance.formatters = new Map<>(
                        new Pair<>(CBaseType.class, BaseTypeFormatter.getInstance()),
                        new Pair<>(CPointerType.class, PointerTypeFormatter.getInstance()),
                        new Pair<>(CArrayType.class, ArrayTypeFormatter.getInstance())
                    );
                }
            }
        }
        return instance;
    }

    private @Service Map<Class<? extends CEntity>, EntityFormatter<? extends CEntity>> formatters;

    private TypeFormatters() {
    }

    @SuppressWarnings("unchecked")
    public <E extends CEntity> @Mandatory EntityFormatter<E> get(@Mandatory E entity) {
        return (EntityFormatter<E>) formatters.get(entity.getClass());
    }
}