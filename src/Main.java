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

        /**
         * Variáveis usadas pelas classes de turista
         */
        int generate, timeAppear, walkTime, maxTourists, timeBath;  // Tourist

        /**
         * Variáveis usadas pelas classes de passárinhos
         */
        int birdGenerate, birdAppear, maxBirds, drinkTime;  // Bird

        /**
         * Caso os argumentos da main estejam vazios, pegue tais argumentos pelo terminal.
         */
        if (args.length == 0) {
            // MARK: Tourist
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
            // Tourist
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

        /**
         * Instância compartilhada da piscina (Lake).
         */
        Lake lake = new Lake(maxTourists, timeBath, maxBirds, drinkTime);

        /**
         * Constantes usadas pelo Turista.
         */
        ThreadTourist.touristConstant = timeAppear;
        ThreadTourist.walkTime = walkTime;

        /**
         * Array de Threads do turista. Tamanho "generate".
         */
        Thread touristThread[] = new Thread[generate];

        /**
         * Constante usada pelos passárinhos.
         */
        ThreadBird.birdConstant = birdAppear;

        /**
         * Array de Threads de passárinhos. Tamanho "birdGenerate".
         */
        Thread birdThreads[] = new Thread[birdGenerate];

        /**
         * For que instancia todas as Threads de Passárinhos (Passando a instância compartilhada
         * de piscina (Lake)).
         */
        for(int i = 0; i < birdThreads.length; i++) {
            birdThreads[i] = new Thread(new ThreadBird(lake));
            birdThreads[i].setName("Bird Thread " + i); // For debug
        }

        /**
         * For que instancia todas as Threads de Turistas (Passando a instância compartilhada
         * de piscina (Lake)).
         */
        for(int i = 0; i < touristThread.length; i++) {
            touristThread[i] = new Thread(new ThreadTourist(lake));
            touristThread[i].setName("Tourist Thread " + i); // For debug
        }

        /**
         * Instanciação da Thread do consumidor de turistas.
         */
        Thread touristConsumer = new Thread(new TouristEater(lake, generate));
        touristConsumer.setName("Tourist Eater Thread"); // For debug

        /**
         * Instanciação da Thread do consumidor de Passárinhos
         */
        Thread birdConsumer = new Thread(new BirdEater(lake, birdGenerate));
        birdConsumer.setName("Bird Eater Thread"); // For debug

        /**
         * Print de inicio da observação.
         */
        System.out.printf("Inicio da observação [%d turistas e %d passarinhos]\n", lake.touristCount, lake.birdCount);

        /**
         * For que inicia todas as Threads de turistas.
         */
        for(Thread x: touristThread)
            x.start();
        /**
         * For que inicia todas as Threads de passárinhos.
         */
        for(Thread y: birdThreads)
            y.start();

        /**
         * Inicialização da Thread consumidor de turistas.
         */
        touristConsumer.start();

        /**
         * Inicialização da Thread consumidor de passárinhos.
         */
        birdConsumer.start();
    }
}
