package ua.iwaithi.fablaze.init;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.zeith.hammeranims.api.geometry.*;
import org.zeith.hammeranims.api.geometry.event.RefreshStaleModelsEvent;
import org.zeith.hammerlib.annotations.*;

@SimplyRegister
public interface ModGeometries
{
    @RegistryName("freya")
    IGeometryContainer REYA = IGeometryContainer.create();
}
