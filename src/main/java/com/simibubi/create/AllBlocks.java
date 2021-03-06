package com.simibubi.create;

import com.simibubi.create.foundation.block.IBlockWithColorHandler;
import com.simibubi.create.foundation.block.IWithoutBlockItem;
import com.simibubi.create.foundation.block.ProperStairsBlock;
import com.simibubi.create.foundation.block.RenderUtilityAxisBlock;
import com.simibubi.create.foundation.block.RenderUtilityBlock;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.modules.IModule;
import com.simibubi.create.modules.contraptions.generators.MotorBlock;
import com.simibubi.create.modules.contraptions.generators.WaterWheelBlock;
import com.simibubi.create.modules.contraptions.receivers.CrushingWheelBlock;
import com.simibubi.create.modules.contraptions.receivers.CrushingWheelControllerBlock;
import com.simibubi.create.modules.contraptions.receivers.DrillBlock;
import com.simibubi.create.modules.contraptions.receivers.EncasedFanBlock;
import com.simibubi.create.modules.contraptions.receivers.HarvesterBlock;
import com.simibubi.create.modules.contraptions.receivers.MechanicalPressBlock;
import com.simibubi.create.modules.contraptions.receivers.TurntableBlock;
import com.simibubi.create.modules.contraptions.receivers.constructs.MechanicalBearingBlock;
import com.simibubi.create.modules.contraptions.receivers.constructs.MechanicalPistonBlock;
import com.simibubi.create.modules.contraptions.receivers.constructs.MechanicalPistonHeadBlock;
import com.simibubi.create.modules.contraptions.receivers.constructs.PistonPoleBlock;
import com.simibubi.create.modules.contraptions.receivers.constructs.RotationChassisBlock;
import com.simibubi.create.modules.contraptions.receivers.constructs.TranslationChassisBlock;
import com.simibubi.create.modules.contraptions.redstone.ContactBlock;
import com.simibubi.create.modules.contraptions.relays.ClutchBlock;
import com.simibubi.create.modules.contraptions.relays.CogWheelBlock;
import com.simibubi.create.modules.contraptions.relays.EncasedBeltBlock;
import com.simibubi.create.modules.contraptions.relays.EncasedShaftBlock;
import com.simibubi.create.modules.contraptions.relays.GearboxBlock;
import com.simibubi.create.modules.contraptions.relays.GearshiftBlock;
import com.simibubi.create.modules.contraptions.relays.ShaftBlock;
import com.simibubi.create.modules.contraptions.relays.ShaftHalfBlock;
import com.simibubi.create.modules.contraptions.relays.belt.BeltBlock;
import com.simibubi.create.modules.contraptions.relays.belt.BeltSupportBlock;
import com.simibubi.create.modules.curiosities.partialWindows.WindowInABlockBlock;
import com.simibubi.create.modules.curiosities.symmetry.block.CrossPlaneSymmetryBlock;
import com.simibubi.create.modules.curiosities.symmetry.block.PlaneSymmetryBlock;
import com.simibubi.create.modules.curiosities.symmetry.block.TriplePlaneSymmetryBlock;
import com.simibubi.create.modules.gardens.CocoaLogBlock;
import com.simibubi.create.modules.logistics.block.BeltFunnelBlock;
import com.simibubi.create.modules.logistics.block.EntityDetectorBlock;
import com.simibubi.create.modules.logistics.block.ExtractorBlock;
import com.simibubi.create.modules.logistics.block.FlexcrateBlock;
import com.simibubi.create.modules.logistics.block.LinkedExtractorBlock;
import com.simibubi.create.modules.logistics.block.RedstoneBridgeBlock;
import com.simibubi.create.modules.logistics.block.StockswitchBlock;
import com.simibubi.create.modules.logistics.block.diodes.FlexpeaterBlock;
import com.simibubi.create.modules.logistics.block.diodes.PulseRepeaterBlock;
import com.simibubi.create.modules.palettes.GlassPaneBlock;
import com.simibubi.create.modules.schematics.block.CreativeCrateBlock;
import com.simibubi.create.modules.schematics.block.SchematicTableBlock;
import com.simibubi.create.modules.schematics.block.SchematicannonBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IForgeRegistry;

public enum AllBlocks {

	__SCHEMATICS__(),
	SCHEMATICANNON(new SchematicannonBlock()),
	SCHEMATICANNON_CONNECTOR(new RenderUtilityBlock()),
	SCHEMATICANNON_PIPE(new RenderUtilityBlock()),
	CREATIVE_CRATE(new CreativeCrateBlock()),
	SCHEMATIC_TABLE(new SchematicTableBlock()),

	__CONTRAPTIONS__(),
	SHAFT(new ShaftBlock(Properties.from(Blocks.ANDESITE))),
	COGWHEEL(new CogWheelBlock(false)),
	LARGE_COGWHEEL(new CogWheelBlock(true)),
	ENCASED_SHAFT(new EncasedShaftBlock()),
	ENCASED_BELT(new EncasedBeltBlock()),
	CLUTCH(new ClutchBlock()),
	GEARSHIFT(new GearshiftBlock()),
	GEARBOX(new GearboxBlock()),
	BELT(new BeltBlock()),
	BELT_SUPPORT(new BeltSupportBlock()),
	BELT_PULLEY(new RenderUtilityAxisBlock()),
	BELT_ANIMATION(new RenderUtilityBlock()),
	MOTOR(new MotorBlock()),
	WATER_WHEEL(new WaterWheelBlock()),
	ENCASED_FAN(new EncasedFanBlock()),
	ENCASED_FAN_INNER(new RenderUtilityAxisBlock()),
	TURNTABLE(new TurntableBlock()),
	SHAFT_HALF(new ShaftHalfBlock()),
	CRUSHING_WHEEL(new CrushingWheelBlock()),
	CRUSHING_WHEEL_CONTROLLER(new CrushingWheelControllerBlock()),
	MECHANICAL_PRESS(new MechanicalPressBlock()),
	MECHANICAL_PRESS_HEAD(new MechanicalPressBlock.Head()),
	MECHANICAL_PISTON(new MechanicalPistonBlock(false)),
	STICKY_MECHANICAL_PISTON(new MechanicalPistonBlock(true)),
	MECHANICAL_PISTON_HEAD(new MechanicalPistonHeadBlock()),
	PISTON_POLE(new PistonPoleBlock()),
	MECHANICAL_BEARING(new MechanicalBearingBlock()),
	MECHANICAL_BEARING_TOP(new ShaftHalfBlock()),
	TRANSLATION_CHASSIS(new TranslationChassisBlock()),
	ROTATION_CHASSIS(new RotationChassisBlock()),
	DRILL(new DrillBlock()),
	HARVESTER(new HarvesterBlock()),

	__LOGISTICS__(),
	CONTACT(new ContactBlock()),
	REDSTONE_BRIDGE(new RedstoneBridgeBlock()),
	STOCKSWITCH(new StockswitchBlock()),
	FLEXCRATE(new FlexcrateBlock()),
	EXTRACTOR(new ExtractorBlock()),
	LINKED_EXTRACTOR(new LinkedExtractorBlock()),
	BELT_FUNNEL(new BeltFunnelBlock()),
	ENTITY_DETECTOR(new EntityDetectorBlock()),
	PULSE_REPEATER(new PulseRepeaterBlock()),
	FLEXPEATER(new FlexpeaterBlock()),
	FLEXPEATER_INDICATOR(new RenderUtilityBlock()),

	__CURIOSITIES__(),
	SYMMETRY_PLANE(new PlaneSymmetryBlock()),
	SYMMETRY_CROSSPLANE(new CrossPlaneSymmetryBlock()),
	SYMMETRY_TRIPLEPLANE(new TriplePlaneSymmetryBlock()),
	WINDOW_IN_A_BLOCK(new WindowInABlockBlock()),

	__GARDENS__(),
	COCOA_LOG(new CocoaLogBlock()),

	__PALETTES__(),
	TILED_GLASS(new GlassBlock(Properties.from(Blocks.GLASS))),
	TILED_GLASS_PANE(new GlassPaneBlock(Properties.from(Blocks.GLASS))),
	
	ANDESITE_BRICKS(new Block(Properties.from(Blocks.ANDESITE))),
	DIORITE_BRICKS(new Block(Properties.from(Blocks.DIORITE))),
	GRANITE_BRICKS(new Block(Properties.from(Blocks.GRANITE))),
	GABBRO(new Block(Properties.from(Blocks.GRANITE)), ComesWith.STAIRS, ComesWith.SLAB, ComesWith.WALL),
	POLISHED_GABBRO(new Block(Properties.from(GABBRO.block))),
	GABBRO_BRICKS(new Block(Properties.from(GABBRO.block)), ComesWith.STAIRS, ComesWith.WALL),
	PAVED_GABBRO_BRICKS(new Block(Properties.from(GABBRO.block)), ComesWith.SLAB),
	INDENTED_GABBRO(new Block(Properties.from(GABBRO.block)), ComesWith.SLAB),
	SLIGHTLY_MOSSY_GABBRO_BRICKS(new Block(Properties.from(GABBRO.block))),
	MOSSY_GABBRO_BRICKS(new Block(Properties.from(GABBRO.block))),
	LIMESAND(new FallingBlock(Properties.from(Blocks.SAND))),
	LIMESTONE(new Block(Properties.from(Blocks.SANDSTONE)), ComesWith.STAIRS, ComesWith.SLAB, ComesWith.WALL),
	LIMESTONE_BRICKS(new Block(Properties.from(LIMESTONE.block)), ComesWith.STAIRS, ComesWith.SLAB, ComesWith.WALL),
	POLISHED_LIMESTONE(new Block(Properties.from(LIMESTONE.block)), ComesWith.SLAB),
	LIMESTONE_PILLAR(new RotatedPillarBlock(Properties.from(LIMESTONE.block))),
	WEATHERED_LIMESTONE(new Block(Properties.from(Blocks.ANDESITE)), ComesWith.STAIRS, ComesWith.SLAB, ComesWith.WALL),
	WEATHERED_LIMESTONE_BRICKS(new Block(Properties.from(WEATHERED_LIMESTONE.block)), ComesWith.STAIRS, ComesWith.SLAB,
			ComesWith.WALL),
	POLISHED_WEATHERED_LIMESTONE(new Block(Properties.from(WEATHERED_LIMESTONE.block)), ComesWith.SLAB),
	WEATHERED_LIMESTONE_PILLAR(new RotatedPillarBlock(Properties.from(WEATHERED_LIMESTONE.block))),
	DOLOMITE(new Block(Properties.from(Blocks.QUARTZ_BLOCK)), ComesWith.STAIRS, ComesWith.SLAB, ComesWith.WALL),
	DOLOMITE_BRICKS(new Block(Properties.from(DOLOMITE.block))),
	POLISHED_DOLOMITE(new Block(Properties.from(DOLOMITE.block))),
	DOLOMITE_PILLAR(new RotatedPillarBlock(Properties.from(DOLOMITE.block))),

	;

	private enum ComesWith {
		WALL, FENCE, FENCE_GATE, SLAB, STAIRS;
	}

	private static class CategoryTracker {
		static IModule currentModule;
	}

	public Block block;
	public Block[] alsoRegistered;
	public IModule module;

	private AllBlocks() {
		CategoryTracker.currentModule = new IModule() {
			@Override
			public String getModuleName() {
				return Lang.asId(name()).replaceAll("__", "");
			}
		};
	}

	private AllBlocks(Block block, ComesWith... comesWith) {
		this.block = block;
		this.block.setRegistryName(Create.ID, Lang.asId(name()));
		this.module = CategoryTracker.currentModule;

		alsoRegistered = new Block[comesWith.length];
		for (int i = 0; i < comesWith.length; i++)
			alsoRegistered[i] = makeRelatedBlock(block, comesWith[i]);
	}

	public static void registerBlocks(IForgeRegistry<Block> registry) {
		for (AllBlocks block : values()) {
			if (block.get() == null)
				continue;

			registry.register(block.block);
			for (Block extra : block.alsoRegistered)
				registry.register(extra);
		}
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry) {
		for (AllBlocks block : values()) {
			if (block.get() == null)
				continue;
			if (block.get() instanceof IWithoutBlockItem)
				continue;

			registerAsItem(registry, block.get());
			for (Block extra : block.alsoRegistered)
				registerAsItem(registry, extra);
		}
	}

	private static void registerAsItem(IForgeRegistry<Item> registry, Block blockIn) {
		registry.register(
				new BlockItem(blockIn, AllItems.standardItemProperties()).setRegistryName(blockIn.getRegistryName()));
	}

	public Block get() {
		return block;
	}

	public boolean typeOf(BlockState state) {
		return state.getBlock() == block;
	}

	private Block makeRelatedBlock(Block block, ComesWith feature) {
		Properties properties = Properties.from(block);
		Block featured = null;

		switch (feature) {
		case FENCE:
			featured = new FenceBlock(properties);
			break;
		case SLAB:
			featured = new SlabBlock(properties);
			break;
		case STAIRS:
			featured = new ProperStairsBlock(block);
			break;
		case WALL:
			featured = new WallBlock(properties);
			break;
		case FENCE_GATE:
			featured = new FenceGateBlock(properties);
			break;
		default:
			return null;
		}

		return featured.setRegistryName(Create.ID,
				block.getRegistryName().getPath() + "_" + Lang.asId(feature.name()));
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerColorHandlers() {
		BlockColors blockColors = Minecraft.getInstance().getBlockColors();
		for (AllBlocks block : values()) {
			if (block.block instanceof IBlockWithColorHandler) {
				blockColors.register(((IBlockWithColorHandler) block.block).getColorHandler(), block.block);
			}
		}
	}

}
