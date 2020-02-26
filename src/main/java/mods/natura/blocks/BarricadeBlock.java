package mods.natura.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.client.BarricadeRender;
import mods.natura.items.blocks.BarricadeItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BarricadeBlock extends NBlock
{
	String name;

	public BarricadeBlock(Block model, int meta, String name)
	{
		super(Material.wood, 4.0F, model, meta, 2, 1);
		this.setBlockName("barricade");

		this.name = name;
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
    public int getRenderType ()
    {
        return BarricadeRender.model;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool (World par1World, int x, int y, int z)
    {
        return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
    }

    @Override
    public void setBlockBoundsBasedOnState (IBlockAccess par1IBlockAccess, int x, int y, int z)
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void harvestBlock (World world, EntityPlayer player, int x, int y, int z, int meta)
    {
        if (meta % 4 > 0)
            world.setBlock(x, y, z, this, meta - 1, 3);
        dropBlockAsItem(world, x, y, z, new ItemStack(this));
    }

    @Override
    public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        ItemStack stack = player.getCurrentEquippedItem();
        if (stack != null && stack.getItem() == Item.getItemFromBlock(this) && !player.isSneaking())
        {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta % 4 != 3)
            {
                world.setBlock(x, y, z, this, meta + 1, 3);
                this.onBlockPlacedBy(world, x, y, z, player, stack);
                this.onPostBlockPlaced(world, x, y, z, meta);

                Block var9 = this;
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, var9.stepSound.soundName, (var9.stepSound.getVolume() + 1.0F) / 2.0F, var9.stepSound.getPitch() * 0.8F);
                player.swingItem();
                if (!player.capabilities.isCreativeMode)
                    stack.stackSize -= 1;

                return true;
            }
        }
        return false;
    }

    @Override
    public void onBlockExploded (World world, int x, int y, int z, Explosion explosion)
    {
        double distance = Math.abs (x - explosion.explosionX + y - explosion.explosionY + z - explosion.explosionZ);
        double power = explosion.explosionSize * 2 / distance;
        int meta = world.getBlockMetadata(x, y, z);
        int trueMeta = meta % 4 - (int)power;
        if (trueMeta < 0)
            world.setBlock(x, y, z, Blocks.air, 0, 0);
        else
            world.setBlockMetadataWithNotify(x, y, z, (int) (meta - power), 3);
        onBlockDestroyedByExplosion(world, x, y, z, explosion);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered (IBlockAccess world, int x, int y, int z, int side)
    {
        return true;
    }

	@Override
	public void reg()
	{
		GameRegistry.registerBlock(this, BarricadeItem.class, "barricade." + name);
	}

	@Override
	public void regRecipe()
	{
		if (this.block != null)
			GameRegistry.addRecipe(new ItemStack(this, 4), "b", 'b', new ItemStack(this.block, 1));
		else
			GameRegistry.addRecipe(new ItemStack(this, 1), "b", "b", 'b', new ItemStack(this.modelBlock, 1, this.meta));
	}

	public BarricadeBlock setRecipeArg(Block block)
	{
		this.block = block;
		return this;
	}

	Block block = null;
}
