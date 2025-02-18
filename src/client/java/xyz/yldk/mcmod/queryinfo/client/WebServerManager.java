package xyz.yldk.mcmod.queryinfo.client;

import com.google.gson.Gson;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.yldk.mcmod.queryinfo.client.config.ModConfigManager;
import xyz.yldk.mcmod.queryinfo.client.data.ClientDataCollector;
import net.minecraft.client.MinecraftClient;
import xyz.yldk.mcmod.queryinfo.client.data.WorldEntitiesCollector;
import xyz.yldk.mcmod.queryinfo.client.tools.ApiTools;
import xyz.yldk.mcmod.queryinfo.client.tools.ModMetadataHelper;

import java.util.HashMap;
import java.util.Map;
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
        javalin.get("/", ctx -> {
            ctx.header("Content-Type", "application/json");
            HashMap<String, Object> subData = new HashMap<>();
            subData.put("name", ModMetadataHelper.getModName());
            subData.put("version", ModMetadataHelper.getModVersion());

            HashMap<String, Object> data = new HashMap<>();
            data.put("queryinfo", true);
            data.put("server", ModMetadataHelper.getModName() + "/" + ModMetadataHelper.getModVersion());
            data.put("version", subData);

            var resp = ApiTools.build(200, "OK", data);
            ctx.result(resp.format());
        });

        // ------------------------------------------------------

        javalin.get("/api/client/info", ctx -> {
            ctx.header("Content-Type", "application/json");
            CompletableFuture<ClientDataCollector.ClientData> future = new CompletableFuture<>();

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
                var resp = ApiTools.build(200, "OK", data);
                ctx.result(resp.format());
            } catch (Exception e) {
                logger.error("[QueryInfo/SERVER] Failed to collect data", e);
                ctx.status(500).result(ApiTools.build(500, "Internet Server Error", null).format());
            }
        });

        javalin.get("/api/client/entities", ctx -> {
            ctx.header("Content-Type", "application/json");
            CompletableFuture<HashMap<String, Object>> future = new CompletableFuture<>();

            // Client Main Thread
            MinecraftClient.getInstance().execute(() -> {
                try {
                    future.complete(WorldEntitiesCollector.collectWorldEntities());
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            });

            try {
                var data = future.get();
                var resp = ApiTools.build(200, "OK", data);
                ctx.result(resp.format());
            } catch (Exception e) {
                logger.error("[QueryInfo/SERVER] Failed to collect data", e);
                ctx.status(500).result(ApiTools.build(500, "Internet Server Error", null).format());
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
