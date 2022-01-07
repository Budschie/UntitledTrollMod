package de.budschie.untitledtrollmod.utils;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Random;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.util.LazyOptional;

public class UntitledUtilsClient
{
	private static final LazyOptional<ArrayList<Entry<String, KeyMapping>>> KEY_MAPPING_LIST = LazyOptional.of(() -> new ArrayList<>(KeyMapping.ALL.entrySet()));
	
	public static final int[] USED_KEY_MAPPINGS = { InputConstants.KEY_4, InputConstants.KEY_2, InputConstants.KEY_ADD, InputConstants.KEY_W, InputConstants.KEY_A,
			InputConstants.KEY_S, InputConstants.KEY_D, InputConstants.KEY_Q, InputConstants.KEY_CAPSLOCK, InputConstants.KEY_SLASH, InputConstants.KEY_E, InputConstants.KEY_LEFT,
			InputConstants.KEY_ESCAPE, InputConstants.KEY_X, InputConstants.KEY_C, InputConstants.KEY_4, InputConstants.KEY_F21, InputConstants.KEY_F22, InputConstants.KEY_R,
			InputConstants.KEY_GRAVE, InputConstants.KEY_F3, InputConstants.KEY_F2, InputConstants.KEY_F5, InputConstants.KEY_B, InputConstants.KEY_Y, InputConstants.KEY_NUMPAD1,
			InputConstants.KEY_DOWN, };
	
	public static void randomizeKeybindings()
	{
		// or play troll music when players notice it and try to change controls back -
		// quote from someone on the fordge discord lol
		Random rand = Minecraft.getInstance().level.getRandom();

		for (int i = 0; i < (int)(KEY_MAPPING_LIST.resolve().get().size() * 0.3f * rand.nextFloat()); i++)
		{
			KeyMapping chosenKeyMapping = KEY_MAPPING_LIST.resolve().get().get(rand.nextInt(KEY_MAPPING_LIST.resolve().get().size())).getValue();
			chosenKeyMapping.setKey(InputConstants.getKey(USED_KEY_MAPPINGS[rand.nextInt(USED_KEY_MAPPINGS.length)], USED_KEY_MAPPINGS[rand.nextInt(USED_KEY_MAPPINGS.length)]));
		}
	}
}
