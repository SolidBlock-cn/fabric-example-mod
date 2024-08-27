package net.fabricmc.example;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class TutorialBlocks {
  // 新的方块
  public static final Block LAVA_BLOCK = register("lava_block", new Block(AbstractBlock.Settings.create().luminance(c -> 15).hardness(1)));
  public static final StairsBlock LAVA_STAIRS = register("lava_stairs", new StairsBlock(LAVA_BLOCK.getDefaultState(), AbstractBlock.Settings.copy(LAVA_BLOCK)));
  public static final SlabBlock LAVA_SLAB = register("lava_slab", new SlabBlock(AbstractBlock.Settings.create().luminance(x -> 15).hardness(1)));
  public static final Block WATER_LOGGABLE_GLASS = register("water_loggable_glass", new WaterLoggableGlass(AbstractBlock.Settings.copy(Blocks.GLASS)));
  public static final Block MIX_BLOCK = register("mix_block", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
  public static final TransparentBlock DIAMOND_PICKAXE_BLOCK = register("diamond_pickaxe_block", new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
  // 新的带有状态的方块
  public static final HuajiBlock HUAJI = register("huaji", new HuajiBlock(AbstractBlock.Settings.create().strength(0.2f).nonOpaque()));
  // 新的熔岩半砖
  public static final DemoBlock DEMO_BLOCK = registerBlockOnly("demo_block", new DemoBlock(AbstractBlock.Settings.create()));

  public static final Block FOUR_SIDED_FURNACE = register("four_sided_furnace", new Block(AbstractBlock.Settings.copy(Blocks.FURNACE).luminance(x -> 15)));

  // 动态改变方块颜色
  public static final ColorBlock COLOR_BLOCK = register("color_block", new ColorBlock(AbstractBlock.Settings.create()));

  public static final BoxBlock BOX_BLOCK = register("box_block", new BoxBlock(AbstractBlock.Settings.create()));

  public static final CropBlock CUSTOM_CROP = registerBlockOnly("custom_crop", new CustomCropBlock(AbstractBlock.Settings.create().nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));


  private static <T extends Block> T register(String path, T block) {
    Registry.register(Registries.BLOCK, Identifier.of("tutorial", path), block);
    Registry.register(Registries.ITEM, Identifier.of("tutorial", path), new BlockItem(block, new Item.Settings()));
    return block;
  }

  private static <T extends Block> T registerBlockOnly(String path, T block) {
    return Registry.register(Registries.BLOCK, Identifier.of("tutorial", path), block);
  }

  public static void initialize() {

  }
}
