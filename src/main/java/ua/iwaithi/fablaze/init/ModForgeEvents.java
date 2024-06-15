package ua.iwaithi.fablaze.init;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.level.LevelEvent;
import ua.iwaithi.fablaze.command.ActorCommand;
import ua.iwaithi.fablaze.content.dataset.NPCMapper;

public class ModForgeEvents {
    public static void onUnload(LevelEvent.Unload e){
        NPCMapper.actorMap.clear();
    }

    public static void onCommandsRegister(RegisterCommandsEvent event){
        new ActorCommand(event.getDispatcher());
    }
}
