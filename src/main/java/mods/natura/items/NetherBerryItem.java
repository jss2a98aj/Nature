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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class NetherBerryItem extends ItemFood
{
    public IIcon[] icons;
    public String[] textureNames = new String[] { "blight", "dusk", "sky", "sting" };//, "haste"

    public NetherBerryItem(int heal)
    {
        super(heal, 0.4F, false);
        setHasSubtypes(true);
        setMaxDamage(0);
        this.setCreativeTab(Natura.tab);
        //this.setAlwaysEdible();
    }

    @Override
    public ItemStack onItemRightClick (ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
        if (player.canEat(true) && player.getFoodStats().getSaturationLevel() < 18F)
            player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));

        return par1ItemStack;
    }

    @Override
    protected void onFoodEaten (ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            int duration = 0;
            PotionEffect potion;
            switch (stack.getItemDamage())
            {
            case 0:
                potion = player.getActivePotionEffect(Potion.regeneration);
                if (potion != null)
                    duration = potion.getDuration();
                player.addPotionEffect(new PotionEffect(Potion.regeneration.id, duration + 8 * 20, 0));

                if (Natura.random.nextFloat() < 0.75f)
                {
                    potion = player.getActivePotionEffect(Potion.poison);
                    if (potion != null)
                        duration = potion.getDuration();
                    else
                        duration = 0;
                    player.addPotionEffect(new PotionEffect(Potion.poison.id, duration + 5 * 20, 0));
                }
                if (Natura.random.nextFloat() < 0.15f)
                {
                    potion = player.getActivePotionEffect(Potion.wither);
                    if (potion != null)
                        duration = potion.getDuration();
                    else
                        duration = 0;
                    player.addPotionEffect(new PotionEffect(Potion.wither.id, duration + 5 * 20, 0));
                }
                break;

            case 1:
                potion = player.getActivePotionEffect(Potion.nightVision);
                if (potion != null)
                    duration = potion.getDuration();
                player.addPotionEffect(new PotionEffect(Potion.nightVision.id, duration + 15 * 20, 0));

                if (Natura.random.nextFloat() < 0.75f)
                {
                    potion = player.getActivePotionEffect(Potion.blindness);
                    if (potion != null)
                        duration = potion.getDuration();
                    else
                        duration = 0;
                    player.addPotionEffect(new PotionEffect(Potion.blindness.id, duration + 3 * 20, 0));
                }
                break;

            case 2:
                potion = player.getActivePotionEffect(Potion.jump);
                if (potion != null)
                    duration = potion.getDuration();
                player.addPotionEffect(new PotionEffect(Potion.jump.id, duration + 8 * 20, 0));

                if (Natura.random.nextFloat() < 0.75f)
                {
                    potion = player.getActivePotionEffect(Potion.moveSlowdown);
                    if (potion != null)
                        duration = potion.getDuration();
                    else
                        duration = 0;
                    player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration + 3 * 20, 0));
                }
                break;

            case 3:
                potion = player.getActivePotionEffect(Potion.damageBoost);
                if (potion != null)
                    duration = potion.getDuration();
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, duration + 10 * 20, 0));

                if (Natura.random.nextFloat() < 0.75f)
                {
                    potion = player.getActivePotionEffect(Potion.digSlowdown);
                    if (potion != null)
                        duration = potion.getDuration();
                    else
                        duration = 0;
                    player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, duration + 10 * 20, 0));
                }
                break;

            case 4:
                potion = player.getActivePotionEffect(Potion.digSpeed);
                if (potion != null)
                    duration = potion.getDuration();
                player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, duration + 10 * 20, 0));

                if (Natura.random.nextFloat() < 0.75f)
                {
                    potion = player.getActivePotionEffect(Potion.weakness);
                    if (potion != null)
                        duration = potion.getDuration();
                    else
                        duration = 0;
                    player.addPotionEffect(new PotionEffect(Potion.weakness.id, duration + 10 * 20, 0));
                }
                break;
            }
        }
    }

    @Override
    public int getMaxItemUseDuration (ItemStack itemstack)
    {
        return 24;
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
        list.add(StatCollector.translateToLocal("tooltip.netherberrybush1"));
        switch (stack.getItemDamage() % 4)
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.netherberrybush2"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.netherberrybush3"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.netherberrybush4"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.netherberrybush5"));
            break;
        }
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
