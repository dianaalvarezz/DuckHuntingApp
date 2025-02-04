package edu.niu.android.duckhunting;

import java.util.TimerTask;

public class GameTimerTask extends TimerTask
{
    private Game game;
    private GameView gameView;

    public GameTimerTask(GameView view)
    {
        gameView = view;
        game = view.getGame();
        game.startDuckFromRightTopHalf();
    }

    public void run()
    {
        game.moveDuck();
        if (game.shotOffScreen())
            game.loadShot();
        else if (game.isShotFired())
            game.moveShot();
        if (game.duckOffScreen())
        {
            game.setDuckShot(false);
            game.startDuckFromRightTopHalf();
        }
        else if (game.duckHit())
        {
            game.setDuckShot(true);
            ((MainActivity) gameView.getContext()).playHitSound();
            game.loadShot();
        }
        gameView.postInvalidate();
    }
}
