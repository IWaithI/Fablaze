package ua.iwaithi.fablaze.content.scheduler;

import java.util.HashMap;
import java.util.Map;

public class Schedule {
    private final Map<Integer, TaskPacket> schedule;

    public Schedule(){
        schedule = new HashMap<>();
    }
    public void reset(){
        for(int i = 0; i < scheduleSize(); i++){
            schedule.get(i).reset();
        }
    }
    public void addPacket(TaskPacket packet){
        schedule.put(schedule.size(),packet);
    }
    public TaskPacket getPacket(int id){
        return schedule.get(id);
    }
    public int scheduleSize(){
        return schedule.size();
    }



}
