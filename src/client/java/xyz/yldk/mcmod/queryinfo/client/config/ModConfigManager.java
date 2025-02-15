package xyz.yldk.mcmod.queryinfo.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILE_NAME = "queryinfo-config.json";
    private static ModConfig config;
    private static Path configPath;

    public static void initialize() {
        // Get Config Directory
        Path configDir = MinecraftClient.getInstance().runDirectory.toPath()
                .resolve("config");

        if (!Files.exists(configDir)) {
            try {
                Files.createDirectories(configDir);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create config directory", e);
            }
        }

        configPath = configDir.resolve(CONFIG_FILE_NAME);
        loadConfig();
    }

    private static void loadConfig() {
        try {
            if (Files.exists(configPath)) {
                String json = Files.readString(configPath);
                config = GSON.fromJson(json, ModConfig.class);
            } else {
                config = new ModConfig();
                saveConfig();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public static void saveConfig() {
        try (Writer writer = Files.newBufferedWriter(configPath)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            JsonWriter jsonWriter = new JsonWriter(writer);
            jsonWriter.setIndent("    "); // 4-space indentation

            GSON.toJson(config, ModConfig.class, jsonWriter);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config", e);
        }
    }


    public static ModConfig getConfig() {
        return config;
    }

}
