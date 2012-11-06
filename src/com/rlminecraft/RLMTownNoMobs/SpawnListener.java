package com.rlminecraft.RLMTownNoMobs;

import java.util.Random;

import org.bukkit.Location;
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
		if (reason != SpawnReason.NATURAL
				&& reason != SpawnReason.JOCKEY
				&& reason != SpawnReason.CHUNK_GEN
				&& reason != SpawnReason.VILLAGE_DEFENSE
				&& reason != SpawnReason.VILLAGE_INVASION)
			return;
		
		// Location check
		if (!plugin.towny.isEnabled()) return;
		Location loc = event.getLocation();
		TownBlock plot = TownyUniverse.getTownBlock(loc);
		if (plot == null) return;
		
		// Entity check
		EntityType entity = event.getEntityType();
		if (	   entity != EntityType.BLAZE
				&& entity != EntityType.CAVE_SPIDER
				&& entity != EntityType.CREEPER
				&& entity != EntityType.ENDER_DRAGON
				&& entity != EntityType.ENDERMAN
				&& entity != EntityType.GHAST
				&& entity != EntityType.GIANT
				&& entity != EntityType.MAGMA_CUBE
				&& entity != EntityType.PIG_ZOMBIE
				&& entity != EntityType.SILVERFISH
				&& entity != EntityType.SKELETON
				&& entity != EntityType.SLIME
				&& entity != EntityType.SPIDER
				&& entity != EntityType.WITCH
				&& entity != EntityType.WITHER
				&& entity != EntityType.ZOMBIE
			)
			return;
		
		// Stop the spawn
		event.setCancelled(true);
		
		/*
		// Possibly spawn villager in its place
		if (rnd.nextFloat() < 0.9f) return;
		World world = loc.getWorld();
		EntityType villager = EntityType.VILLAGER;
		world.spawnEntity(loc, villager);
		*/
	}
}
