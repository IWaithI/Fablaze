package ua.iwaithi.fablaze.content;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ModTimer {
    private static int tick;
    //Not realized yet
    private static int menuTick;

    public static int getTick(){
        return tick;
    }
    public static void tick(){
        if(tick % 100 == 0){
            var player = Minecraft.getInstance().player;
            if(player != null) player.sendSystemMessage(Component.literal("[" + tick + "]").withStyle(ChatFormatting.GOLD));
        }
        tick++;
    }
}
