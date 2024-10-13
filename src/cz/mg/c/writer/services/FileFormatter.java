package cz.mg.c.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CEntity;
import cz.mg.c.entities.CFile;
import cz.mg.c.entities.CFunction;
import cz.mg.c.writer.services.formatters.maps.FileEntityFormatters;
import cz.mg.collections.list.List;
import cz.mg.token.Token;
import cz.mg.token.tokens.SymbolToken;
import cz.mg.token.tokens.WhitespaceToken;

public @Service class FileFormatter {
    private static volatile @Service FileFormatter instance;

    public static @Service FileFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new FileFormatter();
                    instance.formatters = FileEntityFormatters.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service FileEntityFormatters formatters;

    private FileFormatter() {
    }

    public @Mandatory List<Token> format(@Mandatory CFile file) {
        List<Token> tokens = new List<>();
        for (CEntity entity : file.getEntities()) {
            tokens.addCollectionLast(formatters.get(entity).format(entity));
            if (!(entity instanceof CFunction)) {
                tokens.addLast(new SymbolToken(";", -1));
            }
            tokens.addLast(new WhitespaceToken("\n", -1));
        }
        return tokens;
    }
}