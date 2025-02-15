package xyz.yldk.mcmod.queryinfo.client;

import io.javalin.Javalin;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import xyz.yldk.mcmod.queryinfo.client.ClientDataCollector;
import net.minecraft.client.MinecraftClient;

import java.util.concurrent.CompletableFuture;

public class WebServerManager {
    private static Javalin javalin;
    private static int currentPort = 25800;

    public static void startServer() {
        new Thread(() -> {
            while (currentPort < 25899) {
                try {
                    javalin = Javalin.create(config -> {
                        // 修正后的Jetty配置
                        config.jetty.modifyServer(server -> {
                            ServerConnector connector = new ServerConnector(server);
                            connector.setHost("127.0.0.1");
                            connector.setPort(currentPort);
                            server.setConnectors(new ServerConnector[]{connector});
                        });
                    });

                    setupRoutes();
                    javalin.start();
                    System.out.println("[QueryInfo] Web server started on port " + currentPort);
                    return;
                } catch (Exception e) {
                    currentPort++;
                }
            }
            System.err.println("[QueryInfo] Failed to find available port!");
        }).start();
    }

    private static void setupRoutes() {
        javalin.get("/api/info", ctx -> {
            CompletableFuture<String> future = new CompletableFuture<>();

            // 使用MinecraftClient主线程执行
            MinecraftClient.getInstance().execute(() -> {
                try {
                    future.complete(ClientDataCollector.collectGameData());
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            });

            try {
                ctx.result(future.get());
            } catch (Exception e) {
                ctx.status(500).result("Data collection failed");
            }
        });
    }

    public static void stopServer() {
        if (javalin != null) {
            javalin.stop();
        }
    }
}
