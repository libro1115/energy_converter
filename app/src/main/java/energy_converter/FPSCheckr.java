package energy_converter;

class FPSCheckr
{
    protected long startTime;
    protected long fpsTime=0;
    protected static int fps=30;
    int FPS = 0;
    protected int FPSCount =0;
    FPSCheckr(){}
    FPSCheckr(int f){fps=f;}
    void Sleep()
    {
        try
        {
            long runtime = System.currentTimeMillis() - startTime;
            if(runtime <= (1000/fps))
                Thread.sleep(1000/fps-(runtime));
        }
        catch(InterruptedException e){e.printStackTrace();}
    }
    void MinTask()
    {
        if((System.currentTimeMillis() - fpsTime) >= 1000)
        {
            fpsTime = System.currentTimeMillis();
            FPS = FPSCount;
            FPSCount = 0;
        }
        FPSCount++;
        startTime = System.currentTimeMillis();
    }
    int GetFps(){return fps;}
}