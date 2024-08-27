package net.fabricmc.example;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class TutorialEnchantments {
  public static final RegistryKey<Enchantment> FROST = of("frost");

  private static RegistryKey<Enchantment> of(String name) {
    return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("tutorial", name));
  }

  public static void initialize() {
  }
}
