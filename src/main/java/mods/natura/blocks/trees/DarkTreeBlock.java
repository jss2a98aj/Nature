package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.Natura;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DarkTreeBlock extends Block
{
    public IIcon[] icons;
    public String[] textureNames = {"darkwood_bark", "darkwood_heart", "fusewood_bark", "fusewood_heart"};

    public DarkTreeBlock()
    {
        super(Material.wood);
        this.setHardness(3.5F);
        this.setResistance(40F);
        this.setStepSound(Block.soundTypeWood);
        this.setCreativeTab(Natura.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int metadata)
    {
        int tex = metadata % 4 * 2;
        int orientation = metadata / 4;

        //Ends of logs
        return icons[tex + orientation == 0 && (side == 0 || side == 1)
        		           || orientation == 1 && (side == 4 || side == 5)
        		           || orientation == 2 && (side == 2 || side == 3) ? 1 : 0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
            this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i]);
    }

    @Override
    public Item getItemDropped (int par1, Random par2Random, int par3)
    {
        return Item.getItemFromBlock(this);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock (World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        byte b0 = 4;
        int j1 = b0 + 1;

        if (par1World.checkChunksExist(par2 - j1, par3 - j1, par4 - j1, par2 + j1, par3 + j1, par4 + j1))
            for (int k1 = -b0; k1 <= b0; ++k1)
                for (int l1 = -b0; l1 <= b0; ++l1)
                    for (int i2 = -b0; i2 <= b0; ++i2)
                    {
                        Block j2 = par1World.getBlock(par2 + k1, par3 + l1, par4 + i2);

                        if (j2 != null)
                            j2.beginLeavesDecay(par1World, par2 + k1, par3 + l1, par4 + i2);
                    }
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    @Override
    public int onBlockPlaced (World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int j1 = par9 & 3;
        byte b0 = 0;

        switch (par5)
        {
        case 2:
        case 3:
            b0 = 8;
            break;
        case 4:
        case 5:
            b0 = 4;
        }

        return j1 | b0;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped (int par1)
    {
        return limitToValidMetadata (par1);
    }

    /**
     * returns a number between 0 and 3
     */
    public static int limitToValidMetadata (int par0)
    {
        return par0 & 3;
    }

    @Override
    protected ItemStack createStackedBlock (int par1)
    {
        return new ItemStack(this, 1, limitToValidMetadata(par1));
    }

    @Override
    public void onBlockHarvested (World world, int x, int y, int z, int meta, EntityPlayer player)
    {
        if (meta % 4 == 1)
        {
            if (world.difficultySetting.getDifficultyId() > 2)
                world.createExplosion(null, x, y, z, 1.75f, false);
            else
                world.createExplosion(null, x, y, z, 2f, false);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < icons.length / 2; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }

    @Override
    public boolean canSustainLeaves (IBlockAccess world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public boolean isWood (IBlockAccess world, int x, int y, int z)
    {
        return true;
    }
}
