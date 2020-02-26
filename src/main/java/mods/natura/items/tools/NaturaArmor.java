package mods.natura.items.tools;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.Natura;
import mods.natura.common.NContent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class NaturaArmor extends ItemArmor
{
    String itemTexture;
    String armorTexture;

    public NaturaArmor(ArmorMaterial material, int renderIndex, int slotType, String itemTexture, String armorTexture)
    {
        super(material, renderIndex, slotType);
        this.itemTexture = itemTexture;
        this.armorTexture = armorTexture;
        this.setCreativeTab(Natura.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("natura:armor_" + itemTexture);
    }

    @Override
    public String getArmorTexture (ItemStack stack, Entity entity, int slot, String type)
    {
    	return "natura:textures/armor/" + armorTexture + "_" + (slot == 2 ? "2" : "1") + ".png";
    }

    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        switch (armorType)
        {
        case 0:
            list.add(NContent.impHelmetStack.copy());
            break;
        case 1:
            list.add(NContent.impJerkinStack.copy());
            break;
        case 2:
            list.add(NContent.impLeggingsStack.copy());
            break;
        case 3:
            list.add(NContent.impBootsStack.copy());
            break;
        }
    }
}
