package xyz.yldk.mcmod.queryinfo.client.routers.api.client;

import io.javalin.http.Context;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import xyz.yldk.mcmod.queryinfo.client.tools.ApiTools;

public class SendCommandRouter {
    public static void Get(Logger logger, Context ctx) {
        ctx.header("Content-Type", "application/json");

        String cmd = ctx.queryParam("cmd");

        if (cmd == null || cmd.isEmpty()) {
            ctx.status(400).result(ApiTools.build(400, "Bad Request: 'cmd' parameter is required", null).format());
            return;
        }

        if (cmd.startsWith("/")) {
            cmd = cmd.substring(1);
        }

        String finalCmd = cmd;
        MinecraftClient.getInstance().execute(() -> {
            try {
                var mc = MinecraftClient.getInstance();
                var player = mc.player;
                if (player == null){
                    ctx.status(400).result(ApiTools.build(400, "Bad Request: player is null", null).format());
                    return;
                }

                player.networkHandler.sendCommand(finalCmd);
            } catch (Exception e) {
                logger.error("[QueryInfo/SERVER] Failed to send command", e);
                ctx.status(500).result(ApiTools.build(500, "Internet Server Error", null).format());
            }
        });

        ctx.status(200).result(ApiTools.build(200, "OK", null).format());
    }
}
