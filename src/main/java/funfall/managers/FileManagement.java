package funfall.managers;

import funfall.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManagement {
    private Main main;
    public FileManagement(Main main){ this.main = main; }

    public FileConfiguration groupscfg;
    public File groupsfile;
    public FileConfiguration playerscfg;
    public File playersfile;

    public void setupGroups() {
        if(!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }

        groupsfile = new File(main.getDataFolder(), "groups.yml");

        if(!groupsfile.exists()) {
            try {
                groupsfile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage("CREATED GROUPS.YML FILE");
            } catch(IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage( "COULD NOT CREATE GROUPS.YML FILE");
            }
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage( "LOADED GROUPS.YML FILE");
        }

        groupscfg = YamlConfiguration.loadConfiguration(groupsfile);
    }

    public void setupPlayers() {
        if(!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }

        playersfile = new File(main.getDataFolder(), "players.yml");

        if(!playersfile.exists()) {
            try {
                playersfile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage("CREATED PLAYERS.YML FILE");
            } catch(IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage( "COULD NOT CREATE PLAYERS.YML FILE");
            }
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage( "LOADED PLAYERS.YML FILE");
        }

        playerscfg = YamlConfiguration.loadConfiguration(playersfile);
    }

    public FileConfiguration getPlayers() {
        return playerscfg;
    }
    public FileConfiguration getGroups() { return groupscfg; }

    public void savePlayers() {
        try {
            playerscfg.save(playersfile);
        } catch(IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("COULD NOT SAVE PLAYERS.");
        }
    }

    public void saveGroups() {
        try {
            groupscfg.save(groupsfile);
        } catch(IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("COULD NOT SAVE GROUPS.");
        }
    }



}
