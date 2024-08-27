package net.fabricmc.example;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DemoBlock extends BlockWithEntity {
  public DemoBlock(Settings settings) {
    super(settings);
  }

  @Override
  protected MapCodec<? extends BlockWithEntity> getCodec() {
    return createCodec(DemoBlock::new);
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new DemoBlockEntity(pos, state);
  }

  @Override
  protected BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return validateTicker(type, TutorialBlockEntityTypes.DEMO_BLOCK, DemoBlockEntity::tick);
  }

  @Override
  protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    if (world.isClient) return ItemActionResult.SUCCESS;

    if (!(world.getBlockEntity(pos) instanceof DemoBlockEntity blockEntity)) {
      return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    if (!player.getStackInHand(hand).isEmpty()) {
      // Check what is the first open slot and put an item from the player's hand there
      if (blockEntity.getStack(0).isEmpty()) {
        // Put the stack the player is holding into the inventory
        blockEntity.setStack(0, player.getStackInHand(hand).copy());
        // Remove the stack from the player's hand
        player.getStackInHand(hand).setCount(0);
      } else if (blockEntity.getStack(1).isEmpty()) {
        blockEntity.setStack(1, player.getStackInHand(hand).copy());
        player.getStackInHand(hand).setCount(0);
      } else {
        // If the inventory is full we'll print its contents
        player.sendMessage(Text.literal("The inventory is full! The first slot holds ").append(blockEntity.getStack(0).getName()).append(" and the second slot holds ").append(blockEntity.getStack(1).getName()));
      }
    } else {
      // If the player is not holding anything we'll get give him the items in the block entity one by one

      // Find the first slot that has an item and give it to the player
      if (!blockEntity.getStack(1).isEmpty()) {
        // Give the player the stack in the inventory
        player.getInventory().offerOrDrop(blockEntity.getStack(1));
        // Remove the stack from the inventory
        blockEntity.removeStack(1);
      } else if (!blockEntity.getStack(0).isEmpty()) {
        player.getInventory().offerOrDrop(blockEntity.getStack(0));
        blockEntity.removeStack(0);
      } else {
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }
    }
    return ItemActionResult.SUCCESS;
  }

  @Override
  protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

    if (!world.isClient) {
      BlockEntity blockEntity = world.getBlockEntity(pos);
      if (blockEntity instanceof DemoBlockEntity demoBlockEntity) {
        demoBlockEntity.number++;
        player.sendMessage(Text.literal("Number is... " + demoBlockEntity.number), false);
        demoBlockEntity.markDirty();
        world.updateListeners(pos, state, state, 0);
      }
    }

    return ActionResult.SUCCESS;
  }

  @Override
  public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
    final Integer i = stack.get(TutorialDataComponentTypes.NUMBER);
    if (i == null) return;

    tooltip.add(Text.literal("Number: " + i).formatted(Formatting.GRAY));
  }

  @Override
  public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
    final ItemStack pickStack = super.getPickStack(world, pos, state);
    final BlockEntity blockEntity = world.getBlockEntity(pos);
    if (blockEntity instanceof DemoBlockEntity demoBlockEntity) {
      pickStack.applyComponentsFrom(demoBlockEntity.createComponentMap());
    }
    return pickStack;
  }
}
