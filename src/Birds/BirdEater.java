package Birds;

import Lake.Lake;

/**
 * Created by yellow-umbrella on 20/05/17.
 */
public class BirdEater implements Runnable {
    private int consume;
    private Lake lake;

    public BirdEater(Lake lake, int consume) {
        this.lake = lake;
        this.consume = consume;
    }

    public void run() {
        while(consume > 0) {
            try {
                Bird b = lake.removeBird();
            } catch(InterruptedException e) {
                // TODO
            }
            consume--;
        }
        Lake.eating2 = false;
        if(!Lake.eating1 && !Lake.eating2)
            System.out.println("Termino da observaçao [" +lake.touristCount + " turista e " + lake.birdCount + " passarinhos]");
    }
}
