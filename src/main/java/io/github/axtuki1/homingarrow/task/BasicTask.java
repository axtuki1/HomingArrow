package io.github.axtuki1.homingarrow.task;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class BasicTask extends BukkitRunnable {

    private final JavaPlugin plugin;

    private BukkitTask task;

    private boolean isPaused = false;

    public BasicTask(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public enum Status {
        READY,
        RUNNING,
        PAUSED,
    }

    /**
     * 現在のステータスを返す
     * @return ステータス
     */
    public Status getStatus() {

        if ( isPaused ) {
            return Status.PAUSED;
        }
        if  ( task != null ) {
            return Status.RUNNING;
        }
        return Status.READY;
    }

    /**
     * タスクを一時停止する
     */
    public void pause(){
        isPaused = true;
    }

    /**
     * 一時停止にあるタスクを再開する
     */
    public void resume() {
        if ( isPaused ) {
            isPaused = false;
        }
    }

    /**
     * タスクを開始する。
     */
    public void start(){
        if( task == null ){
            task = runTaskTimer(plugin, 1, 1);
        }
    }

    /**
     * タスクを開始する。
     */
    public void start(int periodTick){
        if( task == null ){
            task = runTaskTimer(plugin, 1, periodTick);
        }
    }

    /**
     * タスクを開始する。
     */
    public void start(int delaytick, int periodTick){
        if( task == null ){
            task = runTaskTimer(plugin, delaytick, periodTick);
        }
    }

    /**
     * タスクを強制終了する。
     * このメソッドで終了した場合、再開はできない。
     */
    public void stop(){
        if( task != null ){
            cancel();
            task = null;
        }
    }

    /**
     * タスクを終了する。
     * このメソッドで終了した場合、再開はできない。
     */
    public void end() {
        stop();
    }
}
