package net.bojarsky.ecb;

import com.mojang.logging.LogUtils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;


@Mod(Ecb.MOD_ID)
public class Ecb {
    public static final String MOD_ID = "ecb";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Ecb() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Ecb.LOGGER.info("!!! ECB ENABLED !!!");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

}
