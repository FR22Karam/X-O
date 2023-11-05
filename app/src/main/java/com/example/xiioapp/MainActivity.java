package com.example.xiioapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView PlayerOneScore , PlayerTwoScore ,PlayerStatus;
    private Button [] buttons = new Button[9];
    private Button restGame;
    private  int PlayerOneScoreCount , PlayerTwoScoreCount ,rountCount;
    boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};
    int [][] winnier={
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{2,4,6},{1,4,7}
            ,{2,5,8},{0,4,8}

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlayerOneScore = (TextView) findViewById(R.id.PlayerOneScore);
        PlayerTwoScore = (TextView)  findViewById(R.id.PlayerTwoScoure);
        PlayerStatus = (TextView)  findViewById(R.id.PlayerStatus);
        restGame = (Button) findViewById(R.id.resetGame);
        for ( int i=0; i< buttons.length; i++)
        {
            String buttonID = "btn_"+ i;
            int resourceID = getResources().getIdentifier(buttonID, "id",getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount =0;
        PlayerOneScoreCount = 0;
        PlayerTwoScoreCount = 0;
        activePlayer = true ;

    }

    @Override
    public void onClick(View view) {

        if(!((Button)view).getText().toString().equals("")){
            return;
        }
        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gamePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if(activePlayer)
        {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#fae7b5"));
            gameState[gamePointer] = 0;


        }
        else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#ff004f"));
            gameState[gamePointer] = 1;

        }
        rountCount ++;
        if(check()) {
            if (activePlayer) {
                PlayerOneScoreCount++;
                UpdatePlayerScore();
                Toast.makeText(this, "Player one won !!", Toast.LENGTH_SHORT).show();
                PlayAgain();
            } else {
                PlayerTwoScoreCount++;
                UpdatePlayerScore();
                Toast.makeText(this, "Player two is won !!", Toast.LENGTH_SHORT).show();

                PlayAgain();

            }
        }else if (rountCount == 9) {

            PlayAgain();
            Toast.makeText(this, "No winner", Toast.LENGTH_SHORT).show();
            } else {
                activePlayer = !activePlayer;
            }
            if (PlayerOneScoreCount > PlayerTwoScoreCount) {
                PlayerStatus.setText("Player One is the winner");
            } else if (PlayerTwoScoreCount > PlayerOneScoreCount) {
                PlayerStatus.setText("Player two is the winner");
            } else {
                PlayerStatus.setText("");

            }


        restGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayAgain();
                PlayerOneScoreCount = 0;
                PlayerTwoScoreCount = 0;
                PlayerStatus.setText("");
                UpdatePlayerScore();
            }
        });

    }
    public boolean check()
    {
        boolean winRes = false;
        for (int[] winnig : winnier)
        {
          if(gameState[winnig[0]] == gameState[winnig[1]]
                  && gameState[winnig[1]] == gameState[winnig[2]]
                  && gameState[winnig[0]] !=2 )
          {
              winRes = true;

          }

        }
         return winRes;
    }


    public void  UpdatePlayerScore()
    {
      PlayerOneScore.setText(Integer.toString(PlayerOneScoreCount));
      PlayerTwoScore.setText(Integer.toString(PlayerTwoScoreCount));
    }
    public void PlayAgain(){
        rountCount =0;
        activePlayer =true;
        for ( int i=0 ; i < buttons.length; i++)
        {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}