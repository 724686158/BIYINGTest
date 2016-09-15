package com.example.admin.biyingtest;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextView textView_answer_word;

    TextView textView_answer_amep;

    Button textView_answer_ames;

    TextView textView_answer_brep;

    Button textView_answer_bres;

    TextView textView_answer_pos1;
    TextView textView_answer_mn1;

    TextView textView_answer_pos2;
    TextView textView_answer_mn2;

    TextView textView_answer_pos3;
    TextView textView_answer_mn3;

    TextView textView_answer_pos4;
    TextView textView_answer_mn4;

    TextView textView_answer_pos5;
    TextView textView_answer_mn5;

    Button button_do_translate;
    EditText editText_edit_text;

    View answer_view;

    private Answer answer;

    public static final String BingURL = new String("http://xtk.azurewebsites.net/BingService.aspx");
    public static String amesURL;
    public static String bresURL;
    private MediaPlayer ames_mediaPlayer;
    private MediaPlayer bres_mediaPlayer;


    public static final int TEANSLATE_SUCCESS = 1;
    public static final int TEANSLATE_FALL = -1;

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case TEANSLATE_SUCCESS:
                    showAnswer();
                    Log.d("THIS", answer.toString());
                    break;
                case TEANSLATE_FALL:
                    break;
                default:
                    break;
            }
        }

    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    private void showAnswer() {

        if (answer != null)
        {
            answer_view.setVisibility(View.VISIBLE);

            textView_answer_word.setText(answer.getWord());
            Log.d("!!!", answer.getAmep());

            textView_answer_amep.setText(("美『 " + answer.getAmep() + " 』").toString());

            amesURL = answer.getAmes();
            ames_mediaPlayer = new MediaPlayer();

            initMediaPlayer(ames_mediaPlayer, amesURL);//调整要播放的mp3的网址
            textView_answer_brep.setText("英『 " + answer.getBrep() + " 』".toString());
            bresURL = answer.getBres();
            bres_mediaPlayer = new MediaPlayer();
            initMediaPlayer(bres_mediaPlayer, bresURL);
            if (answer.getPos1() != null) {
                textView_answer_pos1.setVisibility(View.VISIBLE);
                textView_answer_mn1.setVisibility(View.VISIBLE);
                textView_answer_pos1.setText(answer.getPos1());
                textView_answer_mn1.setText(answer.getMn1());
            }
            else
            {
                textView_answer_pos1.setVisibility(View.INVISIBLE);
                textView_answer_mn1.setVisibility(View.INVISIBLE);
            }

            if (answer.getPos2() != null) {
                textView_answer_pos2.setVisibility(View.VISIBLE);
                textView_answer_mn2.setVisibility(View.VISIBLE);
                textView_answer_pos2.setText(answer.getPos2());
                textView_answer_mn2.setText(answer.getMn2());
            }
            else
            {
                textView_answer_pos2.setVisibility(View.INVISIBLE);
                textView_answer_mn2.setVisibility(View.INVISIBLE);
            }

            if (answer.getPos3() != null) {
                textView_answer_pos3.setVisibility(View.VISIBLE);
                textView_answer_mn3.setVisibility(View.VISIBLE);
                textView_answer_pos3.setText(answer.getPos3());
                textView_answer_mn3.setText(answer.getMn3());
            }
            else
            {
                textView_answer_pos3.setVisibility(View.INVISIBLE);
                textView_answer_mn3.setVisibility(View.INVISIBLE);
            }

            if (answer.getPos4() != null) {
                textView_answer_pos4.setVisibility(View.VISIBLE);
                textView_answer_mn4.setVisibility(View.VISIBLE);
                textView_answer_pos4.setText(answer.getPos4());
                textView_answer_mn4.setText(answer.getMn4());
            }
            else
            {
                textView_answer_pos4.setVisibility(View.INVISIBLE);
                textView_answer_mn4.setVisibility(View.INVISIBLE);
            }

            if (answer.getPos5() != null) {
                textView_answer_pos5.setVisibility(View.VISIBLE);
                textView_answer_mn5.setVisibility(View.VISIBLE);
                textView_answer_pos5.setText(answer.getPos5());
                textView_answer_mn5.setText(answer.getMn5());
            }
            else
            {
                textView_answer_pos5.setVisibility(View.INVISIBLE);
                textView_answer_mn5.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            answer_view.setVisibility(View.INVISIBLE);
        }
    }

    private void initMediaPlayer(MediaPlayer mediaPlayer, String URL){
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(URL);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answer_view = findViewById(R.id.answer_view);

        textView_answer_word = (TextView) findViewById(R.id.answer_word);

        textView_answer_amep = (TextView) findViewById(R.id.answer_amep);

        textView_answer_ames = (Button) findViewById(R.id.answer_ames);

        textView_answer_brep = (TextView) findViewById(R.id.answer_brep);

        textView_answer_bres = (Button) findViewById(R.id.answer_bres);

        textView_answer_pos1 = (TextView) findViewById(R.id.answer_pos1);
        textView_answer_mn1 = (TextView) findViewById(R.id.answer_mn1);

        textView_answer_pos2 = (TextView) findViewById(R.id.answer_pos2);
        textView_answer_mn2 = (TextView) findViewById(R.id.answer_mn2);

        textView_answer_pos3 = (TextView) findViewById(R.id.answer_pos3);
        textView_answer_mn3 = (TextView) findViewById(R.id.answer_mn3);

        textView_answer_pos4 = (TextView) findViewById(R.id.answer_pos4);
        textView_answer_mn4 = (TextView) findViewById(R.id.answer_mn4);

        textView_answer_pos5 = (TextView) findViewById(R.id.answer_pos5);
        textView_answer_mn5 = (TextView) findViewById(R.id.answer_mn5);

        button_do_translate = (Button) findViewById(R.id.do_translate);
        editText_edit_text = (EditText) findViewById(R.id.edit_text);
        //先清空下
        showAnswer();


        button_do_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendRequsetWithHttpClient(editText_edit_text.getText().toString());
            }
        });
        editText_edit_text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editText_edit_text.setText(editText_edit_text.getText().toString());//添加这句后实现效果
                Spannable content = editText_edit_text.getText();
                Selection.selectAll(content);

            }
        });

        textView_answer_ames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(amesURL != null)
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ames_mediaPlayer.start();
                        }
                    }).run();

                }
            }
        });

        textView_answer_bres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bresURL != null)
                {
                    bres_mediaPlayer.start();
                }
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void sendRequsetWithHttpClient(final String word) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(BingURL);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("Action", "search"));
                params.add(new BasicNameValuePair("Word", word));
                params.add(new BasicNameValuePair("Format", "jsonwv"));
                try {
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
                    httpPost.setEntity(entity);
                    try {
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        if (httpResponse.getStatusLine().getStatusCode() == 200) {
                            //接下来使用GSON进行解析
                            HttpEntity httpEntity = httpResponse.getEntity();
                            String response = EntityUtils.toString(httpEntity, "utf-8");
                            parseJSONWithGSON(response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (answer != null)
                {
                    Message message = new Message();
                    message.what = TEANSLATE_SUCCESS;
                    handler.sendMessage(message);
                }
                else
                {
                    Message message = new Message();
                    message.what = TEANSLATE_FALL;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        //Log.d("!!!!!!!!", jsonData);
        Gson gson = new Gson();
        answer = gson.fromJson(jsonData, new TypeToken<Answer>() {
        }.getType());
        //Log.d("MainActivity", answer.toString());
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        Action action = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.admin.biyingtest/http/host/path")
        );
        AppIndex.AppIndexApi.start(mClient, action);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action action = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.admin.biyingtest/http/host/path")
        );
        AppIndex.AppIndexApi.end(mClient, action);
        mClient.disconnect();

        if (ames_mediaPlayer != null)
        {
            ames_mediaPlayer.stop();
            ames_mediaPlayer.release();
        }

        if (bres_mediaPlayer != null)
        {
            bres_mediaPlayer.stop();
            bres_mediaPlayer.release();
        }
    }
}
