package com.wulian.awesomesheepswell.fabric;

import com.wulian.awesomesheepswell.AwesomeSheepSwell;
import net.fabricmc.api.ClientModInitializer;

public class AwesomeSheepSwellFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AwesomeSheepSwell.init();
    }
}
