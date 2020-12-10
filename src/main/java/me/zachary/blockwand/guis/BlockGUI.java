package me.zachary.blockwand.guis;

import me.zachary.blockwand.Blockwand;
import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;

public class BlockGUI extends GUI {
    private Blockwand plugin;

    public BlockGUI(Player player, Blockwand plugin) {
        super(player);
        this.plugin = plugin;
    }

    @Override
    protected GUIRows getRows() {
        if(plugin.getConfig().getStringList("Block list gui").size() <= 9){
            return GUIRows.ONE;
        }else if(plugin.getConfig().getStringList("Block list gui").size() <= 18){
            return GUIRows.TWO;
        }else if(plugin.getConfig().getStringList("Block list gui").size() <= 27){
            return GUIRows.THREE;
        }else if(plugin.getConfig().getStringList("Block list gui").size() <= 36){
            return GUIRows.FOUR;
        }else if(plugin.getConfig().getStringList("Block list gui").size() <= 45){
            return GUIRows.FIVE;
        }else if(plugin.getConfig().getStringList("Block list gui").size() <= 54){
            return GUIRows.SIX;
        }
        return GUIRows.SIX;
    }

    @Override
    protected String getTitle() {
        return "§6§lBlock Selector";
    }
}
