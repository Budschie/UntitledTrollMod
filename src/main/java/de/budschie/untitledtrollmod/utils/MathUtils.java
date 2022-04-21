package de.budschie.untitledtrollmod.utils;

import java.util.Random;

public class MathUtils
{
	public static double getRangedRandom(Random random, double range)
	{
		return random.nextDouble(range * 2) - range;
	}
}
