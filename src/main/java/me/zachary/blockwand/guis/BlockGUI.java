package me.zachary.blockwand.guis;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;

public class BlockGUI extends GUI {

    public BlockGUI(Player player) {
        super(player);
    }

    @Override
    protected GUIRows getRows() {
        return GUIRows.ONE;
    }

    @Override
    protected String getTitle() {
        return "§6§lBlock Selector";
    }
}
