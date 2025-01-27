package com.affehund.skiing.common.block;

import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

/**
 * @author Affehund
 *
 */
public abstract class AbstractSkiRackBlock extends Block {
	public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;

	public AbstractSkiRackBlock(Properties properties) {
		super(properties.hardnessAndResistance(2.0f).harvestLevel(0).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
		this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH));
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

	@Override
	public abstract ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player,
			Hand hand, BlockRayTraceResult hit);

	@Override
	public abstract void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState,
			boolean isMoving);

	@Override
	public abstract void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer,
			ItemStack stack);

	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		return Container.calcRedstoneFromInventory((IInventory) worldIn.getTileEntity(pos));
	}

	private static VoxelShape getNorthSouthShape() {
		return Stream.of(Block.makeCuboidShape(0, 0, 1, 3, 2, 15), Block.makeCuboidShape(13, 0, 1, 16, 2, 15),
				Block.makeCuboidShape(1, 2, 5, 2, 16, 11), Block.makeCuboidShape(2, 2, 5, 14, 15, 11),
				Block.makeCuboidShape(14, 2, 5, 15, 16, 11)).reduce((v1, v2) -> {
					return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
				}).get();
	}

	private static VoxelShape getEastWestShape() {
		return Stream.of(Block.makeCuboidShape(1, 0, 13, 15, 2, 16), Block.makeCuboidShape(1, 0, 0, 15, 2, 3),
				Block.makeCuboidShape(5, 2, 14, 11, 16, 15), Block.makeCuboidShape(5, 2, 2, 11, 15, 14),
				Block.makeCuboidShape(5, 2, 1, 11, 16, 2)).reduce((v1, v2) -> {
					return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
				}).get();
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.get(DIRECTION) == Direction.NORTH || state.get(DIRECTION) == Direction.SOUTH)
			return getNorthSouthShape();
		else
			return getEastWestShape();
	}

	@Override
	public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
		return rotate(state, direction);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(DIRECTION, rot.rotate(state.get(DIRECTION)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return rotate(state, mirrorIn.toRotation(state.get(DIRECTION)));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(DIRECTION, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(DIRECTION);
	}
}
