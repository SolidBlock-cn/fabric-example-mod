package net.fabricmc.example;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class DemoBlockEntity extends BlockEntity implements ImplementedInventory, SidedInventory {
  public int number = 0;
  private final DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);

  public DemoBlockEntity(BlockPos pos, BlockState state) {
    super(TutorialBlockEntityTypes.DEMO_BLOCK, pos, state);
  }

  @Override
  protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    nbt.putInt("number", number);
    Inventories.writeNbt(nbt, items, registryLookup);
    super.writeNbt(nbt, registryLookup);
  }

  @Override
  protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.readNbt(nbt, registryLookup);
    number = nbt.getInt("number");
    Inventories.readNbt(nbt, items, registryLookup);
  }

  @Nullable
  @Override
  public Packet<ClientPlayPacketListener> toUpdatePacket() {
    return BlockEntityUpdateS2CPacket.create(this);
  }

  @Override
  public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
    return createNbt(registryLookup);
  }
  public static void tick(World world, BlockPos pos, BlockState state, DemoBlockEntity be) {
  }

  @Override
  protected void readComponents(ComponentsAccess components) {
    super.readComponents(components);
    this.number = components.getOrDefault(TutorialDataComponentTypes.NUMBER, 0);
  }

  @Override
  protected void addComponents(ComponentMap.Builder componentMapBuilder) {
    super.addComponents(componentMapBuilder);
    componentMapBuilder.add(TutorialDataComponentTypes.NUMBER, number);
  }

  @Override
  public void removeFromCopiedStackNbt(NbtCompound nbt) {
    nbt.remove("number");
  }

  @Override
  public DefaultedList<ItemStack> getItems() {
    return items;
  }

  @Override
  public int[] getAvailableSlots(Direction side) {
    return IntStream.of(getItems().size()).toArray();
  }

  @Override
  public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
    return dir != Direction.UP;
  }

  @Override
  public boolean canExtract(int slot, ItemStack stack, Direction dir) {
    return true;
  }
}