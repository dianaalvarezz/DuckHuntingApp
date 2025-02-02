package edu.niu.android.duckhunting;

import android.graphics.Point;
import android.graphics.Rect;
import java.util.Random;

public class Game
{
    private Rect huntingRect;
    private int deltaTime; // in milliSeconds

    private Rect duckRect;
    private int duckWidth;
    private int duckHeight;
    private float duckSpeed;
    private boolean duckShot;

    private Point cannonCenter;
    private int cannonRadius;
    private int barrelLength;
    private int barrelRadius;
    private float cannonAngle;

    private Point shotCenter;
    private int shotRadius;
    private boolean shotFired;
    private float shotAngle;
    private float shotSpeed;

    private Random random;

    public Game(Rect newDuckRect, int newShotRadius,
                float newDuckSpeed, float newShotSpeed)
    {
        setDuckRect(newDuckRect);
        setDuckSpeed(newDuckSpeed);
        setShotRadius(newShotRadius);
        setShotSpeed(newShotSpeed);
        random = new Random();
        shotFired = false;
        duckShot = false;
        cannonAngle = (float) Math.PI / 4; // starting cannon angle
    }

    public Rect getHuntingRect()
    {
        return huntingRect;
    }

    public void setHuntingRect(Rect newHuntingRect)
    {
        if (newHuntingRect != null)
            huntingRect = newHuntingRect;
    }

    public void setDeltaTime(int newDeltaTime)
    {
        if (newDeltaTime > 0)
            deltaTime = newDeltaTime;
    }

    public Rect getDuckRect()
    {
        return duckRect;
    }

    public void setDuckRect(Rect newDuckRect)
    {
        if (newDuckRect != null)
        {
            duckWidth = newDuckRect.right - newDuckRect.left;
            duckHeight = newDuckRect.bottom - newDuckRect.top;
            duckRect = newDuckRect;
        }
    }

    public void setDuckSpeed(float newDuckSpeed)
    {
        if (newDuckSpeed > 0)
            duckSpeed = newDuckSpeed;
    }

    public Point getCannonCenter()
    {
        return cannonCenter;
    }

    public int getCannonRadius()
    {
        return cannonRadius;
    }

    public int getBarrelLength()
    {
        return barrelLength;
    }

    public int getBarrelRadius()
    {
        return barrelRadius;
    }

    public void setCannon(Point newCannonCenter, int newCannonRadius,
                          int newBarrelLength, int newBarrelRadius)
    {
        if (newCannonCenter != null && newCannonRadius > 0
                && newBarrelLength > 0)
        {
            cannonCenter = newCannonCenter;
            cannonRadius = newCannonRadius;
            barrelLength = newBarrelLength;
            barrelRadius = newBarrelRadius;
            shotCenter = new Point(
                    (int) (cannonCenter.x + cannonRadius * Math.cos(cannonAngle)),
                    (int) (cannonCenter.y - cannonRadius * Math.sin(cannonAngle)));
        }
    }

    public Point getShotCenter()
    {
        return shotCenter;
    }

    public int getShotRadius()
    {
        return shotRadius;
    }

    public void setShotRadius(int newShotRadius)
    {
        if (newShotRadius > 0)
            shotRadius = newShotRadius;
    }

    public void setShotSpeed(float newShotSpeed)
    {
        if (newShotSpeed > 0)
            shotSpeed = newShotSpeed;
    }

    public float getCannonAngle()
    {
        return cannonAngle;
    }

    public void setCannonAngle(float newCannonAngle)
    {
        if (newCannonAngle >= 0 && newCannonAngle <= Math.PI / 2)
            cannonAngle = newCannonAngle;
        else if (newCannonAngle < 0)
            cannonAngle = 0;
        else
            cannonAngle = (float) Math.PI / 2;
        if (!isShotFired())
            loadShot();
    }

    public boolean isShotFired()
    {
        return shotFired;
    }

    public void fireShot()
    {
        shotFired = true;
        shotAngle = cannonAngle;
    }

    public boolean isDuckShot()
    {
        return duckShot;
    }

    public void setDuckShot(boolean newDuckShot)
    {
        duckShot = newDuckShot;
    }

    public void startDuckFromRightTopHalf()
    {
        duckRect.left = huntingRect.right;
        duckRect.right = duckRect.left + duckWidth;
        duckRect.top = random.nextInt(huntingRect.bottom / 2);
        duckRect.bottom = duckRect.top + duckHeight;
    }

    public void moveDuck()
    {
        if (!duckShot)
        {
            // move left
            duckRect.left -= duckSpeed * deltaTime;
            duckRect.right -= duckSpeed * deltaTime;
        }
        else
        {
            // move down
            duckRect.top += 5 * duckSpeed * deltaTime;
            duckRect.bottom += 5 * duckSpeed * deltaTime;
        }
    }

    public boolean duckOffScreen()
    {
        return duckRect.right < 0 || duckRect.bottom < 0
                || duckRect.top > huntingRect.bottom
                || duckRect.left > huntingRect.right;
    }

    public void moveShot()
    {
        shotCenter.x += shotSpeed * Math.cos(shotAngle) * deltaTime;
        shotCenter.y -= shotSpeed * Math.sin(shotAngle) * deltaTime;
    }

    public boolean shotOffScreen()
    {
        return shotCenter.x - shotRadius > huntingRect.right
                || shotCenter.y + shotRadius < 0;
    }

    public void loadShot()
    {
        shotFired = false;
        shotCenter.x = (int) (cannonCenter.x
                + cannonRadius * Math.cos(cannonAngle));
        shotCenter.y = (int) (cannonCenter.y
                - cannonRadius * Math.sin(cannonAngle));
    }

    public boolean duckHit()
    {
        return duckRect.intersects(
                shotCenter.x - shotRadius, shotCenter.y - shotRadius,
                shotCenter.x + shotRadius, shotCenter.y + shotRadius);
    }
}
