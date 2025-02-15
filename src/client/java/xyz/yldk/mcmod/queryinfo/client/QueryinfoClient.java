package xyz.yldk.mcmod.queryinfo.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryinfoClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("QueryInfo");

    @Override
    public void onInitializeClient() {
        LOGGER.info("[QueryInfo] Client side initialized!");
        WebServerManager.startServer();
    }
}
