package ua.iwaithi.fablaze.common.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GeoItemArmorModel extends GeoModel<GeoItemArmor> {
    private final String a;
    private final String b;

    public GeoItemArmorModel(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public ResourceLocation getModelResource(GeoItemArmor animatable) {
        return new ResourceLocation("fablaze", this.b);
    }

    public ResourceLocation getTextureResource(GeoItemArmor animatable) {
        return new ResourceLocation("fablaze", this.a);
    }

    public ResourceLocation getAnimationResource(GeoItemArmor animatable) {
        return new ResourceLocation("fablaze", "animations/helmet.animation.json");
    }


//    @Override
//    public void setCustomAnimations(GeoItemArmor animatable, long instanceId, AnimationState<GeoItemArmor> animationState) {
//        super.setCustomAnimations(animatable, instanceId, animationState);
//        final CoreGeoBone head = getAnimationProcessor().getBone("head");
//        final EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
//        if (head != null) {
//            head.setRotX(extraData.headPitch() * Mth.DEG_TO_RAD);
//            head.setRotY(extraData.netHeadYaw() * Mth.DEG_TO_RAD);
//        }
//    }
}

