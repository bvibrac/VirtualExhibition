package com.example.vibrac_b.virtualexhibition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A login screen that offers login via email/password.
 */
public class Sign_UpActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private EditText mNameView;
    private EditText mSurnameView;
    private EditText mConfirmEmailView;
    private EditText mConfirmPasswordView;
    private TextInputLayout mEmailTextInputLayout;
    private TextInputLayout mPasswordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Set up the login form.
        mEmailTextInputLayout = (TextInputLayout) findViewById(R.id.email);
        mEmailView = (AutoCompleteTextView) mEmailTextInputLayout.getEditText();
        populateAutoComplete();

        mPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.password);
        mPasswordView = mPasswordTextInputLayout.getEditText();
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSurnameView = (EditText) findViewById(R.id.surname);
        mNameView = (EditText) findViewById(R.id.name);
        mConfirmEmailView = (EditText) findViewById(R.id.emailC);
        mConfirmPasswordView = (EditText) findViewById(R.id.passwordC);

        Button mEmailSignUpButton = (Button) findViewById(R.id.email_sign_up_button);
        mEmailSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString();
        String surname = mSurnameView.getText().toString();
        String email = mEmailView.getText().toString();
        String confirmE = mConfirmEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmP = mConfirmPasswordView.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        else if (!isEmailistheSame(email, confirmE)){
            Toast MailNotMatch = Toast.makeText(this, "Your mail does not match", Toast.LENGTH_LONG);
            MailNotMatch.setGravity(Gravity.RIGHT| Gravity.LEFT, 0,0);
            MailNotMatch.show();
            return;
        }

        else if (!isPasswordistheSame(password, confirmP)){
            Toast PasswordNotMatch = Toast.makeText(this, "Your password does not match", Toast.LENGTH_LONG);
            PasswordNotMatch.setGravity(Gravity.RIGHT| Gravity.LEFT, 0,0);
            PasswordNotMatch.show();
            return;
        }

        else if(name.isEmpty()){
            Toast NameIsMissing = Toast.makeText(this, "Your name is missing", Toast.LENGTH_LONG);
            NameIsMissing.setGravity(Gravity.RIGHT| Gravity.LEFT, 0,0);
            NameIsMissing.show();
            return;
        }

        else if(surname.isEmpty()) {
            Toast NameIsMissing = Toast.makeText(this, "Your surname is missing", Toast.LENGTH_LONG);
            NameIsMissing.setGravity(Gravity.RIGHT | Gravity.LEFT, 0, 0);
            NameIsMissing.show();
            return;
        }
        else if(email.isEmpty()) {
            Toast NameIsMissing = Toast.makeText(this, "Your email is missing", Toast.LENGTH_LONG);
            NameIsMissing.setGravity(Gravity.RIGHT | Gravity.LEFT, 0, 0);
            NameIsMissing.show();
            return;
        }

        else if(confirmE.isEmpty()) {
            Toast NameIsMissing = Toast.makeText(this, "Your email is not confirm", Toast.LENGTH_LONG);
            NameIsMissing.setGravity(Gravity.RIGHT | Gravity.LEFT, 0, 0);
            NameIsMissing.show();
            return;
        }

        else if(password.isEmpty()) {
            Toast NameIsMissing = Toast.makeText(this, "Your password is missing", Toast.LENGTH_LONG);
            NameIsMissing.setGravity(Gravity.RIGHT | Gravity.LEFT, 0, 0);
            NameIsMissing.show();
            return;
        }

        else if(confirmP.isEmpty()) {
            Toast NameIsMissing = Toast.makeText(this, "Your password is not confirm", Toast.LENGTH_LONG);
            NameIsMissing.setGravity(Gravity.RIGHT | Gravity.LEFT, 0, 0);
            NameIsMissing.show();
            return;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            RequestParams params = new RequestParams();
            params.add("email", email);
            params.add("name", name);
            params.add("password", password);
            RestClient.post("api/users", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject jsonObject = new JSONObject(new String(responseBody));
                        Intent intent = new Intent(getApplicationContext(), Profil_Gestion.class);
                        intent.putExtra("token", jsonObject.getString("token"));
                        startActivity(intent);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }
    }

    private boolean isAllFieldFill(String name, String surname, String email, String confirmE, String password, String confirmP)
    {
        if (name != null && surname != null && email != null && confirmE != null && password != null && confirmP != null){
            return true;
        }
        return false;

    }

    private boolean isEmailistheSame(String email, String confirmE)
    {
        return (email.equals(confirmE));
    }

    private boolean isPasswordistheSame (String password, String confirmP)
    {
        return (password.equals(confirmP));
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Sign_UpActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}

