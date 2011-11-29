package com.socaldevs.minecraft.plugins;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RedstoneMobSpawner extends JavaPlugin {

	
	private PluginManager pm = null;
	
	@Override
	public void onDisable() {
		Logger.getLogger("Minecraft").info("RedstoneMobSpawner has been disabled");
	}

	@Override
	public void onEnable() {
		Logger.getLogger("Minecraft").info("RedstoneMobSpawner has been enabled");
		pm = this.getServer().getPluginManager();
		MobSpawnSignBlockListener listener = new MobSpawnSignBlockListener(this);
		pm.registerEvent(Event.Type.REDSTONE_CHANGE, listener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.SIGN_CHANGE, listener, Event.Priority.Normal, this);
	}

}
