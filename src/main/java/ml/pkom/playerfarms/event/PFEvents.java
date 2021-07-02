package ml.pkom.playerfarms.event;

import org.bukkit.Bukkit;

import ml.pkom.playerfarms.PlayerFarms;
import ml.pkom.playerfarms.item.ItemEvents;

public class PFEvents {
    public PFEvents() {
        Bukkit.getPluginManager().registerEvents(new JoinQuit(), PlayerFarms.getInstance());
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), PlayerFarms.getInstance());
        new ItemEvents();
    }
}
