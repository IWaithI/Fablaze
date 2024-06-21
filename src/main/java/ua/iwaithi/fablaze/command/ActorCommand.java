package ua.iwaithi.fablaze.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import ua.iwaithi.fablaze.content.dataset.NPCMapper;
import ua.iwaithi.fablaze.content.entity.CustomFablazeEntity;

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
//                .then(Commands.literal("say")
//                .then(Commands.argument("Name", StringArgumentType.string())
//                .then(Commands.argument("Message", StringArgumentType.string())
//                .executes(this::sendMessage))))
//
                .then(Commands.literal("change")
                .then(Commands.argument("Name", StringArgumentType.word())
                .then(Commands.argument("Resource", StringArgumentType.word())
                .then(Commands.argument("Glow", BoolArgumentType.bool())
                .executes(this::changeRes)))))
//
//                .then(Commands.literal("name")                                              // - /actor name ...
//                .then(Commands.argument("Name",StringArgumentType.word())                   // - /actor name "Name" ...
//                .then(Commands.argument("Boolean", BoolArgumentType.bool())                 // - /actor name "Name" "Boolean"
//                .executes(this::setVisibility))))
//
                        .then(Commands.literal("look-position")
                        .then(Commands.argument("Name", StringArgumentType.string())
                        .then(Commands.argument("Position", Vec3ArgumentType.vec3())
                        .executes(ctx -> lookPosition(ctx, StringArgumentType.getString(ctx, "Name"), Vec3ArgumentType.getVec3(ctx, "Position"))))))
                        .then(Commands.literal("look-actor")
                        .then(Commands.argument("Name", StringArgumentType.string())
                        .then(Commands.argument("Target", StringArgumentType.string())
                        .executes(ctx -> lookActor(ctx, StringArgumentType.getString(ctx, "Name"), StringArgumentType.getString(ctx, "Target"))))))
                        .then(Commands.literal("look-entity")
                        .then(Commands.argument("Name", StringArgumentType.string())
                        .then(Commands.argument("Entity", EntityArgument.entity())
                        .executes(ctx -> lookEntity(ctx, StringArgumentType.getString(ctx, "Name"), EntityArgument.getEntity(ctx, "Entity"))))))
                        .then(Commands.literal("look-type")
                        .then(Commands.argument("Name", StringArgumentType.string())
                        .then(Commands.argument("Target", ResourceLocationArgument.id())
                        .executes(ctx -> lookType(ctx, StringArgumentType.getString(ctx, "Name"), ResourceLocationArgument.getId(ctx, "Target"))))))
//
//                .then(Commands.literal("animation")
//                .then(Commands.argument("Name", StringArgumentType.word())
//                .then(Commands.argument("Action", StringArgumentType.string())
//                .then(Commands.argument("Emote", StringArgumentType.string())
//                .then(Commands.argument("Look", StringArgumentType.string())
//                .then(Commands.argument("Additional", StringArgumentType.string())
//                .executes(this::setAnimation)))))))
//
                .then(Commands.literal("eliminate")                                         // - /actor eliminate ...
                .then(Commands.argument("Name", StringArgumentType.word())                  // - /actor eliminate "Name" ...
                .executes(this::eliminate))));
    }

    public int listing(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        Player player = command.getSource().getPlayer();
        if (player != null){
            player.sendSystemMessage(Component.literal(NPCMapper.getActors()).withStyle(ChatFormatting.BLUE));
        }
        return 1;
    }

    public int changeRes(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        CustomFablazeEntity entity = NPCMapper.getActorByName(StringArgumentType.getString(command,"Name"));
        entity.changeResource(StringArgumentType.getString(command, "Resource"), BoolArgumentType.getBool(command, "Glow"));
        System.out.println("Resource changed: " + StringArgumentType.getString(command, "Resource"));
        return 1;
    }

    public int resendTarged(CommandContext<CommandSourceStack> command) throws CommandSyntaxException{
        CustomFablazeEntity entity = NPCMapper.getActorByName(StringArgumentType.getString(command,"Name"));
        Vec3 vector = Vec3Argument.getVec3(command,"Coordinates");

        entity.setTarget(vector,1d);


        return 1;
    }
    public int lookPosition(CommandContext<CommandSourceStack> ctx, String actorName, Vec3 position) throws CommandSyntaxException {
        CustomFablazeEntity actor = NPCMapper.getActorByName(actorName);
        if (actor != null) {
            actor.setLookPos(position);
            return 1;
        }
        return 0;
    }

    public int lookActor(CommandContext<CommandSourceStack> ctx, String actorName, String targetActorName) throws CommandSyntaxException {
        CustomFablazeEntity actor = NPCMapper.getActorByName(actorName);
        CustomFablazeEntity targetActor = NPCMapper.getActorByName(targetActorName);
        if (actor != null && targetActor != null) {
            actor.setLookAt(targetActor);
            return 1;
        }
        return 0;
    }

    public int lookEntity(CommandContext<CommandSourceStack> ctx, String actorName, Entity targetEntity) throws CommandSyntaxException {
        CustomFablazeEntity actor = NPCMapper.getActorByName(actorName);
        if (actor != null && targetEntity != null) {
            actor.setLookAt(targetEntity);
            return 1;
        }
        return 0;
    }

    public int lookType(CommandContext<CommandSourceStack> ctx, String actorName, ResourceLocation resourceLocation) throws CommandSyntaxException {
        CustomFablazeEntity actor = NPCMapper.getActorByName(actorName);
        if (actor != null) {
            actor.setLookType(resourceLocation);
            return 1;
        }
        return 0;
    }
    public int eliminate(CommandContext<CommandSourceStack> command) throws CommandSyntaxException {
        String actorName = StringArgumentType.getString(command, "Name");
        CustomFablazeEntity entity = NPCMapper.getActorByName(actorName);
        if (entity != null) {
            NPCMapper.deleteActorFromList(actorName);
            entity.remove(Entity.RemovalReason.KILLED);
            return 1;
        }
        return 0;
    }
}