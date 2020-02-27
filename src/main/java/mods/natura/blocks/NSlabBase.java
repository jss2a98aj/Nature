package mods.natura.blocks;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NContent;
import mods.natura.items.blocks.PlankSlabItem;
import mods.natura.items.blocks.PlankSlabItemExt;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class NSlabBase extends NBlock {
    int startingMeta;

    public NSlabBase(Material material, float hardness, Block model, int meta, int totalSize) {
        super(material, hardness, model, -1, -1, totalSize);
        this.startingMeta = meta;
        this.stepSound = Block.soundTypeWood;
        this.setBlockName("plankSlab");
    }

    @Override
    public void addCollisionBoxesToList (World world, int x, int y, int z, AxisAlignedBB axisalignedbb, List arraylist, Entity entity) {
        setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, entity);
    }

    @Override
    public void setBlockBoundsForItemRender () {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    @Override
    public void setBlockBoundsBasedOnState (IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z) / 8;
        float minY = meta == 1 ? 0.5F : 0.0F;
        float maxY = meta == 1 ? 1.0F : 0.5F;
        setBlockBounds(0.0F, minY, 0F, 1.0F, maxY, 1.0F);
    }

    @Override
    public int onBlockPlaced (World wolrd, int blockX, int blockY, int blockZ, int side, float clickX, float clickY, float clickZ, int metadata) {
        return side == 0 || clickY >= 0.5F && side != 1 ? metadata | 8 : metadata;
    }

    @Override
    public boolean isOpaqueCube () {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock () {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta) {
        return this.modelBlock.getIcon(side, meta % 8 + startingMeta);
    }

    @Override
    public int damageDropped (int meta) {
        return meta % 8;
    }

	@Override
	public void reg() {
		//Because of a messy code base this shit happened.
		switch(i) {
		case 1:
			GameRegistry.registerBlock(this, PlankSlabItem.class, "plankSlab" + i, (i - 1) * 8);
			GameRegistry.addRecipe(new ItemStack(this, 6, 0), "###", '#', new ItemStack(NContent.planks, 1, 0));
	    	GameRegistry.addRecipe(new ItemStack(this, 6, 1), "###", '#', new ItemStack(NContent.planks, 1, 1));
	    	GameRegistry.addRecipe(new ItemStack(this, 6, 2), "###", '#', new ItemStack(NContent.planks, 1, 2));
	    	GameRegistry.addRecipe(new ItemStack(this, 6, 3), "###", '#', new ItemStack(NContent.planks, 1, 3));
	    	GameRegistry.addRecipe(new ItemStack(this, 6, 4), "###", '#', new ItemStack(NContent.planks, 1, 4));
	    	GameRegistry.addRecipe(new ItemStack(this, 6, 5), "###", '#', new ItemStack(NContent.planks, 1, 5));
	    	GameRegistry.addRecipe(new ItemStack(this, 6, 6), "###", '#', new ItemStack(NContent.planks, 1, 6));
	    	GameRegistry.addRecipe(new ItemStack(this, 6, 7), "###", '#', new ItemStack(NContent.planks, 1, 7));
	    	OreDictionary.registerOre("slabWood", new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE));
	    	break;
		case 2:
			GameRegistry.registerBlock(this, PlankSlabItemExt.class, "plankSlab" + i, (i - 1) * 8);
			GameRegistry.addRecipe(new ItemStack(this, 6, 0), "###", '#', new ItemStack(NContent.planks, 1, 8));
	    	GameRegistry.addRecipe(new ItemStack(this, 6, 1), "###", '#', new ItemStack(NContent.planks, 1, 9));
	    	GameRegistry.addRecipe(new ItemStack(this, 6, 2), "###", '#', new ItemStack(NContent.planks, 1, 10));
	    	GameRegistry.addRecipe(new ItemStack(this, 6, 3), "###", '#', new ItemStack(NContent.planks, 1, 11));
	    	GameRegistry.addRecipe(new ItemStack(this, 6, 4), "###", '#', new ItemStack(NContent.planks, 1, 12));
	    	OreDictionary.registerOre("slabWood", new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE));
		}
		i++;
	}

	static int i = 1;

}
