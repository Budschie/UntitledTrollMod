package de.budschie.untitledtrollmod.blocks;

import de.budschie.untitledtrollmod.blocks.classes.BlockingAir;
import de.budschie.untitledtrollmod.blocks.classes.FakeBlock;
import de.budschie.untitledtrollmod.blocks.classes.TrollTNTBlock;
import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry
{
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, UntitledMainClass.MODID);
	
	public static final RegistryObject<Block> TROLL_TNT = REGISTRY.register("troll_tnt", () -> new TrollTNTBlock(Properties.of(Material.EXPLOSIVE).instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> BLOCKING_AIR = REGISTRY.register("blocking_air", () -> new BlockingAir(Properties.of(Material.STONE).sound(SoundType.STONE).noOcclusion().strength(-1, 0)));
	
	// Fake blocks. Look like the real block, but cannot be easily mined and they are useless, eg fake dirt cannot be converted to farmland
	public static final RegistryObject<FakeBlock> FAKE_DIRT = REGISTRY.register("dirt", () -> new FakeBlock(() -> Blocks.DIRT));
	public static final RegistryObject<FakeBlock> FAKE_FARMLAND = REGISTRY.register("farmland", () -> new FakeBlock(() -> Blocks.FARMLAND));
	public static final RegistryObject<FakeBlock> FAKE_TNT = REGISTRY.register("tnt", () -> new FakeBlock(() -> Blocks.TNT));
	public static final RegistryObject<FakeBlock> FAKE_CRAFTING_TABLE = REGISTRY.register("crafting_table", () -> new FakeBlock(() -> Blocks.CRAFTING_TABLE, 0.5f));
	public static final RegistryObject<FakeBlock> FAKE_BEDROCK = REGISTRY.register("bedrock", () -> new FakeBlock(() -> Blocks.BEDROCK, 0.5f));
	public static final RegistryObject<FakeBlock> FAKE_OBSIDIAN = REGISTRY.register("obsidian", () -> new FakeBlock(() -> Blocks.OBSIDIAN, 0.5f));
	public static final RegistryObject<FakeBlock> FAKE_NETHERITE_BLOCK = REGISTRY.register("netherite_block", () -> new FakeBlock(() -> Blocks.NETHERITE_BLOCK, 0.5f));
	public static final RegistryObject<FakeBlock> FAKE_ANCIENT_DEBRIS = REGISTRY.register("ancient_debris", () -> new FakeBlock(() -> Blocks.ANCIENT_DEBRIS, 0.5f));
	public static final RegistryObject<FakeBlock> FAKE_DIAMOND_BLOCK = REGISTRY.register("diamond_block", () -> new FakeBlock(() -> Blocks.DIAMOND_BLOCK, 0.5f));

}
