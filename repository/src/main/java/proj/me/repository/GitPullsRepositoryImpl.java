package proj.me.repository;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import proj.me.ExceptionBundle;
import proj.me.GitPullsRepository;
import proj.me.GitResponse;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by root on 13/2/18.
 */

public class GitPullsRepositoryImpl implements GitPullsRepository {

    private Context applcationContext;
    private static final int PULLS_CLASS_ID = 1;
    public GitPullsRepositoryImpl(Context context){
        applcationContext = context.getApplicationContext();
    }

    @Override
    public void fetchAllGitPulls(String userName, String repoName, Callback callback) {
        RetrofitClient<GitPullsService> retrofitClient = RetrofitClient.createClient();
        GitPullsService gitPullsService = retrofitClient.getRetrofitService(GitPullsService.class, PULLS_CLASS_ID);

        Call<List<GitResponse>> gitResponseCall = gitPullsService.getAllGitPulls(userName, repoName);
        try {
            Response<List<GitResponse>> gitResponse = gitResponseCall.execute();
            if(gitResponse.isSuccessful()) callback.gitPullsSuccess(gitResponse.body());
            else callback.gitPullsFailed(new PullsException("pulls request failed for user : "+userName+", for repository : "+repoName, new IllegalAccessException(gitResponse.message())));
        } catch (IOException e) {
            e.printStackTrace();
            callback.gitPullsFailed(new PullsException("pulls request failed for user : "+userName+", for repository : "+repoName, e));
        }
    }

    private static class PullsException implements ExceptionBundle{
        private String message;
        private Exception exception;
        public PullsException(String message, Exception exception){
            this.message = message;
            this.exception = exception;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public Exception getCause() {
            return exception;
        }
    }
}
