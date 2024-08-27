package net.fabricmc.example;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ColorBlock extends BlockWithEntity {
  public ColorBlock(Settings settings) {
    super(settings);
  }

  @Override
  protected MapCodec<? extends ColorBlock> getCodec() {
    return createCodec(ColorBlock::new);
  }

  @Override
  protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    if (stack.getItem() instanceof DyeItem dyeItem) {
      if (world.getBlockEntity(pos) instanceof ColorBlockEntity colorBlockEntity) {
        final int newColor = dyeItem.getColor().getEntityColor();
        final int originalColor = colorBlockEntity.color;
        colorBlockEntity.color = ColorHelper.Argb.averageArgb(newColor, originalColor);
        stack.decrementUnlessCreative(1, player);
        colorBlockEntity.markDirty();
        world.updateListeners(pos, state, state, 0);
      }
    }
    return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
  }

  @Nullable
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new ColorBlockEntity(pos, state);
  }

  @Override
  protected BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Override
  public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
    super.onPlaced(world, pos, state, placer, itemStack);
    if (!world.isClient) {
//      world.updateListeners(pos, state, state, 0);
    }
  }
}
