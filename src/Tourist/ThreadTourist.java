package Tourist;

import Lake.Lake;

import java.util.Date;

/**
 * Created by yellow-umbrella on 19/05/17.
 */
public class ThreadTourist implements Runnable {
    public static int touristConstant;
    public static int walkTime;
    private Tourist tourist;
    private Lake lake;

    public ThreadTourist(Lake lake) {
        tourist = new Tourist();
        this.lake = lake;
    }

    public void run() {
        try {
            Thread.sleep(this.tourist.id * touristConstant);

            System.out.println(new Date() + ": O turista " + tourist.id + " inicia a caminhada a piscina [" + lake.touristCount + " turistas]");
            Thread.sleep(walkTime);
            System.out.println(new Date() + ": O turista " + tourist.id + " terminou a caminhada e chegou a margem da piscina [" + lake.touristCount + " turistas]");

            lake.insertTourist(tourist);
        } catch(Exception e) {
            // TODO
        }
    }
}
