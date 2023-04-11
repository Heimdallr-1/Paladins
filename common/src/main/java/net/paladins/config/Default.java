package net.paladins.config;

import net.spell_engine.api.item.ItemConfig;
import net.paladins.item.armor.Armors;
import net.paladins.item.Weapons;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Default {
    public final static ItemConfig itemConfig;
    public final static LootConfig lootConfig;
    public final static WorldGenConfig worldGenConfig;
    static {
        itemConfig = new ItemConfig();
        for (var weapon: Weapons.entries) {
            itemConfig.weapons.put(weapon.name(), weapon.defaults());
        }
        for (var armorSet: Armors.entries) {
            itemConfig.armor_sets.put(armorSet.name(), armorSet.defaults());
        }

        lootConfig = new LootConfig();
//        lootConfig.item_groups.put("wands_tier_0", new LootConfig.ItemGroup(List.of(
//                Weapons.noviceWand.id().toString()),
//                1
//        ));
//        lootConfig.item_groups.put("wands_tier_1", new LootConfig.ItemGroup(List.of(
//                Weapons.arcaneWand.id().toString(),
//                Weapons.fireWand.id().toString(),
//                Weapons.frostWand.id().toString()),
//                1
//        ).chance(0.3F));
//        lootConfig.item_groups.put("staves_tier_1", new LootConfig.ItemGroup(List.of(
//                Weapons.arcaneStaff.id().toString(),
//                Weapons.fireStaff.id().toString(),
//                Weapons.frostStaff.id().toString()),
//                1
////        ).chance(0.3F));
//        lootConfig.item_groups.put("robes_tier_1", new LootConfig.ItemGroup(joinLists(
//                Armors.paladinArmorSet.idStrings()),
//                1
//        ).chance(0.25F));
//        lootConfig.item_groups.put("robes_tier_2", new LootConfig.ItemGroup(joinLists(
//                Armors.arcaneRobeSet.idStrings(),
//                Armors.fireRobeSet.idStrings(),
//                Armors.frostRobeSet.idStrings()),
//                1
//        ).chance(0.5F));

        List.of("minecraft:chests/abandoned_mineshaft",
                        "minecraft:chests/igloo_chest",
                        "minecraft:chests/ruined_portal",
                        "minecraft:chests/shipwreck_supply",
                        "minecraft:chests/jungle_temple")
                .forEach(id -> lootConfig.loot_tables.put(id, List.of("wands_tier_0")));

        List.of("minecraft:chests/desert_pyramid",
                        "minecraft:chests/bastion_bridge",
                        "minecraft:chests/jungle_temple",
                        "minecraft:chests/pillager_outpost",
                        "minecraft:chests/simple_dungeon",
                        "minecraft:chests/stronghold_crossing")
                .forEach(id -> lootConfig.loot_tables.put(id, List.of("wands_tier_1")));

        List.of("minecraft:chests/bastion_other",
                        "minecraft:chests/nether_bridge",
                        "minecraft:chests/underwater_ruin_small")
                .forEach(id -> lootConfig.loot_tables.put(id, List.of("staves_tier_1")));

        List.of("minecraft:chests/shipwreck_treasure")
                .forEach(id -> lootConfig.loot_tables.put(id, List.of("robes_tier_1")));

        List.of("minecraft:chests/bastion_treasure",
                        "minecraft:chests/stronghold_library",
                        "minecraft:chests/underwater_ruin_big",
                        "minecraft:chests/woodland_mansion")
                .forEach(id -> lootConfig.loot_tables.put(id, List.of("staves_tier_1", "robes_tier_1")));

        List.of("minecraft:chests/end_city_treasure",
                        "minecraft:chests/ancient_city",
                        "minecraft:chests/stronghold_library")
                .forEach(id -> lootConfig.loot_tables.put(id, List.of("staves_tier_1", "robes_tier_2")));


        worldGenConfig = new WorldGenConfig();
        worldGenConfig.entries.addAll(List.of(
                new WorldGenConfig.Entry("minecraft:village/desert/houses", "paladins:village/desert/paladin_tower", 1),
                new WorldGenConfig.Entry("minecraft:village/desert/houses", "paladins:village/desert/paladin_tower_2", 4),
                new WorldGenConfig.Entry("minecraft:village/savanna/houses", "paladins:village/savanna/paladin_tower", 3),
                new WorldGenConfig.Entry("minecraft:village/plains/houses", "paladins:village/plains/paladin_tower", 4),
                new WorldGenConfig.Entry("minecraft:village/taiga/houses", "paladins:village/taiga/paladin_tower", 4),
                new WorldGenConfig.Entry("minecraft:village/snowy/houses", "paladins:village/snowy/paladin_tower", 1),
                new WorldGenConfig.Entry("minecraft:village/snowy/houses", "paladins:village/snowy/paladin_tower_2", 4)
        ));
    }

    @SafeVarargs
    private static <T> List<T> joinLists(List<T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
