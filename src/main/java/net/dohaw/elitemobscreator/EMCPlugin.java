package net.dohaw.elitemobscreator;

import net.dohaw.corelib.CoreLib;
import net.dohaw.corelib.JPUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class EMCPlugin extends JavaPlugin {

    public static final File CUSTOM_BOSSES_FOLDER = new File("plugins/EliteMobs", "custombosses");
    public static final File CUSTOM_ITEMS_FOLDER = new File("plugins/EliteMobs", "customitems");

    private FieldValuesConfig fvConfig;
    private BaseConfig baseConfig;

    @Override
    public void onEnable() {
        CoreLib.setInstance(this);
        JPUtils.validateFiles("config.yml", "field_values.yml");
        this.baseConfig = new BaseConfig();
        this.fvConfig = new FieldValuesConfig();
        JPUtils.registerCommand("elitemobscreator", new EMCCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public BaseConfig getBaseConfig() {
        return baseConfig;
    }

    public FieldValuesConfig getFieldValueConfig(){
        return fvConfig;
    }

}
