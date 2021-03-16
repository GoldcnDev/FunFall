package funfall.commands;

import funfall.events.GameRunner;
import funfall.main.Main;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FunFallCommand implements CommandExecutor {
    Main main;
    GameRunner startGame;
    public FunFallCommand(Main main, GameRunner sg) {
        this.main = main;
        this.startGame = sg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);
        if(cmd.getLabel().equalsIgnoreCase("funfall")){
            if(!p.hasPermission("funfall.admin")){
                p.sendMessage(checkCC("&cInvalid permissions."));
            } else {
                if (args.length == 0) {
                    p.sendMessage(checkCC("&cInvalid arguments."));
                }
                if (args.length == 1) {
                    if(args[0].equalsIgnoreCase("start")){
                    BukkitRunnable countdown = new BukkitRunnable() {
                        int time = 3;
                        @Override
                        public void run() {
                            if (this.time > 0) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                            TextComponent.fromLegacyText(checkCC("&aStarting &e&lFunFall &ain &b" + this.time + "&a seconds...")));
                                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 5);
                                }
                                this.time--;
                            }
                            if (this.time == 0) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                            TextComponent.fromLegacyText(checkCC("&aStarting &e&lFunFall &ain &b" + this.time + "&a seconds...")));
                                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 5);
                                }
                                startGame.startGame();
                                startGame.victoryCheck();
                                cancel();
                            }
                        }
                    };
                    countdown.runTaskTimer(main, 0L, 20L);

                    } else {
                        p.sendMessage(checkCC("&cNot recognized arguments."));
                    }
                }
            }
        }

        return true;
    }

    public String checkCC(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }


}
