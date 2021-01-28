package me.specnr.multiplayericarus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;

public final class MultiplayerIcarus extends JavaPlugin implements Listener {

    public void sendAll(String msg) {
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("[§4§oIcarus§r] " + msg));
    }

    public PlayerInventory giveFireworks(Player p) {
        PlayerInventory pi = p.getInventory();
        pi.addItem(new ItemStack(Material.FIREWORK_ROCKET, 64));
        return pi;
    }

    public void givePlayerItems(Player p) {
        ItemStack[] elytra = {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.ELYTRA)};
        ItemMeta meta = elytra[2].getItemMeta();
        assert meta != null;
        meta.setUnbreakable(true);
        elytra[2].setItemMeta(meta);
        PlayerInventory pi = giveFireworks(p);
        pi.setArmorContents(elytra);
    }

    @EventHandler
    public void onRespawnEvent(PlayerRespawnEvent e){
        givePlayerItems(e.getPlayer());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("icarus")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                givePlayerItems(p);
            }
            sendAll("Challenge started!");
        } else if (command.getName().equals("refill")) {
            giveFireworks(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())));
        }
        return false;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        System.out.println("Multiplayer Icarus Loaded");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
