package ua.iwaithi.fablaze.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.zeith.hammeranims.core.client.render.entity.BedrockEntityRenderer;
import ua.iwaithi.fablaze.Fablaze;
import ua.iwaithi.fablaze.content.entity.EntityFreya;
import ua.iwaithi.fablaze.init.ModEntities;
import ua.iwaithi.fablaze.init.ModGeometries;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderEntityFreya
		extends BedrockEntityRenderer<EntityFreya>
{
	protected final ResourceLocation texture = Fablaze.id("textures/entity/freya.png");
	
	public RenderEntityFreya(EntityRendererProvider.Context pContext)
	{
		super(pContext, ModGeometries.FREYA, 1);
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntityFreya entityFreya)
	{
		return texture;
	}
	
	@Override
	protected RenderType getRenderType(ResourceLocation texture)
	{
		return RenderType.entityCutout(texture);
	}
	
	@Override
	protected void renderNameTag(EntityFreya entity, Component nameTag, PoseStack pose, MultiBufferSource bufs, int packedLightCoords)
	{
	}
	
	@SubscribeEvent
	public static void registerRenderer(EntityRenderersEvent.RegisterRenderers e)
	{
		e.registerEntityRenderer(ModEntities.FREYA, RenderEntityFreya::new);
	}
}