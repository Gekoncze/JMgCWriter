package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CEntity;
import cz.mg.collections.components.Capacity;
import cz.mg.collections.list.List;
import cz.mg.collections.map.Map;

public @Service class CEntityFormatters {
    private static volatile @Service CEntityFormatters instance;

    public static @Service CEntityFormatters getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new CEntityFormatters();
                    instance.formatters = new Map<>(new Capacity(50));
                }
            }
        }
        return instance;
    }

    private @Service Map<Class<?>, CEntityFormatter> formatters;

    private CEntityFormatters() {
    }

    public @Mandatory List<String> format(@Mandatory CEntity entity) {
        return getFormatter(entity).format(entity);
    }

    private @Mandatory CEntityFormatter getFormatter(@Mandatory CEntity entity) {
        CEntityFormatter formatter = formatters.getOptional(entity.getClass());
        if (formatter != null) {
            return formatter;
        } else {
            throw new UnsupportedOperationException(
                "No formatter found for " + entity.getClass().getSimpleName() + "."
            );
        }
    }
}
