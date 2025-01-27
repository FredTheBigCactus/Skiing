package com.affehund.skiing.core.init;

import com.affehund.skiing.common.block.AcaciaSkiRackBlock;
import com.affehund.skiing.common.block.BirchSkiRackBlock;
import com.affehund.skiing.common.block.CrimsonSkiRackBlock;
import com.affehund.skiing.common.block.DarkOakSkiRackBlock;
import com.affehund.skiing.common.block.JungleSkiRackBlock;
import com.affehund.skiing.common.block.OakSkiRackBlock;
import com.affehund.skiing.common.block.SpruceSkiRackBlock;
import com.affehund.skiing.common.block.WarpedSkiRackBlock;
import com.affehund.skiing.core.ModConstants;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			ModConstants.MOD_ID);

	public static final RegistryObject<Block> ACACIA_SKI_RACK_BLOCK = BLOCKS.register(
			"acacia_" + ModConstants.RegistryStrings.SKI_RACK,
			() -> new AcaciaSkiRackBlock(Block.Properties.create(Material.WOOD)));

	public static final RegistryObject<Block> BIRCH_SKI_RACK_BLOCK = BLOCKS.register(
			"birch_" + ModConstants.RegistryStrings.SKI_RACK,
			() -> new BirchSkiRackBlock(Block.Properties.create(Material.WOOD)));

	public static final RegistryObject<Block> CRIMSON_SKI_RACK_BLOCK = BLOCKS.register(
			"crimson_" + ModConstants.RegistryStrings.SKI_RACK,
			() -> new CrimsonSkiRackBlock(Block.Properties.create(Material.WOOD)));

	public static final RegistryObject<Block> DARK_OAK_SKI_RACK_BLOCK = BLOCKS.register(
			"dark_oak_" + ModConstants.RegistryStrings.SKI_RACK,
			() -> new DarkOakSkiRackBlock(Block.Properties.create(Material.WOOD)));

	public static final RegistryObject<Block> JUNGLE_SKI_RACK_BLOCK = BLOCKS.register(
			"jungle_" + ModConstants.RegistryStrings.SKI_RACK,
			() -> new JungleSkiRackBlock(Block.Properties.create(Material.WOOD)));

	public static final RegistryObject<Block> OAK_SKI_RACK_BLOCK = BLOCKS.register(
			"oak_" + ModConstants.RegistryStrings.SKI_RACK,
			() -> new OakSkiRackBlock(Block.Properties.create(Material.WOOD)));

	public static final RegistryObject<Block> SPRUCE_SKI_RACK_BLOCK = BLOCKS.register(
			"spruce_" + ModConstants.RegistryStrings.SKI_RACK,
			() -> new SpruceSkiRackBlock(Block.Properties.create(Material.WOOD)));

	public static final RegistryObject<Block> WARPED_SKI_RACK_BLOCK = BLOCKS.register(
			"warped_" + ModConstants.RegistryStrings.SKI_RACK,
			() -> new WarpedSkiRackBlock(Block.Properties.create(Material.WOOD)));
}
