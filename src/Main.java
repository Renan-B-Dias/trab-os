import Birds.BirdEater;
import Birds.ThreadBird;
import Lake.Lake;
import Tourist.ThreadTourist;
import Tourist.TouristEater;

import java.util.Scanner;

/**
 * Created by yellow-umbrella on 19/05/17.
 */
public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        int generate, timeAppear, walkTime, maxTourists, timeBath;  // Tourist
        int birdGenerate, birdAppear, maxBirds, drinkTime;  // Bird

        if(false) {
            // Tourist
            generate = 10;
            timeAppear = 2000;      //Milliseconds
            walkTime = 1200;        //Milliseconds
            maxTourists = 3;
            timeBath = 6000;    dsd    //Milliseconds

            //Bird
            birdGenerate = 20;
            birdAppear = 2000;      //Milliseconds
            maxBirds = 2;
            drinkTime = 5000;       //Milliseconds
        }
        else {
            if (args.length == 0) {
                // MARK: UI
                System.out.println("Digite qtd de turistas a ser gerado (int) (numtur)");
                generate = sc.nextInt();
                System.out.println("Digite a constante de tempo para aparecer tourist (int) (intertur)");
                timeAppear = sc.nextInt();
                System.out.println("Digite tempo de caminhada (miliseconds) (camtur)");
                walkTime = sc.nextInt();
                System.out.println("Digite o maximo de turistas no lago (int) (maxtur)");
                maxTourists = sc.nextInt();
                System.out.println("Digite tempo de banho do turista (milisegundos) (aguatur)");
                timeBath = sc.nextInt();

                // MARK: Bird
                System.out.println("Digite qtd de passaros a ser gerado (int) (numpas)");
                birdGenerate = sc.nextInt();
                System.out.println("Digite a constante de tempo para aparecer passaros (int) (interpas)");
                birdAppear = sc.nextInt();
                System.out.println("Digite tempo o maximo de passaros no lago (int) (maxpas)");
                maxBirds = sc.nextInt();
                System.out.println("Digite tempo para tomar agua (int) (beberpas)");
                drinkTime = sc.nextInt();
            } else {
                generate = Integer.parseInt(args[0]);
                timeAppear = Integer.parseInt(args[1]);
                walkTime = Integer.parseInt(args[2]);
                maxTourists = Integer.parseInt(args[3]);
                timeBath = Integer.parseInt(args[4]);

                // Bird
                birdGenerate = Integer.parseInt(args[5]);
                birdAppear = Integer.parseInt(args[6]);
                maxBirds = Integer.parseInt(args[7]);
                drinkTime = Integer.parseInt(args[8]);
            }
        }

        Lake lake = new Lake(maxTourists, timeBath, maxBirds, drinkTime);

        ThreadTourist.touristConstant = timeAppear;
        ThreadTourist.walkTime = walkTime;
        Thread touristThread[] = new Thread[generate];

        ThreadBird.birdConstant = birdAppear;
        Thread birdThreads[] = new Thread[birdGenerate];

        for(int i = 0; i < birdThreads.length; i++) {
            birdThreads[i] = new Thread(new ThreadBird(lake));
            birdThreads[i].setName("Bird Thread " + i); // For debug
        }

        for(int i = 0; i < touristThread.length; i++) {
            touristThread[i] = new Thread(new ThreadTourist(lake));
            touristThread[i].setName("Tourist Thread " + i); // For debug
        }

        Thread touristConsumer = new Thread(new TouristEater(lake, generate));
        touristConsumer.setName("Tourist Eater Thread");

        Thread birdConsumer = new Thread(new BirdEater(lake, birdGenerate));
        birdConsumer.setName("Bird Eater Thread");

        System.out.printf("Inicio da observação [%d turistas e %d passarinhos]\n", lake.touristCount, lake.birdCount);

        for(Thread x: touristThread)
            x.start();
        for(Thread y: birdThreads)
            y.start();

        touristConsumer.start();
        birdConsumer.start();
    }
}
