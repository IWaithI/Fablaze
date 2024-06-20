package ua.iwaithi.fablaze;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.zeith.hammerlib.core.adapter.LanguageAdapter;
import ua.iwaithi.fablaze.common.util.ModItems;
import ua.iwaithi.fablaze.init.ModForgeEvents;

@Mod(Fablaze.MODID)
public class Fablaze
{
	
	public static final String MODID = "fablaze";
	
	private static final Logger LOGGER = LogUtils.getLogger();
	
	public Fablaze() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgebus = MinecraftForge.EVENT_BUS;
		LanguageAdapter.registerMod(MODID);

		forgebus.addListener(ModForgeEvents::onUnload);
		forgebus.addListener(ModForgeEvents::onCommandsRegister);
		forgebus.addListener(ModForgeEvents::onActorDeath);

		ModItems.register(modEventBus);

	}
	
	public static ResourceLocation id(String path)
	{
		return new ResourceLocation(MODID, path);
	}
}
