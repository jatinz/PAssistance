package personal.root.passistance;
/*
 * @author Jatin Patro.
 * 2nd April 2018.
 * Login Authentication Program.
 */
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.view.View.*;

public class LoginActivity extends AppCompatActivity {

    private String uEmail,uPassword;
    private RadioButton rememberMeButton;
    private Button loginButton,signUpButton;
    private FirebaseAuth mAuth;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        uEmail = ((EditText)findViewById(R.id.userEmail)).toString();
        uPassword = ((EditText)findViewById(R.id.userPassword)).toString();
        final CredentialBean credentialBean = new CredentialBean(uEmail,uPassword);

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkLoginTexts(credentialBean))
                    if(validateUser())
                        Toast.makeText(getApplicationContext(),R.string.invalid_credential,Toast.LENGTH_SHORT);
                    else {
                        //start the intent if credential are right.
                    }
            }
        });
    }

    private Boolean checkLoginTexts(CredentialBean credentialBean){

        String uEmail = credentialBean.getuEmail();
        String getuPassword = credentialBean.getuPassword();
        if(uEmail.isEmpty() || uPassword.isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.empty_fields,Toast.LENGTH_SHORT);
            return false;
        }else if(uEmail.length()<5 || !uEmail.contains("@") || !uEmail.contains(".com")) {
            Toast.makeText(getApplicationContext(), R.string.incorrect_email_text,Toast.LENGTH_SHORT);
            return false;
        }else if(getuPassword.length()<6){
            Toast.makeText(getApplicationContext(), R.string.short_password,Toast.LENGTH_SHORT);
            return false;
        }else
            return true;

    }

    private Boolean validateUser(){
        //contains code to validate user against the firebase database.
        Toast.makeText(getApplicationContext(), "Trying to Login...",
                Toast.LENGTH_SHORT).show();
        try {
            mAuth.signInWithEmailAndPassword(uEmail, uPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), "Welcome",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}

class CredentialBean{

    private String uEmail,uPassword;

    CredentialBean(String uEmail,String uPassword){
        this.uEmail = uEmail;
        this.uPassword = uPassword;

    }
    public String getuEmail() {
        return uEmail;
    }

    public String getuPassword() {
        return uPassword;
    }
}
