package ml.pkom.playerfarms.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ml.pkom.playerfarms.classes.Language;
import ml.pkom.playerfarms.system.MenuGui;

public class RealmMenu implements Listener {
    public static ItemStack item;
    private static ItemMeta itemMeta;

    static {
        item = new ItemStack(Material.COMPASS);
        (itemMeta = item.getItemMeta()).setDisplayName(Language.getLangStr("item.realm_menu"));
        item.setItemMeta(itemMeta);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if (e == null) {
            return;
        }
        Player player = e.getPlayer();
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR)) || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            try {
                if (e.getItem().equals(item)) {
                    e.setCancelled(true);
                    MenuGui.openNaviMenu(player);
                }
            } catch (NullPointerException ex) {
                return;
            }
        }
    }
}
