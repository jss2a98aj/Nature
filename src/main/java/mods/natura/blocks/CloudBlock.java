package mods.natura.blocks;

import mods.natura.Natura;
import mods.natura.common.NContent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CloudBlock extends NBlock
{
    public CloudBlock()
    {
        super(Natura.cloud, 0.3F, new String[] {"cloud_white", "cloud_gray", "cloud_dark", "cloud_sulfur"});
        this.setStepSound(soundTypeCloth);
        this.setBlockName("cloud");
    }

    @Override
    public void onEntityCollidedWithBlock (World world, int x, int y, int z, Entity entity)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 3 && entity instanceof EntityArrow && !world.isRemote)
        {
            EntityArrow entityarrow = (EntityArrow) entity;

            if (entityarrow.isBurning())
            {
                this.explode(world, x, y, z, 1, entityarrow.shootingEntity instanceof EntityLiving ? (EntityLiving) entityarrow.shootingEntity : null);
                world.setBlockToAir(x, y, z);
                return;
            }
        }

        if (entity.motionY < 0.0D)
            entity.motionY *= 0.005D;
        entity.fallDistance = 0.0F;
    }

    @Override
    public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 3 && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.flint_and_steel)
        {
            world.setBlockToAir(x, y, z);
            this.explode(world, x, y, z, 1, player);
            return true;
        }
        return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
    }

    /*
    @Override
    public void onBlockDestroyedByExplosion (World world, int x, int y, int z, Explosion par5Explosion)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 3)
        {
            this.explode(world, x, y, z, 1, null);
        }
    }*/

    public void explode (World world, int x, int y, int z, int size, EntityLivingBase living)
    {
        world.createExplosion(living, x, y, z, size, true);
    }

    @Override
    public boolean canDropFromExplosion (Explosion par1Explosion)
    {
        return false;
    }

    @Override
    public int getRenderBlockPass ()
    {
        return 1;
    }

    @Override
    public boolean shouldSideBeRendered (IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
        return iblockaccess.getBlock(x, y, z) == NContent.cloud ? false : super.shouldSideBeRendered(iblockaccess, x, y, z, side);
    }

    @Override
    public boolean renderAsNormalBlock ()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube ()
    {
        return false;
    }

    @Override
    public boolean isBlockSolid (IBlockAccess iblockaccess, int x, int y, int z, int l)
    {
        return iblockaccess.getBlock(x, y, z) == NContent.cloud ? false : super.isBlockSolid(iblockaccess, x, y, z, l);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool (World world, int x, int y, int z)
    {
    	return world.getBlock(x, y - 1, z) == NContent.cloud ? null : AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0D, y + 0.0625D, z + 1.0D);
    }

	@Override
	public void reg() {
		// TODO Auto-generated method stub
	}
}
