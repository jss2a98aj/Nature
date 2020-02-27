package mods.natura.items.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mantle.blocks.abstracts.MultiItemBlock;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class PlankSlabItemExt extends MultiItemBlock
{
    Block block;
    int addToMeta;

    public PlankSlabItemExt(Block id, Integer addToMeta)
    {
        super(id, "block.wood", "slab", NContent.woodTextureNames);
        this.block = id;
        this.addToMeta = addToMeta;

        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.tree9"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.tree10"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.tree11"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            break;
        case 4:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            list.add(StatCollector.translateToLocal("tooltip.fusewood.log"));
            break;
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        Block id = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        int trueMeta = meta % 8;
        boolean flag = id != null;

        if ((side == 1 && !flag || side == 0 && flag) && id == this.block && trueMeta == stack.getItemDamage() && world.setBlock(x, y, z, NContent.planks, trueMeta + addToMeta, 3))
        {
            world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, this.block.stepSound.getBreakSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getPitch() * 0.8F);
            --stack.stackSize;
            return true;
        }
        return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }
    
    public String getUnlocalizedName (ItemStack itemStack) {
    	switch(itemStack.getItemDamage()) {
    	case 0:
    		return "block.wood.purpleheart.slab";
    	case 1:
    		return "block.wood.tiger.slab";
    	case 2:
    		return "block.wood.willow.slab";
    	case 3:
    		return "block.wood.darkwood.slab";
    	case 4:
    		return "block.wood.fusewood.slab";
    	}
    	//Should NOT happen
    	return null;
    }

}

