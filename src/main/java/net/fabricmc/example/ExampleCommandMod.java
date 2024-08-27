package net.fabricmc.example;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.ColorArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;

import java.util.Random;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.command.argument.ColorArgumentType.getColor;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ExampleCommandMod implements ModInitializer {

  public static void registerBroadcast(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(literal("broadcast")
        .requires(source -> source.hasPermissionLevel(2)) // Must be a game master to use the command. Command will not show up in tab completion or execute to non operators or any operator that is permission level 1.
        .then(argument("color", ColorArgumentType.color())
            .then(argument("message", greedyString())
                .executes(ctx -> broadcast(ctx.getSource(), getColor(ctx, "color"), getString(ctx, "message")))))); // You can deal with the arguments out here and pipe them into the command.
  }

  public static void registerGm(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(literal("gm")
        .requires(source -> source.hasPermissionLevel(2))
        .then(argument("mode", StringArgumentType.string()).executes(
            ctx -> {
              GameMode mode = null;
              switch (getString(ctx, "mode")) {
                case "0", "s", "survival" -> mode = GameMode.SURVIVAL;
                case "1", "c", "creative" -> mode = GameMode.CREATIVE;
                case "2", "a", "adventure" -> mode = GameMode.ADVENTURE;
                case "3", "sp", "spectator" -> mode = GameMode.SPECTATOR;
                default -> {
                }
              }
              ctx.getSource().getPlayerOrThrow().changeGameMode(mode);
              return 1;
            }
        )));

    dispatcher.register(literal("gmc")
        .requires(source -> source.hasPermissionLevel(2))
        .executes(ctx -> {
          ctx.getSource().getPlayerOrThrow().changeGameMode(GameMode.CREATIVE);
          return 1;
        })
    );

    dispatcher.register(literal("gms")
        .requires(source -> source.hasPermissionLevel(2))
        .executes(ctx -> {
          ctx.getSource().getPlayerOrThrow().changeGameMode(GameMode.SURVIVAL);
          return 1;
        })
    );

    dispatcher.register(literal("gma")
        .requires(source -> source.hasPermissionLevel(2))
        .executes(ctx -> {
          ctx.getSource().getPlayerOrThrow().changeGameMode(GameMode.ADVENTURE);
          return 1;
        })
    );

    dispatcher.register(literal("gmsp")
        .requires(source -> source.hasPermissionLevel(2))
        .executes(ctx -> {
          ctx.getSource().getPlayerOrThrow().changeGameMode(GameMode.SPECTATOR);
          return 1;
        })
    );


  }

  public static void registerCoinFlip(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(CommandManager.literal("coinflip")
        .executes(ctx -> {
          Random random = new Random();

          if (random.nextBoolean()) { // If heads succeed.
            ctx.getSource().sendFeedback(() -> Text.translatable("coin.flip.heads"), false);
            return Command.SINGLE_SUCCESS;
          }

          throw new SimpleCommandExceptionType(Text.translatable("coin.flip.tails")).create(); //
          // Oh no tails, you lose.
        }));
  }

  public static int broadcast(ServerCommandSource source, Formatting formatting, String message) throws CommandSyntaxException {
    final Text text = Text.literal(message).formatted(formatting);

    return Command.SINGLE_SUCCESS; // Success
  }

  public static void registerFabricTest(CommandDispatcher<ServerCommandSource> dispatcher) {
    LiteralCommandNode<ServerCommandSource> root = dispatcher.register(literal("fabric_test"));
    LiteralCommandNode<ServerCommandSource> root1 = dispatcher.register(literal("fabric_test")
        // You can register under the same literal more than once, it will just register new parts of the branch as shown below if you register a duplicate branch an error will popup in console warning of conflicting commands but one will still work.
        .then(literal("extra")
            .then(literal("long")
                .redirect(root)) // Return to root for chaining
            .then(literal("short")
                .redirect(root))) // Return to root for chaining
        .then(literal("command")
            .executes(ctx -> {
              ctx.getSource().sendFeedback(() -> Text.literal("Chainable Command"), false);
              return Command.SINGLE_SUCCESS;
            })));
  }

  //    public static int giveDiamond(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
//        final ServerCommandSource source = ctx.getSource();
//
//        final PlayerEntity self = source.getPlayer(); // If not a player than the command ends
//        if(!self.inventory.insertStack(new ItemStack(Items.DIAMOND))){
//            throw new SimpleCommandExceptionType(Text.translatable("inventory.isfull")).create();
//        }
//
//        return 1;
//    }
  @Override
  public void onInitialize() {
    CommandRegistrationCallback.EVENT.register((dispatcher, r, dedicated) -> {
      dispatcher.register(literal("foo").executes(context -> {
        System.out.println("foo");
        return 1;
      }));
      registerBroadcast(dispatcher);
      registerGm(dispatcher);
      registerCoinFlip(dispatcher);
      registerFabricTest(dispatcher);
    });

  }
}
