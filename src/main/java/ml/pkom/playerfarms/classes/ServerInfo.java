package ml.pkom.playerfarms.classes;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ServerInfo {
    public static int port = 25567;
    
    public String name = "";
    public int startingPort = 0;
    public String motd = "";
    public boolean exist = false;
    public String createDate = "";
    public String latestDate = "";
    public String worldType = "DEFAULT";
    public String serverState = "offline";
    public int onlinePlayer = 0;
    public int maxPlayer = 15;
    public boolean cmdBlock = true;
    public boolean flyKick = false;
    public String icon = "EMERALD_BLOCK";
    public int iconDamage = 0;
    public int vote = 0;
    public int onlinePlayers = 0;
    private Player player;
    private PFPlayer pfPlayer;

    public ServerInfo(PFPlayer pfPlayer){
        this.pfPlayer = pfPlayer;
        //mcid = player.getName();
        //uuid = player.getUniqueId();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }

    public void setConfigServerInfo(){
        FileConfiguration config = pfPlayer.getConfig();
        config.set("player.mcid", pfPlayer.getMCID());
        config.set("server.exist", exist);
        config.set("server.name", name);
        config.set("server.motd", motd);
        config.set("server.createDate", createDate);
        config.set("server.latestDate", latestDate);
        config.set("server.worldType", worldType);
        config.set("server.maxPlayer", maxPlayer);
        config.set("server.cmdBlock", cmdBlock);
        config.set("server.flyKick", flyKick);
        config.set("server.vote", vote);
        config.set("server.icon", icon);
        config.set("server.icon_damage", iconDamage);
        config.set("server.onlinePlayers", onlinePlayers);
    }
}
