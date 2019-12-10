package ph.roadtrip.roadtrip;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class blockChainWallet extends AppCompatActivity {
    public blockChainWallet(){}
    private String responseData = "";

    public void blockchainVolley(Context context){
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        String new_add = "get_new_address";
        String server_urlpost = "https://block.io/api/v2/"+new_add +"/?api_key=4a06-1e71-bac7-fb7a"; //Points to target which is obtained from IPV4 Address from IP Config

        StringRequest stringRequestpost = new StringRequest(Request.Method.POST, server_urlpost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   //Server Response Handler
                        requestQueue.stop();
                        blockChainWallet.this.responseData = response;
                        Log.e("json", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {    //On Error Response Handler
                error.printStackTrace();
                requestQueue.stop();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                Log.i("", params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<String,String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(stringRequestpost);
    }

    public String getResponseData(){
        return this.responseData;
    }
}
