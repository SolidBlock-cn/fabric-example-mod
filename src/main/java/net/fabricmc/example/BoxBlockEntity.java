package net.fabricmc.example;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class BoxBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
  private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

  public BoxBlockEntity(BlockPos pos, BlockState state) {
    super(TutorialBlockEntityTypes.BOX_BLOCK, pos, state);
  }


  //From the ImplementedInventory Interface

  @Override
  public DefaultedList<ItemStack> getItems() {
    return inventory;
  }

  //These Methods are from the NamedScreenHandlerFactory Interface
  //createMenu creates the ScreenHandler itself
  //getDisplayName will Provide its name which is normally shown at the top

  @Override
  public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
    //We provide *this* to the screenHandler as our class Implements Inventory
    //Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
    return new Generic3x3ContainerScreenHandler(syncId, playerInventory, this);
  }

  @Override
  public Text getDisplayName() {
    // for 1.19+
    return Text.translatable(getCachedState().getBlock().getTranslationKey());
    // for earlier versions
    // return new TranslatableText(getCachedState().getBlock().getTranslationKey());
  }

  @Override
  protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.readNbt(nbt, registryLookup);
    Inventories.readNbt(nbt, this.inventory, registryLookup);
  }

  @Override
  protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.writeNbt(nbt, registryLookup);
    Inventories.writeNbt(nbt, this.inventory,registryLookup);
  }
}

