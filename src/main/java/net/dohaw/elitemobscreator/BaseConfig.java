package net.dohaw.elitemobscreator;

import net.dohaw.corelib.Config;

import java.util.List;

public class BaseConfig extends Config {

    public BaseConfig() {
        super("config.yml");
    }

    public List<String> getMobNameBank(){
        return config.getStringList("Name Bank");
    }

    public int getTimeoutMin(){
        return config.getInt("Timeout Min");
    }

    public int getTimeoutMax(){
        return config.getInt("Timeout Max");
    }

    public int getNumPowers(){
        return config.getInt("Number of Powers");
    }

    public double getMinHealthMultiplier(){
        return config.getDouble("Minimum Health Multiplier");
    }

    public double getMaxHealthMultiplier(){
        return config.getDouble("Maximum Health Multiplier");
    }

    public double getMinDamageMultiplier(){
        return config.getDouble("Minimum Damage Multiplier");
    }

    public double getMaxDamageMultiplier(){
        return config.getDouble("Maximum Damage Multiplier");
    }

    public double getMinSpawnChance(){
        return config.getDouble("Minimum Spawn Chance");
    }

    public double getMaxSpawnChance(){
        return config.getDouble("Maximum Spawn Chance");
    }

    public int getMinimumEnchants(){
        return config.getInt("Minimum Number of Enchants");
    }

    public int getMaximumEnchants(){
        return config.getInt("Maximum Number of Enchants");
    }

    public List<String> getItemsNameBank(){
        return config.getStringList("Display Name Bank");
    }

    public List<String> getFirstLineLoreWordBank(){
        return config.getStringList("Lore Banks.First Line Word Bank");
    }

    public List<String> getSecondLineLoreWordBank(){
        return config.getStringList("Lore Banks.Second Line Word Bank");
    }

    public List<String> getThirdLineLoreWordBank(){
        return config.getStringList("Lore Banks.Third Line Word Bank");
    }

}