package proj.me;

import java.util.List;

/**
 * Created by root on 13/2/18.
 */

public class GitPullsInteractorImpl implements GitPullsInteractor {

    private BackgroudExecutor backgroudExecutor;
    private ForegroundExecutor foregroundExecutor;
    private GitPullsRepository gitPullsRepository;
    public GitPullsInteractorImpl(BackgroudExecutor backgroudExecutor, ForegroundExecutor foregroundExecutor, GitPullsRepository gitPullsRepository){
        this.backgroudExecutor = backgroudExecutor;
        this.foregroundExecutor = foregroundExecutor;
        this.gitPullsRepository = gitPullsRepository;
    }

    String userName, repoName;
    Callback callback;
    @Override
    public void fetchAllGitPulls(String userName, String repoName, Callback callback) {
        this.userName = userName;
        this.repoName = repoName;
        this.callback = callback;
        backgroudExecutor.execute(this);
    }

    @Override
    public void run() {
        gitPullsRepository.fetchAllGitPulls(userName, repoName, repositoryCallback);
    }

    GitPullsRepository.Callback repositoryCallback = new GitPullsRepository.Callback() {
        @Override
        public void gitPullsSuccess(List<GitResponse> gitResponse) {
            notifySuccess(gitResponse);
        }

        @Override
        public void gitPullsFailed(ExceptionBundle exceptionBundle) {
            notifyError(exceptionBundle);
        }
    };

    void notifySuccess(final List<GitResponse> gitResponse){
        foregroundExecutor.post(new Runnable() {
            @Override
            public void run() {
                callback.gitPullsSuccess(gitResponse);
            }
        });
    }

    void notifyError(final ExceptionBundle exceptionBundle){
        foregroundExecutor.post(new Runnable() {
            @Override
            public void run() {
                callback.gitPullsFailed(exceptionBundle);
            }
        });
    }
}
