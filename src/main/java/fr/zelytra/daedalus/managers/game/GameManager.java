package fr.zelytra.daedalus.managers.game;

import fr.zelytra.daedalus.Daedalus;
import fr.zelytra.daedalus.managers.faction.Faction;
import fr.zelytra.daedalus.managers.faction.FactionManager;
import fr.zelytra.daedalus.managers.faction.FactionsEnum;
import fr.zelytra.daedalus.managers.game.settings.DayCycleEnum;
import fr.zelytra.daedalus.managers.game.settings.GameSettings;
import fr.zelytra.daedalus.managers.game.settings.TemplesGenerationEnum;
import fr.zelytra.daedalus.managers.game.time.TimeManager;
import fr.zelytra.daedalus.managers.maze.MazeHandler;
import fr.zelytra.daedalus.utils.Message;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class GameManager {

    private final FactionManager factionManager;
    private final TimeManager timeManager;
    private GameStatesEnum state;
    private boolean started = false;
    private int preStartRunnable;

    public GameManager() {

        this.factionManager = new FactionManager();
        this.timeManager = new TimeManager();
        this.state = GameStatesEnum.WAIT;

    }

    public FactionManager getFactionManager() {
        return factionManager;
    }


    public TimeManager getTimeManager() {
        return timeManager;
    }

    public GameStatesEnum getState() {
        return state;
    }

    public void setState(GameStatesEnum state) {
        this.state = state;
    }

    public boolean isWaiting() {
        return state == GameStatesEnum.WAIT;
    }

    public boolean isRunning() {
        return state == GameStatesEnum.RUNNING;
    }

    public boolean isFinished() {
        return state == GameStatesEnum.FINISHED;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void reverseTempleGeneration() {

        if (GameSettings.GOD_SELECTION == TemplesGenerationEnum.CHOSEN)
            GameSettings.GOD_SELECTION = TemplesGenerationEnum.RANDOM;
        else
            GameSettings.GOD_SELECTION = TemplesGenerationEnum.CHOSEN;

    }

    public boolean increaseGodLimit() {

        if (GameSettings.GOD_LIMIT >= GameSettings.GOD_MAXIMUM)
            return false;
        else
            GameSettings.GOD_LIMIT += 1;

        return true;
    }

    public boolean decreaseGodLimit() {

        if (GameSettings.GOD_LIMIT <= GameSettings.GOD_MINIMUM)
            return false;
        else
            GameSettings.GOD_LIMIT -= 1;

        return true;
    }

    private void applySettings() {

        Bukkit.getWorld("world").setTime(GameSettings.DAY_CYCLE.getTicks());
        if (GameSettings.DAY_CYCLE == DayCycleEnum.DEFAULT)
            Bukkit.getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        else
            Bukkit.getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

        if (GameSettings.HARDCORE)
            Bukkit.getWorld("world").setGameRule(GameRule.NATURAL_REGENERATION, false);
        else
            Bukkit.getWorld("world").setGameRule(GameRule.NATURAL_REGENERATION, true);

    }

    public void preStart(Player op) {
        Bukkit.broadcastMessage("§aThe game is about to start !");
        op.sendMessage("§7(You can cancel the start by opening game settings)");
        AtomicInteger countdown = new AtomicInteger(10);
        started = true;
        preStartRunnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(Daedalus.getInstance(), () -> {

            if (!started) {
                logPlayers("§cStart canceled");
                Bukkit.getScheduler().cancelTask(preStartRunnable);
            }
            Daedalus.getInstance().getGameManager().setState(GameStatesEnum.WAIT);

            if (countdown.get() == 10) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle("§c10", "", 10, 20, 10);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 0.5f, 1f);
                }
            }

            if (countdown.get() <= 5 && countdown.get() > 0) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle("§4" + countdown.get(), "", 10, 20, 10);
                    p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 0.5f, 1f);
                }
            }

            if (countdown.get() == 0) {
                Bukkit.getScheduler().cancelTask(preStartRunnable);
                start();
            }
            logPlayers("§a§lGame starting in [§2" + countdown.get() + "s§a]");
            countdown.getAndDecrement();

        }, 0L, 20L);


    }

    // FONCTION DE DEBUT DE PARTIE
    public void start() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
            player.getActivePotionEffects().clear();
            player.setMaxHealth(20.0);
        }
        Bukkit.getScheduler().runTaskAsynchronously(Daedalus.getInstance(), () -> {
            //Maze generation
            Bukkit.broadcastMessage(Message.getPlayerPrefixe() + "§cStarting generation...");
            Location origin = new Location(Bukkit.getWorld("world"), 0, 0, 0);
            origin.setY(Bukkit.getWorld("world").getHighestBlockYAt((int) origin.getX(), (int) origin.getZ()) + 1);
            MazeHandler maze = new MazeHandler(origin, 300, true, Daedalus.getInstance().getStructureManager().getGeneratedList());
            maze.generateScaleMaze();

            Bukkit.getScheduler().runTask(Daedalus.getInstance(), () -> {
                //TODO Parameter
                Bukkit.getWorld("world").setTime(0);
                Bukkit.getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                //Player Manager
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Faction playerFaction = factionManager.getFactionOf(p);
                    if (playerFaction.getType() == FactionsEnum.SPECTATOR) {
                        p.setGameMode(GameMode.SPECTATOR);
                        p.teleport(new Location(p.getWorld(), 0, 125, 0));
                    } else {
                        p.setGameMode(GameMode.SURVIVAL);
                        p.teleport(playerFaction.getType().getSpawn());
                    }
                }
                //GameManager start
                getTimeManager().start();
                started = false;
                Bukkit.broadcastMessage("§cGame start GLHF !");
                setState(GameStatesEnum.RUNNING);
            });
        });


    }

    public TimeManager getTimer(){
        return this.timeManager;
    }

    // FONCTION DE FIN DE PARTIE
    public void stop() {
        getTimeManager().stop();
        this.state = GameStatesEnum.FINISHED;
        //TODO Mettre tout le monde en créatif
        //TODO FX de victoire

    }

    private void logPlayers(String msg) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            BaseComponent txt = new TextComponent(msg);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, txt);
        }

    }

}
