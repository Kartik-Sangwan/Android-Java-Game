package com.example.final_game.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.final_game.Infrastructure.GameActivity;
import com.example.final_game.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

  private LoginViewModel loginViewModel;
  static DataBaseHelper gameDb;
  static int count = 0;
  GoogleSignInClient mGoogleSignInClient;
  int RC_SIGN_IN = 0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    loginViewModel =
        ViewModelProviders.of(this, new LoginViewModelFactory()).get(LoginViewModel.class);
    gameDb = new DataBaseHelper(this);

    final EditText usernameEditText = findViewById(R.id.username);
    final EditText passwordEditText = findViewById(R.id.password);
    final Button loginButton = findViewById(R.id.login);
    final ProgressBar loadingProgressBar = findViewById(R.id.loading);
    final Button register = findViewById(R.id.register);
    final SignInButton signInButton = findViewById(R.id.Google_Sign_In);

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestEmail().build();

      mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
      signInButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent signInIntent = mGoogleSignInClient.getSignInIntent();
              startActivityForResult(signInIntent, RC_SIGN_IN);

          }
      });

    if (count == 0) {
      count++;
    } else {
      Toast.makeText(getApplicationContext(), "LOGOUT SUCCESSFUL", Toast.LENGTH_LONG).show();
      count++;
    }
    loginViewModel
        .getLoginFormState()
        .observe(
            this,
            new Observer<LoginFormState>() {
              @Override
              public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                  return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                  usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                  passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
              }
            });

    loginViewModel
        .getLoginResult()
        .observe(
            this,
            new Observer<LoginResult>() {
              @Override
              public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                  return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                  showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                  updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);
              }
            });

    TextWatcher afterTextChangedListener =
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // ignore
          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            // ignore
          }

          @Override
          public void afterTextChanged(Editable s) {
            loginViewModel.loginDataChanged(
                usernameEditText.getText().toString(), passwordEditText.getText().toString());
          }
        };
    usernameEditText.addTextChangedListener(afterTextChangedListener);
    passwordEditText.addTextChangedListener(afterTextChangedListener);
    passwordEditText.setOnEditorActionListener(
        new TextView.OnEditorActionListener() {

          @Override
          public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
              loginViewModel.login(
                  usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
            return false;
          }
        });

    loginButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(
                usernameEditText.getText().toString(), passwordEditText.getText().toString());
            String usr = usernameEditText.getText().toString();
            String pass = passwordEditText.getText().toString();
            boolean res = gameDb.checkUser(usr, pass);
            if (res) {
              Intent intent = new Intent(MainActivity.this, GameActivity.class);
              Toast.makeText(
                      getApplicationContext(),
                      "WELCOME " + usr.substring(0, usr.indexOf("@")) + "!",
                      Toast.LENGTH_LONG)
                  .show();
              startActivity(intent);
            } else {
              Toast.makeText(
                      getApplicationContext(),
                      "INCORRECT USERNAME OR PASSWORD. PLEASE SIGN UP FIRST",
                      Toast.LENGTH_LONG)
                  .show();
            }
          }
        });

    register.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            String usr = usernameEditText.getText().toString();
            String pass = passwordEditText.getText().toString();
            gameDb.createUser(usr, pass);
            Toast.makeText(getApplicationContext(), "USER CREATED PLEASE LOGIN.", Toast.LENGTH_LONG)
                .show();
          }
        });
  }

  private void updateUiWithUser(LoggedInUserView model) {
    String welcome = getString(R.string.welcome) + model.getDisplayName();
    // TODO : initiate successful logged in experience
    Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
  }

  private void showLoginFailed(@StringRes Integer errorString) {
    Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
      try {
          GoogleSignInAccount account = completedTask.getResult(ApiException.class);
          String usr = account.getDisplayName();
          gameDb.createUser(usr, "password");
          Intent intent = new Intent(MainActivity.this, GameActivity.class);
          startActivity(intent);
      } catch (Exception e) {
          Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
      }
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            startActivity(new Intent(MainActivity.this, GameActivity.class));
        }
        super.onStart();
    }

    public static DataBaseHelper getGameDb() {
    return gameDb;
  }
}
