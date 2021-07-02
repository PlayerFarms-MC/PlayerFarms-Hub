package ml.pkom.playerfarms.system;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ml.pkom.jsonconfig.api.JsonConfiguration;
import ml.pkom.playerfarms.PlayerFarms;
import ml.pkom.playerfarms.api.PlayerFarmsAPI;
import ml.pkom.playerfarms.classes.Language;
import ml.pkom.playerfarms.classes.PFPlayer;
import ml.pkom.playerfarms.classes.PlayerShowServerList;

public class MenuGui {

    public static int serverListPage = 1;

    public static String naviMenuTitle = Language.getLangStr("navi.title");
    public static String serverListMenuTitle = Language.getLangStr("navi.serverlist.title") + serverListPage;
    public static String myServerMenuTitle = Language.getLangStr("navi.myserver.title");
    private static Inventory naviMenu = Bukkit.createInventory(null, 27, naviMenuTitle);
    private static Inventory serverListMenu = Bukkit.createInventory(null, 54, serverListMenuTitle);
    private static Inventory inputCodeMenu = Bukkit.createInventory(null, InventoryType.ANVIL);
    private static Inventory serverManager = Bukkit.createInventory(null, 27, Language.getLangStr("servermanager.title"));
    private static Inventory selectType = Bukkit.createInventory(null, 27, Language.getLangStr("selecttype.title"));
    //private static Builder inputCodeMenu = new AnvilGUI.Builder().plugin(PlayerFarms.getInstance());

    static {
        try {
            setSlot(naviMenu, 11, Material.GOLD_NUGGET, 1, 0, Language.getLangStr("navi.serverlist.name"),
                    Language.getLangStr("navi.serverlist.lore"));
            setSlot(naviMenu, 13, Material.CHEST, 1, 0, Language.getLangStr("navi.myserver.name"),
                    Language.getLangStr("navi.myserver.lore"));
            setSlot(naviMenu, 15, Material.DIAMOND, 1, 0, Language.getLangStr("navi.presentcode.name"),
                    Language.getLangStr("navi.presentcode.lore"));

            setSlot(serverListMenu, 0, Material.ENDER_CHEST, 1, 0, Language.getLangStr("serverlist.allfarms.name"),
                    null);
            setSlot(serverListMenu, 8, Material.EYE_OF_ENDER, 1, 0, Language.getLangStr("serverlist.online.name"),
                    Language.getLangStr("serverlist.online.lore"));
            setSlot(serverListMenu, 49, Material.ENDER_PEARL, 1, 0, Language.getLangStr("serverlist.random.name"),
                    Language.getLangStr("serverlist.random.lore"));

            ItemStack codeItem = new ItemStack(Material.DIAMOND);
            ItemMeta codeItemMeta = codeItem.getItemMeta();
            codeItemMeta.setDisplayName("Code");
            codeItem.setItemMeta(codeItemMeta);
            // inputCodeMenu.itemLeft(codeItem);
            setSlot(inputCodeMenu, 0, Material.DIAMOND, 1, 0, "Code", null);

            setSlot(serverManager, 13, Material.ANVIL, 1, 0, Language.getLangStr("servermanager.make.name"), null);

            setSlot(selectType, 10, Material.SLIME_BLOCK, 1, 0, Language.getLangStr("selecttype.flat.name"), null);
            setSlot(selectType, 12, Material.SAPLING, 1, 0, Language.getLangStr("selecttype.normal.name"), null);
            setSlot(selectType, 14, Material.BEDROCK, 1, 0, Language.getLangStr("selecttype.void.name"), null);
            setSlot(selectType, 16, Material.WATER_BUCKET, 1, 0, Language.getLangStr("selecttype.upload.name"), null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onGuiClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        e.setCancelled(true);
        String clickedItemName = e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName();
        if (clickedItemName.equalsIgnoreCase(Language.getLangStr("navi.serverlist.name"))) {
            openServerList(player);
            return;
        }
        if (clickedItemName.equalsIgnoreCase(Language.getLangStr("navi.myserver.name"))) {
            openMyServer(player);
            return;
        }
        if (clickedItemName.equalsIgnoreCase(Language.getLangStr("navi.presentcode.name"))) {
            openInputCode(player);
            return;
        }
        if (clickedItemName.equalsIgnoreCase(Language.getLangStr("servermanager.make.name"))) {
            player.openInventory(selectType);
            return;
        }
        if (clickedItemName.equalsIgnoreCase(Language.getLangStr("selecttype.flat.name"))) {
            //player.closeInventory();
            PFPlayer pfPlayer = PFPlayer.getPFPlayer(player);
            pfPlayer.getServerInfo().worldType = "FLAT";
            tmpSetting(player, pfPlayer);
            return;
        }
        if (clickedItemName.equalsIgnoreCase(Language.getLangStr("selecttype.normal.name"))) {
            PFPlayer pfPlayer = PFPlayer.getPFPlayer(player);
            pfPlayer.getServerInfo().worldType = "DEFAULT";
            tmpSetting(player, pfPlayer);
            return;
        }
        if (clickedItemName.equalsIgnoreCase(Language.getLangStr("selecttype.void.name"))) {
            PFPlayer pfPlayer = PFPlayer.getPFPlayer(player);
            pfPlayer.getServerInfo().worldType = "VOID";
            tmpSetting(player, pfPlayer);
            return;
        }
        if (clickedItemName.equalsIgnoreCase(Language.getLangStr("myserver.start.name"))) {
            PFPlayer pfPlayer = PFPlayer.getPFPlayer(player);
            pfPlayer.getServerInfo().serverState = "starting";
            player.sendMessage(Language.getLangStr("messages.server_starting"));
            new ChildServer(player);
            player.closeInventory();
            return;
        }
        if (clickedItemName.equalsIgnoreCase(Language.getLangStr("myserver.flykick.name"))) {
            PFPlayer pfPlayer = PFPlayer.getPFPlayer(player);
            pfPlayer.getServerInfo().flyKick = !pfPlayer.getServerInfo().flyKick;
            setSlot(e.getClickedInventory(), 5, Material.FEATHER, 1, 0, Language.getLangStr("myserver.flykick.name"),
                    Language.getLangStr("myserver.flykick.lore")
                            + (pfPlayer.getServerInfo().flyKick ? "§aON" : "§cOFF"));
            pfPlayer.saveConfig();
            return;
        }
        if (clickedItemName.equalsIgnoreCase(Language.getLangStr("myserver.cmdblock.name"))) {
            PFPlayer pfPlayer = PFPlayer.getPFPlayer(player);
            pfPlayer.getServerInfo().cmdBlock = !pfPlayer.getServerInfo().cmdBlock;
            setSlot(e.getClickedInventory(), 6, Material.COMMAND, 1, 0, Language.getLangStr("myserver.cmdblock.name"),
                    Language.getLangStr("myserver.cmdblock.lore")
                            + (pfPlayer.getServerInfo().cmdBlock ? "§aON" : "§cOFF"));
            pfPlayer.saveConfig();
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(serverListMenuTitle)) {
            if (e.getSlot() > 9 && e.getSlot() < 45){
                PlayerFarmsAPI.connectServer(player, PlayerShowServerList.getServerList(player).getSlotOwnerName(e.getSlot()));
            }
            return;
        }
        return;
    }

    public static void tmpSetting(Player player, PFPlayer pfPlayer){
            pfPlayer.getServerInfo().exist = true;
            pfPlayer.getServerInfo().name = player.getName() + Language.getLangStr("navi.myserver.title");
            pfPlayer.getServerInfo().setPlayer(player);
            pfPlayer.getServerInfo().setConfigServerInfo();
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
            pfPlayer.getServerInfo().createDate = format.format(date);
            pfPlayer.getServerInfo().latestDate = format.format(date);
            pfPlayer.saveConfig();

            openMyServer(player);
    }

    public static void openNaviMenu(Player player){
        player.openInventory(naviMenu);
        return;
    }

    public static void makeServerList(Player player){
        for (int i = 10; i <= 45; i++) {
            serverListMenu.clear(i);
        }
        int n = 10;
        PlayerShowServerList playerShowServerList = new PlayerShowServerList(player);
        String serverUUIDs[] = PlayerFarmsAPI.fileReadContents(new File(PlayerFarms.getInstance().getDataFolder(), "OnlineServers.txt")).split("\n");
        for (String uuid : serverUUIDs) {
            if (uuid == null || uuid.isEmpty() || uuid == "\n"){
                continue;
            }
            JsonConfiguration uuidJConfig = new JsonConfiguration(new File(PlayerFarms.getInstance().getDataFolder(), "players/" + uuid + ".json"));
            FileConfiguration uuidConfig = new YamlConfiguration();
            uuidConfig = uuidJConfig.getJsonConfig();
            playerShowServerList.addSlot(n, Bukkit.getPlayer(UUID.fromString(uuid)));
            setSlot(serverListMenu, n, Material.getMaterial(uuidConfig.getString("server.icon")), 1,
                    uuidConfig.getInt("server.icon_damage"), "§a" + uuidConfig.getString("server.name"),
                    uuidConfig.getString("server.motd")
                     + "\n\n§7プレイヤー数 §a" + uuidConfig.getString("server.onlinePlayers") + "§7/" + uuidConfig.getString("server.maxPlayer")
                     + "\n§b投票 §d" + uuidConfig.getString("server.vote")
                     + "\n§r§l主: §7" + uuidConfig.getString("player.mcid")

            );
            n++;  
        }
    }

    public static void openServerList(Player player) {
        makeServerList(player);
        player.openInventory(serverListMenu);
        return;
    }

    public static void openMyServer(Player player) {
        PFPlayer pfPlayer = PFPlayer.getPFPlayer(player);
        if (pfPlayer.getServerInfo().exist){
            Inventory myServerMenu = Bukkit.createInventory(null, 9, player.getName() + myServerMenuTitle);
            setSlot(myServerMenu, 1, Material.WOOL, 1, 5, Language.getLangStr("myserver.start.name"), null);
            setSlot(myServerMenu, 3, Material.PAPER, 1, 0, Language.getLangStr("myserver.rename.name"),
                    Language.getLangStr("myserver.rename.lore"));
            setSlot(myServerMenu, 5, Material.FEATHER, 1, 0, Language.getLangStr("myserver.flykick.name"),
                    Language.getLangStr("myserver.flykick.lore") + (pfPlayer.getServerInfo().flyKick ? "§aON" : "§cOFF"));
            setSlot(myServerMenu, 6, Material.COMMAND, 1, 0, Language.getLangStr("myserver.cmdblock.name"),
                    Language.getLangStr("myserver.cmdblock.lore") + (pfPlayer.getServerInfo().cmdBlock ? "§aON" : "§cOFF"));
            setSlot(myServerMenu, 7, Material.CHEST, 1, 0, Language.getLangStr("myserver.backup.name"),
                    Language.getLangStr("myserver.backup.lore"));
            setSlot(myServerMenu, 8, Material.BARRIER, 1, 0, Language.getLangStr("myserver.delete.name"), null);
            player.openInventory(myServerMenu);
            return;
        }
        player.openInventory(serverManager);
    }

    public static void openInputCode(Player player) {
        //inputCodeMenu.open(player);
        player.openInventory(inputCodeMenu);
        return;
    }

    public static void setSlot(Inventory inventory, int slotNumber, Material itemType, int amount, int data,
        String name, String lore) {
        ItemStack item = new ItemStack(itemType);
        item.setAmount(amount);
        ItemMeta itemMeta = item.getItemMeta();
        item.setDurability((short) data);
        if (name != null) {
            itemMeta.setDisplayName(name);
        }
        if (lore != null) {
            List<String> loreList;
            loreList = Arrays.asList(lore.split("\n"));
            itemMeta.setLore(loreList);
        }
        item.setItemMeta(itemMeta);
        inventory.setItem(slotNumber, item);
    }
}
