package funfall.main;

import funfall.commands.*;
import funfall.events.JoinQuitListener;
import funfall.events.GameRunner;
import funfall.managers.FileManagement;
import funfall.managers.PermissionManagement;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

	public Main plugin;
	public FileManagement fm = new FileManagement(this);
	PermissionManagement pm = new PermissionManagement();
	JoinQuitListener jql = new JoinQuitListener(this, pm);
	GameRunner sg = new GameRunner();
	FunFallCommand addPermissionCommand = new FunFallCommand(this, sg);
	
	@Override
	public void onEnable() {
		plugin = this;
		createConfig();
		fm.setupGroups();
		fm.setupPlayers();
		fm.groupscfg.options().copyDefaults(true);
		fm.playerscfg.options().copyDefaults(true);
		Bukkit.getServer().getConsoleSender().sendMessage("FunFall is enabled.");
		Bukkit.getServer().getPluginManager().registerEvents(jql, this);
		Bukkit.getServer().getPluginManager().registerEvents(sg, this);
		Bukkit.getPluginCommand("funfall").setExecutor(addPermissionCommand);
	}
	
	@Override
	public void onDisable() {
		Bukkit.getServer().getConsoleSender().sendMessage("FunFall is disabled.");
	}

	private void createConfig() {
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdirs();
			}
			File file = new File(getDataFolder(), "config.yml");
			if (!file.exists()) {
				getLogger().info("Config.yml not found, creating!");
				saveDefaultConfig();
			} else {
				getLogger().info("Config.yml found, loading!");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
