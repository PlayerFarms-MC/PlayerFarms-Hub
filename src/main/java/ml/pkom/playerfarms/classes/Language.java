package ml.pkom.playerfarms.classes;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ml.pkom.jsonconfig.api.JsonConfiguration;
import ml.pkom.playerfarms.PlayerFarms;

public class Language {
    private File pluginFolder = PlayerFarms.getInstance().getDataFolder();

    private JsonConfiguration jConfig = new JsonConfiguration(new File(pluginFolder, "lang/ja_jp.json"));
    private FileConfiguration config;
    private static Language lang;

    public static Object getLang(String str){
        return lang.config.get(str);
    }

    public static String getLangStr(String str) {
        String res = lang.config.getString(str);
        if (res == null) {
            return "null";
        }
        return res;
    }

    public static int getLangInt(String str) {
        return lang.config.getInt(str);
    }

    public Language(){
        lang = this;
        if (!pluginFolder.exists())
            pluginFolder.mkdir();
        File langFolder = new File(pluginFolder, "lang/");
        if (!langFolder.exists())
            langFolder.mkdir();

        config = new YamlConfiguration();
        if (new File(pluginFolder, "lang/ja_jp.json").exists()) {
            config = jConfig.getJsonConfig();
        } else {
            config.set("navi.title", "PlayerFarms Navigator");
            config.set("navi.serverlist.title", "§3Server List Page ");
            config.set("navi.myserver.title", "'s Server");
            config.set("navi.serverlist.name", "§b現在人気なサーバー");
            config.set("navi.serverlist.lore", "§7現在人気のあるサーバーを表示します。");
            config.set("navi.myserver.name", "§bあなたの§dFarm");
            config.set("navi.myserver.lore", "§7サーバーの設定を表示します。");
            config.set("navi.presentcode.name", "§aプレゼントコードを使用する");
            config.set("navi.presentcode.lore", "§7入手したコードを入力してプレゼントを貰います！");
            config.set("serverlist.allfarms.name", "§eすべてのFarmsを見る");
            config.set("serverlist.online.name", "§eオンライン");
            config.set("serverlist.online.lore", "§e投票順に変更する");
            config.set("serverlist.random.name", "§bランダムなサーバー");
            config.set("serverlist.random.lore", "§7ランダムなサーバーへ行きます。");
            config.set("myserver.start.name", "§aサーバーを起動します");
            config.set("myserver.rename.name", "§bサーバーをリネームします");
            config.set("myserver.rename.lore", "§7サーバーをリネームするにはここをクリックしてください。");
            config.set("myserver.flykick.name", "§aフライキックをトグルします");
            config.set("myserver.flykick.lore", "§7フライキックは:");
            config.set("myserver.cmdblock.name", "§aコマンドブロックをトグルします");
            config.set("myserver.cmdblock.lore", "§7コマンドブロックは:");
            config.set("myserver.backup.name", "§aバックアップする");
            config.set("myserver.backup.lore", "§eサーバーのデータをバックアップ");
            config.set("myserver.delete.name", "§cサーバーのデータを消します");
            config.set("servermanager.title", "Server Manager");
            config.set("servermanager.make.name", "§aサーバーを作ります");
            config.set("selecttype.title", "Select Type");
            config.set("selecttype.flat.name", "§eFlatワールド");
            config.set("selecttype.normal.name", "§e普通のワールド");
            config.set("selecttype.void.name", "§eVoidワールド");
            config.set("selecttype.upload.name", "§eアップロード");
            config.set("messages.server_starting", "§bサーバーの準備をしています。");
            config.set("messages.server_started", "§aサーバーの準備が完了しました。");
            config.set("messages.server_connect", "§bに接続しています。");
            config.set("messages.other_server_starting", "§bそのサーバーは現在起動中です。");
            config.set("messages.server_start_failed", "§cサーバーの起動に失敗しました。再度お試しください。");
            config.set("messages.server_eula_agreed", "§aEulaに同意しました。");
            config.set("item.realm_menu", "§dサーバーの管理§7（右クリック）");
            config.set("item.gadget_menu", "§aガジェットメニュー§7（右クリック）");
            config.set("item.crate_box", "§b報酬箱§7（右クリック）");
        }
        jConfig.setJsonConfig(config);
        jConfig.saveJsonConfig();
    }
}
