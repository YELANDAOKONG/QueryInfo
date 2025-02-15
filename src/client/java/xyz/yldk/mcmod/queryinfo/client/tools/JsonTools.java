package xyz.yldk.mcmod.queryinfo.client.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

public class JsonTools {
    private static final Gson GSON_OLD = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static String manualIndentFormat(Object data) {
        try (java.io.StringWriter writer = new java.io.StringWriter()) {
            JsonWriter jsonWriter = new JsonWriter(writer);
            jsonWriter.setIndent("    "); // 4-space indentation
            GSON_OLD.toJson(data, data.getClass(), jsonWriter);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to format JSON", e);
        }
    }

}
