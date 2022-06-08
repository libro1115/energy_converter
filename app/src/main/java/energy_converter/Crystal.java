package energy_converter;
import static energy_converter.Constants.*;
import java.awt.*;
import java.util.Random;

import javax.swing.plaf.synth.SynthStyle;

public class Crystal {
    static final int correction = 5;//補正　一回りマスより小さく
    static final int break_delay = 300;
    int x;
    int y;
    int dy;//落下中の座標
    boolean reverse = true;
    long break_timer=-1;
    boolean dropping = false;
    EColor color = EColor.NONE;
    void Draw(Graphics gra)
    {
        int dx = x*BLOCK + ORIGIN_X + correction;
        gra.setColor(Color());
        //破壊判定
        if(this.break_timer > 0)
        {
            if(System.currentTimeMillis() > break_timer)
            {
                this.color = EColor.NONE;
                break_timer = -1;
                dropping = false;
                reverse = false;
                dy = GetDropedPosition();
            }
            else 
                gra.drawOval(dx,dy,BLOCK-correction*2,BLOCK-correction*2);
            return;
        }
        switch(color){
            case NONE:
                return;
            case BLACK:
                gra.fillRect(dx, dy, BLOCK-correction*2, BLOCK-correction*2);
                break;
            case WHITE:
                if(reverse)
                    gra.drawPolygon(new int[] {dx,dx+BLOCK-correction*2,dx + (BLOCK-correction*2)/2}, new int[] {dy+BLOCK-correction*2,dy+BLOCK-correction*2,dy}, 3);
                else 
                    gra.drawPolygon(new int[] {dx,dx+BLOCK-correction*2,dx + (BLOCK-correction*2)/2}, new int[] {dy,dy,dy+BLOCK-correction*2}, 3);
                    break;
            default:
                if(reverse)
                    gra.fillPolygon(new int[] {dx,dx+BLOCK-correction*2,dx + (BLOCK-correction*2)/2}, new int[] {dy+BLOCK-correction*2,dy+BLOCK-correction*2,dy}, 3);
                else 
                    gra.fillPolygon(new int[] {dx,dx+BLOCK-correction*2,dx + (BLOCK-correction*2)/2}, new int[] {dy,dy,dy+BLOCK-correction*2}, 3);
                    break;
        }
    }
    //破壊処理
    void Break(final Crystal pair)
    {
        if(!pair.reverse)
            return;
        break_timer = System.currentTimeMillis() + 1000 + break_delay*(pair.y-this.y);
        color = pair.color;
        dropping = false;
        reverse = false;
        dy = GetDropedPosition();
    }
    //床があるときのｙ座標
    int GetDropedPosition(){return ORIGIN_Y + BLOCK*(SQUARES_HEIGTH-1) - BLOCK*y + correction;}
    
    protected Color Color()
    {
        switch(color)
        {
            case RED:
                return Color.RED;
            case BLUE:
                return Color.CYAN;
            case WHITE:
            case BLACK:
                return Color.BLACK;
            case ORANGE:
                return Color.ORANGE;
            case GREEN:
                return Color.GREEN;
        }
        return Color.WHITE;
    }
}
enum EColor
{
    NONE(-1),RED(0),BLUE(1),WHITE(2),ORANGE(3),GREEN(4),BLACK(5);
    private int id;
    private EColor(int id)
    {
        this.id = id;
    }
    public int ID(){return this.id;}
    public static EColor rand(Boolean in_none)
    {
        var rand = new Random();
        int i = rand.nextInt(in_none ? BLACK.id+2:BLACK.id+1);
        if(in_none)
            i--;
        switch(i)
        {
            case -1:
                return NONE;
            case 0:
                return RED;
            case 1:
                return BLUE;
            case 2:
                return WHITE;
            case 3:
                return ORANGE;
            case 4:
                return GREEN;
            case 5:
                return BLACK;
        }
        return NONE;
    }
}