package net.fabricmc.example;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TutorialBlockEntityTypes {
  public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
    return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of("tutorial", path), blockEntityType);
  }

  public static final BlockEntityType<DemoBlockEntity> DEMO_BLOCK = register("demo_block", BlockEntityType.Builder.create(DemoBlockEntity::new, TutorialBlocks.DEMO_BLOCK).build());
  public static final BlockEntityType<ColorBlockEntity> COLOR_BLOCK = register("color_block", BlockEntityType.Builder.create(ColorBlockEntity::new, TutorialBlocks.COLOR_BLOCK).build());
  public static final BlockEntityType<BoxBlockEntity> BOX_BLOCK = register("box_block", BlockEntityType.Builder.create(BoxBlockEntity::new, TutorialBlocks.BOX_BLOCK).build());

  public static void initialize() {
  }
}
