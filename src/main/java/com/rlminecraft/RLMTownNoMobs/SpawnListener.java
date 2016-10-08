package com.rlminecraft.RLMTownNoMobs;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
//import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityInteractEvent;

import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownBlockType;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class SpawnListener implements Listener {

	private RLMTownNoMobs plugin;
	private Random rnd;

	private static final List<SpawnReason> checkedSpawnReasons = Arrays.asList(
			SpawnReason.NATURAL,
			SpawnReason.JOCKEY,
			SpawnReason.CHUNK_GEN,
			SpawnReason.VILLAGE_DEFENSE,
			SpawnReason.VILLAGE_INVASION,
			SpawnReason.CHUNK_GEN,
			SpawnReason.LIGHTNING
	);
	
	public SpawnListener(RLMTownNoMobs instance) {
		plugin = instance;
		rnd = new Random();
	}
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		// Spawn Reason Check
		SpawnReason reason = event.getSpawnReason();
		// Restricted reasons)
		if (!checkedSpawnReasons.contains(reason)) {
			return;
		}
		
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
		if (rnd.nextDouble() < (1.0 - plugin.conf.getDouble("villagerRatio",0.05))) return;
		World world = loc.getWorld();
		EntityType villager = EntityType.VILLAGER;
		if (world.spawnEntity(loc, villager) == null)
			plugin.console.warning("Could not create villager at location " + loc.toString());
	}
	
	@EventHandler
	public void onDoorInteract (EntityInteractEvent event){
		// Check if entity is villager
		if (event.getEntityType() != EntityType.VILLAGER) return;
		
		// Check config for whether villagers are allowed to toggle doors
		if (plugin.conf.getBoolean("villagerDoors",false)) return;
		
		// Check whether material interacted with is a door
		if (   event.getBlock().getType() != Material.WOOD_DOOR
			&& event.getBlock().getType() != Material.WOODEN_DOOR
			&& event.getBlock().getType() != Material.IRON_DOOR
			&& event.getBlock().getType() != Material.IRON_DOOR_BLOCK
			&& event.getBlock().getType() != Material.TRAP_DOOR)
			return;
		
		// Check if villager is in town
		if (!plugin.towny.isEnabled()) return;
		Location loc = event.getBlock().getLocation();
		TownBlock plot = TownyUniverse.getTownBlock(loc);
		if (   plot == null
			|| plot.hasTown()
			|| plot.getType() == TownBlockType.WILDS)
			return;
		
		// Cancel interaction if all checks pass
		event.setCancelled(true);
	}
}
