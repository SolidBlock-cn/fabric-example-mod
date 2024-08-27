package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class DemoBlockItem extends BlockItem {
  public DemoBlockItem(Block block, Settings settings) {
    super(block, settings);
  }

  @Override
  public Text getName(ItemStack stack) {
    final MutableText name = Text.translatable(stack.getTranslationKey());
    if (stack.contains(TutorialDataComponentTypes.NUMBER)) {
      name.append(" - number=" + stack.get(TutorialDataComponentTypes.NUMBER));
    }
    return name;
  }
}
