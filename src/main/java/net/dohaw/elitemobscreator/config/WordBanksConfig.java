package net.dohaw.elitemobscreator.config;

import net.dohaw.corelib.Config;

import java.util.List;

public class WordBanksConfig extends Config {

    public WordBanksConfig() {
        super("word_banks.yml");
    }

    public List<String> getAdjectives(){
        return config.getStringList("adjective");
    }

    public List<String> getNouns(){
        return config.getStringList("noun");
    }

    public List<String> getVerbers(){
        return config.getStringList("verber");
    }

    public List<String> getVerbs(){
        return config.getStringList("verb");
    }

}
