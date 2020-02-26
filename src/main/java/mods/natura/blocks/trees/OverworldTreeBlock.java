package mods.natura.blocks.trees;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.Natura;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class OverworldTreeBlock extends BlockLog
{
    public IIcon[] icons;
    public String[] textureNames = new String[] { "maple_bark", "maple_heart", "silverbell_bark", "silverbell_heart", "purpleheart_bark", "purpleheart_heart", "tiger_bark", "tiger_heart" };

    public OverworldTreeBlock()
    {
        super();
        this.setHardness(2.0F);
        this.setStepSound(Block.soundTypeWood);
        this.setCreativeTab(Natura.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int metadata)
    {
        int tex = (metadata % 4) * 2;
        int orientation = metadata / 4;

        switch (orientation)
        //Ends of logs
        {
        case 0:
            if (side == 0 || side == 1)
                return icons[tex + 1];
            break;
        case 1:
            if (side == 4 || side == 5)
                return icons[tex + 1];
            break;
        case 2:
            if (side == 2 || side == 3)
                return icons[tex + 1];
            break;
        }

        return icons[tex];
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
    public ArrayList<ItemStack> getDrops (World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = Lists.newArrayList();
        ret.add(new ItemStack(this,1, metadata % 4));
        return ret;
    }

    @Override
    public int damageDropped(int meta)
    {
        return meta % 4;
    }

   /* public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return metadata % 4 != 2 ? blockFlammability[blockID] : 0;
    }

    public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return metadata % 4 != 2 ? blockFireSpreadSpeed[blockID] : 0;
    }*/

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < icons.length / 2; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }
}
