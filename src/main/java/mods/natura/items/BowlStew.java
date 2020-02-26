package mods.natura.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BowlStew extends ItemFood
{
    public BowlStew()
    {
        super(6, 0.6f, false);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses ()
    {
        return true;
    }

    @Override
    public IIcon getIcon (ItemStack stack, int renderPass)
    {
    	return renderPass == 0 ? Items.bowl.getIconFromDamage(0) : this.itemIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
    	this.itemIcon = iconRegister.registerIcon("natura:stew_glowshroom");
    }
    @Override
    public ItemStack onEaten (ItemStack stack, World world, EntityPlayer player)
    {
        player.getFoodStats().func_151686_a(this, stack);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(stack, world, player);

        if (!player.capabilities.isCreativeMode)
        {
            --stack.stackSize;
            if (stack.stackSize <= 0)
            	return new ItemStack(Items.bowl);

            player.inventory.addItemStackToInventory(new ItemStack(Items.bowl));
        }

        return stack;
    }

    @Override
    protected void onFoodEaten (ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            int duration;
            PotionEffect potion;

            potion = player.getActivePotionEffect(Potion.nightVision);
            duration = potion != null ? potion.getDuration() : 0;
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, duration + 45 * 25, 0));

            potion = player.getActivePotionEffect(Potion.weakness);
            duration = potion != null ? potion.getDuration() : 0;
            player.addPotionEffect(new PotionEffect(Potion.weakness.id, duration + 16 * 25, 0));

            potion = player.getActivePotionEffect(Potion.weakness);
            duration = potion != null ? potion.getDuration() : 0;
            player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, duration + 8 * 25, 0));
        }
    }
}
