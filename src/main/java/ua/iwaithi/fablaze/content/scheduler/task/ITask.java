package ua.iwaithi.fablaze.content.scheduler.task;

import ua.iwaithi.fablaze.content.entity.CustomFablazeEntity;

public interface ITask {
    enum TaskState{
        InQueue,
        Active,
        Done,
        Disabled,
        Interrupted;
    }
    void perform(CustomFablazeEntity performer);
    void check(CustomFablazeEntity performer);
    TaskState getStatus();
    void setStatus(TaskState state);
}
