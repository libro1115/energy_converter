package energy_converter;
import java.awt.*;


import static energy_converter.Constants.*;
//背景の升目作成
public class BackGround {
    public void Draw(Graphics g)
    {
        DrawVerticalLine(g);
        DrawHorizonlLine(g);
    }

    private void DrawVerticalLine(Graphics g)
    {
        Graphics2D G = (Graphics2D)g;
        for(int i = 0;i < SQUARES_WIDTH+1;i++)
        {
            var width = (i == 0 || i == SQUARES_WIDTH)? 4 : 2;
            var color = (i == 0 || i == SQUARES_WIDTH)? Color.RED : Color.BLACK;
            G.setStroke(new BasicStroke(width));
            G.setColor(color);
            g.drawLine(ORIGIN_X + i*BLOCK, ORIGIN_Y, ORIGIN_X + i*BLOCK, ORIGIN_Y+BLOCK*SQUARES_HEIGTH);
        }
    }
    private void DrawHorizonlLine(Graphics g)
    {
        Graphics2D G = (Graphics2D)g;
        for(int i = 0;i < SQUARES_HEIGTH + 1;i++)
        {
            var width = (i == 0 || i == SQUARES_HEIGTH)? 4 : 2;
            var color = (i <= 1 || i == SQUARES_HEIGTH)? Color.RED : Color.BLACK;
            G.setStroke(new BasicStroke(width));
            G.setColor(color);
            g.drawLine(ORIGIN_X, ORIGIN_Y+i*BLOCK, ORIGIN_X + BLOCK*SQUARES_WIDTH, ORIGIN_Y+i*BLOCK);
        }
    }
}
