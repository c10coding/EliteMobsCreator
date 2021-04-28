package net.dohaw.elitemobscreator;

import net.dohaw.corelib.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EMCGenerator {

    public static void generateMobValues(BaseConfig config, FieldValuesConfig fvConfig) throws IOException {

        ThreadLocalRandom current = ThreadLocalRandom.current();

        List<String> entityTypes = fvConfig.getEntityTypes();
        String randomEntityType = entityTypes.get(current.nextInt(entityTypes.size()));

        List<String> nameBank = config.getNameBank();
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
        if(potentialDuplicateFile.exists()){
            System.out.println("In here");
            int numDuplicateFiles = getNumDuplicateFiles(customBossesFolder, fileName);
            fileName = fileName + "_" + numDuplicateFiles;
        }

        fileName = fileName + ".yml";

        System.out.println("FILENAME: " + fileName);
        System.out.println("PATH: " + potentialDuplicateFile.getAbsolutePath());
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
        genConfig.set("uniqueLootList", new ArrayList<>());
        genConfig.set("dropsEliteMobsLoot", true);
        genConfig.set("dropsVanillaLoot", false);
        genConfig.set("spawnChance", spawnChance);
        genConfig.set("announcementPriority", 1);
        genConfig.set("disguise", "Set me manually!!!");
        genConfig.set("frozen", false);

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

}
