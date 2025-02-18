package xyz.yldk.mcmod.queryinfo.client.data;

import com.google.gson.Gson;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.math.Vec3d;
import xyz.yldk.mcmod.queryinfo.client.tools.Base64Tools;

import java.util.HashMap;

public class ClientDataCollector {
    private static final Gson gson = new Gson();

    public static ClientData collectGameData() {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        ClientData data = new ClientData();

        // Client Status
        {
            data.fps = client.getCurrentFps();
            data.isInGame = client.world != null;
            data.isPaused = client.isPaused();
            data.isDemo = client.isDemo();
            data.isFinishedLoading = client.isFinishedLoading();
            data.isRealmsEnabled = client.isRealmsEnabled();
            data.isInSingleplayer = client.isInSingleplayer();
            data.isIntegratedServerRunning = client.isIntegratedServerRunning();
            data.isConnectedToLocalServer = client.isConnectedToLocalServer();
            data.isMultiplayerEnabled = client.isMultiplayerEnabled();
        }

        // Others
        {
            // Player
            if (player != null) {
                HashMap<String, Object> mapPlayer = new HashMap<>();
                mapPlayer.put("name", player.getName().getString());
                mapPlayer.put("uuid", player.getUuidAsString());
                var displayName = player.getDisplayName();
                mapPlayer.put("displayName", displayName == null ? null : displayName.getString());
                mapPlayer.put("health", player.getHealth());
                mapPlayer.put("armor", player.getArmor());
                mapPlayer.put("blockX", player.getBlockX());
                mapPlayer.put("blockY", player.getBlockY());
                mapPlayer.put("blockZ", player.getBlockZ());
                mapPlayer.put("teamColorValue", player.getTeamColorValue());
                mapPlayer.put("absorptionAmount", player.getAbsorptionAmount());
                mapPlayer.put("damageTiltYaw", player.getDamageTiltYaw());
                mapPlayer.put("blockInteractionRange", player.getBlockInteractionRange());
                mapPlayer.put("attackCooldownProgressPerTick", player.getAttackCooldownProgressPerTick());
                mapPlayer.put("defaultPortalCooldown", player.getDefaultPortalCooldown());
                mapPlayer.put("score", player.getScore());
                mapPlayer.put("seepTimer", player.getSleepTimer());
                mapPlayer.put("luck", player.getLuck());
                mapPlayer.put("enchantmentTableSeed", player.getEnchantmentTableSeed());
                mapPlayer.put("movementSpeed", player.getMovementSpeed());
                mapPlayer.put("air", player.getAir());
                mapPlayer.put("armorVisibility", player.getArmorVisibility());
                mapPlayer.put("bodyYaw", player.getBodyYaw());
                mapPlayer.put("headYaw", player.getHeadYaw());
                var chunkPos = player.getChunkPos();
                mapPlayer.put("chunkPosX", chunkPos.x);
                mapPlayer.put("chunkPosZ", chunkPos.z);
                Vec3d pos = player.getPos();
                mapPlayer.put("pos", new double[]{pos.x, pos.y, pos.z});
                {
                    HashMap<String, Object> mapPlayerHunger = new HashMap<>();
                    mapPlayerHunger.put("foodLevel", player.getHungerManager().getFoodLevel());
                    mapPlayerHunger.put("prevFoodLevel", player.getHungerManager().getPrevFoodLevel());
                    mapPlayerHunger.put("saturationLevel", player.getHungerManager().getSaturationLevel());
                    mapPlayerHunger.put("exhaustion", player.getHungerManager().getExhaustion());
                    mapPlayer.put("hunger", mapPlayerHunger);
                }

                data.player = mapPlayer;
            }

            // World
            if (client.world != null) {
                HashMap<String, Object> mapWorld = new HashMap<>();
                mapWorld.put("time", client.world.getTime());
                mapWorld.put("type", client.world.getRegistryKey().getValue().toString());
                mapWorld.put("moonSize", client.world.getMoonSize());
                mapWorld.put("moonPhase", client.world.getMoonPhase());
                mapWorld.put("loadedChunkCount", client.world.getChunkManager().getLoadedChunkCount());
                {
                    HashMap<String, Object> mapWorldDimension = new HashMap<>();
                    mapWorldDimension.put("ambientLight", client.world.getDimension().ambientLight());
                    mapWorldDimension.put("bedWorks", client.world.getDimension().bedWorks());
                    mapWorldDimension.put("coordinateScale", client.world.getDimension().coordinateScale());
                    mapWorldDimension.put("fixedTime", client.world.getDimension().fixedTime().toString());
                    mapWorldDimension.put("hasCeiling", client.world.getDimension().hasCeiling());
                    mapWorldDimension.put("hasFixedTime", client.world.getDimension().hasFixedTime());
                    mapWorldDimension.put("hasRaids", client.world.getDimension().hasRaids());
                    mapWorldDimension.put("hasSkyLight", client.world.getDimension().hasSkyLight());
                    mapWorldDimension.put("minY", client.world.getDimension().minY());
                    mapWorldDimension.put("monsterSpawnBlockLightLimit", client.world.getDimension().monsterSpawnBlockLightLimit());
                    mapWorldDimension.put("height", client.world.getDimension().height());
                    mapWorldDimension.put("logicalHeight", client.world.getDimension().logicalHeight());
                    mapWorldDimension.put("natural", client.world.getDimension().natural());
                    mapWorldDimension.put("piglinSafe", client.world.getDimension().piglinSafe());
                    mapWorldDimension.put("respawnAnchorWorks", client.world.getDimension().respawnAnchorWorks());
                    mapWorldDimension.put("ultrawarm", client.world.getDimension().ultrawarm());
                    mapWorld.put("dimension", mapWorldDimension);
                }

                data.world = mapWorld;
            }



        }

        return data;
    }

    public static class ClientData {
        public int fps;
        public boolean isInGame;
        public boolean isPaused;
        public boolean isDemo;
        public boolean isFinishedLoading;
        public boolean isRealmsEnabled;
        public boolean isInSingleplayer;
        public boolean isIntegratedServerRunning;
        public boolean isConnectedToLocalServer;
        public boolean isMultiplayerEnabled;

        public HashMap<String, Object> player = new HashMap<>();
        public HashMap<String, Object> world = new HashMap<>();

    }
}
