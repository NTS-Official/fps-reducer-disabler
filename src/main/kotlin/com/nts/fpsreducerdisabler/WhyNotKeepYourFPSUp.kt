package com.nts.fpsreducerdisabler

import net.fabricmc.api.ModInitializer
import net.minecraft.resources.Identifier
import org.slf4j.LoggerFactory

object WhyNotKeepYourFPSUp : ModInitializer {
	const val MOD_ID: String = "fpsreducerdisabler"
	const val MOD_NAME: String = "Why not Keep Your FPS up?"

	private val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		LOGGER.info("[$MOD_ID] $MOD_NAME successfully initialized.")
	}

	fun id(path: String): Identifier = Identifier.fromNamespaceAndPath(MOD_ID, path)
}
