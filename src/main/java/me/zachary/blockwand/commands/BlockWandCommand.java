package me.zachary.blockwand.commands;

import me.zachary.blockwand.Blockwand;
import me.zachary.blockwand.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.theprogramsrc.supercoreapi.spigot.commands.CommandResult;
import xyz.theprogramsrc.supercoreapi.spigot.commands.SpigotCommand;
import xyz.theprogramsrc.supercoreapi.spigot.utils.SpigotConsole;

import java.util.ArrayList;
import java.util.List;

public class BlockWandCommand extends SpigotCommand {
    private Blockwand plugin;

    public BlockWandCommand(Blockwand blockwand) {
        this.plugin = blockwand;
    }

    @Override
    public String getCommand() {
        return "blockwand";
    }

    @Override
    public CommandResult onPlayerExecute(Player player, String[] strings) {
        if(!player.hasPermission("blockwand.give")){
            player.sendMessage(ChatUtils.color("&4You don't have permission to execute this comamnd."));
            return CommandResult.COMPLETED;
        }
        if(strings[0].equalsIgnoreCase("give")){
            if(strings.length == 2){
                Player target = Bukkit.getPlayer(strings[1]);
                if(target != null){
                    target.getInventory().setItemInMainHand(plugin.getBlockWand(plugin.getConfig().getString("Default wand.Block"), plugin.getConfig().getString("Default wand.Cost")));
                }else
                    player.sendMessage(ChatUtils.color("Errors, this player is not found"));
            }else
                player.getInventory().setItemInMainHand(plugin.getBlockWand(plugin.getConfig().getString("Default wand.Block"), plugin.getConfig().getString("Default wand.Cost")));
        }
        return CommandResult.COMPLETED;
    }

    @Override
    public CommandResult onConsoleExecute(SpigotConsole spigotConsole, String[] strings) {
        return CommandResult.COMPLETED;
    }

    @Override
    public List<String> getCommandComplete(Player player, String alias, String[] args) {
        List<String> args1 = new ArrayList<>();
        List<String> args2 = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        if(args[0].equals("give")){
            for (int i = 0; i < players.length; i++) {
                args2.add(players[i].getName());
            }
            return args2;
        }
        if(args[0] != null){
            if(player.hasPermission("blockwand.give")) args1.add("give");
            return args1;
        }
        return args2;
    }
}
