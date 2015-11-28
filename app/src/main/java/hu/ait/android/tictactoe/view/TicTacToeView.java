package hu.ait.android.tictactoe.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.tictactoe.MainActivity;
import hu.ait.android.tictactoe.R;
import hu.ait.android.tictactoe.model.TicTacToeModel;

/**
 * Created by Jason on 9/17/15.
 */

// Press Alt-Enter on View, then choose super class constructors.
// Choose the one with two parameters -> to relate with the XML file.
public class TicTacToeView extends View {

    private Paint paintBg;
    private Paint paintLine;
    private Paint paintO;
    private Paint paintX;

    private Bitmap backGroundBitmap;
    private Bitmap gearBitmap;

    private boolean thereIsAWinnerOrDraw = false;

    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Paint object in android -> color in black.
        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setStyle(Paint.Style.FILL);

        // Paint for O
        paintO = new Paint();
        paintO.setColor(Color.CYAN);
        paintO.setStyle(Paint.Style.STROKE);
        paintO.setStrokeWidth(30);

        // Paint for X
        paintX = new Paint();
        paintX.setColor(Color.RED);
        paintX.setStyle(Paint.Style.STROKE);
        paintX.setStrokeWidth(30);

        // Paint Line
        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        backGroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        gearBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gear);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        backGroundBitmap = backGroundBitmap.createScaledBitmap(backGroundBitmap, getWidth(), getHeight(), false);
        gearBitmap = gearBitmap.createScaledBitmap(gearBitmap, getWidth()/3, getHeight()/3, false);

    }

    @Override
    // Draw the Background
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw rectangle -> draw background to black.
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

        canvas.drawBitmap(backGroundBitmap, 0, 0, null);

        // Draw line on canvas (testing)
        // canvas.drawLine(0,0,getWidth(),100,paintLine);

        // Calling the function below to the draw game area.
        // The order of the function calls is important here.
        drawGameArea(canvas);

        // Call the nice function
        drawPlayers(canvas);

    }

    // Understand this -> most complicated function.
    private void drawPlayers(Canvas canvas) {
        // Start two for loops
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (TicTacToeModel.getInstance().getFieldContent(i, j) ==
                        TicTacToeModel.CIRCLE) {

                    // draw a circle at the center of the field
                    // This is the only place where comments are allowed.


                    // The if branch
                    // X coordinate: left side of the square + half width of the square
                    // getWidth() -> always come from the parent function.
                    float centerX = i * getWidth() / 3;
                    float centerY = j * getHeight() / 3;

                    // 6 - 2 -> to have some space on the line.
                    // what looks better.
                    // reason for commenting -> more mathematical logic.
                    int radius = getHeight() / 6 - 35;

                    canvas.drawBitmap(gearBitmap, centerX, centerY, paintO);


                    // When we have a cross in the field.
                    // We draw to two lines for the cross, \ and /
                } else if (TicTacToeModel.getInstance().getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas.drawLine(i * getWidth() / 3 + 35, j * getHeight() / 3 + 35,
                            (i + 1) * getWidth() / 3 - 35,
                            (j + 1) * getHeight() / 3 - 35, paintX);

                    canvas.drawLine((i + 1) * getWidth() / 3 - 35, j * getHeight() / 3 + 35,
                            i * getWidth() / 3 + 35, (j + 1) * getHeight() / 3 - 35, paintX);



                    /*
                    canvas.drawText("X", ... (specify coordinate)); (don't forget about this)
                    you can draw string on the board
                    you can control how big it is

                    Another thing you can use
                    canvas.drawBitmap

                    draw background for replication.


                    paintLine.setText

                    create separate paint object for different file
                    or change in the code dynamically -> not recommended.
                     */
                }
            }
        }
    }

    // Function to draw the game area -> very smart.
    // Separate codes so that it is clear to whoever reading the code.
    private void drawGameArea(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        // two horizontal lines
        canvas.drawLine(0, getHeight() / 3, getWidth(), getHeight() / 3,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 3, getWidth(),
                2 * getHeight() / 3, paintLine);

        // two vertical lines
        canvas.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 3, 0, 2 * getWidth() / 3, getHeight(),
                paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

/*            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.start_button:
                        chronometerO.setBase(SystemClock.elapsedRealtime());
                        chronometerO.start();
                        break;
                    case R.id.stop_button:
                        chronometerO.stop();
                        break;
                }
            }*/

            // Reset model if the game already ends
            if (thereIsAWinnerOrDraw) {
                TicTacToeModel.getInstance().resetModel();
                thereIsAWinnerOrDraw = false;
            } else {
            /* Strategy -> divide the screen size by 3, get an integer. */

                // In which column should I put my player
                int tX = ((int) event.getX()) / (getWidth() / 3);
                int tY = ((int) event.getY()) / (getHeight() / 3);

                handleFieldTouch(tX, tY);

                winningModel();
            }
            // Repaints the view indirectly, kind of. This is to redraw the screen.
            // Touched it and
            invalidate();

        }

        return super.onTouchEvent(event);
    }

    private void winningModel() {
        if (TicTacToeModel.getInstance().isWinner()) {
            ((MainActivity) getContext()).showSnackBarWithDelete(
                    "Congratulations " +
                            ((TicTacToeModel.getInstance().getNextPlayer() == TicTacToeModel.CIRCLE) ? "X" : "O")
                            + " wins!!!");
//            stopTimer();
            thereIsAWinnerOrDraw = true;
        } else if (TicTacToeModel.getInstance().boardIsFull()) {
            ((MainActivity) getContext()).showSnackBarWithDelete(
                    "It appears that we have a draw!!");
//            stopTimer();
            thereIsAWinnerOrDraw = true;
        } else {
            ((MainActivity) getContext()).showSnackBarWithDelete(
                    "The next player is: " +
                            ((TicTacToeModel.getInstance().getNextPlayer() == TicTacToeModel.CIRCLE) ? "O" : "X")
            );
        }
    }

    private void handleFieldTouch(int tX, int tY) {
        // if tx < 3, ty < 3 or if it is empty
        if (tX < 3 && tY < 3 &&
                TicTacToeModel.getInstance().getFieldContent(tX, tY) == TicTacToeModel.EMPTY) {
            TicTacToeModel.getInstance().setFieldContent(
                    tX, tY, TicTacToeModel.getInstance().getNextPlayer()
            );
            TicTacToeModel.getInstance().changeNextPlayer();
        }
    }

    // Trick -> choose the smaller value for width/height and make a square out of that.
    // Clever.
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    // Basically clear the game area.
    public void clearGameArea() {
        TicTacToeModel.getInstance().resetModel();
        invalidate();
    }

    public void updateTimer() {
        if (TicTacToeModel.getInstance().getNextPlayer() == TicTacToeModel.CIRCLE) {
            ((MainActivity)getContext()).stopXStartO();
        }

        invalidate();
    }

}