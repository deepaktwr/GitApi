package proj.me.gitapi;

import android.os.Handler;
import android.os.Looper;

import proj.me.ForegroundExecutor;

/**
 * Created by root on 13/2/18.
 */

public class ForegroudExecutorImpl implements ForegroundExecutor {
    private static final class UIThreadInstanceHolder{
        private static ForegroudExecutorImpl INSTANCE = new ForegroudExecutorImpl();
    }

    public static ForegroudExecutorImpl getInstance(){
        return UIThreadInstanceHolder.INSTANCE;
    }

    private Handler handler;
    private ForegroudExecutorImpl(){
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
