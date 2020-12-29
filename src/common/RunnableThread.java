package common;

/**
 *
 * abstraction of all the while(running) classes
 * */
public abstract class RunnableThread implements Runnable{

    private Thread thread;
    private boolean running = false;

    @Override
    public final void run() {
        while (running) execute();
    }

    public synchronized void startThread(){
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    public synchronized void stopThread(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public abstract void execute();

    public Thread getThread() {
        return thread;
    }
}
