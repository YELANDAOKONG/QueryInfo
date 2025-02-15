package xyz.yldk.mcmod.queryinfo.client;

import net.fabricmc.api.ClientModInitializer;

public class QueryinfoClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WebServerManager.startServer();
    }
}
