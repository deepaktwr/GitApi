package proj.me.repository;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import proj.me.BackgroudExecutor;

/**
 * Created by root on 13/2/18.
 */

public class BackgroundExecutorImpl implements BackgroudExecutor {

    private static final int INITIAL_POOL_SIZE = 1;
    private static final int MAX_POOL_SIZE = 1;
    private static final int KEEP_ALIVE_TIME = 10;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private static final class ExecutorInstanceHolder{
        private static final BackgroundExecutorImpl INSTANCE = new BackgroundExecutorImpl();
    }

    public static BackgroundExecutorImpl getInstance(){
        return ExecutorInstanceHolder.INSTANCE;
    }

    private ThreadPoolExecutor threadPoolExecutor;

    private BackgroundExecutorImpl(){
        BlockingQueue<Runnable> wordQueue = new LinkedBlockingQueue<>();
        threadPoolExecutor = new ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, wordQueue);
    }

    @Override
    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }
}
