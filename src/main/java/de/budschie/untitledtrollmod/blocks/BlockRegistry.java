package de.budschie.untitledtrollmod.blocks;

import de.budschie.untitledtrollmod.blocks.classes.BlockingAir;
import de.budschie.untitledtrollmod.blocks.classes.TrollTNTBlock;
import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.world.level.block.Block;
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
}
