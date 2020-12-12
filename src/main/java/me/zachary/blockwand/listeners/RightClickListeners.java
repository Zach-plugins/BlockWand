package me.zachary.blockwand.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
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
        NBTItem nbtItem = new NBTItem(event.getItem());
        String price = nbtItem.getString("Price");
        String material = nbtItem.getString("Material");
        if(block != null && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getItem().isSimilar(plugin.getBlockWand(material, price))){
            World world = Bukkit.getWorld(player.getWorld().getUID());
            if(plugin.getConfig().getBoolean("Take block in inventory")){
                if(player.getInventory().contains(Material.valueOf(material))){
                    Location Loc = new Location(world, event.getClickedBlock().getX() + event.getBlockFace().getModX(), event.getClickedBlock().getY() + event.getBlockFace().getModY(), event.getClickedBlock().getZ() + event.getBlockFace().getModZ());
                    Loc.getBlock().setType(Material.valueOf(material));
                    ItemStack itemRemove = new ItemStack(Material.valueOf(material));
                    itemRemove.setAmount(1);
                    player.getInventory().removeItem(itemRemove);
                }else
                    player.sendMessage(ChatUtils.color(plugin.getMessageFile().getString("Not enough block").replace("%Block%", material.toLowerCase().replace("_", " "))));
            }else{
                if(Blockwand.econ.getBalance(player) >= Double.parseDouble(price)){
                    Location Loc = new Location(world, event.getClickedBlock().getX() + event.getBlockFace().getModX(), event.getClickedBlock().getY() + event.getBlockFace().getModY(), event.getClickedBlock().getZ() + event.getBlockFace().getModZ());
                    Loc.getBlock().setType(Material.valueOf(material));
                    Blockwand.econ.withdrawPlayer(player, Double.parseDouble(price));
                }else
                    player.sendMessage(ChatUtils.color(plugin.getMessageFile().getString("Not enough money").replace("%Money%", price)));
            }
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
                if(plugin.getConfig().getBoolean("Take block in inventory"))
                    lore.add(ChatUtils.color(plugin.getMessageFile().getString("Block selector Item lore item")));
                else
                    lore.add(ChatUtils.color(plugin.getMessageFile().getString("Block selector Item lore money").replace("%Money%", split[1])));
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
