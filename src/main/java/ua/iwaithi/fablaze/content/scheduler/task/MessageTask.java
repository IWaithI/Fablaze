package ua.iwaithi.fablaze.content.scheduler.task;

import ua.iwaithi.fablaze.content.entity.CustomFablazeEntity;

public class MessageTask implements ITask{
    private TaskState state;
    private String message;

    public MessageTask(String message){
        this.state = TaskState.InQueue;
        this.message = message;
    }


    @Override
    public void perform(CustomFablazeEntity performer) {
        if(performer.talk(message)){
            this.state = TaskState.Done;
        }else this.state = TaskState.Interrupted;
    }

    @Override
    public void check(CustomFablazeEntity performer){
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

    public String getMessage() {
        return message;
    }
}
