package com.example.barcodescanner;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.squareup.picasso.Picasso;

public class ResultActivity extends AppCompatActivity {
    private TextView styleText;
    private TextView materialsText;
    private TextView manutext;
    private TextView estText;
    private TextView scoreText;
    public ImageView img;
    private JSONObject trial;
    private String materialString;
    private String imgURL;
    private Iterator<String> keys;
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
        scoreText = findViewById(R.id.score);
        img = (ImageView) findViewById(R.id.img);
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
                        imgURL = trial.getJSONObject("image").getString("1");
                        keys = trial.getJSONObject("composition").keys();
                        while(keys.hasNext()){
                            String key = keys.next();
                            String val = trial.getJSONObject("composition").getString(key);
                            Log.i("info", val );
                            if(val != null && key != null) {
                                materialString += (key + "(" + val +"); ");
                            }
                        }
                        materialString = materialString.substring(4, materialString.length() -2);

//                        for (int i = 0; i < materialList.length(); i++) {
//                            JSONObject jsonObj = materialList.getJSONObject(i);
//                            String k = jsonObj.keys().next();
//                            materialString += (k+ " "+ jsonObj.getInt("k"));
//                            Log.i("Info", "Key: " + k + ", value: " + jsonObj.getString(k));
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ResultActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                styleText.setText(trial.getString("style"));
                                manutext.setText(trial.getString("madeIn"));
                                materialsText.setText(materialString);
                                Picasso.get().load(imgURL).into(img);
                                switch(trial.getString("madeIn")){
                                    case "India":
                                        estText.setText("3.80");
                                        if(materialString.contains("cotton")){
                                            scoreText.setText("61");
                                            scoreText.setTextColor(Color.YELLOW);
                                        }
                                         break;
                                    case "USA":
                                        estText.setText("2.35");
                                        if(materialString.contains("cotton")){
                                            scoreText.setText("95");
                                            scoreText.setTextColor(Color.GREEN);
                                        }
                                        break;
                                    case "Bangladesh":
                                        estText.setText("3.89");
                                        if(materialString.contains("cotton")){
                                            scoreText.setText("55");
                                            scoreText.setTextColor(Color.RED);
                                        }
                                        break;
                                    default:
                                        estText.setText("4.24");
                                        scoreText.setText("35");
                                        scoreText.setTextColor(Color.RED);
                                        break;
                                }
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
