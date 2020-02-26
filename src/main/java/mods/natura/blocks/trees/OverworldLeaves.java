package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NContent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class OverworldLeaves extends NLeaves
{
    public OverworldLeaves()
    {
        super();
        this.setBlockName("RareLeaves");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        String[] textureNames = {"maple", "silverbell", "purpleheart", "tiger"};
        this.fastIcons = new IIcon[textureNames.length];
        this.fancyIcons = new IIcon[textureNames.length];

        for (int i = 0; i < this.fastIcons.length; i++)
        {
            this.fastIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_leaves_fast");
            this.fancyIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_leaves_fancy");
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier (IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z) % 4;
        if (meta == 0)
            return 0xcc5718;

        /*if (meta == 2)
        {
            return 0x451941;
        }*/

        if (meta == 2)
        {
            int i1 = 0;
            int j1 = 0;
            int k1 = 0;

            for (int l1 = -1; l1 <= 1; ++l1)
                for (int i2 = -1; i2 <= 1; ++i2)
                {
                    int j2 = world.getBiomeGenForCoords(x + i2, z + l1).getBiomeFoliageColor(x, y, z);
                    i1 += (j2 & 16711680) >> 16;
                    j1 += (j2 & 65280) >> 8;
                    k1 += j2 & 255;
                }

            return ((i1 / 9 & 255) << 16 | (j1 / 9 & 255) << 8 | k1 / 9 & 255) + 0x222222;
        }

        return 0xffffff;
    }

    @Override
    public boolean isOpaqueCube ()
    {
    	return Blocks.leaves.isOpaqueCube();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int metadata)
    {
        int meta = metadata % 4;

        return (Blocks.leaves.isOpaqueCube() ? fastIcons : fancyIcons)[meta];
    }

    @Override
    public Item getItemDropped (int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(NContent.rareSapling);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for (int i = 0; i < 4; i++)
    		par3List.add(new ItemStack(par1, 1, i));
    }

    @Override
    public int getLightOpacity (IBlockAccess world, int x, int y, int z)
    {
        return this.getLightOpacity();
    }
}
