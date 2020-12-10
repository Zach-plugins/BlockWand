package me.zachary.blockwand.listeners;

import me.zachary.blockwand.Blockwand;
import me.zachary.blockwand.guis.BlockGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;

public class RightClickListeners implements Listener {
    private Blockwand plugin;

    public RightClickListeners(Blockwand plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    Block block;
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getHand() == EquipmentSlot.OFF_HAND || event.getItem() == null){
            block = event.getClickedBlock();
            return;
        }
        String[] material_lore = event.getItem().getItemMeta().getLore().get(3).split(" ");
        if(block != null && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getItem().isSimilar(plugin.getBlockWand(material_lore[2]))){
            World world = Bukkit.getWorld(player.getWorld().getUID());
            Location Loc = new Location(world, event.getClickedBlock().getX() + event.getBlockFace().getModX(), event.getClickedBlock().getY() + event.getBlockFace().getModY(), event.getClickedBlock().getZ() + event.getBlockFace().getModZ());
            Loc.getBlock().setType(Material.valueOf(material_lore[2]));
        }else if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            BlockGUI blockGUI = new BlockGUI(player);
            blockGUI.isTitleCentered();
            blockGUI.addButton(new GUIButton(0, new ItemStack(Material.STONE), clickAction -> player.getInventory().setItemInMainHand(plugin.getBlockWand("STONE"))));
            blockGUI.addButton(new GUIButton(1, new ItemStack(Material.DIRT), clickAction -> player.getInventory().setItemInMainHand(plugin.getBlockWand("DIRT"))));
            blockGUI.addButton(new GUIButton(2, new ItemStack(Material.COBBLESTONE), clickAction -> player.getInventory().setItemInMainHand(plugin.getBlockWand("COBBLESTONE"))));
            blockGUI.addButton(new GUIButton(3, new ItemStack(Material.OAK_LOG), clickAction -> player.getInventory().setItemInMainHand(plugin.getBlockWand("OAK_LOG"))));
            blockGUI.addButton(new GUIButton(4, new ItemStack(Material.SAND), clickAction -> player.getInventory().setItemInMainHand(plugin.getBlockWand("SAND"))));
            blockGUI.addButton(new GUIButton(5, new ItemStack(Material.GRAVEL), clickAction -> player.getInventory().setItemInMainHand(plugin.getBlockWand("GRAVEL"))));
            blockGUI.addButton(new GUIButton(6, new ItemStack(Material.GRASS_BLOCK), clickAction -> player.getInventory().setItemInMainHand(plugin.getBlockWand("GRASS_BLOCK"))));
            blockGUI.addButton(new GUIButton(7, new ItemStack(Material.DIAMOND_BLOCK), clickAction -> player.getInventory().setItemInMainHand(plugin.getBlockWand("DIAMOND_BLOCK"))));
            blockGUI.addButton(new GUIButton(8, new ItemStack(Material.OAK_PLANKS), clickAction -> player.getInventory().setItemInMainHand(plugin.getBlockWand("OAK_PLANKS"))));
            blockGUI.open();
        }
        block = null;
    }
}
