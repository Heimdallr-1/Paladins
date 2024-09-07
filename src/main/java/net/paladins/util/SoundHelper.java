package net.paladins.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.paladins.PaladinsMod;
import net.paladins.entity.BarrierEntity;
import net.paladins.item.Shields;
import net.paladins.item.armor.PaladinArmor;
import net.paladins.item.armor.PriestArmor;

import java.util.List;
import java.util.Map;

public class SoundHelper {
    public static final SoundEvent divineProtectionImpact = SoundEvent.of(Identifier.of(PaladinsMod.ID, "divine_protection_impact"));

    public static List<String> soundKeys = List.of(
            "divine_protection_release",
            "judgement_impact",
            "divine_protection_impact",
            "battle_banner_release",
            "battle_banner_presence",
            "holy_shock_damage",
            "holy_shock_heal",
            "holy_beam_start_casting",
            "holy_beam_casting",
            "holy_beam_damage",
            "holy_beam_heal",
            "holy_beam_release"
    );

    public static Map<String, Float> soundDistances = Map.of(
        "judgement_impact", Float.valueOf(48F)
    );

    public static void registerSounds() {
        for (var soundKey: soundKeys) {
            var soundId = Identifier.of(PaladinsMod.ID, soundKey);
            var customTravelDistance = soundDistances.get(soundKey);
            var soundEvent = (customTravelDistance == null)
                    ? SoundEvent.of(soundId)
                    : SoundEvent.of(soundId, customTravelDistance);
            Registry.register(Registries.SOUND_EVENT, soundId, soundEvent);
        }
    }

    public record Entry(Identifier id, SoundEvent sound, RegistryEntry<SoundEvent> entry) {}
    private static Entry registerSound(String key) {
        var soundId = Identifier.of(PaladinsMod.ID, key);
        var event = SoundEvent.of(soundId);
        var entry = Registry.registerReference(Registries.SOUND_EVENT, soundId, event);
        return new Entry(soundId, event, entry);
    }
    public static final Entry paladin_armor_equip = registerSound("plate_equip");
    public static final Entry priest_robe_equip = registerSound("cloth_equip");
    public static final Entry shield_equip = registerSound("shield_equip");
    public static final Entry holy_barrier_activate = registerSound("holy_barrier_activate");
    public static final Entry holy_barrier_idle = registerSound("holy_barrier_idle");
    public static final Entry holy_barrier_impact = registerSound("holy_barrier_impact");
    public static final Entry holy_barrier_deactivate = registerSound("holy_barrier_deactivate");

    public static void playSoundEvent(World world, Entity entity, SoundEvent soundEvent) {
        playSoundEvent(world, entity, soundEvent, 1, 1);
    }

    public static void playSoundEvent(World world, Entity entity, SoundEvent soundEvent, float volume, float pitch) {
        world.playSound(
                (PlayerEntity)null,
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                soundEvent,
                SoundCategory.PLAYERS,
                volume,
                pitch);
    }
}
