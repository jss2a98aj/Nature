package mods.natura.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.Natura;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BerryItem extends ItemFood
{
    public IIcon[] icons;
    public String[] textureNames = {"rasp", "blue", "black", "geo"};

    public BerryItem(int heal)
    {
        super(heal, 0.4F, false);
        setHasSubtypes(true);
        setMaxDamage(0);
        this.setCreativeTab(Natura.tab);
        this.setAlwaysEdible();
    }

    @Override
    public ItemStack onItemRightClick (ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
        if (player.canEat(true) && player.getFoodStats().getSaturationLevel() < 18F)
            player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));

        return par1ItemStack;
    }

    @Override
    public int getMaxItemUseDuration (ItemStack itemstack)
    {
        return 16;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage (int meta)
    {
        return icons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
            this.icons[i] = iconRegister.registerIcon("natura:berry_" + textureNames[i]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("tooltip.berry"));
    }

    /* Name override */
    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return (new StringBuilder()).append("item.berry.").append(textureNames[itemstack.getItemDamage()]).toString();
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 4; ++var4)
            par3List.add(new ItemStack(par1, 1, var4));
    }
}
