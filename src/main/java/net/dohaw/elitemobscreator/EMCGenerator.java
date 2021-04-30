package net.dohaw.elitemobscreator;

import net.dohaw.corelib.StringUtils;
import net.dohaw.elitemobscreator.config.BaseConfig;
import net.dohaw.elitemobscreator.config.FieldValuesConfig;
import net.dohaw.elitemobscreator.config.GeneratedFileConfig;
import net.dohaw.elitemobscreator.config.WordBanksConfig;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryType;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EMCGenerator {

    private static List<Material> validItemMaterials;
    private static List<Material> validBossArmorTypes;

    public static void generateBoss(BaseConfig config, FieldValuesConfig fvConfig) throws IOException {

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

        double healthMultiplier = toFourDecimalPlaces(current.nextDouble(config.getMinHealthMultiplier(), config.getMaxHealthMultiplier()));
        double damageMultiplier = toFourDecimalPlaces(current.nextDouble(config.getMinDamageMultiplier(), config.getMaxDamageMultiplier()));
        double spawnChance = toFourDecimalPlaces(current.nextDouble(config.getMinSpawnChance(), config.getMaxSpawnChance()));

        Material randomHelmet = getRandomArmorPiece(ArmorType.HELMET);
        Material randomChestplate = getRandomArmorPiece(ArmorType.CHESTPLATE);
        Material randomLeggings = getRandomArmorPiece(ArmorType.LEGGINGS);
        Material randomBoots = getRandomArmorPiece(ArmorType.BOOTS);

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
        genConfig.set("chestplate", randomChestplate.name());
        genConfig.set("leggings", randomLeggings.name());
        genConfig.set("boots", randomBoots.name());
        genConfig.set("helmet", randomHelmet.name());

        generatedConfig.saveConfig();

    }

    public static void generateItem(BaseConfig config, WordBanksConfig wbConfig) throws IOException {

        ThreadLocalRandom current = ThreadLocalRandom.current();

        Material randomMaterial = validItemMaterials.get(current.nextInt(validItemMaterials.size()));
        
        int randomNumberEnchants = current.nextInt(config.getMinimumEnchants(), config.getMaximumEnchants());
        List<Enchantment> applicableEnchantments = getApplicableEnchantments(randomMaterial);
        List<String> randomEnchantments = new ArrayList<>();

        for (int i = 0; i < randomNumberEnchants; i++) {
            Enchantment randomEnchantment = applicableEnchantments.get(current.nextInt(applicableEnchantments.size()));
            randomEnchantments.add(randomEnchantment.getName().toUpperCase() + "," + randomEnchantment.getMaxLevel());
        }

        String randomDisplayName = createRandomItemName(wbConfig, config.getNameFormats(), current);
        if(randomDisplayName.contains("$itemType")){
            String matStr = randomMaterial.toString().toLowerCase();
            matStr = matStr.replace("_", " ");
            matStr = matToProperString(matStr);
            randomDisplayName = randomDisplayName.replace("$itemType", matStr);
        }

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



    public static double toFourDecimalPlaces(double dub){
        BigDecimal bd = new BigDecimal(Double.toString(dub));
        bd = bd.setScale(4, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String createRandomItemName(WordBanksConfig wbConfig, List<String> nameFormats, ThreadLocalRandom current){

        String randomNameFormat = nameFormats.get(current.nextInt(nameFormats.size()));
        if(randomNameFormat.contains("$verb")){
            List<String> verbs = wbConfig.getVerbs();
            String randomVerb = verbs.get(current.nextInt(verbs.size()));
            randomNameFormat = randomNameFormat.replace("$verb", randomVerb);
        }

        if(randomNameFormat.contains("$adjective")){
            List<String> adjectives = wbConfig.getAdjectives();
            String randomAdjective = adjectives.get(current.nextInt(adjectives.size()));
            randomNameFormat = randomNameFormat.replace("$adjective", randomAdjective);
        }

        if(randomNameFormat.contains("$verber")){
            List<String> verbers = wbConfig.getVerbers();
            String randomVerber = verbers.get(current.nextInt(verbers.size()));
            randomNameFormat = randomNameFormat.replace("$verber", randomVerber);
        }

        if(randomNameFormat.contains("$noun")) {
            List<String> nouns = wbConfig.getNouns();
            String randomVerber = nouns.get(current.nextInt(nouns.size()));
            randomNameFormat = randomNameFormat.replace("$noun", randomVerber);
        }

        return randomNameFormat;

    }

    public static String matToProperString(String matStr){

        // stores each characters to a char array
        char[] charArray = matStr.toCharArray();
        boolean foundSpace = true;

        for(int i = 0; i < charArray.length; i++) {

            // if the array element is a letter
            if(Character.isLetter(charArray[i])) {

                // check space is present before the letter
                if(foundSpace) {

                    // change the letter into uppercase
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            }

            else {
                // if the new character is not character
                foundSpace = true;
            }
        }

        return String.valueOf(charArray);

    }

    public static Material getRandomArmorPiece(ArmorType armorType){
        String armorTypeStr = armorType.name();
        List<Material> matchedArmorTypes = new ArrayList<>();
        for(Material mat : validBossArmorTypes){
            if(mat.name().contains(armorTypeStr)){
                System.out.println("MAT: " + mat);
                System.out.println("TYPE: " + armorTypeStr);
                matchedArmorTypes.add(mat);
            }
        }
        return matchedArmorTypes.get(ThreadLocalRandom.current().nextInt(matchedArmorTypes.size()));
    }

    public static void setValidItemMaterials(List<Material> validItemMaterials) {
        EMCGenerator.validItemMaterials = validItemMaterials;
    }

    public static void setValidBossArmorTypes(List<Material> validBossArmorTypes) {
        EMCGenerator.validBossArmorTypes = validBossArmorTypes;
    }


}
