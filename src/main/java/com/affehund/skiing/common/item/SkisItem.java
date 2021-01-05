package com.affehund.skiing.common.item;

import java.util.List;
import java.util.function.Predicate;

import com.affehund.skiing.common.entity.SkisEntity;
import com.affehund.skiing.common.entity.SkisEntity.SkisType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SkisItem extends Item {
	private static final Predicate<Entity> field_219989_a = EntityPredicates.NOT_SPECTATING
			.and(Entity::canBeCollidedWith);

	private static final String NBT_TYPE = "Type";

	public SkisItem(Item.Properties properties) {
		super(properties);
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
		if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
			return ActionResult.resultPass(itemstack);
		} else {
			Vector3d vector3d = playerIn.getLook(1.0F);
			List<Entity> list = worldIn.getEntitiesInAABBexcluding(playerIn,
					playerIn.getBoundingBox().expand(vector3d.scale(5.0D)).grow(1.0D), field_219989_a);
			if (!list.isEmpty()) {
				Vector3d vector3d1 = playerIn.getEyePosition(1.0F);

				for (Entity entity : list) {
					AxisAlignedBB axisalignedbb = entity.getBoundingBox()
							.grow((double) entity.getCollisionBorderSize());
					if (axisalignedbb.contains(vector3d1)) {
						return ActionResult.resultPass(itemstack);
					}
				}
			}

			if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
				SkisEntity entity = new SkisEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y,
						raytraceresult.getHitVec().z);
				entity.setSkisType(getSkisType(itemstack));
				entity.rotationYaw = playerIn.rotationYaw;
				if (!worldIn.hasNoCollisions(entity, entity.getBoundingBox().grow(-0.1D))) {
					return ActionResult.resultFail(itemstack);
				} else {
					if (!worldIn.isRemote) {
						worldIn.addEntity(entity);
						if (!playerIn.abilities.isCreativeMode) {
							itemstack.shrink(1);
						}
					}
					playerIn.addStat(Stats.ITEM_USED.get(this));
					return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
				}
			} else {
				return ActionResult.resultPass(itemstack);
			}
		}
	}

	public static SkisEntity.SkisType getSkisType(ItemStack stack) {
		try {
			return SkisEntity.SkisType.valueOf(stack.getOrCreateTag().getString(NBT_TYPE));
		} catch (Exception e) {
			return SkisType.ACACIA;
		}

	}

	public void setSkisType(ItemStack stack, String type) {
		stack.getOrCreateTag().putString(NBT_TYPE, type);
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (isInGroup(group)) {
			for (SkisEntity.SkisType type : SkisEntity.SkisType.values()) {
				ItemStack stack = new ItemStack(this);
				setSkisType(stack, type.name());
				items.add(stack);
			}
		}
	}

//	@Override
//	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
//		String type = getSkisType(stack).name().toLowerCase();
//		String string = "Type: " + type.substring(0, 1).toUpperCase() + type.substring(1);
//		tooltip.add(new StringTextComponent(string));
//	}
}