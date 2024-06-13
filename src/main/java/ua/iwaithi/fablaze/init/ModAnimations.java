package ua.iwaithi.fablaze.init;

import org.zeith.hammeranims.api.animation.AnimationHolder;
import org.zeith.hammeranims.api.animation.IAnimationContainer;
import org.zeith.hammerlib.annotations.*;

@SimplyRegister
public interface ModAnimations {
    @RegistryName("npc")
    IAnimationContainer NPC_ANIMATION_ROOT = IAnimationContainer.create();

    AnimationHolder NPC_IDLE = NPC_ANIMATION_ROOT.holder("animation.npc.idle");
    AnimationHolder NPC_WALK = NPC_ANIMATION_ROOT.holder("animation.npc.walk");
    AnimationHolder NPC_RUN = NPC_ANIMATION_ROOT.holder("animation.npc.run");
}
