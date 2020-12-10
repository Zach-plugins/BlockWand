package me.zachary.blockwand;

import me.zachary.blockwand.commands.BlockWandCommand;
import me.zachary.blockwand.listeners.RightClickListeners;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Blockwand extends SpigotPlugin {
    public static Economy econ = null;

    @Override
    public void onPluginLoad() {

    }

    @Override
    public void onPluginEnable() {
        new BlockWandCommand(this);
        new RightClickListeners(this);

        if (!setupEconomy() ) {
            System.out.println(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onPluginDisable() {

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
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
