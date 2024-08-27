package net.fabricmc.example;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class TutorialItemGroups {
  public static final ItemGroup TEST_GROUP = FabricItemGroup.builder()
      .icon(() -> new ItemStack(TutorialItems.CUSTOM_ITEM))
      .displayName(Text.translatable("itemGroup.tutorial.test_group"))
      .entries((context, entries) -> {
        entries.add(TutorialItems.CUSTOM_ITEM);
        entries.add(TutorialItems.CUSTOM_SEEDS);
        entries.add(TutorialBlocks.LAVA_BLOCK);
        entries.add(TutorialBlocks.LAVA_STAIRS);
        entries.add(TutorialBlocks.LAVA_SLAB);
        entries.add(TutorialBlocks.WATER_LOGGABLE_GLASS);
        entries.add(TutorialBlocks.MIX_BLOCK);
        entries.add(TutorialBlocks.DIAMOND_PICKAXE_BLOCK);
        entries.add(TutorialBlocks.DEMO_BLOCK);
        entries.add(TutorialBlocks.FOUR_SIDED_FURNACE);
        entries.add(TutorialBlocks.COLOR_BLOCK);
        entries.add(TutorialBlocks.BOX_BLOCK);
      })
      .build();
  public static void initialize() {
    // Since 1.21:
    Registry.register(Registries.ITEM_GROUP, Identifier.of("tutorial", "test_group"), TEST_GROUP);
  }
}