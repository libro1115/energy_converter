package energy_converter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Keyboard extends KeyAdapter
{
    private static ArrayList<Integer> pressedButtons = new ArrayList<>();
    private long timer;
    private long wait;
    Keyboard(long wait_time)
    {
        super();
        this.wait = wait_time;
    }
    void ResetTimer(){timer = System.currentTimeMillis();}
    boolean IsTimer(){return System.currentTimeMillis() - timer > wait;}

    public boolean isKeyPressed(int keyCode) {
        return IsTimer() && pressedButtons.contains(keyCode);
    }
    @Override
	public void keyTyped(KeyEvent e)
    {
        super.keyTyped(e);
        timer = System.currentTimeMillis();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if(!pressedButtons.contains(e.getKeyCode())) pressedButtons.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        timer = System.currentTimeMillis()-wait*2;
        pressedButtons.remove((Integer) e.getKeyCode());
    }
}
