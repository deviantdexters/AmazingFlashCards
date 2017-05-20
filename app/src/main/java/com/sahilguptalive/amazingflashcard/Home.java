package com.sahilguptalive.amazingflashcard;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.LocalErrors;
import com.example.ValidationIssue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Home extends AppCompatActivity {

    public static final String PATHNAME = "WordWebSoftware";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final File wordWebExtDir
                = new File(Environment.getExternalStorageDirectory(), PATHNAME);
        if (wordWebExtDir.exists() && wordWebExtDir.isDirectory()) {
            File bookmarFile = new File(wordWebExtDir, "bookmarks.txt");
            if (bookmarFile.exists() && bookmarFile.isFile()) {
                proceedWithWordWebBookmarksReading(bookmarFile);
                return;
            }
        }
        IssuesAndValidationHandler
                .showMessageToUserFor(ValidationIssue.WORD_WEB_FILE_NOT_FOUND);
    }

    private void proceedWithWordWebBookmarksReading(@NonNull final File wordWebBookmarkFile) {
        try {
            final FileInputStream fileInputStream = new FileInputStream(wordWebBookmarkFile);
            final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine = bufferedReader.readLine();
            while (readLine != null) {
                readLine = bufferedReader.readLine();
            }
        } catch (IOException e) {
            IssuesAndValidationHandler.showMessageToUserFor(LocalErrors.FILE_READING_FAILED);
        }
    }
}
