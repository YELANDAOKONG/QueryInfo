package xyz.yldk.mcmod.queryinfo.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILE_NAME = "queryinfo-config.json";
    private static volatile ModConfig config; // volatile
    private static Path configPath;

    // 添加初始化状态检查
    private static boolean initialized = false;

    public static synchronized void initialize() {
        if (initialized) return;

        Path configDir = MinecraftClient.getInstance().runDirectory.toPath()
                .resolve("config");

        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create config directory", e);
        }

        configPath = configDir.resolve(CONFIG_FILE_NAME);
        loadConfig();
        initialized = true;
    }

    private static void loadConfig() {
        try {
            if (Files.exists(configPath)) {
                String json = Files.readString(configPath);
                try {
                    config = GSON.fromJson(json, ModConfig.class);
                    if (config == null || !isConfigValid(config)) {
                        createDefaultConfig();
                    }
                } catch (JsonSyntaxException e) {
                    System.err.println("Invalid config format, creating new config");
                    createDefaultConfig();
                }
            } else {
                createDefaultConfig();
            }
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
            createDefaultConfig();
        }
    }

    private static void createDefaultConfig() {
        config = new ModConfig();
        saveConfig();
    }

    private static boolean isConfigValid(ModConfig config) {
        // 添加基础参数验证
        return config.apiPort > 0 && config.apiPort <= 65535 &&
                config.apiPortMax >= config.apiPort &&
                config.apiAcceptQueueSize > 0;
    }

    public static void saveConfig() {
        try {
            Files.writeString(configPath, GSON.toJson(config));
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }

    public static ModConfig getConfig() {
        if (!initialized) {
            synchronized (ModConfigManager.class) {
                if (!initialized) {
                    initialize();
                }
            }
        }
        return config;
    }
}
