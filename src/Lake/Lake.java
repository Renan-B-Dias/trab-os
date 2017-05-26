package Lake;

import Birds.Bird;
import Birds.ThreadBird;
import Tourist.Tourist;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * Created by yellow-umbrella on 17/05/17.
 */
public class Lake {

    public static boolean eating1 =true, eating2 = true;
    private Semaphore willAdd = new Semaphore(1);

    private static int maxTourists;   // Buffer size
    private static int batheTime;
    public int touristCount = 0;
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

    public void insertTourist(Tourist tourist) throws InterruptedException {
        empty.acquire();
        willAdd.acquire();

        mutex.acquire();
            touristArray[in] = tourist;
            in = (in +1) % maxTourists;
            touristCount++;
            System.out.println(new Date() + ": O turista " + tourist.id + " entrou na piscina [" + touristCount + " turistas]");
        mutex.release();

        willAdd.release();
        Thread.sleep(batheTime); // Time tourist takes to bathe
        full.release();
    }

    public Tourist removeTourist() throws InterruptedException {
        full.acquire();

        mutex.acquire();
            Tourist tourist = touristArray[out];
            out = (out+1) % maxTourists;
            touristCount--;
            System.out.println(new Date() + ": O turista " + tourist.id + " saiu da piscina [" + touristCount + " turistas]");
        mutex.release();

        empty.release();
        return tourist;
    }


    private static int maxBirds;
    private static int drinkTime;
    public int birdCount = 0;
    private Bird[] birdsArray;
    private int birdIn, birdOut;
    private Semaphore birdMutex, birdEmpty, birdFull;

    public void insertBird(Bird bird) throws InterruptedException {

        while(touristCount == maxTourists || birdCount == maxBirds) {
            if(touristCount != maxTourists)
                break;
            System.out.println(new Date() + ": O passarinho " + bird.id + " teve que ir embora porque ha muitos turistas [" + birdCount + " passarinhos]");
            Thread.sleep(ThreadBird.birdConstant);
            System.out.println(new Date() + ": O passarinho: " + bird.id + " voltou a margem da piscina [" + birdCount + " passarinhos]");
        }

        willAdd.acquire();
        birdEmpty.acquire();

        birdMutex.acquire();
            birdsArray[birdIn] = bird;
            birdIn = (birdIn + 1) % maxBirds;
            birdCount++;
        birdMutex.release();

        willAdd.release();

        System.out.println(new Date() + ": O passarinho " + bird.id + " começou a beber agua da piscina [" + birdCount + " passarinhos]");
        Thread.sleep(drinkTime);

        birdFull.release();
    }

    public Bird removeBird() throws InterruptedException {
        birdFull.acquire();

        birdMutex.acquire();
            Bird bird = birdsArray[birdOut];
            birdOut = (birdOut + 1) % maxBirds;
            birdCount--;
        birdMutex.release();
        System.out.println(new Date() + ": O passarinho " + bird.id + " ja bebeu agua e nao volta mais [" + birdCount + " passarinhos]");

        birdEmpty.release();
        return bird;
    }
}
