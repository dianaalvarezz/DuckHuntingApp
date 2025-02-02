package edu.niu.android.duckhunting;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import java.util.Timer;
public class MainActivity extends Activity
{
    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
// get status bar height
        Resources res = getResources();
        int statusBarHeight = 0;
        int statusBarId =
                res.getIdentifier("status_bar_height", "dimen", "android");
        if (statusBarId > 0)
            statusBarHeight = res.getDimensionPixelSize(statusBarId);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        gameView = new GameView(this, size.x, size.y - statusBarHeight);
        setContentView(gameView);
        Timer gameTimer = new Timer();
        gameTimer.schedule(new GameTimerTask(gameView),
                0, GameView.DELTA_TIME);
    }
}
