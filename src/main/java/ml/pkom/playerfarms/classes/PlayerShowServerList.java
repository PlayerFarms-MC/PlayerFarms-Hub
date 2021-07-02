package ml.pkom.playerfarms.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerShowServerList {
    private Player showedPlayer;
    private List<MadeServerListSlotData> slotDatas = new ArrayList<MadeServerListSlotData>();

    private static List<PlayerShowServerList> containsMadeServerLists = new ArrayList<PlayerShowServerList>();

    public static PlayerShowServerList getServerList(Player player){
        for (PlayerShowServerList serverList : containsMadeServerLists) {
            if(serverList.showedPlayer.getUniqueId().equals(player.getUniqueId())) {
                return serverList;
            }
        }
        return null;
    }

    public static void ifExist_RemoveServerList(Player player) {
        for (PlayerShowServerList serverList : containsMadeServerLists) {
            if (serverList.showedPlayer.getUniqueId().equals(player.getUniqueId())) {
                containsMadeServerLists.remove(serverList);
                serverList = null;
                break;
            }
        }
    }

    public void addSlot(int slot, Player owner) {
        slotDatas.add(new MadeServerListSlotData(slot, owner));
    }

    public String getSlotOwnerName(int slot) {
        for (MadeServerListSlotData data : slotDatas) {
            if (data.indexSlot.equals(slot)) {
                return data.ownerMCID;
            }
        }
        return null;
    }

    public PlayerShowServerList(Player player){
        this.showedPlayer = player;
        ifExist_RemoveServerList(player);
        containsMadeServerLists.add(this);
    }

    public String getMCID() {
        return showedPlayer.getName();
    }

    public UUID getUUID() {
        return showedPlayer.getUniqueId();
    }

    public Player getPlayer(){
        return showedPlayer;
    }
}
