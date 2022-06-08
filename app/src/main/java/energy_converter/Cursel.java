package energy_converter;

import java.awt.*;
import static energy_converter.Constants.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
//入れ替え個所のフレーム作成
public class Cursel extends KeyAdapter{
    static final int correction = 5;//補正　一回りマスより小さく
    protected int x;
    protected int y;
    Keyboard key;
    Cursel()
    {
        x = 2;
        y = 0;
        key = new Keyboard(100);
    }
    Cursel(int X,int Y)
    {
        x = X;
        y = Y;
        if(x < 0)x = 0;
        if(y < 0)y = 0;
        if(x > SQUARES_WIDTH-2)x = SQUARES_WIDTH-2;
        if(y > SQUARES_HEIGTH-2)y=SQUARES_HEIGTH-2;
    }
    void Draw(Graphics g)
    {
        var G = (Graphics2D)g;
        var dx = x * BLOCK + ORIGIN_X + correction;
        var dy = ORIGIN_Y + (SQUARES_HEIGTH - y-1) * BLOCK + correction;
        var width = 4;
        var color = Color.ORANGE;
        G.setStroke(new BasicStroke(width));
        G.setColor(color);
        g.drawRect(dx, dy, BLOCK*2-correction*2, BLOCK - correction*2);
    }
    void Move()
    {

        if(key.isKeyPressed(KeyEvent.VK_UP))
        {
            if(y < SQUARES_HEIGTH-2)
                y++;
        }
        else if(key.isKeyPressed(KeyEvent.VK_DOWN))
        {
            if(y >= 1)
                y--;
        }
        else if(key.isKeyPressed(KeyEvent.VK_RIGHT))
        {
            if(x < SQUARES_WIDTH-2) 
                x++;
        }
        else if(key.isKeyPressed(KeyEvent.VK_LEFT))
        {
            if(x >= 1)
                x--;
        }
        else 
            return;
        key.ResetTimer();
    }
}
