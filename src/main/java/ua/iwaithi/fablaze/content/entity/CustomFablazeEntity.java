package ua.iwaithi.fablaze.content.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.zeith.hammeranims.api.animsys.AnimationSystem;
import org.zeith.hammeranims.api.animsys.CommonLayerNames;
import org.zeith.hammeranims.api.animsys.layer.AnimationLayer;
import org.zeith.hammeranims.api.tile.IAnimatedEntity;
import ua.iwaithi.fablaze.content.dataset.NPCMapper;
import ua.iwaithi.fablaze.init.ModAnimations;
import ua.iwaithi.fablaze.init.ModEntities;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CustomFablazeEntity extends PathfinderMob implements IAnimatedEntity {

    protected final AnimationSystem animations = AnimationSystem.create(this);

    public CustomFablazeEntity(EntityType<? extends CustomFablazeEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        if(!level().isClientSide()){
            NPCMapper.addActorToList(Integer.toString(this.getId()),this);
        }
        this.setPersistenceRequired();
    }

    @Override
    public void setupSystem(AnimationSystem.Builder builder)
    {
        builder.autoSync().addLayers(
                new AnimationLayer.Builder(CommonLayerNames.AMBIENT),
                new AnimationLayer.Builder(CommonLayerNames.LEGS)
        );
    }

    @Override
    public AnimationSystem getAnimationSystem()
    {
        return animations;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound)
    {
        pCompound.put("Animations", animations.serializeNBT());
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound)
    {
        animations.deserializeNBT(pCompound.getCompound("Animations"));
        super.readAdditionalSaveData(pCompound);
    }

    @SubscribeEvent
    public static void attributes(EntityAttributeCreationEvent e)
    {
        e.put(ModEntities.FABLAZENPC, Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D).build());
    }

    @Override
    public void tick()
    {
        animations.tick();
        super.tick();

        if(level().isClientSide) return;

        animations.startAnimationAt(CommonLayerNames.AMBIENT, ModAnimations.NPC_IDLE);

        var pos = position();
        double moved = Math.sqrt(pos.distanceToSqr(xo, yo, zo));
        boolean posChanged = Math.abs(pos.x - xo) >= 0.00390625F || Math.abs(pos.z - zo) >= 0.00390625F;
        if(!posChanged) moved = 0;

        if(moved > 0)
        {
            animations.startAnimationAt(CommonLayerNames.LEGS, moved > 0.2 ? ModAnimations.NPC_RUN : ModAnimations.NPC_WALK);
        } else
        {
            animations.stopAnimation(CommonLayerNames.LEGS, 0.4F);
        }
    }

}
