package mods.natura.plugins;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mods.natura.Natura;
import mods.natura.blocks.crops.CropBlock;
import mods.natura.plugins.waila.NaturaCropDataProvider;

public class Waila implements IPluginBase
{
    static final String modId = "Waila";

    @Override
    public String name ()
    {
      return modId;
    }

    @Override
	public boolean loadable ()
    {
    	return Loader.isModLoaded(modId);
    }

    @Override
    @Optional.Method (modid = modId)
    public void preInit (FMLPreInitializationEvent evt)
    {
    }

    @Override
    @Optional.Method (modid = modId)
    public void init (FMLInitializationEvent evt)
    {
        Natura.logger.info("Waila detected.");
        FMLInterModComms.sendMessage(modId, "register", "mods.natura.plugins.waila.Waila.wailaCallback");
    }

    @Override
    @Optional.Method (modid = modId)
    public void postInit (FMLPostInitializationEvent evt)
    {
    }

    @Optional.Method (modid = modId)
    public static void wailaCallback (IWailaRegistrar registrar) {
        IWailaDataProvider cropProvider = new NaturaCropDataProvider();

        registrar.registerStackProvider(cropProvider, CropBlock.class);
        registrar.registerBodyProvider(cropProvider, CropBlock.class);
    }
}
