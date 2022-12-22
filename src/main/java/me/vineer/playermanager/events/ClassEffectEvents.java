package me.vineer.playermanager.events;

import me.vineer.playermanager.PlayerClass;
import me.vineer.playermanager.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeWrapper;
import org.bukkit.scheduler.BukkitRunnable;

public class ClassEffectEvents implements Listener {
    @EventHandler
    public void onPlayerDrinkMilk(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() != Material.MILK_BUCKET) return;
        Player player = e.getPlayer();
        String Class = PlayerClass.getPlayerClass(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(Class.equals(PlayerClass.ATLANT_CLASS)) {
                    player.addPotionEffect(PotionEffectTypeWrapper.CONDUIT_POWER.createEffect(Integer.MAX_VALUE, 0));
                } else if(Class.equals(PlayerClass.FIRE_CLASS)) {
                    player.addPotionEffect(PotionEffectTypeWrapper.FIRE_RESISTANCE.createEffect(Integer.MAX_VALUE, 0));
                }
            }
        }.runTaskLater(PlayerManager.getPlugin(), 2L);
    }


    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        String Class = PlayerClass.getPlayerClass(killer);
        if(Class.equals(PlayerClass.BERSERK_CLASS)) {
            killer.addPotionEffect(PotionEffectTypeWrapper.REGENERATION.createEffect(100, 2));
            killer.addPotionEffect(PotionEffectTypeWrapper.INCREASE_DAMAGE.createEffect(200, 0));
        }
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if((event.getEntity().getKiller() instanceof Player)) {
            Player player = event.getEntity().getKiller();
            String Class = PlayerClass.getPlayerClass(player);
            if(Class.equals(PlayerClass.HUNTER_CLASS)) {
                player.addPotionEffect(PotionEffectTypeWrapper.REGENERATION.createEffect(20, 2));
            }
        }
    }

    @EventHandler
    public void onJoinPlayerjoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String Class = PlayerClass.getPlayerClass(player);
        if(Class.equals(PlayerClass.ATLANT_CLASS)) {
            player.addPotionEffect(PotionEffectTypeWrapper.CONDUIT_POWER.createEffect(Integer.MAX_VALUE, 0));
        } else if(Class.equals(PlayerClass.FIRE_CLASS)) {
            player.addPotionEffect(PotionEffectTypeWrapper.FIRE_RESISTANCE.createEffect(Integer.MAX_VALUE, 0));
        }
    }

    @EventHandler
    public void onDeathPlayer(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        String Class = PlayerClass.getPlayerClass(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(Class.equals(PlayerClass.ATLANT_CLASS)) {
                    player.addPotionEffect(PotionEffectTypeWrapper.CONDUIT_POWER.createEffect(Integer.MAX_VALUE, 0));
                } else if(Class.equals(PlayerClass.FIRE_CLASS)) {
                    player.addPotionEffect(PotionEffectTypeWrapper.FIRE_RESISTANCE.createEffect(Integer.MAX_VALUE, 0));
                }
            }
        }.runTaskLater(PlayerManager.getPlugin(), 2L);
    }
}
