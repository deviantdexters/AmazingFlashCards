package com.sahilguptalive.amazingflashcard;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.LocalErrors;
import com.example.ValidationIssue;
import com.sahilguptalive.amazingflashcard.dbmanager.BookmarkedWordDbManager;
import com.sahilguptalive.storage.info.SourceOfWord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity {

    public static final String PATHNAME = "WordWebSoftware";
    private static final int REQ_GRANT_EXTERNAL_STORAGE = 123;
    private static final String[] STORAGE_PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    @BindView(R.id.root_layout)
    ConstraintLayout mRootLayout;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        readFileNotSafe();
    }

    private void readFileSafe() {
        final File wordWebExtDir
                = new File(Environment.getExternalStorageDirectory(), PATHNAME);
        if (wordWebExtDir.exists() && wordWebExtDir.isDirectory()) {
            File bookmarkWordsFile = new File(wordWebExtDir, "bookmarks.txt");
            if (bookmarkWordsFile.exists() && bookmarkWordsFile.isFile()) {
                proceedWithWordWebBookmarksReading(bookmarkWordsFile);
                return;
            }
        }
        IssuesAndValidationHandler
                .showMessageToUserFor(mContext, ValidationIssue.WORD_WEB_FILE_NOT_FOUND);
    }

    public void readFileNotSafe() {
        //Step-1 check if SDK is below marshmallow, if yes show contacts directly. Else move to Step-2
        //This step can be skipped, since we are using app compat activity, which is backward compatible.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            readFileSafe();
            return;
        }
        //Step-2 check if required permissions have been granted. If yes show contacts directly. Else move to Step-3
        if (isPermissionGranted(STORAGE_PERMISSION)) {
            readFileSafe();
            return;
        }
        //Step-3 Check if we need to display rationale behind requesting this permission.
        //If yes, show some relevant text to show rationale behind this permission.
        //Else move to Step-6
        if (ActivityCompat
                .shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat
                .shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showRationaleForContactPermission();
            return;
        }

        //Step-6 Request for contacts permission.
        requestForStoragePermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_GRANT_EXTERNAL_STORAGE) {
            //Step-7 We need to check if we have been granted the requested permissions
            //Since grant results and permissions array can be empty if interaction with user is interrupted
            //Therefore, We have to regard this as a failure to have requested permissions.
            if (grantResults.length != 1 || permissions.length != 1) {
                //no permission have been granted. This is a failure.
                //If this requested permission is very critical for app,
                //then app may request for granting permissions again from this point of code.
                informOfContactPermissionGrantFailure();
                return;
            }
            //If granted results have any one of the element as PackageManager.PERMISSION_DENIED
            //,then it is also a failure to have requested permissions.
            if ((grantResults[0] == PackageManager.PERMISSION_DENIED)) {
                //any one or all of requested permission have not been granted. This is a failure.
                //If this requested permission is very critical for app,
                //then app may request for granting permissions again from this point of code.
                informOfContactPermissionGrantFailure();
                return;
            }
            //Step-8 Since all checks have been made we have been granted the requested permissions.
            //Therefore we can show contacts
            readFileSafe();
        }
    }

    private void showRationaleForContactPermission() {
        //Step-4 show snack-bar with string and action
        Snackbar.make(mRootLayout
                , R.string.rationale_for_storage_permission
                , Snackbar.LENGTH_LONG)
                .setAction(android.R.string.ok, new ContactRationaleActionClickListener())
                .show();
    }

    /**
     * This implementation is application requirements specific. Some app may opt to skip this part
     */
    private void informOfContactPermissionGrantFailure() {
        Snackbar.make(mRootLayout
                , R.string.failure_of_storage_permission
                , Snackbar.LENGTH_LONG)
                .show();
    }

    private void requestForStoragePermission() {
        ActivityCompat.requestPermissions(this
                , STORAGE_PERMISSION
                , REQ_GRANT_EXTERNAL_STORAGE);
    }

    private boolean isPermissionGranted(String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void proceedWithWordWebBookmarksReading(@NonNull final File wordWebBookmarkFile) {
        try {
            final FileInputStream fileInputStream = new FileInputStream(wordWebBookmarkFile);
            final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readWord = bufferedReader.readLine();
            while (readWord != null) {
                BookmarkedWordDbManager.insertIfNotAdded(SourceOfWord.WORDWEB_BOOKMARK, readWord);
                readWord = bufferedReader.readLine();
            }
            mContext.startActivity(FlashViewerActivity.createIntent(mContext));
        } catch (IOException e) {
            IssuesAndValidationHandler.showMessageToUserFor(mContext, LocalErrors.FILE_READING_FAILED);
        }
    }

    private class ContactRationaleActionClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            //Step-5 When user accepts the rationale, we can again request for permissions.
            requestForStoragePermission();
        }
    }
}
