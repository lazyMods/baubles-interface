package lazy.baublesinterface;

import com.mojang.datafixers.util.Pair;
import lazy.baubles.api.BaublesAPI;
import lazy.baubles.api.cap.IBaublesItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InterfaceRenderer {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent event) {
        if (!InterfaceConfigs.ENABLED.get()) return;
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            renderInterface(event.getWindow().getGuiScaledWidth(), event.getWindow().getGuiScaledHeight());
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void renderInterface(int gsWidth, int gsHeight) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            IBaublesItemHandler baublesInv = BaublesAPI.getBaublesHandler(player).orElseThrow(NullPointerException::new);

            int xOff = 0, yOff = 0;
            int size = 16 * 4;
            Pair<Integer, Integer> startPos = resolveStartPos(gsWidth, gsHeight, size);

            if (InterfaceConfigs.VERTICAL.get()) {
                yOff = 16;
            } else {
                xOff = 16;
            }

            for (int i = 0; i < 4; i++) {
                ItemStack stackInSlot = baublesInv.getStackInSlot(i);
                Minecraft.getInstance().getItemRenderer().renderGuiItem(stackInSlot, startPos.getFirst() + xOff * i, startPos.getSecond() + yOff * i);
                Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, stackInSlot, startPos.getFirst() + xOff * i, startPos.getSecond() + yOff * i);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static Pair<Integer, Integer> resolveStartPos(int gsWidth, int gsHeight, int size) {
        int startX = 0, startY = 0;
        boolean vertical = InterfaceConfigs.VERTICAL.get();
        int margin = InterfaceConfigs.INTERFACE_MARGIN.get();
        switch (InterfaceConfigs.INTERFACE_POSITION.get()) {
            case TOP_LEFT:
                startX = margin;
                startY = margin;
                break;
            case BOTTOM_LEFT:
                startX = margin;
                if (vertical) startY = gsHeight - size - margin;
                else startY = gsHeight - 16 - margin;
                break;
            case TOP_RIGHT:
                startY = margin;
                if (vertical) startX = gsWidth - 16 - margin;
                else startX = gsWidth - size - margin;
                break;
            case BOTTOM_RIGHT:
                if (vertical) {
                    startX = gsWidth - 16 - margin;
                    startY = gsHeight - size - margin;
                } else {
                    startX = gsWidth - size - margin;
                    startY = gsHeight - 16 - margin;
                }
                break;
        }
        return new Pair<>(startX, startY);
    }
}
