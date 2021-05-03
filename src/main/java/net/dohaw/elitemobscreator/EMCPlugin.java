package net.dohaw.elitemobscreator;

import net.dohaw.corelib.CoreLib;
import net.dohaw.corelib.JPUtils;
import net.dohaw.corelib.helpers.ItemStackHelper;
import net.dohaw.elitemobscreator.config.BaseConfig;
import net.dohaw.elitemobscreator.config.FieldValuesConfig;
import net.dohaw.elitemobscreator.config.WordBanksConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A plugin that generates randomly customized boss or item files for the plugin "EliteMobs"
 *
 * @author Caleb Owens (c10coding on Github)
 */
public final class EMCPlugin extends JavaPlugin {

    public static final File CUSTOM_BOSSES_FOLDER = new File("plugins/EliteMobs", "custombosses");
    public static final File CUSTOM_ITEMS_FOLDER = new File("plugins/EliteMobs", "customitems");

    private FieldValuesConfig fvConfig;
    private BaseConfig baseConfig;
    private WordBanksConfig wbConfig;

    @Override
    public void onEnable() {
        CoreLib.setInstance(this);
        JPUtils.validateFiles("config.yml", "field_values.yml", "word_banks.yml");
        this.baseConfig = new BaseConfig();
        this.fvConfig = new FieldValuesConfig();
        this.wbConfig = new WordBanksConfig();
        JPUtils.registerCommand("elitemobscreator", new EMCCommand(this));
        EMCGenerator.setValidItemMaterials(getValidItemMaterials());
        EMCGenerator.setValidBossArmorTypes(getValidMaterials(BaseConfig.ExclusionType.BOSS_ARMOR));
        EMCGenerator.setValidMainHandItems(getValidMaterials(BaseConfig.ExclusionType.MAIN_HAND));
        EMCGenerator.setValidOffHandItems(getValidMaterials(BaseConfig.ExclusionType.OFF_HAND));
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

    public WordBanksConfig getWordBanksConfig(){
        return wbConfig;
    }

    private List<Material> getValidItemMaterials(){

        List<Material> validMaterials = new ArrayList<>();
        for(Material mat : Material.values()){

            String materialName = mat.toString().toLowerCase();
            boolean hasKeyWord = hasKeyWord(materialName, false);
            boolean hasExcludedWord = hasKeyWord(materialName, true);
            if(hasKeyWord && !hasExcludedWord){
                validMaterials.add(mat);
            }

        }

        return validMaterials;

    }

    private List<Material> getValidMaterials(BaseConfig.ExclusionType exclusionType){

        List<Material> validMaterials = new ArrayList<>();
        List<Material> excludedMaterials = baseConfig.getExcludedMaterials(exclusionType);
        for(Material mat : Material.values()){
            if(exclusionType == BaseConfig.ExclusionType.MAIN_HAND || exclusionType == BaseConfig.ExclusionType.OFF_HAND){
                if(ItemStackHelper.isToolOrWeapon(new ItemStack(mat)) && !excludedMaterials.contains(mat)){
                    validMaterials.add(mat);
                }
            }else if(exclusionType == BaseConfig.ExclusionType.BOSS_ARMOR){
                if(ItemStackHelper.isArmor(mat) && !excludedMaterials.contains(mat)){
                    validMaterials.add(mat);
                }
            }

        }

        return validMaterials;

    }

    private boolean hasKeyWord(String materialName, boolean excluded){

        List<String> validKeyWords = Arrays.asList("axe", "sword", "helmet", "leggings", "chestplate", "boots");
        List<String> excludedKeyWords = Arrays.asList("pickaxe", "turtle", "diamond", "netherrite");

        List<String> loopedList = excluded ? excludedKeyWords : validKeyWords;

        for(String s : loopedList){
            if(materialName.contains(s)){
                return true;
            }
        }

        return false;

    }

}
