package xyz.yldk.mcmod.queryinfo.client.tools;

import java.util.UUID;

public class ApiTools {

    public static ApiResponse build(int code, String message, Object data){
        long time = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString();
        return new ApiResponse(code, message, time, uuid, data);
    }

    public static String format(ApiResponse response){
        return JsonTools.manualIndentFormat(response);
    }
}

