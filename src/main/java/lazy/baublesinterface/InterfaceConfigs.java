package lazy.baublesinterface;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class InterfaceConfigs {

    public static ForgeConfigSpec CLIENT;

    public static ForgeConfigSpec.BooleanValue ENABLED;
    public static ForgeConfigSpec.BooleanValue VERTICAL;
    public static ForgeConfigSpec.EnumValue<InterfacePosition> INTERFACE_POSITION;
    public static ForgeConfigSpec.IntValue INTERFACE_MARGIN;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push(Ref.MOD_ID);
        ENABLED = builder.comment("Should this render or not.").define("enabled", true);
        VERTICAL = builder.comment("When enabled, the interface is set to vertical orientation.").define("vertical_orientation", true);
        INTERFACE_POSITION = builder.comment("Corner Position").defineEnum("interface_position", InterfacePosition.BOTTOM_LEFT);
        INTERFACE_MARGIN = builder.comment("Interface corner margins").defineInRange("interface_margins", 5, 0, Integer.MAX_VALUE);
        builder.pop();

        CLIENT = builder.build();

    }

    public static void registerAndLoadConfig() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT);
        CommentedFileConfig config = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(Ref.MOD_ID.concat("-client.toml"))).sync().writingMode(WritingMode.REPLACE).build();
        config.load();
        CLIENT.setConfig(config);
    }
}
