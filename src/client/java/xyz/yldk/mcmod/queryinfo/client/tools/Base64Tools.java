package xyz.yldk.mcmod.queryinfo.client.tools;

import java.util.Base64;

public class Base64Tools {
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    public static String byteArrayToBase64(byte[] bytes) {
        return ENCODER.encodeToString(bytes);
    }

    public static byte[] base64ToByteArray(String base64String) {
        return DECODER.decode(base64String);
    }
}

