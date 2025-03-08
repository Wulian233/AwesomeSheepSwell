package com.wulian.awesomesheepswell.fabric;

import com.wulian.awesomesheepswell.AwesomeSheepSwell;
import net.fabricmc.api.ModInitializer;

public class AwesomeSheepSwellFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AwesomeSheepSwell.ConfigInitializer();
    }
}
