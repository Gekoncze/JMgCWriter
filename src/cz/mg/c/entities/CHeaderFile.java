package cz.mg.c.entities;

import cz.mg.annotations.classes.Entity;
import cz.mg.annotations.requirement.Required;
import cz.mg.annotations.storage.Part;
import cz.mg.c.entities.directives.IncludeDirective;
import cz.mg.collections.list.List;

import java.nio.file.Path;

public @Entity class CHeaderFile extends CFile {
    private List<IncludeDirective> includes = new List<>();

    public CHeaderFile() {
    }

    public CHeaderFile(Path path, List<CMainEntity> entities) {
        super(path, entities);
    }

    @Required @Part
    public List<IncludeDirective> getIncludes() {
        return includes;
    }

    public void setIncludes(List<IncludeDirective> includes) {
        this.includes = includes;
    }
}
