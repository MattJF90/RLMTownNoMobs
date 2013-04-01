package com.rlminecraft.RLMTownNoMobs;

import java.util.logging.Logger;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.palmergames.bukkit.towny.Towny;

public class RLMTownNoMobs extends JavaPlugin {
	
	YamlConfiguration conf;
	Logger console;
	Towny towny;
	
	public void onEnable() {
		// Initialize stdout stream
		console = this.getLogger();
		
		// Hook into Towny
		towny = (Towny) this.getServer().getPluginManager().getPlugin("Towny");
		
		// Load configuration (create if necessary)
		this.saveDefaultConfig();
		conf = (YamlConfiguration) this.getConfig();
		
		// Register spawn listener
		this.getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
	}
	
	public void onDisable() {
	}
}
