package net.paladins.village;

import com.google.common.collect.ImmutableSet;
import net.fabric_extras.structure_pool.api.StructurePoolAPI;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.paladins.PaladinsMod;
import net.paladins.block.PaladinBlocks;
import net.paladins.item.Weapons;
import net.paladins.item.armor.Armors;
import net.paladins.item.armor.PaladinArmor;
import net.paladins.util.SoundHelper;
import net.runes.api.RuneItems;

import java.util.LinkedHashMap;
import java.util.List;

public class PaladinVillagers {
    public static final String PALADIN_MERCHANT = "monk";

    public static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(Identifier.of(PaladinsMod.ID, name),
                1, 10, ImmutableSet.copyOf(block.getStateManager().getStates()));
    }

    public static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> workStation) {
        var id = Identifier.of(PaladinsMod.ID, name);
        return Registry.register(Registries.VILLAGER_PROFESSION, Identifier.of(PaladinsMod.ID, name), new VillagerProfession(
                id.toString(),
                (entry) -> {
                    return entry.matchesKey(workStation);
                },
                (entry) -> {
                    return entry.matchesKey(workStation);
                },
                ImmutableSet.of(),
                ImmutableSet.of(),
                SoundHelper.paladin_armor_equip.sound())
        );
    }

//    private static class Offer {
//        int level;
//        ItemStack input;
//        ItemStack output;
//        int maxUses;
//        int experience;
//        float priceMultiplier;
//
//        public Offer(int level, ItemStack input, ItemStack output, int maxUses, int experience, float priceMultiplier) {
//            this.level = level;
//            this.input = input;
//            this.output = output;
//            this.maxUses = maxUses;
//            this.experience = experience;
//            this.priceMultiplier = priceMultiplier;
//        }
//
//        public static Offer buy(int level, ItemStack item, int price, int maxUses, int experience, float priceMultiplier) {
//            return new Offer(level, item, new ItemStack(Items.EMERALD, price), maxUses, experience, priceMultiplier);
//        }
//
//        public static Offer sell(int level, ItemStack item, int price, int maxUses, int experience, float priceMultiplier) {
//            return new Offer(level, new ItemStack(Items.EMERALD, price), item, maxUses, experience, priceMultiplier);
//        }
//    }

    public static void register() {
        StructurePoolAPI.injectAll(PaladinsMod.villageConfig.value);
        var poi = registerPOI(PALADIN_MERCHANT, PaladinBlocks.MONK_WORKBENCH);
        var profession = registerProfession(
                PALADIN_MERCHANT,
                RegistryKey.of(Registries.POINT_OF_INTEREST_TYPE.getKey(), Identifier.of(PaladinsMod.ID, PALADIN_MERCHANT)));

//        List<Offer> paladinMerchantOffers = List.of(
//                Offer.sell(1, new ItemStack(RuneItems.get(RuneItems.RuneType.HEALING), 8), 2, 128, 1, 0.01f),
//                Offer.sell(1, Weapons.acolyte_wand.item().getDefaultStack(), 4, 12, 5, 0.1f),
//                Offer.sell(1, Weapons.wooden_great_hammer.item().getDefaultStack(), 8, 12, 8, 0.1f),
//                Offer.buy(2, new ItemStack(Items.WHITE_WOOL, 5), 8, 12, 8, 0.0f),
//                Offer.buy(2, new ItemStack(Items.IRON_INGOT, 6), 9, 12, 8, 0.0f),
//                Offer.buy(2, new ItemStack(Items.CHAIN, 6), 3, 12, 8, 0.0f),
//                Offer.buy(2, new ItemStack(Items.GOLD_INGOT, 6), 9, 12, 8, 0.0f),
//                Offer.sell(2, Weapons.holy_staff.item().getDefaultStack(), 12, 12, 10, 0.05f),
//                Offer.sell(2, Weapons.iron_great_hammer.item().getDefaultStack(), 12, 12, 10, 0.05f),
//                Offer.sell(3, Armors.paladinArmorSet_t1.head.getDefaultStack(), 15, 12, 13, 0.05f),
//                Offer.sell(3, Armors.paladinArmorSet_t1.feet.getDefaultStack(), 15, 12, 13, 0.05f),
//                Offer.sell(3, Armors.priestArmorSet_t1.head.getDefaultStack(), 15, 12, 13, 0.05f),
//                Offer.sell(3, Armors.priestArmorSet_t1.feet.getDefaultStack(), 15, 12, 13, 0.05f),
//                Offer.sell(4, Armors.paladinArmorSet_t1.chest.getDefaultStack(), 20, 12, 15, 0.05f),
//                Offer.sell(4, Armors.paladinArmorSet_t1.legs.getDefaultStack(), 20, 12, 15, 0.05f),
//                Offer.sell(4, Armors.priestArmorSet_t1.chest.getDefaultStack(), 20, 12, 15, 0.05f),
//                Offer.sell(4, Armors.priestArmorSet_t1.legs.getDefaultStack(), 20, 12, 15, 0.05f)
//            );

        LinkedHashMap<Integer, List<TradeOffers.Factory>> trades = new LinkedHashMap<>();
        trades.put(1, List.of(
                new TradeOffers.SellItemFactory(RuneItems.get(RuneItems.RuneType.HEALING), 2, 8, 128, 1, 0.01f),
                new TradeOffers.SellItemFactory(Weapons.acolyte_wand.item(), 4, 1, 12, 5),
                new TradeOffers.SellItemFactory(Weapons.wooden_great_hammer.item(), 8, 1, 12, 8)
        ));
        trades.put(2, List.of(
                new TradeOffers.BuyItemFactory(Items.WHITE_WOOL, 5, 12, 5, 8),
                new TradeOffers.BuyItemFactory(Items.IRON_INGOT, 6, 12, 5, 8),
                new TradeOffers.BuyItemFactory(Items.CHAIN, 6, 12, 5, 8),
                new TradeOffers.BuyItemFactory(Items.GOLD_INGOT, 6, 12, 5, 8)
        ));
        trades.put(3, List.of(
                new TradeOffers.SellItemFactory(Armors.paladinArmorSet_t1.head, 15, 1, 12, 13),
                new TradeOffers.SellItemFactory(Armors.paladinArmorSet_t1.feet, 15, 1, 12, 13),
                new TradeOffers.SellItemFactory(Armors.priestArmorSet_t1.head, 15, 1, 12, 13),
                new TradeOffers.SellItemFactory(Armors.priestArmorSet_t1.feet, 15, 1, 12, 13)
        ));
        trades.put(4, List.of(
                new TradeOffers.SellItemFactory(Armors.paladinArmorSet_t1.chest, 20, 1, 12, 15),
                new TradeOffers.SellItemFactory(Armors.paladinArmorSet_t1.legs, 20, 1, 12, 15),
                new TradeOffers.SellItemFactory(Armors.priestArmorSet_t1.chest, 20, 1, 12, 15),
                new TradeOffers.SellItemFactory(Armors.priestArmorSet_t1.legs, 20, 1, 12, 15)
        ));

        for (var entry: trades.entrySet()) {
            TradeOfferHelper.registerVillagerOffers(profession, entry.getKey(), factories -> {
                factories.addAll(entry.getValue());
            });
        }

        TradeOfferHelper.registerVillagerOffers(profession, 5, factories -> {
            factories.add(((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    Weapons.diamond_holy_staff.item(),
                    40,
                    3,
                    30,
                    0F).create(entity, random)
            ));
            factories.add(((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    Weapons.diamond_claymore.item(),
                    40,
                    3,
                    30,
                    0F).create(entity, random)
            ));
            factories.add(((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    Weapons.diamond_great_hammer.item(),
                    40,
                    3,
                    30,
                    0F).create(entity, random)
            ));
        });
    }
}
