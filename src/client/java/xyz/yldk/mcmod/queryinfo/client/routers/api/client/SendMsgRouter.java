package xyz.yldk.mcmod.queryinfo.client.routers.api.client;

import io.javalin.http.Context;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import xyz.yldk.mcmod.queryinfo.client.tools.ApiTools;
import xyz.yldk.mcmod.queryinfo.client.tools.CharTools;

public class SendMsgRouter {
    public static void Get(Logger logger, Context ctx) {
        ctx.header("Content-Type", "application/json");

        String msg = ctx.queryParam("msg");

        if (msg == null || msg.isEmpty()) {
            ctx.status(400).result(ApiTools.build(400, "Bad Request: 'msg' parameter is required", null).format());
            return;
        }
        if (msg.length() >= 256) {
            ctx.status(400).result(ApiTools.build(400, "Bad Request: 'msg' parameter is too long (max 256 characters)", null).format());
            return;
        }

        if (msg.length() >= 256) {
            msg = msg.substring(0, 256);
        }
        String finalMsg = msg;
        MinecraftClient.getInstance().execute(() -> {
            try {
                var mc = MinecraftClient.getInstance();
                var player = mc.player;
                if (player == null){
                    ctx.status(400).result(ApiTools.build(400, "Bad Request: player is null", null).format());
                    return;
                }


                player.networkHandler.sendChatMessage(finalMsg);
            } catch (Exception e) {
                logger.error("[QueryInfo/SERVER] Failed to send message", e);
                ctx.status(500).result(ApiTools.build(500, "Internet Server Error", null).format());
            }
        });

        ctx.status(200).result(ApiTools.build(200, "OK", null).format());
    }
}
