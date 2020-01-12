package com.example.barcodescanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResultActivity extends AppCompatActivity {
    private TextView mTextViewResult;
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
        mTextViewResult = findViewById(R.id.text_view_result);
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

                    ResultActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewResult.setText(myResponse);
                        }
                    });
                }
            }
        });

    }
}
