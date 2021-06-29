package AilsaSyaffaDynia.jwork_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class MainActivity, berfungsi untuk mengatur avtivity setelah proses login
 * @author Ailsa Syaffa Dynia
 * @version 28-06-2021
 */

public class MainActivity extends AppCompatActivity {
    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobIdList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    private static int jobSeekerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            jobSeekerId = extras.getInt("jobseekerid");
        }

        /**
         * Digunakan untuk mendapakan list job yang ada
         */
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        refreshList();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(MainActivity.this, ApplyJobActivity.class);
                int jobId = childMapping.get(listRecruiter.get(groupPosition)).get(childPosition).getId();
                String jobName = childMapping.get(listRecruiter.get(groupPosition)).get(childPosition).getName();
                String jobCategory = childMapping.get(listRecruiter.get(groupPosition)).get(childPosition).getCategory();
                int jobFee = childMapping.get(listRecruiter.get(groupPosition)).get(childPosition).getFee();

                intent.putExtra("job_id", jobId);
                intent.putExtra("job_name", jobName);
                intent.putExtra("job_category", jobCategory);
                intent.putExtra("job_fee", jobFee);
                intent.putExtra("jobseekerId", jobSeekerId);
                startActivity(intent);
                return true;
            }
        });
    }

    /**
     * Untuk mendapatkan Json object
     */
    protected void refreshList(){
        Response.Listener<String> responseListener = response -> {
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

                        Location location1 = new Location(province, city, description);

                        int recruiterId = recruiter.getInt("id");
                        String recruiterName = recruiter.getString("name");
                        String recruiterEmail = recruiter.getString("email");
                        String recruiterPhoneNumber = recruiter.getString("phoneNumber");

                        Recruiter newRecruiter = new Recruiter(recruiterId, recruiterName, recruiterEmail, recruiterPhoneNumber, location1);
                        if (listRecruiter.size() > 0) {
                            boolean success = true;
                            for (Recruiter rec : listRecruiter)
                                if (rec.getId() == newRecruiter.getId())
                                    success = false;
                            if (success) {
                                listRecruiter.add(newRecruiter);
                            }
                        } else {
                            listRecruiter.add(newRecruiter);
                        }

                        int jobId = job.getInt("id");
                        int jobFee = job.getInt("fee");
                        String jobName = job.getString("name");
                        String jobCategory = job.getString("category");

                        Job newJob = new Job(jobId, jobName, newRecruiter, jobFee, jobCategory);
                        jobIdList.add(newJob);

                        for (Recruiter rec : listRecruiter) {
                            ArrayList<Job> temp = new ArrayList<>();
                            for (Job job2 : jobIdList) {
                                if (job2.getRecruiter().getId() == rec.getId()) {
                                    temp.add(job2);
                                }
                            }
                            childMapping.put(rec, temp);
                        }
                    }
                    listAdapter = new MainListAdapter(MainActivity.this, listRecruiter, childMapping);
                    expListView.setAdapter(listAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };

        /**
         * Untuk tombol applied job
         */
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
        Button btnAppliedJob = findViewById(R.id.btnAppliedJob);
        btnAppliedJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelesaiJobActivity.class);
                intent.putExtra("jobseekerid", jobSeekerId);
                startActivity(intent);
            }
        });
    }
}
