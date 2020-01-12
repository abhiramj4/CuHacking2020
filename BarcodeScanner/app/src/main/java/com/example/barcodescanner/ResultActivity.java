package com.example.barcodescanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResultActivity extends AppCompatActivity {
    private TextView styleText;
    private TextView materialsText;
    private TextView manutext;
    private TextView estText;
    private JSONObject trial;
//    int id = getIntent().getIntExtra("bid", 0 );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("bid");
        request(id);
    }

    private void request(int barcode){
        styleText     = findViewById(R.id.styleName);
        materialsText = findViewById(R.id.materials);
        manutext = findViewById(R.id.manu);
        estText = findViewById(R.id.est);
        String url = "https://ecostep.herokuapp.com/items/" + barcode;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //get json
                if(response.isSuccessful()){
                    final String myResponse = response.body().string();
                    try {
                        trial = new JSONObject(myResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ResultActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                styleText.setText(trial.getString("style"));
                                manutext.setText(trial.getString("madeIn"));
                                materialsText.setText(trial.getJSONObject("composition").getString("cotton"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

    }
}
