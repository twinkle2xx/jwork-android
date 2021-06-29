package AilsaSyaffaDynia.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class MenuRequest, untuk membuat menu yang dipanggil dari activity
 * @author Ailsa Syaffa Dynia
 * @version 28-06-2021
 */

public class MenuRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8080/job";
    private Map<String, String> params;

    /**
     * Menu request yang diminta pada MainActivity
     * @param listener response yang dilakukan dari objek pada view
     */
    public MenuRequest(Job job, Response.Listener<String> listener){
        super(Method.GET, URL, listener, null);
        //params = new HashMap<>();
        //params.put("id", String.valueOf(job.getId()));
        //params.put("name", job.getName());
        //params.put("recruiter", String.valueOf(job.getRecruiter()));
        //params.put("fee", String.valueOf(job.getFee()));
        //params.put("category", job.getCategory());
    }

    /**
     * Mengembalikan parameter Map dari POST yang digunakan untuk request job
     * @return parameter request yang ada
     * @throws AuthFailureError jika ada kesalahan autentikasi
     */
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}