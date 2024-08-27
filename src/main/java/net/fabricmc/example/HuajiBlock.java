package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HuajiBlock extends Block {
    public static final BooleanProperty HARDENED = BooleanProperty.of("hardened");

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(HARDENED);
    }

    public HuajiBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HARDENED, false));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (state.get(HARDENED)) return ActionResult.PASS;
        world.setBlockState(pos, state.with(HARDENED, true));
        return ActionResult.SUCCESS;
    }
}
