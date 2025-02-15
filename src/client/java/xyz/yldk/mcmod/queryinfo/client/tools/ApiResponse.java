package xyz.yldk.mcmod.queryinfo.client.tools;

public class ApiResponse {

    public int code;
    public String msg; // message
    public long time; // 13-length timestamp
    public String uuid;
    public Object data;

    public ApiResponse(int code, String message, long time, String uuid, Object data) {
        this.code = code;
        this.msg = message;
        this.time = time;
        this.uuid = uuid;
        this.data = data;
    }

    public String format(){
        return ApiTools.format(this);
    }
}
