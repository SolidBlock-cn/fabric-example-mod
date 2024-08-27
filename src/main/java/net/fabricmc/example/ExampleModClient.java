package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.util.Identifier;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class ExampleModClient implements ClientModInitializer {
  public static void registerModelPredicateProviders() {
    // For versions before 1.21, replace 'Identifier.ofVanilla' with 'new Identifier'.
    ModelPredicateProviderRegistry.register(TutorialItems.CUSTOM_ITEM, Identifier.ofVanilla("pull"), (itemStack, clientWorld, livingEntity, seed) -> {
      if (livingEntity == null) {
        return 0.0F;
      }
      return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime(livingEntity) - livingEntity.getItemUseTimeLeft()) / 20.0F;
    });

    ModelPredicateProviderRegistry.register(TutorialItems.CUSTOM_ITEM, Identifier.ofVanilla("pulling"), (itemStack, clientWorld, livingEntity, seed) -> {
      if (livingEntity == null) {
        return 0.0F;
      }
      return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
    });
  }

  @Override
  public void onInitializeClient() {
    // ...
    registerModelPredicateProviders();

    ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && view.getBlockEntityRenderData(pos) instanceof Integer integer ? integer : 0x3495eb, TutorialBlocks.COLOR_BLOCK);
    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> Optional.ofNullable(stack.get(DataComponentTypes.BLOCK_ENTITY_DATA)).map(nbtComponent -> nbtComponent.copyNbt().getInt("color")).orElse(0x3495eb), TutorialBlocks.COLOR_BLOCK);

    ModelLoadingPlugin.register(new TutorialModelLoadingPlugin());

    BlockEntityRendererFactories.register(TutorialBlockEntityTypes.DEMO_BLOCK, DemoBlockEntityRenderer::new);
    HandledScreens.register(ExampleMod.BOX_SCREEN_HANDLER, BoxScreen::new);               BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), TutorialBlocks.CUSTOM_CROP);
  }
}
