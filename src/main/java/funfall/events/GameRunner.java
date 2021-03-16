package funfall.events;

import funfall.managers.ColourUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GameRunner implements Listener {

    Main m;
    public boolean gameRunning = false;
    int deathcount = 0;
    HashMap<Player, Integer> deaths = new HashMap<Player, Integer>();
    Set<Player> dead = new HashSet<Player>();
    int playercount = Bukkit.getOnlinePlayers().size();
    Player winner;
    public void startGame() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            deaths.put(players, deathcount);
            gameRunning = true;
            players.teleport(new Location(players.getWorld(), 500, 250, 500));
            players.setGameMode(GameMode.SURVIVAL);
            players.sendTitle(ColourUtils.colour("&a&lTime to fall!"), "", 5, 5, 5);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        Player p = (Player) e.getEntity();
        if (gameRunning && p.getHealth() - e.getDamage() <= 0) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL && deaths.get(p) <= 3) {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 10, 5);
                Bukkit.getServer().broadcastMessage(ColourUtils.colour("&e&lFunFall &6- &c" + p.getName() + " &4died."));
                p.teleport(new Location(p.getWorld(), 500, 250, 500));
                deaths.put(p, deathcount++);
            }
            if(deaths.get(p) >= 3){
                p.setGameMode(GameMode.SPECTATOR);
                dead.add(p);
                for(Player players: Bukkit.getOnlinePlayers()){
                    players.playSound(players.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 7, 5);
                }
                Bukkit.getServer().broadcastMessage(ColourUtils.colour("&e&lFunFall &6- &c" + p.getName() + "&7 has been &4&lELIMINATED!"));
            }
        }
    }

    public void victoryCheck(){
          if(dead.size() == Bukkit.getOnlinePlayers().size()-1){
              for(Player p: Bukkit.getOnlinePlayers()){
                  if(!dead.contains(p)){
                      winner = p;
                  }
                  p.sendTitle(ColourUtils.colour("&b&l" + winner + " &awon!"), "", 10, 5, 5);
                  winner.playSound(winner.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 10, 5);
              }
          }
    }

}
