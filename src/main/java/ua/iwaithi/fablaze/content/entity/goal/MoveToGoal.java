package ua.iwaithi.fablaze.content.entity.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;
import ua.iwaithi.fablaze.content.entity.CustomFablazeEntity;

public class MoveToGoal extends Goal {
    private Vec3 target;
    private double speed;
    private double leaveRad;
    private double enterRad;

    private final CustomFablazeEntity actor;
    private final PathNavigation navigator;

    public MoveToGoal (CustomFablazeEntity entity){
        this.actor = entity;
        this.target = new Vec3(entity.xo,entity.yo,entity.zo);
        this.navigator = entity.getNavigation();
    }

    public void setTarget(Vec3 coordinates){this.target = coordinates;}
    public Vec3 getTarget(){return this.target;}

    public void setSpeed(double speed){this.speed = speed;}
    public double getSpeed(){return this.speed;}

    public void setRadius(double enterRadius, double leaveRadius){
        this.enterRad = enterRadius;
        this.leaveRad = leaveRadius;
    }
    public double getLeaveRad() {return leaveRad;}
    public double getEnterRad() {return enterRad;}

    @Override
    public boolean canUse() {
        if(Math.abs(new Vec3(actor.xo,actor.yo,actor.zo).subtract(target).length()) <= enterRad){
            return false;
        }else if(Math.abs(new Vec3(actor.xo,actor.yo,actor.zo).subtract(target).length()) >= leaveRad){
            return true;
        }else return false;
    }

    @Override
    public void tick() {
        super.tick();
        navigator.moveTo(target.x(),target.y(),target.z(),speed);
    }
}
