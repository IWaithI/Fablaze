package ua.iwaithi.fablaze.content.scheduler;

import ua.iwaithi.fablaze.content.scheduler.task.ITask;

import java.util.HashMap;
import java.util.Map;

public class TaskPacket {
    public enum PacketType{
        Undefined,
        Single,
        Multiple,
        Chained;
    }
    private final Map<Integer, ITask> packet;
    private PacketType type;

    private ITask getFirst(){
        return packet.get(0);
    }
    private ITask getLast() {
        return packet.get(packet.size()-1);
    }

    public TaskPacket(Boolean isChained,ITask... tasks){
        if (tasks.length != 0) {
            packet = new HashMap<>(tasks.length);
            for (ITask task : tasks) {
                packet.put(packet.size(), task);
            }
            if(packet.size() == 1){
                this.type = PacketType.Single;
            }else if(isChained){
                this.type = PacketType.Chained;
            }else this.type = PacketType.Multiple;

        }else{
            this.packet = new HashMap<>();
            this.type = PacketType.Undefined;
        }
    }

    public int getTaskLength(){
        return packet.size();
    }
    public boolean isChained(){
        return type == PacketType.Chained;
    }
    public boolean isAllDone(){
        for (ITask task : packet.values()) {
            if(task.getStatus() != ITask.TaskState.Done) return false;
        }return true;
    }
    public ITask[] getTasks(){
        ITask[] tasks = new ITask[packet.size()];
        for(int i = 0; i < packet.size(); i++){
            tasks[i] = packet.get(i);
        }
        return tasks;
    }

    public ITask getTaskByID(int id){
        if(id > 0 && id < packet.size()) return packet.get(id);
        else if (id <= 0) {return getFirst();}
        else return getLast();
    }
    public void reset(){
        for(int i = 0; i < packet.size(); i++){
            packet.get(i).setStatus(ITask.TaskState.InQueue);
        }
    }

}
