package mods.natura.items.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class NLeavesDarkItem extends ItemBlock
{
    public static final String blockType[] = {"darkwood", "darkwood.flowering", "darkwood.fruit", "fusewood"};

    public NLeavesDarkItem(Block i)
    {
        super(i);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata (int md)
    {
        return md | 4;
    }

    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
    	return "block.leaves." + blockType[itemstack.getItemDamage() % blockType.length];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            list.add(StatCollector.translateToLocal("tooltip.darkwood1"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            list.add(StatCollector.translateToLocal("tooltip.darkwood2"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            list.add(StatCollector.translateToLocal("tooltip.fusewood.log"));
            break;
        }
    }
}
