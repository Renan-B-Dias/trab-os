package Birds;

import Lake.Lake;

import java.util.Date;

/**
 * Created by yellow-umbrella on 20/05/17.
 */
public class ThreadBird implements Runnable {

    public static int birdConstant;

    private Bird bird;
    private Lake lake;

    public ThreadBird(Lake lake) {
        this.lake = lake;
        this.bird = new Bird();
    }

    public void run() {

        try {
            Thread.sleep(this.bird.id * birdConstant);  // Time to generate bird
        } catch(Exception e) {

        }

        try {
            System.out.println(new Date() + " Passaro: " + bird.id + " chegou no lago");
            lake.insertBird(bird);
        } catch(Exception e) {

        }

    }

}
