package net.dohaw.elitemobscreator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class EMCCommand implements CommandExecutor {

    private EMCPlugin plugin;

    public EMCCommand(EMCPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            EMCGenerator.generateItem(plugin.getBaseConfig());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
