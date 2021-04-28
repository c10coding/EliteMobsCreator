package net.dohaw.elitemobscreator;

import net.dohaw.corelib.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class EMCGenerator {

    private static List<Material> validMaterials;

    public static void generateMob(BaseConfig config, FieldValuesConfig fvConfig) throws IOException {

        ThreadLocalRandom current = ThreadLocalRandom.current();

        List<String> entityTypes = fvConfig.getEntityTypes();
        String randomEntityType = entityTypes.get(current.nextInt(entityTypes.size()));

        List<String> nameBank = config.getMobNameBank();
        String randomName = nameBank.get(current.nextInt(nameBank.size()));
        int timeout = current.nextInt(config.getTimeoutMin(), config.getTimeoutMax());

        List<String> randomPowers = new ArrayList<>();
        List<String> powerPool = fvConfig.getPowers();
        for (int i = 0; i < config.getNumPowers(); i++) {
            String powerSelected = powerPool.get(current.nextInt(powerPool.size()));
            randomPowers.add(powerSelected);
        }

        String spawnMessage = StringUtils.colorString(randomName + "&c has spawned!");

        String fileName = StringUtils.removeChatColor(randomName);
        fileName = fileName.replaceAll(" ", "_");

        double healthMultiplier = current.nextDouble(config.getMinHealthMultiplier(), config.getMaxHealthMultiplier());
        double damageMultiplier = current.nextDouble(config.getMinDamageMultiplier(), config.getMaxDamageMultiplier());
        double spawnChance = current.nextDouble(config.getMinSpawnChance(), config.getMaxSpawnChance());

        File customBossesFolder = EMCPlugin.CUSTOM_BOSSES_FOLDER;
        File potentialDuplicateFile = new File(customBossesFolder, fileName);
        // If a duplicate file is made, it adds a number at the end depending on how many duplicate files there are.
        if(potentialDuplicateFile.exists()){
            int numDuplicateFiles = getNumDuplicateFiles(customBossesFolder, fileName);
            fileName = fileName + "_" + numDuplicateFiles;
        }

        fileName += ".yml";

        File generatedFile = new File(customBossesFolder, fileName);
        generatedFile.createNewFile();

        GeneratedFileConfig generatedConfig = new GeneratedFileConfig(generatedFile);
        FileConfiguration genConfig = generatedConfig.getConfig();

        genConfig.set("entityType", randomEntityType);
        genConfig.set("isEnabled", true);
        genConfig.set("name", randomName);
        genConfig.set("level", "dynamic");
        genConfig.set("healthMultiplier", healthMultiplier);
        genConfig.set("damageMultplier", damageMultiplier);
        genConfig.set("timeout", timeout);
        genConfig.set("isPersistent", true);
        genConfig.set("isBaby", false);
        genConfig.set("powers", randomPowers);
        genConfig.set("spawnMessage", spawnMessage);
        genConfig.set("deathMessage", "Some random death message");
        genConfig.set("uniqueLootList", "");
        genConfig.set("dropsEliteMobsLoot", true);
        genConfig.set("dropsVanillaLoot", false);
        genConfig.set("spawnChance", spawnChance);
        genConfig.set("announcementPriority", 1);
        genConfig.set("disguise", "");
        genConfig.set("frozen", false);

        generatedConfig.saveConfig();

    }

    public static void generateItem(BaseConfig config) throws IOException {

        ThreadLocalRandom current = ThreadLocalRandom.current();

        Material randomMaterial = validMaterials.get(current.nextInt(validMaterials.size()));
        
        int randomNumberEnchants = current.nextInt(config.getMinimumEnchants(), config.getMaximumEnchants());
        List<Enchantment> applicableEnchantments = getApplicableEnchantments(randomMaterial);
        List<String> randomEnchantments = new ArrayList<>();

        for (int i = 0; i < randomNumberEnchants; i++) {
            Enchantment randomEnchantment = applicableEnchantments.get(current.nextInt(applicableEnchantments.size()));
            randomEnchantments.add(randomEnchantment.getName().toUpperCase() + "," + randomEnchantment.getMaxLevel());
        }

        List<String> itemNameBank = config.getItemsNameBank();
        String randomDisplayName = itemNameBank.get(current.nextInt(itemNameBank.size()));

        List<String> randomLore = new ArrayList<>();
        List<String> firstLineWordBank = config.getFirstLineLoreWordBank();
        List<String> secondLineWordBank = config.getSecondLineLoreWordBank();
        List<String> thirdLineWorkBank = config.getThirdLineLoreWordBank();

        //Gets random lore lines
        randomLore.add(firstLineWordBank.get(current.nextInt(firstLineWordBank.size())));
        randomLore.add(secondLineWordBank.get(current.nextInt(secondLineWordBank.size())));
        randomLore.add(thirdLineWorkBank.get(current.nextInt(thirdLineWorkBank.size())));

        String fileName = StringUtils.removeChatColor(randomDisplayName);
        fileName = fileName.replaceAll(" ", "_");

        File customItemsFolder = EMCPlugin.CUSTOM_ITEMS_FOLDER;
        File potentialDuplicateFile = new File(customItemsFolder, fileName);
        if(potentialDuplicateFile.exists()){
            int numDuplicateFiles = getNumDuplicateFiles(customItemsFolder, fileName);
            fileName = fileName + "_" + numDuplicateFiles;
        }

        fileName += ".yml";

        File generatedFile = new File(customItemsFolder, fileName);
        generatedFile.createNewFile();

        GeneratedFileConfig generatedConfig = new GeneratedFileConfig(generatedFile);
        FileConfiguration genConfig = generatedConfig.getConfig();

        genConfig.set("isEnabled", true);
        genConfig.set("name", randomDisplayName);
        genConfig.set("material", randomMaterial.toString());
        genConfig.set("lore", randomLore);
        genConfig.set("enchantments", randomEnchantments);
        genConfig.set("potionEffects", " ");
        genConfig.set("dropWeight", "dynamic");
        genConfig.set("scalability", "scalable");
        genConfig.set("itemType", "unique");

        generatedConfig.saveConfig();

    }

    private static int getNumDuplicateFiles(File folder, String fileName){
        int numDuplicates = 0;
        for(String fName : folder.list()){
            if(fName.equalsIgnoreCase(fileName)){
                numDuplicates++;
            }
        }
        return numDuplicates;
    }
    
    private static List<Enchantment> getApplicableEnchantments(Material material){
        List<Enchantment> applicableEnchantments = new ArrayList<>();
        for(Enchantment ench : Enchantment.values()){
            if(ench.getItemTarget().includes(material)){
                applicableEnchantments.add(ench);
            }
        }
        return applicableEnchantments;
    }

    public static void setValidMaterials(List<Material> validMaterials) {
        EMCGenerator.validMaterials = validMaterials;
    }

}
