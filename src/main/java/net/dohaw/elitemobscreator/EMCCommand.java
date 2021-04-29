package net.dohaw.elitemobscreator;

import net.dohaw.corelib.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class EMCCommand implements CommandExecutor {

    private final EMCPlugin plugin;

    public EMCCommand(EMCPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean executed = true;
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("i") || args[0].equalsIgnoreCase("item")){

                boolean generated;
                try {
                    EMCGenerator.generateItem(plugin.getBaseConfig());
                    generated = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    generated = false;
                }

                if(generated){
                    sender.sendMessage(StringUtils.colorString("&bYou have created a new custom EliteMobs item!"));
                }else{
                    sender.sendMessage(StringUtils.colorString("&cThere was an error while trying to create your item."));
                }

            }else if(args[0].equalsIgnoreCase("b") || args[0].equalsIgnoreCase("boss")) {

                boolean generated;
                try {
                    EMCGenerator.generateBoss(plugin.getBaseConfig(), plugin.getFieldValueConfig());
                    generated = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    generated = false;
                }

                if (generated) {
                    sender.sendMessage(StringUtils.colorString("&bYou have generated a new custom EliteMobs boss!"));
                } else {
                    sender.sendMessage(StringUtils.colorString("&cThere was an error while trying to create your custom boss."));
                }
            }else if(args[0].equalsIgnoreCase("reload")){
                plugin.getBaseConfig().reloadConfig();
                plugin.getFieldValueConfig().reloadConfig();
                sender.sendMessage(StringUtils.colorString("&aThe EliteMobsCreator configs have been reloaded!"));
            }else{
                executed = false;
            }
        }else{
            executed = false;
        }

        if(!executed){
            sender.sendMessage(StringUtils.colorString("&cEliteMobs&7Creator&f commands:"));
            sender.sendMessage(StringUtils.colorString("&6/emc b &f- Generates a boss config"));
            sender.sendMessage(StringUtils.colorString("&6/emc i &f- Generates a item config"));
        }

        return executed;
    }

}
