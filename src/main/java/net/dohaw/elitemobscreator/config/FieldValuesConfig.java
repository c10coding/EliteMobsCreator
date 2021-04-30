package net.dohaw.elitemobscreator.config;

import net.dohaw.corelib.Config;

import java.util.List;

public class FieldValuesConfig extends Config {

    public FieldValuesConfig() {
        super("field_values.yml");
    }

    public List<String> getEntityTypes(){
        return getCommaList("Entity Types");
    }

    public List<String> getPowers(){
        return getCommaList("Powers");
    }

}
