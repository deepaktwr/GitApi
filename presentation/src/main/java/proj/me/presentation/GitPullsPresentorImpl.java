package proj.me.presentation;

import java.util.List;

import proj.me.ExceptionBundle;
import proj.me.GitPullsInteractor;
import proj.me.GitResponse;

/**
 * Created by root on 13/2/18.
 */

public class GitPullsPresentorImpl implements GitPullsPresentor {

    GitPullsInteractor gitPullsInteractor;
    Callback callback;
    public GitPullsPresentorImpl(GitPullsInteractor gitPullsInteractor, Callback callback){
        this.gitPullsInteractor = gitPullsInteractor;
        this.callback = callback;
    }

    @Override
    public void getAllPulls(String userName, String repoName) {
        callback.startLoading();
        gitPullsInteractor.fetchAllGitPulls(userName, repoName, interactorCallback);
    }

    GitPullsInteractor.Callback interactorCallback = new GitPullsInteractor.Callback() {
        @Override
        public void gitPullsSuccess(List<GitResponse> gitResponse) {
            callback.finishLoading();
            callback.pullsSuccess(gitResponse);
        }
        @Override
        public void gitPullsFailed(ExceptionBundle exceptionBundle) {
            callback.finishLoading();
            callback.pullsFailed(exceptionBundle);
        }
    };
}
