package net.fabricmc.example;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class TutorialScreen extends Screen {
  private final Screen parent;

  protected TutorialScreen(Screen parent) {
    super(Text.literal("My tutorial screen"));
    this.parent = parent;
  }


  public ButtonWidget button1 = ButtonWidget.builder(Text.literal("Button 1"), button -> {
        System.out.println("You clicked button1!");
      })
      .dimensions(width / 2 - 205, 20, 200, 20)
      .tooltip(Tooltip.of(Text.literal("Tooltip of button1")))
      .build();
  public ButtonWidget button2 = ButtonWidget.builder(Text.literal("Button 2"), button -> {
        System.out.println("You clicked button2!");
      })
      .dimensions(width / 2 + 5, 20, 200, 20)
      .tooltip(Tooltip.of(Text.literal("Tooltip of button2")))
      .build();

  @Override
  protected void init() {
    button1.setPosition(width / 2 - 205, 20);
    button2.setPosition(width / 2 + 5, 20);

    addDrawableChild(button1);
    addDrawableChild(button2);
  }

  @Override
  public void render(DrawContext context, int mouseX, int mouseY, float delta) {
    super.render(context, mouseX, mouseY, delta);
    context.drawCenteredTextWithShadow(textRenderer, Text.literal("You must see me"), width / 2, height / 2, 0xffffff);
    final MultilineText multilineText = MultilineText.create(textRenderer, Text.literal("The text is pretty long ".repeat(20)), width - 20);
    multilineText.drawWithShadow(context, 10, height / 2, 16, 0xffffff);
  }

  @Override
  public void close() {
    client.setScreen(parent);
  }
}
