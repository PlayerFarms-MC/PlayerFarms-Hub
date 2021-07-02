package ml.pkom.playerfarms.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;

import ml.pkom.playerfarms.classes.Language;
import ml.pkom.playerfarms.system.MenuGui;

public class InventoryClick implements Listener {

    @EventHandler
    public void onGuiClick(InventoryClickEvent e) {
        if (e == null){
            return;
        }
        if (e.getInventory().getItem(e.getSlot()) == null) {
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(MenuGui.naviMenuTitle)) {
            MenuGui.onGuiClick(e);
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(MenuGui.serverListMenuTitle)) {
            MenuGui.onGuiClick(e);
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(((Player) e.getWhoClicked()).getDisplayName() + MenuGui.myServerMenuTitle)) {
            MenuGui.onGuiClick(e);
            return;
        }
        //if (e.getInventory().getName().equalsIgnoreCase(Language.getLangStr("myserver.start.name"))) {
        //    e.getWhoClicked().sendMessage("§aサーバーを起動しています...");
        //    return;
        //}
        if (e.getInventory().getName().equalsIgnoreCase(Language.getLangStr("servermanager.title"))) {
            MenuGui.onGuiClick(e);
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(Language.getLangStr("selecttype.title"))) {
            MenuGui.onGuiClick(e);
            return;
        }
    }
}
