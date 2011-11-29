package com.socaldevs.minecraft.plugins;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;

public class MobSpawnSignBlockListener extends BlockListener{

	private RedstoneMobSpawner p = null;
	private Server server = null;
	private long lastTime = 0;
	
	public MobSpawnSignBlockListener(RedstoneMobSpawner instance){
		p = instance;
		server = p.getServer();
	}
	
	@Override
	public void onSignChange(SignChangeEvent e){
		if(e.getLine(0).equalsIgnoreCase("[spawnmob]")){
			Player p = e.getPlayer();
			if(!p.hasPermission("RedstoneMobSpawner.create")){
				p.sendMessage("[RedstoneMobSpawner] You do not have permission to create a spawner");
				Block b = e.getBlock();
				b.setType(Material.AIR);
				return;
			}
			CreatureType ct = null;
			int quantity = 1;
			if(e.getLine(2) != null && !e.getLine(2).equals("")){
				quantity = Integer.parseInt(e.getLine(2));
			}
			try{
				ct = CreatureType.valueOf(e.getLine(1).toUpperCase());
				p.sendMessage("[RedstoneMobSpawner] This sign will create "+quantity+" "+ct.getName()+" every time a redstone current is applied.");
			}catch(IllegalArgumentException ex){
				p.sendMessage("[RedstoneMobSpawner] "+e.getLine(1)+" is not a valid mob type");
			}
		}
	}
	
	@Override
	public void onBlockRedstoneChange(BlockRedstoneEvent e){
		Block b = e.getBlock();
		if(!(b.getState() instanceof Sign)) return;
		Sign sign = (Sign) b.getState();
		if(b.isBlockPowered() || b.isBlockIndirectlyPowered()){
			if(sign.getLine(0).equalsIgnoreCase("[spawnmob]")){
				if(Math.abs(System.currentTimeMillis() - lastTime) > 2){
					Location spawn = b.getLocation();
					World w = b.getWorld();
					String mob = sign.getLine(1);
					CreatureType c = CreatureType.valueOf(mob.toUpperCase());
					int quantity = 1;
					if(sign.getLine(2) != null && !sign.getLine(2).equals("")){
						quantity = Integer.parseInt(sign.getLine(2));
					}
					for(int i=0; i<quantity; i++){
						w.spawnCreature(spawn, c);
					}
					lastTime = System.currentTimeMillis();
				}
			}
			else return;
		}
		else return;
	}
	
	private void broadcast(String message){
		server.broadcastMessage(message);
	}
}
