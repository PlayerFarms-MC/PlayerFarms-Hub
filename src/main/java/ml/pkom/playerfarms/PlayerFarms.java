package ml.pkom.playerfarms;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import ml.pkom.playerfarms.api.CommandExecuteEvent;
import ml.pkom.playerfarms.classes.Language;
import ml.pkom.playerfarms.command.PFCommand;
import ml.pkom.playerfarms.event.PFEvents;

public class PlayerFarms extends JavaPlugin {
    public static final String MOD_ID = "playerfarms"; // MODID
    public static final String MOD_NAME = "PlayerFarms"; // MOD名
    public static final String MOD_VER = "1.0.0"; // MODバージョン
    public static final String MOD_AUT = "Pitan"; // MOD開発者

    private static PlayerFarms playerFarms;

    public static PlayerFarms getInstance() {
        return playerFarms;
    }

    @Override
    public void onEnable()
    {
        playerFarms = this;
        new Language();
        new PFEvents();
        getLogger().info("PlayerFarms is enabled!");
    }

    @Override
    public void onDisable()
    {
        getLogger().info("PlayerFarms is disabled!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args)
    {
        CommandExecuteEvent event = new CommandExecuteEvent(sender, cmd, str, args);
        boolean res = PFCommand.onCommandEvent(event);
        return res;
    }
}