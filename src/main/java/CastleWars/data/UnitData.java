package CastleWars.data;

import arc.Events;
import arc.struct.IntIntMap;
import arc.struct.IntMap;
import arc.struct.ObjectMap;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.units.Reconstructor;

public class UnitData {

    public static IntIntMap cost = new IntIntMap();
    public static IntIntMap cooldowns = new IntIntMap();

    public static void init() {
        // Ground
        cost.put(UnitTypes.dagger.id, 20);
        cost.put(UnitTypes.mace.id, 80);
        cost.put(UnitTypes.fortress.id, 120);
        cost.put(UnitTypes.scepter.id, 400);
        cost.put(UnitTypes.reign.id, 950);

        // GroundSupport
        cost.put(UnitTypes.nova.id, 30);
        cost.put(UnitTypes.pulsar.id, 80);
        cost.put(UnitTypes.quasar.id, 180);
        cost.put(UnitTypes.vela.id, 600);
        cost.put(UnitTypes.corvus.id, 1600);

        // Naval
        cost.put(UnitTypes.risso.id, 50);
        cost.put(UnitTypes.minke.id, 100);
        cost.put(UnitTypes.bryde.id, 80);
        cost.put(UnitTypes.sei.id, 500);
        cost.put(UnitTypes.omura.id, 2000);
        // Spiders
        cost.put(UnitTypes.crawler.id, 10);
        cost.put(UnitTypes.atrax.id, 50);
        cost.put(UnitTypes.spiroct.id, 100);
        cost.put(UnitTypes.arkyid.id, 600);
        cost.put(UnitTypes.toxopid.id, 1250);

        // Air ?xd
        cost.put(UnitTypes.flare.id, 25);
        cost.put(UnitTypes.horizon.id, 50);
        cost.put(UnitTypes.zenith.id, 250);
        cost.put(UnitTypes.antumbra.id, 2500);
        cost.put(UnitTypes.eclipse.id, 5000);

        // Support Air | lol?
        cost.put(UnitTypes.mono.id, 50);
        cost.put(UnitTypes.poly.id, 100);
        cost.put(UnitTypes.mega.id, 300);
        cost.put(UnitTypes.quad.id, 1500);
        cost.put(UnitTypes.oct.id, 10000);

        // Core
        cost.put(UnitTypes.alpha.id, 100);
        cost.put(UnitTypes.beta.id, 150);
        cost.put(UnitTypes.gamma.id, 250);

        // Block xd | oh no
        cost.put(UnitTypes.block.id, 100);

        for (UnitType[] u : ((Reconstructor)Blocks.additiveReconstructor).upgrades) {
            cooldowns.put(u[0].id, 0);
            cooldowns.put(u[1].id, 0);
        }

        for (UnitType[] u : ((Reconstructor)Blocks.exponentialReconstructor).upgrades) {
            cooldowns.put(u[0].id, 500);
            cooldowns.put(u[1].id, 1000);
        }

        for (UnitType[] u : ((Reconstructor)Blocks.tetrativeReconstructor).upgrades) {
            cooldowns.put(u[1].id, 5000);
        }

        Events.on(EventType.UnitDestroyEvent.class, event -> {
            if (cost.containsKey(event.unit.type.id) && event.unit.team == Team.purple) {
                Team t = event.unit.y > (Vars.world.height() / 2) ? Team.blue : Team.sharded;
                for (PlayerData data : PlayerData.datas.values()) {
                    if (data.player.team() == t && !event.unit.spawnedByCore) {
                        int m = get(event.unit.type);
                        data.money += m;
                        Call.label(data.player.con, "[lime]" + m, 0.5f, event.unit.x, event.unit.y);
                    }
                }
            }
        });
    }

    public static int get(UnitType type) {
        return cost.get(type.id);
    }

    public static int getCooldown(UnitType type) {
        return cooldowns.get(type.id);
    }
}
