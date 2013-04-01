package com.rlminecraft.RLMTownNoMobs;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
//import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class SpawnListener implements Listener {
	
	RLMTownNoMobs plugin;
	Random rnd;
	
	public SpawnListener(RLMTownNoMobs instance) {
		plugin = instance;
		rnd = new Random();
	}
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		// Spawn Reason Check
		SpawnReason reason = event.getSpawnReason();
		// Restricted reasons
		if (reason != SpawnReason.NATURAL
				&& reason != SpawnReason.JOCKEY
				&& reason != SpawnReason.CHUNK_GEN
				&& reason != SpawnReason.VILLAGE_DEFENSE
				&& reason != SpawnReason.VILLAGE_INVASION
				&& reason != SpawnReason.CHUNK_GEN)
			return;
		
		// Location check
		if (!plugin.towny.isEnabled()) return;
		Location loc = event.getLocation();
		TownBlock plot = TownyUniverse.getTownBlock(loc);
		if (plot == null) return;
		
		// Entity check
		EntityType entity = event.getEntityType();
		// Do not do anything with non-banned mobs
		if (!plugin.conf.getList("bannedMobs").contains(entity.toString())) return;
		
		// Stop the spawn
		event.setCancelled(true);
		
		
		// Possibly spawn villager in its place
		if (rnd.nextDouble() < (1.0 - plugin.conf.getDouble("villagerRatio",0.1))) return;
		World world = loc.getWorld();
		EntityType villager = EntityType.VILLAGER;
		if (world.spawnEntity(loc, villager) == null)
			plugin.console.warning("Could not create villager at location " + loc.toString());
		
	}
}
