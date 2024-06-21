package ua.iwaithi.fablaze.content.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.zeith.hammeranims.api.animsys.AnimationSystem;
import org.zeith.hammeranims.api.animsys.CommonLayerNames;
import org.zeith.hammeranims.api.animsys.layer.AnimationLayer;
import org.zeith.hammeranims.api.tile.IAnimatedEntity;
import ua.iwaithi.fablaze.content.dataset.NPCMapper;
import ua.iwaithi.fablaze.content.entity.goal.LookAtGoal;
import ua.iwaithi.fablaze.content.entity.goal.MoveToGoal;
import ua.iwaithi.fablaze.init.ModAnimations;
import ua.iwaithi.fablaze.init.ModEntities;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CustomFablazeEntity extends PathfinderMob implements IAnimatedEntity {

    protected final AnimationSystem animations = AnimationSystem.create(this);
    private Entity lookAt = null;
    private Vec3 lookPos = null;
    private boolean isCustomLookSet = false;

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

    public static final EntityDataAccessor<String> DATA_CHARACTER =
            SynchedEntityData.defineId(CustomFablazeEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Boolean> DATA_GLOW =
            SynchedEntityData.defineId(CustomFablazeEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(DATA_GLOW, getPersistentData().getBoolean("isGlow"));
        this.entityData.define(DATA_CHARACTER, getPersistentData().getString("character"));
        if(entityData.get(DATA_CHARACTER).isBlank()){
            this.entityData.set(DATA_CHARACTER, "symmetry");
        }
    }

    public void changeResource(String resource, boolean isGlow){
        this.entityData.set(DATA_CHARACTER, resource);
        this.entityData.set(DATA_GLOW, isGlow);
        System.out.println("Attempted change res to " + resource + ". Result: " + this.entityData.get(DATA_CHARACTER));
    }
    public String getResource(){
        return entityData.get(DATA_CHARACTER);
    }
    public boolean isGlow(){ return  entityData.get(DATA_GLOW); }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound)
    {
        pCompound.put("Animations", animations.serializeNBT());
        pCompound.putString("character", this.entityData.get(DATA_CHARACTER));
        pCompound.putBoolean("isGlow", this.entityData.get(DATA_GLOW));
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound)
    {
        animations.deserializeNBT(pCompound.getCompound("Animations"));
        this.entityData.set(DATA_CHARACTER, pCompound.getString("character"));
        this.entityData.set(DATA_GLOW, pCompound.getBoolean("isGlow"));
        super.readAdditionalSaveData(pCompound);
    }

    @SubscribeEvent
    public static void attributes(EntityAttributeCreationEvent e)
    {
        e.put(ModEntities.FABLAZENPC, Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D).build());
    }

    MoveToGoal moveGoal;
    LookAtGoal lookGoal;
    RandomLookAroundGoal randomLookGoal;

    protected void registerGoals() {
        removeFreeWill();
        moveGoal = new MoveToGoal(this);
        lookGoal = new LookAtGoal(this);
        randomLookGoal = new RandomLookAroundGoal(this);
        this.goalSelector.addGoal(1, moveGoal);
        this.goalSelector.addGoal(1, randomLookGoal);
        this.goalSelector.addGoal(2, lookGoal);
    }

    private void resetLookGoals() {
        lookGoal.reset();
        this.goalSelector.removeGoal(randomLookGoal);
        isCustomLookSet = true;
    }

    public void setTarget(Vec3 target, double speed) {
        moveGoal.setTarget(target);
        moveGoal.setSpeed(speed);
    }

    public void setLookAt(Entity target) {
        resetLookGoals();
        lookGoal.setLookAt(target);
    }

    public void setLookPos(Vec3 pos) {
        resetLookGoals();
        lookGoal.setLookPos(pos);
    }

    public void setLookType(ResourceLocation target) {
        resetLookGoals();
        lookGoal.setLookType(target);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {

        if (player.isHolding(Items.STICK)) {
            if (!entityData.get(DATA_GLOW) && !level().isClientSide() && hand == InteractionHand.MAIN_HAND) {

                player.sendSystemMessage(Component.literal("Glow ON"));
            } else if(!level().isClientSide() && hand == InteractionHand.MAIN_HAND) player.sendSystemMessage(Component.literal("Glow OFF"));
            entityData.set(DATA_GLOW, !entityData.get(DATA_GLOW));

        }return InteractionResult.SUCCESS;
    }


    @Override
    public void tick() {
        animations.tick();
        super.tick();

        if (level().isClientSide) return;

        animations.startAnimationAt(CommonLayerNames.AMBIENT, ModAnimations.NPC_IDLE);

        var pos = position();
        double moved = Math.sqrt(pos.distanceToSqr(xo, yo, zo));
        boolean posChanged = Math.abs(pos.x - xo) >= 0.00390625F || Math.abs(pos.z - zo) >= 0.00390625F;
        if (!posChanged) moved = 0;

        if (moved > 0) {
            animations.startAnimationAt(CommonLayerNames.LEGS, moved > 0.2 ? ModAnimations.NPC_RUN : ModAnimations.NPC_WALK);
        } else {
            animations.stopAnimation(CommonLayerNames.LEGS, 0.4F);
        }
        if (this.lookAt != null && lookAt.isAlive()) {
            this.getLookControl().setLookAt(lookAt);
        } else if (this.lookPos != null) {
            this.getLookControl().setLookAt(lookPos);
        } else if (!isCustomLookSet) {
            this.goalSelector.addGoal(1, randomLookGoal);
        }
    }

}
