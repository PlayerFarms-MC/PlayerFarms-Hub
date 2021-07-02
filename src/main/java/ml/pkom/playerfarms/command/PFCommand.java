package ml.pkom.playerfarms.command;

import ml.pkom.playerfarms.api.CommandExecuteEvent;
import ml.pkom.playerfarms.system.MenuGui;

public class PFCommand {
    public static boolean onCommandEvent(CommandExecuteEvent e){
        if (e.commandEquals("farms")) {
            MenuGui.openNaviMenu(e.getPlayer());
            return true;
        }
        if (e.commandEquals("server")) {
            
            return true;
        }
        return false;
    }
}
