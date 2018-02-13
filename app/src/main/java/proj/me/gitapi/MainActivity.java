package proj.me.gitapi;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import proj.me.BackgroudExecutor;
import proj.me.ExceptionBundle;
import proj.me.ForegroundExecutor;
import proj.me.GitPullsInteractor;
import proj.me.GitPullsInteractorImpl;
import proj.me.GitPullsRepository;
import proj.me.GitResponse;
import proj.me.presentation.GitPullsPresentor;
import proj.me.presentation.GitPullsPresentorImpl;
import proj.me.repository.BackgroundExecutorImpl;
import proj.me.repository.GitPullsRepositoryImpl;

public class MainActivity extends AppCompatActivity implements GitPullsPresentor.Callback{

    private GitPullsPresentor gitPullsPresentor;
    private LinearLayout pullsContainer;
    private ProgressDialog progressDialog;
    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        ForegroundExecutor foregroundExecutor = ForegroudExecutorImpl.getInstance();
        BackgroudExecutor backgroudExecutor = BackgroundExecutorImpl.getInstance();
        GitPullsRepository gitPullsRepository = new GitPullsRepositoryImpl(this);
        GitPullsInteractor gitPullsInteractor = new GitPullsInteractorImpl(backgroudExecutor, foregroundExecutor, gitPullsRepository);

        gitPullsPresentor = new GitPullsPresentorImpl(gitPullsInteractor, this);

        final EditText userName = (EditText) findViewById(R.id.user_name);
        final EditText repoName = (EditText) findViewById(R.id.repo_name);
        pullsContainer = (LinearLayout) findViewById(R.id.pulls_container);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(userName.getText())) showMessage("Please provide user");
                else if(TextUtils.isEmpty(repoName.getText())) showMessage("Please provide repository");
                else gitPullsPresentor.getAllPulls(userName.getText().toString(), repoName.getText().toString());
            }
        });
    }

    void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pullsSuccess(List<GitResponse> gitResponse) {
        pullsContainer.removeAllViews();

        if(gitResponse == null || gitResponse.size() == 0){
            Log.e("res", "size 000000");
            TextView textView = (TextView) inflater.inflate(R.layout.text_item, null);
            textView.setText("No Pull requests found");

            pullsContainer.addView(textView);
        } else {
            Log.e("res", "size " + gitResponse.size());
            for (GitResponse response : gitResponse) {
                TextView textView = (TextView) inflater.inflate(R.layout.text_item, null);
                textView.setText(response.getTitle() + "\n" + response.getUrl()+"\n\n");

                pullsContainer.addView(textView);
            }
        }
        pullsContainer.requestLayout();
    }

    @Override
    public void pullsFailed(ExceptionBundle exceptionBundle) {
        pullsContainer.removeAllViews();
        showMessage(exceptionBundle.getMessage());
    }

    @Override
    public void startLoading() {
        progressDialog.show();
    }

    @Override
    public void finishLoading() {
        if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
    }
}
