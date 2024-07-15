package ua.iwaithi.fablaze.content.scheduler.task;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import ua.iwaithi.fablaze.content.entity.CustomFablazeEntity;

public class LookTask implements ITask{
    private TaskState state;
    private Vec3 coordinates = null;
    private Entity target = null;
    private ResourceLocation type = null;
    private boolean isTargetOnPlayer = false;

    public LookTask(Vec3 coordinates){
        this.coordinates = coordinates;
        this.state = TaskState.InQueue;
    }
    public LookTask(Entity target){
        this.target = target;
        this.state = TaskState.InQueue;
    }
    public LookTask(ResourceLocation type){
        this.type = type;
        this.state = TaskState.InQueue;
    }
    public LookTask(boolean lookAtPlayer){
        this.isTargetOnPlayer = lookAtPlayer;
        this.state = TaskState.InQueue;
    }

    @Override
    public void perform(CustomFablazeEntity performer) {
        if(coordinates != null){
            performer.setLookPos(coordinates);
        }else if(target != null){
            performer.setLookAt(target);
        }else if(type != null){
            performer.setLookType(type);
        }else if(isTargetOnPlayer){
            var player = Minecraft.getInstance().player;
            if(player != null) performer.setLookAt(player);
        }else{
            performer.resetLook();
        }
        setStatus(TaskState.Done);
    }

    @Override
    public void check(CustomFablazeEntity performer) {
        perform(performer);
    }

    @Override
    public TaskState getStatus() {
        return this.state;
    }

    @Override
    public void setStatus(TaskState state) {
        this.state = state;
    }
}
