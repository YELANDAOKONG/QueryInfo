package xyz.yldk.mcmod.queryinfo.client.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.yldk.mcmod.queryinfo.client.WebServerManager;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "stop", at = @At("HEAD"))
    private void onStop(CallbackInfo ci) {
        xyz.yldk.mcmod.queryinfo.client.WebServerManager.stopServer();
    }
}
