package me.zachary.blockwand.listeners;

import me.zachary.blockwand.Blockwand;
import me.zachary.blockwand.ChatUtils;
import me.zachary.blockwand.guis.BlockGUI;
import org.apache.commons.lang.WordUtils;
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
import org.bukkit.inventory.meta.ItemMeta;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;

import java.util.ArrayList;
import java.util.List;

public class RightClickListeners implements Listener {
    private final Blockwand plugin;

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
        String[] price_lore = event.getItem().getItemMeta().getLore().get(2).split(" ");
        String[] material_lore = event.getItem().getItemMeta().getLore().get(3).split(" ");
        if(block != null && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getItem().isSimilar(plugin.getBlockWand(material_lore[2], price_lore[3]))){
            if(Blockwand.econ.getBalance(player) >= Double.parseDouble(price_lore[3])){
                World world = Bukkit.getWorld(player.getWorld().getUID());
                Location Loc = new Location(world, event.getClickedBlock().getX() + event.getBlockFace().getModX(), event.getClickedBlock().getY() + event.getBlockFace().getModY(), event.getClickedBlock().getZ() + event.getBlockFace().getModZ());
                Loc.getBlock().setType(Material.valueOf(material_lore[2]));
                Blockwand.econ.withdrawPlayer(player, Double.parseDouble(price_lore[3]));
            }else
                player.sendMessage(ChatUtils.color("&cYou don't have enough money. You need " + price_lore[3] + "$"));
        }else if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            BlockGUI blockGUI = new BlockGUI(player, plugin);
            blockGUI.isTitleCentered();
            Integer i = 0;
            for(String item : plugin.getConfig().getStringList("Block list gui")){
                String[] split = item.split(",");
                ItemStack itemblock = new ItemStack(Material.valueOf(split[0]));
                ItemMeta itemMeta = itemblock.getItemMeta();
                itemMeta.setDisplayName(ChatUtils.color("&6") + WordUtils.capitalize(split[0].toLowerCase().replace("_", " ")));
                List<String> lore = new ArrayList<>();
                lore.add(ChatUtils.color("&7Cost per block placed: &e" + split[1] + "$"));
                itemMeta.setLore(lore);
                itemblock.setItemMeta(itemMeta);

                blockGUI.addButton(new GUIButton(i, itemblock, clickAction -> player.getInventory().setItemInMainHand(plugin.getBlockWand(split[0], split[1]))));
                i++;
            }
            blockGUI.open();
        }
        block = null;
    }
}
