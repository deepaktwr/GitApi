package proj.me.presentation;

import java.util.List;

import proj.me.ExceptionBundle;
import proj.me.GitResponse;

/**
 * Created by root on 13/2/18.
 */

public interface GitPullsPresentor extends Presentor{
    void getAllPulls(String userName, String repoName);

    interface Callback extends View{
        void pullsSuccess(List<GitResponse> gitResponse);
        void pullsFailed(ExceptionBundle exceptionBundle);
    }
}
