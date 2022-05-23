package zieit.labs.pr5;

import java.util.Timer;
import java.util.TimerTask;

public class Pr5 {

    public static class RunnableTask extends TimerTask {
        private int valueToIncrement;

        public RunnableTask(Integer valueToIncrement) {
            this.valueToIncrement = valueToIncrement;
        }
        @Override
        public void run() {
            System.out.println("Got value " + valueToIncrement);
            safeWait(2000);
            valueToIncrement++;
            System.out.println("Incremented value " + valueToIncrement);
            safeWait(2000);
            System.out.println("End job with value " + valueToIncrement);
        }
    }

    public static void main(String[] args) {
        Timer timer = new Timer(true);

        timer.schedule(new RunnableTask(0), 5000);
        timer.schedule(new RunnableTask(10), 0, 5000);

        safeWait(20000);
    }

    private static void safeWait(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
