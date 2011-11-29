package com.socaldevs.minecraft.plugins;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class SDUtils {

	
	public static ArrayList<Block> surroundingBlocks(Block b){
		ArrayList<Block> blocks = new ArrayList<Block>();
        for (BlockFace face : BlockFace.values()){
        	if (face == BlockFace.UP){
        		Block above = b.getRelative(BlockFace.UP);
        		Block above2 = above.getRelative(BlockFace.UP);
        		blocks.add(above);
        		blocks.add(above2);
        	}
        	blocks.add(b.getRelative(face));
        }
        return blocks;
	}
}
