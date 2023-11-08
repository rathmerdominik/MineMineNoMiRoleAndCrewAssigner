package net.hammerclock.roleassigner;

import net.hammerclock.roleassigner.config.CommonConfig;
import net.hammerclock.roleassigner.event.RoleAssignerEvent;
import net.hammerclock.roleassigner.event.CrewAssignerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(RoleAssigner.PROJECT_ID)
public class RoleAssigner {
    public static final Logger LOGGER = LogManager.getLogger(RoleAssigner.PROJECT_ID);
    public static final String PROJECT_ID = "roleassigner";

    public RoleAssigner() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST,
                () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        ModLoadingContext context = ModLoadingContext.get();

        context.registerConfig(Type.COMMON, CommonConfig.CONFIG, "mmnm-roleassign-common.toml");
        
        MinecraftForge.EVENT_BUS.register(new CrewAssignerEvent());
        MinecraftForge.EVENT_BUS.register(new RoleAssignerEvent());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        LOGGER.info("Role Assigner Started!");
    }

}
