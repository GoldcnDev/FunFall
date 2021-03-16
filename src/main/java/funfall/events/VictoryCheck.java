package funfall.events;

import funfall.managers.ColourUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VictoryCheck implements Listener {
    private final Map<UUID, Integer> deathCount = new HashMap<UUID, Integer>();

    private boolean gameRunning = false;
    private int deadCount = 0;
    Player winner;

    public void on(EntityDamageEvent e) {
        if (gameRunning && ((Player) e.getEntity()).getHealth() - e.getDamage() <= 0) {
            e.setCancelled(true);
            // etc...
            if (!this.deathCount.containsKey(e.getEntity().getUniqueId())) {
                this.deathCount.putIfAbsent(e.getEntity().getUniqueId(), 0);
            }
            this.deathCount.computeIfPresent(e.getEntity().getUniqueId(), (uuid, count) -> {
                if (count >= 3) {
                    deadCount++;
                    // Player lost...
                }
                return count++;
            });

            if (this.victoryCheck()) {
               for(Player p: Bukkit.getOnlinePlayers()){
                   p.sendTitle(ColourUtils.colour("&b&l" + winner + " &awon!"), "", 10, 5, 5);
                   winner.playSound(winner.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 10, 5);
               }
            }
        }
    }

    private boolean victoryCheck() {
        return Bukkit.getOnlinePlayers().size() - this.deadCount == 1;
    }
}
