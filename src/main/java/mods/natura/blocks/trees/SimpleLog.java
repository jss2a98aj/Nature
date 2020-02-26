package mods.natura.blocks.trees;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.Natura;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class SimpleLog extends Planks
{
    public String[] textureNames = new String[] { "redwood_bark", "redwood_heart", "redwood_root" };

    public SimpleLog()
    {
        super();
        this.setCreativeTab(Natura.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i]);
        }
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

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        int meat = meta;
        if (meat >= textureNames.length)
            meat = textureNames.length - 1;
        return icons[meat];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < 3; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }
}
