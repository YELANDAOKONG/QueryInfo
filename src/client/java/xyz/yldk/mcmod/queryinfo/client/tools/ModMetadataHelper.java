package xyz.yldk.mcmod.queryinfo.client.tools;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import xyz.yldk.mcmod.queryinfo.client.QueryinfoClient;

import java.util.Optional;

public class ModMetadataHelper {
    public static final String MOD_ID = QueryinfoClient.MOD_ID;
    private static String cachedVersion = null;

    public static String getModVersion() {
        if (cachedVersion == null) {
            Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(MOD_ID);
            if (container.isPresent()) {
                cachedVersion = container.get().getMetadata().getVersion().getFriendlyString();
            } else {
                cachedVersion = "DEV";
            }
        }
        return cachedVersion;
    }

    public static String getFullVersion() {
        return FabricLoader.getInstance().getModContainer(MOD_ID)
                .map(mod -> mod.getMetadata().getVersion().toString())
                .orElse("0.0.0+unknown");
    }

    public static String getModName() {
        return FabricLoader.getInstance().getModContainer(MOD_ID)
                .map(mod -> mod.getMetadata().getName())
                .orElse("Unknown Mod");
    }
}
