package proj.me.repository;

import java.util.List;

import proj.me.GitResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by root on 13/2/18.
 */

public interface GitPullsService {
    @GET("repos/{user_name}/{repo_name}/pulls")
    Call<List<GitResponse>> getAllGitPulls(@Path("user_name") String userName, @Path("repo_name") String repoName);
}
