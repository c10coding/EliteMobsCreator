package net.dohaw.elitemobscreator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EMCCommand implements CommandExecutor {

    private EMCPlugin plugin;

    public EMCCommand(EMCPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            EMCGenerator.generateMobValues(plugin.getBaseConfig(), plugin.getFieldValueConfig());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
