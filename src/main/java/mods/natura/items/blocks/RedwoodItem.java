package mods.natura.items.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mantle.blocks.abstracts.MultiItemBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class RedwoodItem extends MultiItemBlock
{
    public static final String blockType[] = { "bark", "heart", "root", "", "", "", "", "", "", "", "", "", "", "", "", "" };

    public RedwoodItem(Block i)
    {
        super(i, "block", "redwood", blockType);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /*  @Override
      public String getUnlocalizedName (ItemStack itemstack)
      {
          return (new StringBuilder()).append(blockType[itemstack.getItemDamage()]).append("Redwood").toString();
      }*/

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.redwood1"));
            list.add(StatCollector.translateToLocal("tooltip.redwood2"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.redwood3"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.redwood4"));
            break;
        }
    }
}
