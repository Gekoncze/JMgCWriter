package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CEntity;
import cz.mg.collections.list.List;

public @Service interface EntityFormatter<E extends CEntity> {
    @Mandatory List<String> format(@Mandatory E entity);
}