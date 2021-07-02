package ml.pkom.playerfarms.classes;

import java.util.UUID;

import org.bukkit.entity.Player;

public class MadeServerListSlotData {
    public Integer indexSlot;
    public UUID ownerUuid;
    public String ownerMCID;

    public MadeServerListSlotData(int slot, Player owner){
        indexSlot = slot;
        ownerUuid = owner.getUniqueId();
        ownerMCID = owner.getName();
    }
}
