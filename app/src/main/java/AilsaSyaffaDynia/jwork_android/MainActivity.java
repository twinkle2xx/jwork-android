package AilsaSyaffaDynia.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobIdList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();

    Location loc1 = new Location("DKI Jakarta", "Jakut", "Jakarta Utara");
    Recruiter rct1 = new Recruiter(1,"Ailsa SD", "ailsasd@gmail.com", "081234567890", loc1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse != null) {
                        for (int i = 0; i < jsonResponse.length(); i++){
                            JSONObject job = jsonResponse.getJSONObject(i);
                            JSONObject recruiter = job.getJSONObject("recruiter");
                            JSONObject location = recruiter.getJSONObject("location");

                            String city = location.getString("city");
                            String province = location.getString("province");
                            String description = location.getString("description");

                            Location loc1 = new Location(city, province, description);

                            int recruiterId = recruiter.getInt("id");
                            String rctName = recruiter.getString("name");
                            String rctEmail = recruiter.getString("email");
                            String rctPhoneNumber = recruiter.getString("phoneNumber");

                            Recruiter rct1 = new Recruiter(recruiterId, rctName, rctEmail, rctPhoneNumber, loc1);
                            if (listRecruiter.size() > 0) {
                                boolean success = true;
                                for (Recruiter rec : listRecruiter)
                                    if (rec.getId() == rct1.getId())
                                        success = false;
                                if (success) {
                                    listRecruiter.add(rct1);
                                }
                            } else {
                                listRecruiter.add(rct1);
                            }

                            int jobId = job.getInt("id");
                            int jobPrice = job.getInt("price");
                            String jobName = job.getString("name");
                            String jobCategory = job.getString("category");

                            Job job1 = new Job(jobId, jobName, rct1, jobPrice, jobCategory);
                            jobIdList.add(job1);

                            for (Recruiter rct : listRecruiter) {
                                ArrayList<Job> tempRecruiter = new ArrayList<>();
                                for (Job jobs : jobIdList) {
                                    if (jobs.getRecruiter().getName().equals(rct.getName()) ||
                                            jobs.getRecruiter().getEmail().equals(rct.getEmail()) ||
                                            jobs.getRecruiter().getphoneNumber().equals(rct.getphoneNumber()))
                                    {
                                        tempRecruiter.add(jobs);
                                    }
                                }
                                childMapping.put(rct, tempRecruiter);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                };
            }
        };
    }
}