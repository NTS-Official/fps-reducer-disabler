package com.nts.fpsreducerdisabler.mixin

import com.mojang.blaze3d.platform.FramerateLimitTracker
import com.nts.fpsreducerdisabler.Utils
import net.minecraft.client.Options
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

@Mixin(FramerateLimitTracker::class)
class FpsLimitTrackerMixin {
    // Shadowed options
    @Shadow @Final private lateinit var options: Options

    // Modify Fps Limit
    @Inject(method = ["getFramerateLimit"], at = [At("HEAD")], cancellable = true)
    private fun getFramerateLimit(cir: CallbackInfoReturnable<Int>) =
        if (options.inactivityFpsLimit().get() == Utils.NEVER) cir.returnValue = this.options.framerateLimit().get()
        else null
}