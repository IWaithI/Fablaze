package ua.iwaithi.fablaze.content.scheduler;

import ua.iwaithi.fablaze.content.entity.CustomFablazeEntity;
import ua.iwaithi.fablaze.content.scheduler.task.ITask;
import ua.iwaithi.fablaze.content.scheduler.task.MessageTask;
import ua.iwaithi.fablaze.content.scheduler.task.MotionTask;

public class TaskScheduler {
    enum SchedulerState{
        Idle,
        Active,
        Loop,
        Override;
    }
    private final CustomFablazeEntity performer;
    private Schedule schedule;
    private Schedule overrideSchedule;
    private SchedulerState state;
    private int tickrate = 10;
    private int tick = 0;
    private int currentPacketID = 0;
    private int currentStageID = 0;

    public TaskScheduler(CustomFablazeEntity performer){
        this.state = SchedulerState.Active;
        this.performer = performer;
    }

    private void debugMessage(){
        System.out.println(" SYSTEM: " + state +" - Packet ID: " + currentPacketID + " - Stage ID: " + currentStageID);
    }

    private void loop(){
        if (this.state == SchedulerState.Loop && this.currentPacketID == schedule.scheduleSize()){
            this.currentPacketID = 0;
            schedule.reset();
        }else if(this.state == SchedulerState.Active && this.currentPacketID == schedule.scheduleSize()){
            this.currentPacketID = 0;
            this.state = SchedulerState.Idle;
        }
    }
    public boolean isLooping(){
        return state == SchedulerState.Loop;
    }
    public void setLooping(boolean bool){
        if(bool)setState(SchedulerState.Loop);
        else loop();
    }

    private void useTask(ITask task){
        switch (task.getStatus()){
            case InQueue -> {
                task.setStatus(ITask.TaskState.Active);
                task.perform(this.performer);
            }
            case Active -> task.check(this.performer);
            case Done, Disabled -> {}
            case Interrupted -> task.perform(this.performer);
        }
    }

    private void setState(SchedulerState state){
        this.state = state;
    }



    private TaskPacket getPacket(int id){
        if(id >= 0 && id <= schedule.scheduleSize()) return schedule.getPacket(id);
        else return null;
    }
    public Schedule getCurrentSchedule(){
        if(this.state != SchedulerState.Override){
            return overrideSchedule;
        }else return schedule;
    }

    private void nextStage(){
        debugMessage();
        if(this.state != SchedulerState.Idle){
            this.currentStageID++;
        }
    }
    public void nextPacket(){
        debugMessage();
        if(this.state != SchedulerState.Idle){
            this.currentPacketID++;
            this.currentStageID = 0;
        }
    }

    public void assign(Schedule newSchedule){
        this.schedule = newSchedule;
    }

    public boolean run(){
        if(this.currentPacketID != 0) return false;
        this.currentStageID = 0;
        setState(SchedulerState.Active);
        schedule.reset();
        return true;
    }
    public boolean restart(){
        if(this.state == SchedulerState.Idle) return false;
        schedule.reset();
        this.currentPacketID = 0;
        this.currentStageID = 0;
        return true;
    }

    // Not realized yet
    public void performNow(ITask task){
        setState(SchedulerState.Override);
    }

    public void update(){
        if(tick++ % tickrate == 0){
            loop();
            var packet = getPacket(this.currentPacketID);
            if(packet != null){
                if (packet.isAllDone()) {
                    nextPacket();
                } else if(!packet.isChained()){
                    var tasks = packet.getTasks();
                    for(ITask task : tasks){
                        useTask(task);
                    }
                } else{
                    var task = packet.getTaskByID(currentStageID);
                    useTask(task);
                    nextStage();
                }
            }
        }
    }
}
