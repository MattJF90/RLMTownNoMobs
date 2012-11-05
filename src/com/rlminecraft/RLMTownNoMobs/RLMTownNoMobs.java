package com.rlminecraft.RLMTownNoMobs;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.palmergames.bukkit.towny.Towny;

public class RLMTownNoMobs extends JavaPlugin {
	
	Logger console;
	Towny towny;
	
	public void onEnable() {
		console = this.getLogger();
		towny = (Towny) this.getServer().getPluginManager().getPlugin("Towny");
		this.getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
	}
	
	public void onDisable() {
		
	}
}
