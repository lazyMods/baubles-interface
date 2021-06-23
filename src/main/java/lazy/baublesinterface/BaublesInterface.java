package lazy.baublesinterface;

import net.minecraftforge.fml.common.Mod;

@Mod(Ref.MOD_ID)
public class BaublesInterface {

    public BaublesInterface() {
        InterfaceConfigs.registerAndLoadConfig();
    }
}