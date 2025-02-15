package xyz.yldk.mcmod.queryinfo.client.data;

import com.google.gson.Gson;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ClientDataCollector {
    private static final Gson gson = new Gson();

    public static String collectGameData() {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        ClientData data = new ClientData();

        if (player != null) {
            // 玩家基础信息
            data.playerName = player.getName().getString();
            data.health = player.getHealth();
            data.hunger = player.getHungerManager().getFoodLevel();

            // 位置数据
            Vec3d pos = player.getPos();
            data.position = new double[]{pos.x, pos.y, pos.z};

            // 世界信息
            data.worldTime = client.world.getTime();
            data.dimension = client.world.getRegistryKey().getValue().toString();

            // 客户端状态
            data.fps = client.getCurrentFps();
            data.isInGame = client.world != null;
        }

        return gson.toJson(data);
    }

    private static class ClientData {
        public String playerName;
        public float health;
        public int hunger;
        public double[] position;
        public long worldTime;
        public String dimension;
        public int fps;
        public boolean isInGame;
    }
}
