package com.sahilguptalive.amazingflashcard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.LocalErrors;
import com.example.ValidationIssue;

/**
 * Created on 20-05-2017.
 */
public class IssuesAndValidationHandler {

    static void showMessageToUserFor(@NonNull final Context context,
                                     @NonNull final ValidationIssue validationIssue) {

        Toast.makeText(context, resolve(validationIssue), Toast.LENGTH_LONG).show();
    }

    static void showMessageToUserFor(@NonNull final Context context,
                                     @NonNull final LocalErrors localErrors) {
        Toast.makeText(context, resolve(localErrors), Toast.LENGTH_LONG).show();
    }

    private static int resolve(final LocalErrors localErrors) {
        switch (localErrors) {
            case FILE_READING_FAILED:
            default:
                return R.string.local_error_file_reading_failed;
        }
    }

    private static int resolve(ValidationIssue validationIssue) {
        switch (validationIssue) {
            case WORD_WEB_FILE_NOT_FOUND:
            default:
                return R.string.validation_issue_unable_to_locate_file;
        }
    }
}
