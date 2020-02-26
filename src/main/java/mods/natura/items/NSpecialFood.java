package mods.natura.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class NSpecialFood extends ItemFood
{
    int[] hunger;
    float[] saturation;
    String[] unlocalizedNames;
    String[] iconNames;
    IIcon[] icons;

    public NSpecialFood(int[] hunger, float[] saturation, String[] textureNames, String[] iconNames)
    {
        super(0, 0, false);
        this.hunger = hunger;
        this.saturation = saturation;
        this.unlocalizedNames = textureNames;
        this.iconNames = iconNames;
        this.setHasSubtypes(true);
    }

    @Override
    public int func_150905_g(ItemStack stack)
    {
        return hunger[stack.getItemDamage()];
    }

    @Override
    public float func_150906_h(ItemStack stack)
    {
        return saturation[stack.getItemDamage()];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage (int meta)
    {
        return icons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[iconNames.length];

        for (int i = 0; i < this.icons.length; ++i)
            this.icons[i] = iconRegister.registerIcon("natura:" + iconNames[i]);
    }

    @Override
    public String getUnlocalizedName (ItemStack stack)
    {
        return getUnlocalizedName() + "." + unlocalizedNames[MathHelper.clamp_int(stack.getItemDamage(), 0, unlocalizedNames.length)];
    }

    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < unlocalizedNames.length; i++)
            list.add(new ItemStack(id, 1, i));
    }
}
