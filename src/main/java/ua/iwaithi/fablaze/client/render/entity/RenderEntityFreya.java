package ua.iwaithi.fablaze.client.render.entity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.zeith.hammeranims.core.client.render.entity.BedrockEntityRenderer;
import ua.iwaithi.fablaze.init.EntityFreya;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderEntityFreya
        extends BedrockEntityRenderer<EntityFreya>
{
    // Here is where the texture is specified
    protected final ResourceLocation texture = new ResourceLocation("fablaze", "textures/entity/freya.png");

    public RenderEntityFreya(EntityRendererProvider.Context pContext)
    {
        // Here is where we specify the geometry file
        super(pContext, ContainersHA.FREYA_GEOM, 1);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityFreya entityFreya) {
        return texture;
    }

    @Override
    protected RenderType getRenderType(ResourceLocation texture)
    {
        return RenderType.entityCutout(texture);
    }

    // This method binds our renderer to the entity type
    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers e)
    {
        e.registerEntityRenderer(ContainersHA.FREYA_ENTITY, RenderEntityFreya::new);
    }
}