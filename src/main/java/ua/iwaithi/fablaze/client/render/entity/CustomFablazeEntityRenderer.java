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
import org.jetbrains.annotations.NotNull;
import org.zeith.hammeranims.core.client.render.entity.BedrockEntityRenderer;
import ua.iwaithi.fablaze.Fablaze;
import ua.iwaithi.fablaze.content.entity.CustomFablazeEntity;
import ua.iwaithi.fablaze.init.ModEntities;
import ua.iwaithi.fablaze.init.ModGeometries;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CustomFablazeEntityRenderer extends BedrockEntityRenderer<CustomFablazeEntity> {

    public CustomFablazeEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, ModGeometries.SYMMETRY, 0.5f);

    }

    @Override
    protected List<RenderType> getRenderPasses(CustomFablazeEntity entity) {
        if(entity.isGlow()){
            return List.of(
                    RenderType.entityCutout(Fablaze.id("textures/entity/" + entity.getResource() + ".png")),
                    RenderType.eyes(Fablaze.id("textures/entity/" + entity.getResource() + "_glow.png"))
            );
        }else return List.of(
                RenderType.entityCutout(Fablaze.id("textures/entity/" + entity.getResource() + ".png")),
                RenderType.eyes(Fablaze.id("textures/entity/emptyglow.png"))
        );
    }

    @Override
    public void render(@NotNull CustomFablazeEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
//        System.out.println("render");
    }

    @Override
    public ResourceLocation getTextureLocation(CustomFablazeEntity entity) {

        return Fablaze.id("textures/entity/" + entity.getResource() + ".png");
    }

    @Override
    protected void renderNameTag(CustomFablazeEntity entity, Component nameTag, PoseStack pose, MultiBufferSource bufs, int packedLightCoords) {
    }

    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers e)
    {
        e.registerEntityRenderer(ModEntities.FABLAZENPC, CustomFablazeEntityRenderer::new);
    }

}
