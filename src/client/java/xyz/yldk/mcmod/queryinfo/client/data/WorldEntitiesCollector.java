package xyz.yldk.mcmod.queryinfo.client.data;

import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldEntitiesCollector {
    public static HashMap<String, Object> collectWorldEntities()
    {
        MinecraftClient client = MinecraftClient.getInstance();
        HashMap<String, Object> result = new HashMap<>();
        result.put("isInGame", client.world != null);
        List<EntityData> entitiesList = new ArrayList<>();
        if (client.world != null)
        {
            var entities = client.world.getEntities();
            for (var entity : entities) {
                EntityData entityData = new EntityData();
                entityData.name = entity.getName().getString();
                entityData.type = entity.getType().toString();
                entityData.uuid = entity.getUuidAsString();
                entityData.x = entity.getX();
                entityData.y = entity.getY();
                entityData.z = entity.getZ();
                entityData.pitch = entity.getPitch();
                entityData.yaw = entity.getYaw();
                entityData.blockX = entity.getBlockX();
                entityData.blockY = entity.getBlockY();
                entityData.blockZ = entity.getBlockZ();
                entityData.age = entity.age;
                entitiesList.add(entityData);
            }
        }
        result.put("entities", entitiesList);
        return result;
    }

    public static class EntityData {
        public String name;
        public String type;
        public String uuid;
        public double x;
        public double y;
        public double z;
        public double pitch;
        public double yaw;
        public int blockX;
        public int blockY;
        public int blockZ;
        public int age;
    }
}
