package net.fabricmc.example;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ChargeableBlock extends Block {
  public static final BooleanProperty CHARGED = BooleanProperty.of("charged");

  // The block instance. You can place it anywhere. Make the class is initialized.
  public static final ChargeableBlock CHARGEABLE_BLOCK = new ChargeableBlock(AbstractBlock.Settings.copy(Blocks.STONE));

  public ChargeableBlock(Settings settings) {
    super(settings);
  }
  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(CHARGED);
  }

  @Override
  protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 1);
    world.setBlockState(pos, state.with(CHARGED, true));
    return ActionResult.SUCCESS;
  }
  @Override
  public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
    if (world.getBlockState(pos).get(CHARGED)){
      // Summoning the Lighting Bolt at the block
      LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
      lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos));
      world.spawnEntity(lightningEntity);
    }

    world.setBlockState(pos, state.with(CHARGED, false));
    super.onSteppedOn(world, pos, state, entity);
  }
}
