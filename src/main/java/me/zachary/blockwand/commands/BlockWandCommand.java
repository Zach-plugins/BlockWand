package me.zachary.blockwand.commands;

import me.zachary.blockwand.Blockwand;
import me.zachary.blockwand.ChatUtils;
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
        if(strings[0].equalsIgnoreCase("give")){
            player.getInventory().setItemInMainHand(plugin.getBlockWand(plugin.getConfig().getString("Default wand.Block"), plugin.getConfig().getString("Default wand.Cost")));
        }
        return CommandResult.COMPLETED;
    }

    @Override
    public CommandResult onConsoleExecute(SpigotConsole spigotConsole, String[] strings) {
        return CommandResult.COMPLETED;
    }
}
