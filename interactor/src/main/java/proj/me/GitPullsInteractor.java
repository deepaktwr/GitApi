package proj.me;

import java.util.List;

/**
 * Created by root on 13/2/18.
 */

public interface GitPullsInteractor extends Interactor{
    void fetchAllGitPulls(String userName, String repoName, Callback callback);

    interface Callback {
        void gitPullsSuccess(List<GitResponse> gitResponse);
        void gitPullsFailed(ExceptionBundle exceptionBundle);
    }
}
