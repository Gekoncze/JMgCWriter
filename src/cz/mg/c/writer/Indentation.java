package cz.mg.c.writer;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

public @Service class Indentation {
    private static volatile @Service Indentation instance;

    public static @Service Indentation getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new Indentation();
                }
            }
        }
        return instance;
    }

    private Indentation() {
    }

    public void add(@Mandatory List<String> lines) {
        for (ListItem<String> item = lines.getFirstItem(); item != null; item = item.getNextItem()) {
            item.set(add(item.get()));
        }
    }

    private @Mandatory String add(@Mandatory String s) {
        return "\t" + s;
    }
}
