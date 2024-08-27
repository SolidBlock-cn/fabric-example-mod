package net.fabricmc.example;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class TutorialItems {

  private TutorialItems() {}

  // an instance of our new item
  public static final Item CUSTOM_ITEM = register("custom_item", new Item(new Item.Settings()));
  public static final BlockItem DEMO_BLOCK = register("demo_block", new DemoBlockItem(TutorialBlocks.DEMO_BLOCK, new Item.Settings()));
  public static final Item CUSTOM_SEEDS = register("custom_seeds", new AliasedBlockItem(TutorialBlocks.CUSTOM_CROP, new Item.Settings()));

  public static <T extends Item> T register(String path, T item) {
    // For versions below 1.21, please replace ''Identifier.of'' with ''new Identifier''
    return Registry.register(Registries.ITEM, Identifier.of("tutorial", path), item);
  }

  public static void registerItemGroups() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
      content.addAfter(Items.OAK_DOOR, CUSTOM_ITEM);
    });
  }

  public static void initialize() {
  }
}