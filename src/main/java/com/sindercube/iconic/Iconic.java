package com.sindercube.iconic;

import com.sindercube.iconic.cache.CacheReloader;
import com.sindercube.iconic.emote.EmoteLoader;
import com.sindercube.iconic.format.FormatTextComponent;
import com.sindercube.iconic.format.FormatLoader;
import com.sindercube.iconic.icon.IconTextComponent;
import com.sindercube.iconic.icon.IconLoader;
import com.sindercube.iconic.splash.SplashLoader;
import com.sindercube.iconic.textComponents.BaseTextComponent;
import com.sindercube.iconic.textComponents.TextComponentStorage;
import com.sindercube.iconic.textComponents.defaultComponents.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Iconic implements ClientModInitializer {
	public static final String MODID = "iconic";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitializeClient() {
		initializeResourceReloadListeners(
				CacheReloader.INSTANCE,
				//SplashLoader.INSTANCE,
				IconLoader.INSTANCE,
				FormatLoader.INSTANCE,
				EmoteLoader.INSTANCE
		);
		initializeTextComponents(
			new TextComponent(),
			new TranslateComponent(),
			new ScoreComponent(),
			new SelectorComponent(),
			new KeybindComponent(),
			new IconTextComponent(),
			new NBTComponent(),
			new FormatTextComponent()
		);
		FabricLoader.getInstance().getModContainer(MODID).ifPresent(container ->
			ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(MODID, "information_tooltips"), container, "Information Tooltips", ResourcePackActivationType.NORMAL)
		);
		LOGGER.info("Iconic initialized!");
	}
	public void initializeResourceReloadListeners(IdentifiableResourceReloadListener... resourceReloadListeners) {
		for (IdentifiableResourceReloadListener resourceReloadListener : resourceReloadListeners) {
			ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(resourceReloadListener);
		}
	}
	public void initializeTextComponents(BaseTextComponent ... components) {
		TextComponentStorage tcp = TextComponentStorage.INSTANCE;
		for (BaseTextComponent component : components) {
			tcp.addComponent(component);
		}
	}
}
