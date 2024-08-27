package net.fabricmc.example;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TutorialModelLoadingPlugin implements ModelLoadingPlugin {
  public static final ModelIdentifier FOUR_SIDED_FURNACE_MODEL = new ModelIdentifier(Identifier.of("tutorial", "four_sided_furnace"), "");
  public static final ModelIdentifier FOUR_SIDED_FURNACE_MODEL_ITEM = new ModelIdentifier(Identifier.of("tutorial", "four_sided_furnace"), "inventory");

  @Override
  public void onInitializeModelLoader(Context pluginContext) {
    // We want to add our model when the models are loaded
    pluginContext.modifyModelOnLoad().register((original, context) -> {
      // This is called for every model that is loaded, so make sure we only target ours
      final ModelIdentifier id = context.topLevelId();
      if (id != null && (id.equals(FOUR_SIDED_FURNACE_MODEL) || id.equals(FOUR_SIDED_FURNACE_MODEL_ITEM))) {
        return new FourSidedFurnaceModel();
      } else {
        // If we don't modify the model we just return the original as-is
        return original;
      }
    });
  }
}