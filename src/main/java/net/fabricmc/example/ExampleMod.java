package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ExampleMod implements ModInitializer {

  public static final ScreenHandlerType<? extends BoxScreenHandler> BOX_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of("tutorial", "box_block"), new ScreenHandlerType<>(BoxScreenHandler::new, FeatureSet.empty()));

  static {
    BlockRenderLayerMap.INSTANCE.putBlock(TutorialBlocks.MIX_BLOCK, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(TutorialBlocks.DIAMOND_PICKAXE_BLOCK, RenderLayer.getCutout());
  }

  @Override
  public void onInitialize() {
    TutorialItems.initialize();
    BlockRenderLayerMap.INSTANCE.putBlock(TutorialBlocks.WATER_LOGGABLE_GLASS, RenderLayer.getCutout());

    TutorialBlocks.initialize();
    TutorialItems.registerItemGroups();
    TutorialBlockEntityTypes.initialize();
    TutorialItemGroups.initialize();
    TutorialEnchantments.initialize();

    Registries.ITEM.get(Identifier.ofVanilla("mace"));
    Registries.ITEM.get(Identifier.ofVanilla("invalid_name"));
    Registries.ITEM.getOrEmpty(Identifier.ofVanilla("mace"));
    Registries.ITEM.getOrEmpty(Identifier.ofVanilla("invalid_name"));

    Registries.BLOCK.getId(Blocks.STONE);

    TutorialDataComponentTypes.initialize();
  }
}
