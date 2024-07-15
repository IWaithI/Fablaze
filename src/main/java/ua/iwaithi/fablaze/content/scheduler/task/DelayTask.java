package ua.iwaithi.fablaze.content.scheduler.task;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import ua.iwaithi.fablaze.content.ModTimer;
import ua.iwaithi.fablaze.content.entity.CustomFablazeEntity;

public class DelayTask implements ITask{
    private TaskState state;
    private int delay;
    private int startTime;

    public DelayTask(int ticks){
        this.delay = ticks;
        this.state = TaskState.InQueue;
    }

    @Override
    public void perform(CustomFablazeEntity performer) {
        var time = ModTimer.getTick();
        this.startTime = time;
        //Debug
        var player = Minecraft.getInstance().player;
        if(player != null) player.sendSystemMessage(Component.literal("[DelayTask start time: " + time + " -> " + delay + "]").withStyle(ChatFormatting.RED));
    }

    @Override
    public void check(CustomFablazeEntity performer) {
        if(ModTimer.getTick() > startTime + delay){
            // Debug
            var player = Minecraft.getInstance().player;
            if(player != null) player.sendSystemMessage(Component.literal("[DelayTask: DONE]").withStyle(ChatFormatting.RED));
            //
            setStatus(TaskState.Done);
        }else setStatus(TaskState.Active);
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
