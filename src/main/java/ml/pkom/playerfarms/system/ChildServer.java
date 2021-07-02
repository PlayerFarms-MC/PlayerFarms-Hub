package ml.pkom.playerfarms.system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import ml.pkom.playerfarms.PlayerFarms;
import ml.pkom.playerfarms.api.PlayerFarmsAPI;
import ml.pkom.playerfarms.classes.Language;
import ml.pkom.playerfarms.classes.PFPlayer;
import ml.pkom.playerfarms.classes.ServerInfo;

public class ChildServer {
    private File pluginFolder = PlayerFarms.getInstance().getDataFolder();
    private Player owner;

    private static List<UUID> containsPlayerUUIDs = new ArrayList<UUID>();
    private static List<ChildServer> containsServers = new ArrayList<ChildServer>();

    public static boolean existServer(Player player) {
        if (containsPlayerUUIDs.contains(player.getUniqueId())) {
            return true;
        } else {
            return false;
        }
    }

    public static ChildServer getPFPlayer(Player player) {
        if (!ChildServer.existServer(player)) {
            new ChildServer(player);
        }
        for (ChildServer server : containsServers) {
            if (server.owner.getUniqueId() == player.getUniqueId()) {
                return server;
            }
        }

        player.sendMessage("§cエラーが発生しました。何度も続く場合は運営へご報告ください。");
        return null;
    }

    public ChildServer(Player player){
        owner = player;
        containsPlayerUUIDs.add(player.getUniqueId());
        containsServers.add(this);
        if (!pluginFolder.exists())
            pluginFolder.mkdir();
        File serversFolder = new File(pluginFolder, "servers/");
        if (!serversFolder.exists())
            serversFolder.mkdir();
        File serverFolder = new File(serversFolder, player.getUniqueId() + "/");
        if (!serverFolder.exists())
            serverFolder.mkdir();
        File spigotFile = new File(pluginFolder, "spigot-1.12.2.jar");

        PlayerFarmsAPI.fileWriteContents(new File(serverFolder, "start.bat"), "cd " + serverFolder.getAbsolutePath()
                + "\njava -server -Xms1G -Xmx1G -jar \"" + spigotFile.getAbsolutePath() + "\" nogui");
        if (!new File(serverFolder, "eula.txt").exists()){
            PlayerFarmsAPI.fileWriteContents(new File(serverFolder, "eula.txt"), "eula=true");
            player.sendMessage(Language.getLangStr("messages.server_eula_agreed"));
        }
        PFPlayer pfPlayer = PFPlayer.getPFPlayer(player);
        ServerInfo sInfo = pfPlayer.getServerInfo();
        PlayerFarmsAPI.fileWriteContents(new File(serverFolder, "server.properties"),
            "view-distance=10" + "\n" +
            "max-build-height=256" + "\n" +
            "server-ip=" + "\n" +
            "level-seed=" + "\n" +
            "allow-nether=true" + "\n" +
            "enable-command-block=" + sInfo.cmdBlock + "\n" +
            "server-port=" + ServerInfo.port + "\n" +
            "gamemode=0" + "\n" +
            "enable-rcon=false" + "\n" +
            "op-permission-level=4" + "\n" +
            "enable-query=false" + "\n" +
            "prevent-proxy-connections=false" + "\n" +
            "generator-settings=" + "\n" +
            "resource-pack=" + "\n" +
            "player-idle-timeout=0" + "\n" +
            "level-name=world" + "\n" +
            "motd=" + sInfo.motd + "\n" +
            "server-name=" + sInfo.name + "\n" +
            "force-gamemode=false" + "\n" +
            "hardcore=false" + "\n" +
            "white-list=false" + "\n" +
            "pvp=true" + "\n" +
            "spawn-npcs=true" + "\n" +
            "generate-structures=true" + "\n" +
            "spawn-animals=true" + "\n" +
            "snooper-enabled=true" + "\n" +
            "difficulty=1" + "\n" +
            "network-compression-threshold=256" + "\n" +
            "level-type=" + sInfo.worldType + "\n" +
            "spawn-monsters=true" + "\n" +
            "max-players=" + sInfo.maxPlayer + "\n" +
            "resource-pack-sha1=" + "\n" +
            "online-mode=false" + "\n" +
            "allow-flight=" + !sInfo.flyKick + "\n" +
            "max-world-size=29999984" + "\n"
        );
        sInfo.startingPort = ServerInfo.port;
        ServerInfo.port++;
        if (!new File(serverFolder, "spigot.yml").exists()) {
            PlayerFarmsAPI.fileWriteContents(new File(serverFolder, "spigot.yml"), "config-version: 11" + "\n"
                    + "settings:" + "\n" + "  debug: false" + "\n" + "  save-user-cache-on-stop-only: false" + "\n"
                    + "  late-bind: false" + "\n" + "  player-shuffle: 0" + "\n" + "  sample-count: 12" + "\n"
                    + "  user-cache-size: 1000" + "\n" + "  bungeecord: true" + "\n" + "  int-cache-limit: 1024" + "\n"
                    + "  item-dirty-ticks: 20" + "\n" + "  timeout-time: 60" + "\n" + "  restart-on-crash: true" + "\n"
                    + "  restart-script: ./start.sh" + "\n" + "  netty-threads: 4" + "\n" + "  attribute:" + "\n"
                    + "    maxHealth:" + "\n" + "      max: 2048.0" + "\n" + "    movementSpeed:" + "\n"
                    + "      max: 2048.0" + "\n" + "    attackDamage:" + "\n" + "      max: 2048.0" + "\n"
                    + "  moved-wrongly-threshold: 0.0625" + "\n" + "  moved-too-quickly-multiplier: 10.0" + "\n"
                    + "  filter-creative-items: true" + "\n" + "commands:" + "\n" + "  tab-complete: 0" + "\n"
                    + "  spam-exclusions:" + "\n" + "  - /skill" + "\n" + "  replace-commands:" + "\n" + "  - setblock"
                    + "\n" + "  - summon" + "\n" + "  - testforblock" + "\n" + "  - tellraw" + "\n" + "  log: true"
                    + "\n" + "  silent-commandblock-console: false" + "\n" + "messages:" + "\n"
                    + "  whitelist: You are not whitelisted on this server!" + "\n"
                    + "  unknown-command: Unknown command. Type \"/help\" for help." + "\n"
                    + "  server-full: The server is full!" + "\n" + "  outdated-client: Outdated client! Please use {0}"
                    + "\n" + "  outdated-server: Outdated server! I'm still on {0}" + "\n"
                    + "  restart: Server is restarting" + "\n" + "stats:" + "\n" + "  disable-saving: false" + "\n"
                    + "  forced-stats: {}" + "\n" + "advancements:" + "\n" + "  disable-saving: false" + "\n"
                    + "  disabled:" + "\n" + "  - minecraft:story/disabled" + "\n" + "world-settings:" + "\n"
                    + "  default:" + "\n" + "    verbose: true" + "\n" + "    enable-zombie-pigmen-portal-spawns: true"
                    + "\n" + "    arrow-despawn-rate: 1200" + "\n" + "    merge-radius:" + "\n" + "      item: 2.5"
                    + "\n" + "      exp: 3.0" + "\n" + "    item-despawn-rate: 6000" + "\n"
                    + "    nerf-spawner-mobs: false" + "\n" + "    dragon-death-sound-radius: 0" + "\n"
                    + "    view-distance: 10" + "\n" + "    wither-spawn-sound-radius: 0" + "\n"
                    + "    hanging-tick-frequency: 100" + "\n" + "    zombie-aggressive-towards-villager: true" + "\n"
                    + "    mob-spawn-range: 4" + "\n" + "    growth:" + "\n" + "      cactus-modifier: 100" + "\n"
                    + "      cane-modifier: 100" + "\n" + "      melon-modifier: 100" + "\n"
                    + "      mushroom-modifier: 100" + "\n" + "      pumpkin-modifier: 100" + "\n"
                    + "      sapling-modifier: 100" + "\n" + "      wheat-modifier: 100" + "\n"
                    + "      netherwart-modifier: 100" + "\n" + "      vine-modifier: 100" + "\n"
                    + "      cocoa-modifier: 100" + "\n" + "    entity-activation-range:" + "\n" + "      animals: 32"
                    + "\n" + "      monsters: 32" + "\n" + "      misc: 16" + "\n"
                    + "      tick-inactive-villagers: true" + "\n" + "    entity-tracking-range:" + "\n"
                    + "      players: 48" + "\n" + "      animals: 48" + "\n" + "      monsters: 48" + "\n"
                    + "      misc: 32" + "\n" + "      other: 64" + "\n" + "    hunger:" + "\n"
                    + "      jump-walk-exhaustion: 0.05" + "\n" + "      jump-sprint-exhaustion: 0.2" + "\n"
                    + "      combat-exhaustion: 0.1" + "\n" + "      regen-exhaustion: 6.0" + "\n"
                    + "      swim-multiplier: 0.01" + "\n" + "      sprint-multiplier: 0.1" + "\n"
                    + "      other-multiplier: 0.0" + "\n" + "    random-light-updates: false" + "\n"
                    + "    seed-village: 10387312" + "\n" + "    seed-feature: 14357617" + "\n"
                    + "    seed-monument: 10387313" + "\n" + "    seed-slime: 987234911" + "\n" + "    ticks-per:"
                    + "\n" + "      hopper-transfer: 8" + "\n" + "      hopper-check: 1" + "\n" + "    hopper-amount: 1"
                    + "\n" + "    save-structure-info: true" + "\n" + "    squid-spawn-range:" + "\n"
                    + "      min: 45.0" + "\n" + "    max-tnt-per-tick: 100" + "\n" + "    max-tick-time:" + "\n"
                    + "      tile: 50" + "\n" + "      entity: 50" + "\n");
        }
        String[] Command = {"cmd","/c", new File(serverFolder, "start.bat").getAbsolutePath()};
        PlayerFarmsAPI.bungeeSend("addServer," + player.getName() + "," + sInfo.startingPort + "," + sInfo.motd);
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(Command);
        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage(Language.getLangStr("messages.server_start_failed"));
        }
    }
}
