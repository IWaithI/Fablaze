package ua.iwaithi.fablaze.init;

import org.zeith.hammeranims.api.animation.AnimationHolder;
import org.zeith.hammeranims.api.animation.IAnimationContainer;
import org.zeith.hammerlib.annotations.*;

@SimplyRegister
public interface ModAnimations {
    @RegistryName("hui_animation")
    IAnimationContainer HUI_ANIMATION = IAnimationContainer.create();

    AnimationHolder HUI_ANIMATION_IDLE = HUI_ANIMATION.holder("idle");
    AnimationHolder HUI_ANIMATION_WALK = HUI_ANIMATION.holder("walk");
}
