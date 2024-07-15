package ua.iwaithi.fablaze.content.scheduler.task;

import net.minecraft.world.phys.Vec3;
import ua.iwaithi.fablaze.content.entity.CustomFablazeEntity;

public class MotionTask implements ITask{
    private TaskState state;
    private final Vec3 destination;
    private final double speed;
    private final double enterArea;
    private final double leaveArea;

    public MotionTask(Vec3 destination){
        this(destination, 0.7d);
    }
    public MotionTask(Vec3 destination, double speed){
        this(destination,speed,3D,1D);
    }
    public MotionTask(Vec3 destination, double speed, double enterArea, double leaveArea){
        this.state = TaskState.InQueue;
        this.destination = destination;
        this.speed = speed;
        this.enterArea = enterArea;
        this.leaveArea = leaveArea;
    }

    @Override
    public void perform(CustomFablazeEntity performer) {
        performer.setDestination(destination, speed);
        performer.setDestinationArea(enterArea,leaveArea);
    }

    @Override
    public void check(CustomFablazeEntity performer) {
        if(performer.getDestination() == destination){
            if(performer.isDestinationReached()) this.state = TaskState.Done;
            else this.state = TaskState.Active;
        }else {
            this.state = TaskState.Interrupted;
            perform(performer);
        }
    }

    @Override
    public TaskState getStatus() {
        return this.state;
    }
    @Override
    public void setStatus(TaskState state) {
        this.state = state;
    }


    public Vec3 getDestination(){
        return this.destination;
    }
    public double getEnterArea(){
        return this.enterArea;
    }
    public double getLeaveArea(){
        return this.leaveArea;
    }
    public double getSpeed(){
        return this.speed;
    }

}
