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
        } catch(Exception e) {

        }

        try {
            System.out.println(new Date() + ": Turista com id: " + tourist.id + " começou a caminhada");
            Thread.sleep(walkTime);
            System.out.println(new Date() + ": Turista com id: " + tourist.id + " chegou a margem");
            lake.insertTourist(tourist);
        } catch(Exception e) {

        }
    }
}
