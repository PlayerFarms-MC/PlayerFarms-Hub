package ml.pkom.playerfarms.classes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ml.pkom.jsonconfig.api.JsonConfiguration;
import ml.pkom.playerfarms.PlayerFarms;

public class PFPlayer {
    private File pluginFolder = PlayerFarms.getInstance().getDataFolder();

    private Player player;
    private FileConfiguration config;
    private JsonConfiguration jConfig;
    private File configFolder = new File(pluginFolder, "players/");
    private File configFile;
    private ServerInfo server = new ServerInfo(this);

    private static List<UUID> containsPlayerUUIDs = new ArrayList<UUID>();
    private static List<PFPlayer> containsPFPlayers = new ArrayList<PFPlayer>();

    public static boolean existPFPlayer(Player player){
        if(containsPlayerUUIDs.contains(player.getUniqueId())){
            return true;
        }else{
            return false;
        }
    }

    public static PFPlayer getPFPlayer(Player player){
        if (!PFPlayer.existPFPlayer(player)) {
            new PFPlayer(player);
        }
        for (PFPlayer pfPlayer : containsPFPlayers) {
            if(pfPlayer.player.getUniqueId().equals(player.getUniqueId())) {
                return pfPlayer;
            }
        }

        player.sendMessage("§cエラーが発生しました。何度も続く場合は運営へご報告ください。");
        return null;
    }

    public PFPlayer(Player player){
        this.player = player;
        containsPlayerUUIDs.add(player.getUniqueId());
        containsPFPlayers.add(this);
        configFile = new File(configFolder, getUUID().toString() + ".json");
        if (!pluginFolder.exists())
            pluginFolder.mkdir();
        if (!configFolder.exists())
            configFolder.mkdir();
        jConfig = new JsonConfiguration(configFile);
        config = new YamlConfiguration();
        if (configFile.exists()) {
            config = jConfig.getJsonConfig();
            server.exist = config.getBoolean("server.exist");
            if (server.exist == true){
                server.name = config.getString("server.name");
                server.motd = config.getString("server.motd");
                server.createDate = config.getString("server.createDate");
                server.latestDate = config.getString("server.latestDate");
                server.maxPlayer = config.getInt("server.maxPlayer");
                server.flyKick = config.getBoolean("server.flyKick");
                server.cmdBlock = config.getBoolean("server.cmdBlock");
                if (config.contains("server.vote"))
                    server.vote = config.getInt("server.vote");
                if (config.contains("server.icon"))
                server.icon = config.getString("server.icon");
                if (config.contains("server.icon_damage"))
                    server.iconDamage = config.getInt("server.icon_damage");
                if (config.contains("server.onlinePlayers"))
                    server.onlinePlayers = config.getInt("server.onlinePlayers");
            }
        } else {
            config.set("player.mcid", getMCID());
            config.set("player.uuid", getUUID());
            config.set("server.exist", false);
        }
        jConfig.setJsonConfig(config);
        jConfig.saveJsonConfig();
    }

    public ServerInfo getServerInfo() {
        return server;
    }

    public String getMCID() {
        return player.getName();
    }

    public UUID getUUID() {
        return player.getUniqueId();
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void saveConfig() {
        jConfig.setJsonConfig(config);
        jConfig.saveJsonConfig();
    }

    public FileConfiguration getConfig(){
        return config;
    }
}
