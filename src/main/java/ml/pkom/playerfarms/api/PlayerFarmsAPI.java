package ml.pkom.playerfarms.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerFarmsAPI {
    private static Logger log = Bukkit.getLogger();

    /**
     * すべてのプレイヤーへメッセージを送信します
     * 
     * @param msg メッセージ
     */
    public static void broadcast(String msg) {
        Bukkit.getServer().broadcastMessage(msg);
        return;
    }

    /**
     * コンソールへ文字列を出力
     * 
     * @param msg メッセージ
     */
    public static void sendLog(String msg) {
        log.info(msg);
    }

    public static boolean fileWriteContents(File file, String string) {
        try {
            PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
            writer.println(string);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String fileReadContents(File file) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = "";
            String string = "";
            while ((line = reader.readLine()) != null) {
                string += line + "\n";
            }
            reader.close();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
    }

    public static void giveItem_once(Player player, ItemStack itemStack, int slot) {
        if (!player.getInventory().contains(itemStack)) {
            if (player.getInventory().getItem(slot) == null) {
                player.getInventory().setItem(slot, itemStack);
            } else {
                player.getInventory().addItem(itemStack);
            }
        }
    }

    private static Socket socket;

    public static void bungeeSend(String string) {
        try {
            socket = new Socket("localhost", 25564);
            OutputStream out = socket.getOutputStream();
            out.write(string.getBytes("UTF-8"));
            out.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void connectServer(Player player, String serverName) {
        bungeeSend("connect," + player.getUniqueId() + "," + serverName);
    }
}
