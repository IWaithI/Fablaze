package ua.iwaithi.fablaze;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
//import org.zeith.hammerlib.core.adapter.LanguageAdapter;

@Mod(Fablaze.MODID)
public class Fablaze
{

    public static final String MODID = "fablaze";

    private static final Logger LOGGER = LogUtils.getLogger();


    public Fablaze()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    }

}
