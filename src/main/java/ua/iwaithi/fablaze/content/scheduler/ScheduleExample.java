package ua.iwaithi.fablaze.content.scheduler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import ua.iwaithi.fablaze.content.scheduler.task.DelayTask;
import ua.iwaithi.fablaze.content.scheduler.task.LookTask;
import ua.iwaithi.fablaze.content.scheduler.task.MessageTask;
import ua.iwaithi.fablaze.content.scheduler.task.MotionTask;

public class ScheduleExample {
    public static Schedule getCycledSheduleExample(){
        var schedule = new Schedule();
        schedule.addPacket(new TaskPacket(false,
                new MotionTask(new Vec3(15,10,15)),
                new MessageTask("Hello, where i'm???"),
                new LookTask(true),
                new DelayTask(350)));
        schedule.addPacket(new TaskPacket(false,
                new MotionTask(new Vec3(20,10,20)),
                new LookTask(false),
                new MessageTask("That is not funny, help me!"),
                new DelayTask(350)));
        schedule.addPacket(new TaskPacket(false,
                new MotionTask(new Vec3(25,10,10)),
                new MessageTask("Hey, guys..."),
                new DelayTask(350)));
        return schedule;
    }

}
