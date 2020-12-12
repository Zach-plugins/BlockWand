package me.zachary.blockwand;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.zachary.blockwand.commands.BlockWandCommand;
import me.zachary.blockwand.files.MessageFile;
import me.zachary.blockwand.listeners.RightClickListeners;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import xyz.theprogramsrc.supercoreapi.global.Metrics;
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
        saveDefaultConfig();
        int pluginId = 9628;
        Metrics metrics = new Metrics(this, pluginId);

        new BlockWandCommand(this);
        new RightClickListeners(this);
        new MessageFile(this);

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

    @Override
    public boolean isPaid() {
        return false;
    }

    public MessageFile getMessageFile(){
        return new MessageFile(this);
    }

    public ItemStack getBlockWand(String material, String price){
        ItemStack blockwand = new ItemStack(Material.STICK);
        ItemMeta blockwandmeta = blockwand.getItemMeta();
        blockwandmeta.setDisplayName(ChatUtils.color(getMessageFile().getString("Block wand name")));
        blockwandmeta.setLore(getLore(material, price));
        blockwand.setItemMeta(blockwandmeta);
        NBTItem nbtblockwand = new NBTItem(blockwand);
        nbtblockwand.setString("Material", material);
        nbtblockwand.setString("Price", price);
        return nbtblockwand.getItem();
    }

    public List<String> getLore(String material, String price){
        List<String> lore = new ArrayList<>();
        lore.add(ChatUtils.color(getMessageFile().getString("Block wand lore item ability")));
        lore.add(ChatUtils.color(getMessageFile().getString("Block wand lore place block").replace("%Block%", material.toLowerCase().replace("_", " "))));
        if(getConfig().getBoolean("Take block in inventory"))
            lore.add(ChatUtils.color(getMessageFile().getString("Block wand lore item inventory")));
        else
            lore.add(ChatUtils.color(getMessageFile().getString("Block wand lore item cost block").replace("%Price%", price)));
        lore.add(ChatUtils.color(getMessageFile().getString("Block wand lore current block").replace("%Item%", material)));
        return lore;
    }
}
