package cz.mg.c.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CEntity;
import cz.mg.collections.list.List;
import cz.mg.token.Token;

public @Service interface EntityFormatter<E extends CEntity> {
    @Mandatory List<Token> format(@Mandatory E entity);
}