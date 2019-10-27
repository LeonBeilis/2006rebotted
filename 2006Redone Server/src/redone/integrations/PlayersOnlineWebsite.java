package redone.integrations;

import redone.game.players.PlayerHandler;

import java.io.IOException;
import java.net.URL;

public class PlayersOnlineWebsite {

    static String password;
    private static boolean hasntwared = true;

    private static void setWebsitePlayersOnline(int amount) throws IOException {
        URL url;
        url = new URL("https://2006rebotted.tk/playersonline.php?pass=" + password + "&amount=" + amount);
        url.openStream().close();
    }

    private static int count = 50;
    public static void addUpdatePlayersOnlineTask() {
<<<<<<< HEAD
        if (!password.equals("") && password != null) {
=======
        if (password != null && !password.equals("")) {
>>>>>>> abcb1133d85bfcd6fc1f75082d464ba3db6dfa03
            if (count == 0) {
                try {
                    setWebsitePlayersOnline(PlayerHandler.getPlayerCount());
                    count = 50;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                count--;
            }
        } else if (hasntwared) {
            hasntwared = false;
            System.out.println("No Website Password Set So Website Integration Tasks Stopped");
        }
    }
}
