package ua.iwaithi.fablaze.common.util;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ua.iwaithi.fablaze.common.client.GeoItemArmor;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS;
    public static final RegistryObject<Item> SHINOBI_HELMET;
    public static final RegistryObject<Item> SELENA_CHESTPLATE;
    public static final RegistryObject<Item> SELENA_LEGGINGS;

    public ModItems() {

    }

    public static void register(IEventBus eventBus) { ITEMS.register(eventBus); }

    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "fablaze");

        SHINOBI_HELMET = ITEMS.register("shinobi_helmet", () -> {
            return new GeoItemArmor(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, new Item.Properties(), "textures/armor/shinobi_helmet.png", "geo/shinobi_helmet.geo.json");
        });

        SELENA_CHESTPLATE = ITEMS.register("selena_chestplate", () -> {
            return new GeoItemArmor(ArmorMaterials.LEATHER, ArmorItem.Type.CHESTPLATE, new Item.Properties(), "textures/armor/selena.png", "geo/selena.geo.json");
        });

        SELENA_LEGGINGS = ITEMS.register("selena_leggings", () -> {
            return new GeoItemArmor(ArmorMaterials.LEATHER, ArmorItem.Type.LEGGINGS, new Item.Properties(), "textures/armor/selena.png", "geo/selena.geo.json");
        });

    }
}