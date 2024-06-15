package ua.iwaithi.fablaze.content.dataset;

import ua.iwaithi.fablaze.content.entity.CustomFablazeEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NPCMapper {
    public static Map<String, CustomFablazeEntity> actorMap = new HashMap<>();

    public static void addActorToList(String key, CustomFablazeEntity actor){
        int id = actor.getId();
        if(actorMap.containsKey(String.valueOf(id))){
            actorMap.remove(String.valueOf(id));
        }
        actorMap.put(key, actor);

    }
    public static void deleteActorFromList(String key){
        actorMap.remove(key);

    }
    public static void renameActorInList(int id, String key){
        if(actorMap.containsKey(String.valueOf(id))){
            CustomFablazeEntity actor = getActorByName(String.valueOf(id));
            deleteActorFromList(String.valueOf(id));
            addActorToList(key, actor);
        }
    }


    public static CustomFablazeEntity getActorByName(String key){
        return actorMap.get(key);
    }
    public static boolean contains(CustomFablazeEntity actor){
        return actorMap.containsValue(actor);
    }


    public static String getActors(){
        Set<String> ActorList;
        ActorList = actorMap.keySet();
        return ActorList.toString();
    }



}