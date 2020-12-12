package me.zachary.blockwand.files;

import me.zachary.blockwand.Blockwand;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.utils.storage.SpigotYMLConfig;

public class MessageFile extends SpigotModule {
    private Blockwand plugin;
    private SpigotYMLConfig cfg;

    public MessageFile(Blockwand plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad() {
        this.cfg = new SpigotYMLConfig(this.getPluginFolder(), "messages.yml");
        this.loadDefaults();
    }

    private void loadDefaults() {
        this.cfg.getConfig().options().header("Here you can modified message");
        this.cfg.getConfig().options().copyHeader(true);
        this.cfg.add("No Permissions", "&4You don't have permission to execute this comamnd.");
        this.cfg.add("Player not found", "Errors, this player is not found.");
        this.cfg.add("Successful reload", "&cYou have successful reload the config.");
        this.cfg.add("Not enough block", "&cYou don't have enough item in your inventory to do that. You need 1 %Block%");
        this.cfg.add("Not enough money", "&cYou don't have enough money. You need %Money%$");
        this.cfg.add("Block selector Item lore item", "&eTake block in your inventory.");
        this.cfg.add("Block selector Item lore money", "&7Cost per block placed: &e%Money%$");
        this.cfg.add("Block wand name", "&aInfinite Block Wand");
        this.cfg.add("Block wand lore item ability", "&6Item Ability: Place a stone block &e&lRIGHT CLICK");
        this.cfg.add("Block wand lore place block", "&7Place a %Block% block.");
        this.cfg.add("Block wand lore item inventory", "&eTake block in your inventory.");
        this.cfg.add("Block wand lore item cost block", "&7Cost per block:&6 %Price% &7$");
        this.cfg.add("Block wand lore current block", "&7Current block&6 %Item%");
    }

    public String getString(String path){
        return this.cfg.getString(path);
    }
}
