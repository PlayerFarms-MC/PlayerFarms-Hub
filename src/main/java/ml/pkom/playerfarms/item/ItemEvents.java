package ml.pkom.playerfarms.item;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import ml.pkom.playerfarms.PlayerFarms;

public class ItemEvents {
    public ItemEvents() {
        registerItemEvent(new RealmMenu());
        registerItemEvent(new CrateBox());
    }

    public void registerItemEvent(Listener listener){
        Bukkit.getPluginManager().registerEvents(listener, PlayerFarms.getInstance());
    }
}
