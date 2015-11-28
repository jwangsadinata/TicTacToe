package hu.ait.android.tictactoe;

import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import java.util.LinkedHashMap;

import hu.ait.android.tictactoe.view.TicTacToeView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutContent;
    private TicTacToeView ticTacToeView;
    private Chronometer chronometerO;
    private Chronometer chronometerX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);

        chronometerO = (Chronometer) findViewById(R.id.chronometerO);
        chronometerX = (Chronometer) findViewById(R.id.chronometerX);
        // Creating functions for the restart button to clear the area again.
        ticTacToeView =
                (TicTacToeView) findViewById(R.id.gameView);
        ticTacToeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticTacToeView.updateTimer();
            }
        });
        Button btnRestart =
                (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticTacToeView.clearGameArea();

                showSnackBarMessage("Game has been restarted!!");
            }
        });
    }

    /*    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    */

    public void showSnackBarMessage(String msg) {
        Snackbar.make(layoutContent, msg, Snackbar.LENGTH_LONG).show();

        // get reference to the layout
    }

    public void showSnackBarWithDelete(String msg) {
        Snackbar.make(layoutContent, msg,
                Snackbar.LENGTH_LONG).setAction(
                "Restart", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Restart the game
                        ticTacToeView.clearGameArea();
                    }
                }
        ).show();
    }

    public void stopXStartO(){
        chronometerX.stop();
        chronometerO.setBase(SystemClock.elapsedRealtime());
        chronometerO.start();
    }
}
