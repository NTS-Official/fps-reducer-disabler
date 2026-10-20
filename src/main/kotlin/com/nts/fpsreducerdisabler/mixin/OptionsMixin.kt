package com.nts.fpsreducerdisabler.mixin

import net.minecraft.client.InactivityFpsLimit
import net.minecraft.client.OptionInstance
import net.minecraft.client.Options
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.network.chat.Component
import net.minecraft.util.StringRepresentable
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType

@Mixin(Options::class)
abstract class OptionsMixin {
    @Unique private var `fpsreducerdisabler$initialized`: Boolean = false

    @Shadow abstract fun inactivityFpsLimit(): OptionInstance<InactivityFpsLimit>

    @Inject(method = ["<init>"], at = [At(value = "INVOKE", target = "Ljava/lang/Object;<init>()V", shift = At.Shift.AFTER)])
    private fun injectNeverEnum(ci: CallbackInfo) {
        val lookup = MethodHandles.privateLookupIn(InactivityFpsLimit::class.java, MethodHandles.lookup())
        val constructorHandle = lookup.findConstructor(InactivityFpsLimit::class.java, MethodType.methodType(Void.TYPE, String::class.java, Int::class.javaPrimitiveType, String::class.java, String::class.java))
        val currentValues = InactivityFpsLimit.entries.toTypedArray()
        val neverInstance = constructorHandle.invoke("NEVER", currentValues.size, "never", "options.inactivityFpsLimit.never") as InactivityFpsLimit

        MixinUtils.NEVER = neverInstance
        val newValues = currentValues + neverInstance
        val valuesHandle = lookup.findStaticVarHandle(InactivityFpsLimit::class.java, "\$VALUES", Array<InactivityFpsLimit>::class.java)
        valuesHandle.set(newValues)

        InactivityFpsLimit.CODEC = StringRepresentable.fromEnum { newValues }
    }

    @Inject(method = ["<init>"], at = [At("RETURN")])
    private fun fixInactivityTooltip(ci: CallbackInfo) {
        val option = inactivityFpsLimit()
        val tooltipField = OptionInstance::class.java.getDeclaredField("tooltip")
        tooltipField.isAccessible = true
        @Suppress("UNCHECKED_CAST") val originalSupplier = tooltipField.get(option) as OptionInstance.TooltipSupplier<InactivityFpsLimit>
        val wrappedSupplier = OptionInstance.TooltipSupplier<InactivityFpsLimit> { value ->
            if (value === MixinUtils.NEVER) Tooltip.create(Component.translatable("options.inactivityFpsLimit.never.tooltip"))
            else originalSupplier.apply(value)
        }
        tooltipField.set(option, wrappedSupplier)
    }
}
