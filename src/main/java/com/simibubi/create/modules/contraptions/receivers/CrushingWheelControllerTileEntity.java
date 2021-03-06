package com.simibubi.create.modules.contraptions.receivers;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import com.simibubi.create.AllRecipes;
import com.simibubi.create.AllTileEntities;
import com.simibubi.create.CreateConfig;
import com.simibubi.create.foundation.block.SyncedTileEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class CrushingWheelControllerTileEntity extends SyncedTileEntity implements ITickableTileEntity {

	public static class Inventory extends RecipeWrapper {
		protected int processingDuration;
		protected boolean appliedRecipe;

		public Inventory() {
			super(new ItemStackHandler(10));
		}

		@Override
		public void clear() {
			super.clear();
			processingDuration = 0;
			appliedRecipe = false;
		}

		public void write(CompoundNBT nbt) {
			NonNullList<ItemStack> stacks = NonNullList.create();
			for (int slot = 0; slot < inv.getSlots(); slot++) {
				ItemStack stack = inv.getStackInSlot(slot);
				stacks.add(stack);
			}
			ItemStackHelper.saveAllItems(nbt, stacks);
			nbt.putInt("ProcessingTime", processingDuration);
			nbt.putBoolean("AppliedRecipe", appliedRecipe);
		}

		public static Inventory read(CompoundNBT nbt) {
			Inventory inventory = new Inventory();
			NonNullList<ItemStack> stacks = NonNullList.withSize(10, ItemStack.EMPTY);
			ItemStackHelper.loadAllItems(nbt, stacks);

			for (int slot = 0; slot < stacks.size(); slot++)
				inventory.setInventorySlotContents(slot, stacks.get(slot));
			inventory.processingDuration = nbt.getInt("ProcessingTime");
			inventory.appliedRecipe = nbt.getBoolean("AppliedRecipe");

			return inventory;
		}

		public ItemStackHandler getItems() {
			return (ItemStackHandler) inv;
		}

	}

	private static DamageSource damageSource = new DamageSource("create.crush").setDamageBypassesArmor()
			.setDifficultyScaled();

	public Entity processingEntity;
	private UUID entityUUID;
	protected boolean searchForEntity;

	private Inventory contents;
	public float crushingspeed;

	public CrushingWheelControllerTileEntity() {
		super(AllTileEntities.CRUSHING_WHEEL_CONTROLLER.type);
		contents = new Inventory();
	}

	@Override
	public void tick() {
		if (isFrozen())
			return;
		if (searchForEntity) {
			searchForEntity = false;
			List<Entity> search = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(getPos()),
					e -> entityUUID.equals(e.getUniqueID()));
			if (search.isEmpty())
				clear();
			else
				processingEntity = search.get(0);
		}
		
		if (!isOccupied())
			return;
		if (crushingspeed == 0)
			return;
		
		float speed = crushingspeed / 2.5f;

		if (!hasEntity()) {

			float processingSpeed = speed / (!contents.appliedRecipe ? contents.getStackInSlot(0).getCount() : 1);
			contents.processingDuration -= processingSpeed;
			spawnParticles(contents.getStackInSlot(0));

			if (world.isRemote)
				return;

			if (contents.processingDuration < 20 && !contents.appliedRecipe) {
				applyRecipe();
				contents.appliedRecipe = true;
				world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2 | 16);
				return;
			}

			Vec3d outPos = new Vec3d(pos).add(.5, -.5, .5);
			if (contents.processingDuration <= 0) {
				for (int slot = 0; slot < contents.getSizeInventory(); slot++) {
					ItemStack stack = contents.getStackInSlot(slot);
					if (stack.isEmpty())
						continue;
					ItemEntity entityIn = new ItemEntity(world, outPos.x, outPos.y, outPos.z, stack);
					entityIn.setMotion(Vec3d.ZERO);
					world.addEntity(entityIn);
				}
				contents.clear();
				world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2 | 16);
				return;
			}

			return;
		}

		if (!processingEntity.isAlive()
				|| !processingEntity.getBoundingBox().intersects(new AxisAlignedBB(pos).grow(.5f))) {
			clear();
			return;
		}

		processingEntity.setMotion(new Vec3d(0, Math.max(-speed / 4f, -.5f), 0));

		if (world.isRemote)
			return;

		if (!(processingEntity instanceof ItemEntity)) {
			processingEntity.attackEntityFrom(damageSource, CreateConfig.parameters.crushingDamage.get());
			return;
		}

		ItemEntity itemEntity = (ItemEntity) processingEntity;
		if (processingEntity.posY < pos.getY() + .25f) {
			insertItem(itemEntity);
			itemEntity.remove();
			world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2 | 16);
		}

	}

	protected void spawnParticles(ItemStack stack) {
		if (stack == null || stack.isEmpty())
			return;

		IParticleData particleData = null;
		if (stack.getItem() instanceof BlockItem)
			particleData = new BlockParticleData(ParticleTypes.BLOCK,
					((BlockItem) stack.getItem()).getBlock().getDefaultState());
		else
			particleData = new ItemParticleData(ParticleTypes.ITEM, stack);

		Random r = world.rand;
		for (int i = 0; i < 4; i++)
			world.addParticle(particleData, pos.getX() + r.nextFloat(), pos.getY() + r.nextFloat(),
					pos.getZ() + r.nextFloat(), 0, 0, 0);
	}

	private void applyRecipe() {
		Optional<CrushingRecipe> recipe = world.getRecipeManager().getRecipe(AllRecipes.Types.CRUSHING, contents,
				world);

		if (recipe.isPresent()) {
			int rolls = contents.getStackInSlot(0).getCount();
			contents.clear();

			for (int roll = 0; roll < rolls; roll++) {
				List<ItemStack> rolledResults = recipe.get().rollResults();

				for (int i = 0; i < rolledResults.size(); i++) {
					ItemStack stack = rolledResults.get(i);

					for (int slot = 0; slot < contents.getSizeInventory(); slot++) {
						stack = contents.getItems().insertItem(slot, stack, false);

						if (stack.isEmpty())
							break;
					}
				}
			}

		} else {
			contents.clear();
		}

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		if (hasEntity() && !isFrozen())
			compound.put("Entity", NBTUtil.writeUniqueId(entityUUID));
		contents.write(compound);
		compound.putFloat("Speed", crushingspeed);

		return super.write(compound);
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);

		if (compound.contains("Entity") && !isFrozen() && !isOccupied()) {
			entityUUID = NBTUtil.readUniqueId(compound.getCompound("Entity"));
			this.searchForEntity = true;
		}
		crushingspeed = compound.getFloat("Speed");
		contents = Inventory.read(compound);

	}

	public void startCrushing(Entity entity) {
		processingEntity = entity;
		entityUUID = entity.getUniqueID();
	}

	private void insertItem(ItemEntity entity) {
		contents.clear();
		contents.setInventorySlotContents(0, entity.getItem());
		Optional<CrushingRecipe> recipe = world.getRecipeManager().getRecipe(AllRecipes.Types.CRUSHING, contents,
				world);

		contents.processingDuration = recipe.isPresent() ? recipe.get().getProcessingDuration() : 100;
		contents.appliedRecipe = false;
	}

	public void clear() {
		processingEntity = null;
		entityUUID = null;
	}

	public boolean isOccupied() {
		return hasEntity() || !contents.isEmpty();
	}

	public boolean hasEntity() {
		return processingEntity != null;
	}
	
	public static boolean isFrozen() {
		return CreateConfig.parameters.freezeCrushing.get();
	}

}
