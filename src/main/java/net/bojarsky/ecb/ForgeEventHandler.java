package net.bojarsky.ecb;

import net.dries007.tfc.common.capabilities.food.TFCFoodData;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(
        modid = "ecb",
        bus = Bus.FORGE
)
public final class ForgeEventHandler {

    public static Map<String, CompoundTag> racionmap = new HashMap<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            String playerName = player.getName().getString();

            TFCFoodData foodData = (TFCFoodData) player.getFoodData();

            CompoundTag nutritionData = new CompoundTag();
            nutritionData.put("nutrients", foodData.getNutrition().writeToNbt());

            racionmap.put(playerName, nutritionData);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        String playerName = player.getName().getString();
        if (racionmap.containsKey(playerName)) {
            CompoundTag savedNutritionData = racionmap.get(playerName);

            TFCFoodData currentFoodData = (TFCFoodData) player.getFoodData();

            currentFoodData.getNutrition().readFromNbt(savedNutritionData.getCompound("nutrients"));

            racionmap.remove(playerName);
        }
    }

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        LevelAccessor var2 = event.getLevel();
        if (var2 instanceof ServerLevel level) {
            Runnable t = () -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException var3) {
                    var3.printStackTrace();
                }

                ((GameRules.BooleanValue)level.getGameRules().getRule(GameRules.RULE_REDUCEDDEBUGINFO)).set(false, level.getServer());
                Ecb.LOGGER.info("!!! COORDS ARE RETURNED !!!");
            };
            Thread var3 = new Thread(t);
            var3.start();
        }
    }

}