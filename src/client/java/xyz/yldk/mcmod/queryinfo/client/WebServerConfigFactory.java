package xyz.yldk.mcmod.queryinfo.client;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import xyz.yldk.mcmod.queryinfo.client.config.ModConfigManager;

public class WebServerConfigFactory {

    public static void modifyServer(Server server, int currentPort){
        // try-with-res
        try (var connector = new ServerConnector(server)) {
            connector.setReuseAddress(true);
            connector.setReusePort(true);
            connector.setHost(ModConfigManager.getConfig().apiHost);
            connector.setPort(currentPort);
            connector.setAcceptQueueSize(ModConfigManager.getConfig().apiAcceptQueueSize);
            server.setConnectors(new ServerConnector[]{ connector });
        }
    }

}
