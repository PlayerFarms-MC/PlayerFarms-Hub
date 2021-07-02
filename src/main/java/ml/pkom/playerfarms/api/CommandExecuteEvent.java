package ml.pkom.playerfarms.api;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecuteEvent {
    private CommandSender sender;
    private Command cmd;
    private String str;
    private String[] args;

    /**
     * コマンドイベントを作成します
     * 
     * @param sender プレイヤー
     * @param cmd コマンド
     * @param str コマンドの内容
     * @param args コマンドの引数
     */
    public CommandExecuteEvent(CommandSender sender, Command cmd, String str, String[] args){
        this.sender = sender;
        this.cmd = cmd;
        this.str = str;
        this.args = args;
    }

    public CommandSender getSender(){
        return sender;
    }

    public Player getPlayer(){
        return (Player) sender;
    }

    public Command getCommand(){
        return cmd;
    }

    public String getCommandString() {
        return cmd.getName();
    }

    public boolean commandEquals(String str){
        return getCommandString().equalsIgnoreCase(str);
    }

    public String getString(){
        return str;
    }

    public String[] getArgs(){
        return args;
    }

    public String getArg(int i){
        return args[i];
    }
}
