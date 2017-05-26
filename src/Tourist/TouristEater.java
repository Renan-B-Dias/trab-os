package Tourist;

import Lake.Lake;

import java.util.Date;

/**
 * Created by yellow-umbrella on 19/05/17.
 */
public class TouristEater implements Runnable {
    private int consume;
    private Lake lake;

    public TouristEater(Lake lake, int consume) {
        this.lake = lake;
        this.consume = consume;
    }

    public void run() {
        while(consume > 0) {
            try {
                Tourist t = lake.removeTourist();
            } catch (Exception e) {
                // TODO
            }
            consume--;
        }
        Lake.eating1 = false;
        if(!Lake.eating1 && !Lake.eating2)
            System.out.println("Termino da observaçao [" +lake.touristCount + " turista e " + lake.birdCount + " passarinhos]");
    }
}
