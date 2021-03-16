package funfall.events;

import funfall.main.Main;
import funfall.managers.PermissionManagement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {
    Main main;
    PermissionManagement pm;

    public JoinQuitListener(Main main, PermissionManagement perms) {
        this.main = main;
        this.pm = perms;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String playerGroup = main.fm.getPlayers().getString(String.valueOf(p.getUniqueId()));
        String groupPrefix = main.fm.getGroups().getString("groups." + playerGroup + ".prefix");
        if(groupPrefix == null) {
            main.fm.getPlayers().set(p.getUniqueId().toString(), "default");
            p.setPlayerListName(checkColourCodes("&7") + p.getName());
        }
        p.setPlayerListName(checkColourCodes(groupPrefix) + p.getName());
        for(Player online: Bukkit.getOnlinePlayers()){
            online.playSound(online.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 5, 3);
        }
        if(!main.fm.getGroups().contains(String.valueOf(p.getUniqueId()))){
            main.fm.getGroups().set(p.getUniqueId().toString(), "default");
        }
        e.setJoinMessage(checkColourCodes("&a&lJOIN: " + groupPrefix + p.getDisplayName()));
        pm.setupPermissions(p);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        String playerGroup = main.fm.getPlayers().getString(String.valueOf(p.getUniqueId()));
        String groupPrefix = main.fm.getGroups().getString("groups." + playerGroup + ".prefix");
        pm.playerPermissions.remove(p.getUniqueId());
        e.setQuitMessage(checkColourCodes("&c&lLEAVE: " + groupPrefix + p.getDisplayName()));
    }

    public String checkColourCodes(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }




}
