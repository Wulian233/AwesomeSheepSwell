package com.wulian.awesomesheepswell;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@me.shedaniel.autoconfig.annotation.Config(name = AwesomeSheepSwell.MOD_ID)
public class Config implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 5, max = 32)
    @ConfigEntry.Gui.Tooltip
    public int maxThickness = 20;
}
