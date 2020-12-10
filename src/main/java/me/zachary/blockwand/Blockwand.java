package me.zachary.blockwand;

import me.zachary.blockwand.commands.BlockWandCommand;
import me.zachary.blockwand.listeners.RightClickListeners;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Blockwand extends SpigotPlugin {

    @Override
    public void onPluginLoad() {

    }

    @Override
    public void onPluginEnable() {
        new BlockWandCommand(this);
        new RightClickListeners(this);
    }

    @Override
    public void onPluginDisable() {

    }

    public ItemStack getBlockWand(String string){
        ItemStack blockwand = new ItemStack(Material.STICK);
        ItemMeta blockwandmeta = blockwand.getItemMeta();
        blockwandmeta.setDisplayName(ChatUtils.color("&aInfinite Block Wand"));
        blockwandmeta.setLore(getLore(string));
        blockwand.setItemMeta(blockwandmeta);
        return blockwand;
    }

    public List<String> getLore(String string){
        List<String> lore = new ArrayList<>();
        lore.add(ChatUtils.color("&6Item Ability: Place a stone block &e&lRIGHT CLICK"));
        lore.add(ChatUtils.color("&7Place a stone block."));
        lore.add(ChatUtils.color("&7Cost: &6FREE"));
        lore.add(ChatUtils.color("&7Current block&6 " + string));
        return lore;
    }
}
