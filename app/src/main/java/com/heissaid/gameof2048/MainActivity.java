package com.heissaid.gameof2048;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public MainActivity() {
        mainActivity=this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore=(TextView) findViewById(R.id.tvScore);
        highestScore=(TextView) findViewById(R.id.hiScore);




        // 读取历史最高分
        FileInputStream in;
        byte[] by = new byte[10];
        try {
            in = openFileInput(SCORE);
            in.read(by);
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < 10; i++) {
                if (by[i] != 0) {
                    buffer.append(by[i] - 48);
                }
            }
            Log.d("dd", buffer.toString());
            MainActivity.historyScore = Long.valueOf(buffer.toString());
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        highestScore.setText(historyScore+"");

    }



    public void clearScore() {
        score=0;
        showScore();
    }

    public void showScore(){
        tvScore.setText(score+"");
    }

    public void addScore(int s) {
        score+=s;
        showScore();
        showHighestScore();
    }




    @Override
    protected void onPause() {
        //如果分数超出了历史最高分，则保存数据
        if (overhistroy) {
            FileOutputStream fos;
            try {
                fos = openFileOutput(SCORE, Context.MODE_PRIVATE);
                fos.write(String.valueOf(historyScore).getBytes());
                fos.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onPause();
    }

    public void showHighestScore(){
        // 更新最高分
        if (score > historyScore) {
            overhistroy=true;
            historyScore = (long) score;
            highestScore.setText(historyScore+"");
        }
    }



    /**
     * 历史最高分
     */
    private static Long historyScore = (long) 0;

    /**
     * 是否超出了最高分，如果超出了，则在退出时会保存数据
     */
    private static boolean overhistroy=false;

    String SCORE="hiScore.txt";


    private static final String HIGH_KEY="hi-score";
    private int score=0;
    private TextView tvScore;
    private TextView highestScore;
    private static MainActivity mainActivity=null;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

}
