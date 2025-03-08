package com.wulian.awesomesheepswell.fabric;

import com.wulian.awesomesheepswell.AwesomeSheepSwell;
import net.fabricmc.api.ClientModInitializer;

public class AwesomeSheepSwellFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AwesomeSheepSwell.ClientInitializer();
    }
}
