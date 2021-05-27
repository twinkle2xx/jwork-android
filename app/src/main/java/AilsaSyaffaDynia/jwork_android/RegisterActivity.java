package AilsaSyaffaDynia.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText etName = findViewById(R.id.etName);
        final EditText etEmail2 = findViewById(R.id.etEmail2);
        final EditText etPassword2 = findViewById(R.id.etPassword2);
        Button btRegist = findViewById(R.id.btRegist);

        btRegist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String name = etName.getText().toString();
                String email = etEmail2.getText().toString();
                String password = etPassword2.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null){
                                Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(JSONException e){
                            Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
            }
        });
    }
}