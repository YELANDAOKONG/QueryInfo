package xyz.yldk.mcmod.queryinfo.client;

import com.google.gson.Gson;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.yldk.mcmod.queryinfo.client.config.ModConfigManager;
import xyz.yldk.mcmod.queryinfo.client.data.ClientDataCollector;
import net.minecraft.client.MinecraftClient;

import java.util.concurrent.CompletableFuture;

public class WebServerManager {
    private static Javalin javalin;
    private static int currentPort = 25800; // Default
    public static Logger logger = LoggerFactory.getLogger("QueryInfoServer");

    public static void startServer() {
        int minPort = ModConfigManager.getConfig().apiPort;
        int maxPort = ModConfigManager.getConfig().apiPortMax;
        currentPort = minPort;
        new Thread(() -> {
            while (currentPort < maxPort) {
                try {
                    javalin = Javalin.create(config -> {
                        // Jetty Config
                        config.jetty.modifyServer(server -> {
                            // ServerConnector connector = new ServerConnector(server);
                            // connector.setHost("127.0.0.1");
                            // connector.setPort(currentPort);
                            // server.setConnectors(new ServerConnector[]{connector});
                            WebServerConfigFactory.modifyServer(server, currentPort);
                        });
                    });

                    setupRoutes();
                    javalin.start();
                    logger.info("[QueryInfo] Web server started on port {}", currentPort);
                    return;
                } catch (Exception e) {
                    currentPort++;
                }
            }
            logger.error("[QueryInfo] Failed to find available port! (In 25800-25899)");
        }).start();
    }

    private static void setupRoutes() {
        javalin.get("/api/info", ctx -> {
            CompletableFuture<String> future = new CompletableFuture<>();

            // Client Main Thread
            MinecraftClient.getInstance().execute(() -> {
                try {
                    future.complete(ClientDataCollector.collectGameData());
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            });

            try {
                var data = future.get();
                // To 4-identify json ...

                ctx.result();
            } catch (Exception e) {
                ctx.status(500).result("Data collection failed");
            }
        });

        // More APIs Here
    }

    public static void stopServer() {
        if (javalin != null) {
            javalin.stop();
        }
    }
}
