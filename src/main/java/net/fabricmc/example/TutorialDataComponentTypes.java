package net.fabricmc.example;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class TutorialDataComponentTypes {
  public static final ComponentType<Integer> NUMBER = register("number", builder -> builder
      .codec(Codec.INT)
      .packetCodec(PacketCodecs.INTEGER));

  public static <T> ComponentType<T> register(String path, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
    return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of("tutorial", path), builderOperator.apply(ComponentType.builder()).build());
  }

  public static void initialize() {
  }
}
