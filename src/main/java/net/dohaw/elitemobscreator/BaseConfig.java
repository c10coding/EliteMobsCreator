package net.dohaw.elitemobscreator;

import net.dohaw.corelib.Config;

import java.util.List;

public class BaseConfig extends Config {

    public BaseConfig() {
        super("config.yml");
    }

    public List<String> getNameBank(){
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

}
