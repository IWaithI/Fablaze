package ua.iwaithi.fablaze.init;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.LevelEvent;
import ua.iwaithi.fablaze.command.ActorCommand;
import ua.iwaithi.fablaze.content.dataset.NPCMapper;
import ua.iwaithi.fablaze.content.entity.CustomFablazeEntity;

public class ModForgeEvents {
    public static void onUnload(LevelEvent.Unload e){
        NPCMapper.actorMap.clear();
    }

    public static void onCommandsRegister(RegisterCommandsEvent event){
        new ActorCommand(event.getDispatcher());
    }

    public static void onActorDeath(LivingDeathEvent event){
        if(event.getEntity() != null && event.getEntity().getClass() == CustomFablazeEntity.class){
            NPCMapper.deleteActorFromList(String.valueOf(event.getEntity().getId()));
        }
    }
}
