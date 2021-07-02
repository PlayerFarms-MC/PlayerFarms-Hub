package ml.pkom.playerfarms.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ml.pkom.playerfarms.api.PlayerFarmsAPI;
import ml.pkom.playerfarms.classes.PFPlayer;
import ml.pkom.playerfarms.item.CrateBox;
import ml.pkom.playerfarms.item.RealmMenu;

public class JoinQuit implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        e.setJoinMessage("§e" + player.getName() + " has join the lobby.");
        if (!PFPlayer.existPFPlayer(player)){
            new PFPlayer(player);
        }
        PlayerFarmsAPI.giveItem_once(player, RealmMenu.item, 0);
        PlayerFarmsAPI.giveItem_once(player, CrateBox.item, 3);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        e.setQuitMessage("§c" + player.getName() + " has left the lobby.");
    }
}
