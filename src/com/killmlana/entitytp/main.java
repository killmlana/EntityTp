package com.killmlana.entitytp;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) {
            getLogger().info("Player has teleported");
            Player player = (Player) event.getPlayer();
                int radius = 10;
                for (Entity pet : player.getWorld().getNearbyEntities(event.getFrom(), radius, radius, radius)) {
                    if (pet instanceof Tameable && pet instanceof Sittable) {
                        if (((((Tameable) pet).isTamed())) && ((Tameable) pet).getOwner().equals(event.getPlayer()) && (!((Sittable) pet).isSitting())) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                                public void run() {
                                    event.getFrom().getChunk().load();
                                    pet.teleport(player);
                                }

                            }, 20L);

                        }
                    }
                }
            }

        if (event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) {
            Player player = (Player) event.getPlayer();
            int radius = 2;
            for (Entity entity : player.getWorld().getNearbyEntities(event.getFrom(), radius, radius, radius)) {
                if (entity instanceof Vehicle && entity instanceof Tameable) {
                    if (entity.getEntityId() == player.getVehicle().getEntityId()) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                            public void run() {
                                entity.teleport(player);
                                event.getFrom().getChunk().load();
                                getLogger().info("Player is inside vehicle");
                            }

                        }, 20L);
                    }
                }
            }

        }
    }
}