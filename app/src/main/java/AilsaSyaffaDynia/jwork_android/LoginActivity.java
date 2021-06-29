package AilsaSyaffaDynia.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTransparentStatusBar(LoginActivity.this);
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button btLogin = findViewById(R.id.btLogin);
        final TextView tvRegist = findViewById(R.id.tvRegist);
        final Button btRegist = findViewById(R.id.btRegist);

        /**
         * Saat tombol login ditekan, maka field password dan email akan diperiksa, apakah sesuai regex
         */
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regexEmail = "^([\\w\\&\\*_~]+\\.{0,1})+@[\\w][\\w\\-]*(\\.[\\w\\-]+)+$";
                String regexPassword = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$";

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Email dibutuhkan");
                    btLogin.setEnabled(true);
                    return;
                }

                if(!Pattern.matches(regexEmail, email)){
                    etEmail.setError("Email tidak valid");
                    btLogin.setEnabled(true);
                    return;
                }

                if(!Pattern.matches(regexPassword, password)){
                    etPassword.setError("Password tidak valid");
                    btLogin.setEnabled(true);
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    etPassword.setError("Password dibutuhkan");
                    btLogin.setEnabled(true);
                    return;
                }
                else{
                    btLogin.setEnabled(false);
                }

                /**
                 * Json object untuk menerima response
                 */
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("jobseekerid", jsonObject.getInt("id"));
                                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        }
                        catch(JSONException e){
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        /**
         * Untuk tombol register dan mengaraj ke RegisterActivity
         */
        tvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Untuk membuat status bar transparan dan full screen
     * @param activity
     */
    public void setTransparentStatusBar(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
