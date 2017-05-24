package Lake;

import Birds.Bird;
import Tourist.Tourist;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * Created by yellow-umbrella on 17/05/17.
 */
public class Lake {

    private static int maxTourists;   // Buffer size
    private static int batheTime;

    private Tourist[] touristArray;
    private int in, out;
    private Semaphore mutex;
    private Semaphore empty;
    private Semaphore full;

    public Lake(int maxTourists, int batheTime, int maxBirds, int drinkTime) {
        // Tourist
        in = 0;
        out = 0;
        touristArray = new Tourist[maxTourists];
        mutex = new Semaphore(1);
        empty = new Semaphore(maxTourists);
        full = new Semaphore(0);
        this.maxTourists = maxTourists;
        this.batheTime = batheTime;

        // Bird
        birdIn = 0;
        birdOut = 0;
        birdsArray = new Bird[maxBirds];
        birdMutex = new Semaphore(1);
        birdEmpty = new Semaphore(maxBirds);
        birdFull = new Semaphore(0);
        this.maxBirds = maxBirds;
        this.drinkTime = drinkTime;
    }

    public void insertTourist(Tourist tourist) throws Exception {
        empty.acquire();
        mutex.acquire();

        touristArray[in] = tourist;
        in = (in +1) % maxTourists;

        mutex.release();

        System.out.println(new Date() + ": O turista " + tourist.id + " entrou na piscina");
        Thread.sleep(batheTime); // Time tourist takes to bathe
        System.out.println(new Date() + ": O turista " + tourist.id + " saiu da piscina");
        full.release();
    }

    public Tourist removeTourist() throws Exception {
        full.acquire();
        mutex.acquire();

        Tourist t = touristArray[out];
        out = (out+1) % maxTourists;

        mutex.release();
        empty.release();
        return t;
    }


    private static int maxBirds;
    private static int drinkTime;

    private Bird[] birdsArray;
    private int birdIn, birdOut;
    private Semaphore birdMutex, birdEmpty, birdFull;

    public void insertBird(Bird bird) throws Exception {
        birdEmpty.acquire();
        birdMutex.acquire();

        birdsArray[birdIn] = bird;
        birdIn = (birdIn + 1) % maxBirds;

        birdMutex.release();
        System.out.println(new Date() + ": O passaro " + bird.id + " bebendo agua");
        Thread.sleep(drinkTime);
        System.out.println(new Date() + ": O passaro " + bird.id + " terminou de beber");
        birdFull.release();
    }

    public Bird removeBird() throws Exception {
        birdFull.acquire();
        birdMutex.acquire();

        Bird b = birdsArray[birdOut];
        birdOut = (birdOut + 1) % maxBirds;

        birdMutex.release();
        birdEmpty.release();
        return b;
    }

}
