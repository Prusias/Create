package com.simibubi.create;

import java.util.function.Supplier;

import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.modules.contraptions.base.ProcessingRecipeSerializer;
import com.simibubi.create.modules.contraptions.receivers.CrushingRecipe;
import com.simibubi.create.modules.contraptions.receivers.PressingRecipe;
import com.simibubi.create.modules.contraptions.receivers.SplashingRecipe;
import com.simibubi.create.modules.curiosities.placementHandgun.BuilderGunUpgradeRecipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;

public enum AllRecipes {

	BLOCKZAPPER_UPGRADE(BuilderGunUpgradeRecipe.Serializer::new, IRecipeType.CRAFTING),
	CRUSHING(() -> new ProcessingRecipeSerializer<>(CrushingRecipe::new), Types.CRUSHING),
	SPLASHING(() -> new ProcessingRecipeSerializer<>(SplashingRecipe::new), Types.SPLASHING),
	PRESSING(() -> new ProcessingRecipeSerializer<>(PressingRecipe::new), Types.PRESSING),

	;

	public static class Types {
		public static IRecipeType<CrushingRecipe> CRUSHING = register("crushing");
		public static IRecipeType<SplashingRecipe> SPLASHING = register("splashing");
		public static IRecipeType<PressingRecipe> PRESSING = register("pressing");

		static <T extends IRecipe<?>> IRecipeType<T> register(final String key) {
			return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new IRecipeType<T>() {
				public String toString() {
					return key;
				}
			});
		}
	}

	public IRecipeSerializer<?> serializer;
	public Supplier<IRecipeSerializer<?>> supplier;
	public IRecipeType<? extends IRecipe<? extends IInventory>> type;

	private AllRecipes(Supplier<IRecipeSerializer<?>> supplier,
			IRecipeType<? extends IRecipe<? extends IInventory>> type) {
		this.supplier = supplier;
		this.type = type;
	}

	public static void register(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		for (AllRecipes r : AllRecipes.values()) {
			r.serializer = r.supplier.get();
			ResourceLocation location = new ResourceLocation(Create.ID, Lang.asId(r.name()));
			event.getRegistry().register(r.serializer.setRegistryName(location));
		}
	}

}
