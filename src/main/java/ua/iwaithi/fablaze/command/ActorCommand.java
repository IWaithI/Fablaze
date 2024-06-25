package ua.iwaithi.fablaze.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import ua.iwaithi.fablaze.content.dataset.NPCMapper;
import ua.iwaithi.fablaze.content.entity.CustomFablazeEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;

public class ActorCommand {

    public ActorCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        Vec3Argument Vec3ArgumentType = null;
        dispatcher.register(Commands.literal("actor")                                   // - /actor ...

                .then(Commands.literal("move")                                              // - /actor move
                .then(Commands.argument("Name", StringArgumentType.word())                  // - /actor move "Name" ...
                .then(Commands.argument("Coordinates", Vec3Argument.vec3())                 // - /actor move "Name" "Coordinates" ...
                .executes(this::resendTarged))))                                                   //Execute

                .then(Commands.literal("list").executes(this::listing))                     // - /actor list = Execute
//
                .then(Commands.literal("say")
                .then(Commands.argument("Name", StringArgumentType.string())
                .then(Commands.argument("Message", StringArgumentType.string())
                .executes(this::sendMessage))))
//
//                .then(Commands.literal("change")
//                .then(Commands.argument("Name", StringArgumentType.word())
//                .then(Commands.argument("Resource", StringArgumentType.word())
//                .executes(this::changeRes))))
//
//                .then(Commands.literal("name")                                              // - /actor name ...
//                .then(Commands.argument("Name",StringArgumentType.word())                   // - /actor name "Name" ...
//                .then(Commands.argument("Boolean", BoolArgumentType.bool())                 // - /actor name "Name" "Boolean"
//                .executes(this::setVisibility))))
//
//                .then(Commands.literal("look-actor")
//                .then(Commands.argument("Name", StringArgumentType.word())
//                .then(Commands.argument("Target", StringArgumentType.word())
//                .executes(this::setLook))))
//
//                .then(Commands.literal("look-position")
//                        .then(Commands.argument("Name", StringArgumentType.word())
//                                .then(Commands.argument("Coordinates", Vec3Argument.vec3())
//                                        .executes(this::setLookPos))))
//
//                .then(Commands.literal("look-entity")
//                .then(Commands.argument("Name", StringArgumentType.word())
//                .then(Commands.argument("Target", EntityArgument.entity())
//                .executes(this::setLookEntity))))
//
//                .then(Commands.literal("look-type")
//                .then(Commands.argument("Name", StringArgumentType.word())
//                .then(Commands.argument("Target", EntitySummonArgument.id()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
//                .executes(this::setLookType))))
//
//                .then(Commands.literal("animation")
//                .then(Commands.argument("Name", StringArgumentType.word())
//                .then(Commands.argument("Action", StringArgumentType.string())
//                .then(Commands.argument("Emote", StringArgumentType.string())
//                .then(Commands.argument("Look", StringArgumentType.string())
//                .then(Commands.argument("Additional", StringArgumentType.string())
//                .executes(this::setAnimation)))))))
//
//                .then(Commands.literal("eliminate")                                         // - /actor eliminate ...
//                .then(Commands.argument("Name", StringArgumentType.word())                  // - /actor eliminate "Name" ...
//                .executes(this::eliminate))));                                                       //Execute
    }

    public int listing(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        Player player = command.getSource().getPlayer();
        if (player != null){
            player.sendSystemMessage(Component.literal(NPCMapper.getActors()).withStyle(ChatFormatting.BLUE));
        }
        return 1;
    }

    public int resendTarged(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        CustomFablazeEntity entity = NPCMapper.getActorByName(StringArgumentType.getString(command,"Name"));
        Vec3 vector = Vec3Argument.getVec3(command,"Coordinates");

        entity.setTarget(vector,1d);


        return 1;
    }

    public int sendMessage(CommandContext<CommandSourceStack> command) throws CommandSyntaxException {
        CustomFablazeEntity entity = NPCMapper.getActorByName(StringArgumentType.getString(command, "Name"));
        entity.talk(StringArgumentType.getString(command, "Message"));
        return 1;
    }
}