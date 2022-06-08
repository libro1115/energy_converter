package energy_converter;

import static energy_converter.Constants.*;
import java.awt.*;
import java.util.Random;

public class Area {
    static final int drop_speed = 3;
    int drop_frequency = 2000;//ドロップ頻度
    long drop_counter = 0;
    Crystal[][] data;
    Area()
    {
        data = new Crystal[SQUARES_WIDTH][SQUARES_HEIGTH];
        for(int i = 0;i < SQUARES_WIDTH;i++)
        {
            for(int j=0;j < SQUARES_HEIGTH;j++)
            {
                data[i][j] = new Crystal();
            }
        }
        drop_counter = System.currentTimeMillis();
        MakeStart();
    }
    //初期状態
    void MakeStart()
    {
        for(int i = 0;i < SQUARES_WIDTH;i++)
        {
            for(int j=0;j < SQUARES_HEIGTH;j++)
            {
                data[i][j].reverse = false;
                data[i][j].x = i;
                data[i][j].y = j;
                data[i][j].dy = data[i][j].GetDropedPosition();
                if(j < 3)
                    data[i][j].color = EColor.rand(false);//3列までランダムに埋めるNONEなし
            }
        }
    }
    //描画
    void Draw(Graphics gra)
    {
        for(int i = 0;i < SQUARES_WIDTH;i++)
        {
            for(int j=0;j < SQUARES_HEIGTH;j++)
            {
                data[i][j].Draw(gra);
            }
        }
    }
    //入れ替え
    void Swap(final int x, final int y)
    {
        if(x > SQUARES_WIDTH-2 || y > SQUARES_HEIGTH-2)
            return;
        //反転中
        if(data[x][y].reverse || data[x+1][y].reverse)
            return;
        //落下中
        if(data[x][y].dropping || data[x+1][y].dropping)
            return;
        if(data[x][y].break_timer > 0 || data[x+1][y].break_timer > 0)
            return;
        var mem = data[x][y].color;
        data[x][y].color = data[x+1][y].color;
        data[x+1][y].color = mem;
    }
    //落下
    void Drop()
    {
        for(int i = 0;i < SQUARES_WIDTH;i++)
        {
            for(int j=0;j < SQUARES_HEIGTH;j++)
            {
                if(data[i][j].color == EColor.NONE)
                {
                    data[i][j].dropping = false;
                    data[i][j].dy = data[i][j].GetDropedPosition();
                    data[i][j].reverse = false;
                    continue;
                }
                //落下中
                if(data[i][j].dropping)
                    data[i][j].dy +=drop_speed;
                //下がNONE
                if(!data[i][j].dropping && j != 0 && data[i][j-1].color == EColor.NONE)
                    data[i][j].dropping = true;
                //マス更新判定
                if(data[i][j].dy > data[i][j].GetDropedPosition())
                {
                    //底に到達
                    if(j == 0 || data[i][j-1].color != EColor.NONE)
                        Break(i,j);
                    else
                    {
                        data[i][j-1].color = data[i][j].color;
                        data[i][j-1].dropping = true;
                        data[i][j-1].dy = data[i][j].dy;
                        data[i][j-1].reverse = data[i][j].reverse;
                        data[i][j].color = EColor.NONE;
                    }
                    data[i][j].dropping = false;
                    data[i][j].dy = data[i][j].GetDropedPosition();
                    data[i][j].reverse = false;
                    
                }
            }
        }
        NewDrop();
    }
    //破壊判定
    void Break(final int x, final int y)
    {
        //落下終了
        data[x][y].dropping = false;
        //反転でなければ終了
        if(!data[x][y].reverse)
            return;
        //下に何もなければ終了
        if(y <= 0)
            return;
        int pair=-1;
        for(int i = y-1;i >= 0;i--)
        {
            //落下中なら無効
            if(data[x][i].dropping)
                return;
            //黒が間に挟まると不可
            if(data[x][i].color == EColor.BLACK && data[x][y].color != EColor.BLACK)
                return;
            //反転と同色で挟むと消える
            if(data[x][i].color == data[x][y].color)
            {
                pair = i;
                break;
            }
        }
        if(pair < 0)
            return;
        for(int i = pair;i <= y;i++)
            data[x][i].Break(data[x][y]);
    }
    //新規の落下物
    void NewDrop()
    {
        if(drop_counter > System.currentTimeMillis())
            return;
        drop_counter = System.currentTimeMillis() + drop_frequency;
        var rand = new Random();
        int x = rand.nextInt(SQUARES_WIDTH-1);
        int y = SQUARES_HEIGTH-1;
        data[x][y].color = EColor.rand(false);
        data[x][y].reverse = true;
    }
    boolean IsGameOver()
    {
        for(int i = 0;i < SQUARES_WIDTH;i++)
            if(data[i][SQUARES_HEIGTH].color != EColor.NONE && data[i][SQUARES_HEIGTH-1].color != EColor.NONE)
                return true;
        return false;
    }
}
