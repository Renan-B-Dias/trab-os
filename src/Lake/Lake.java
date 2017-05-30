package Lake;

import Birds.Bird;
import Birds.ThreadBird;
import Tourist.Tourist;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * Created by yellow-umbrella on 17/05/17.
 * Classe que define uma piscina onde passárinhos e turistas tentam acessar.
 */
public class Lake {

    /**
     * Variáveis que indicam se a classe TouristEater e BirdEater ainda estão consumindo seus
     * respectivos objetos.
     */
    public static boolean eating1 = true, eating2 = true;

    /**
     * Semáforo que tanca uma adição por vez de passárinho e turista.
     */
    private Semaphore willAdd = new Semaphore(1);

    /**
     * Qauntidade máxima de turistas na piscina.
     */
    private static int maxTourists;   // Buffer size

    /**
     * Tempo de banho de um turista.
     */
    private static int batheTime;

    /**
     * Countador de turistas na atualmente na piscina.
     */
    public int touristCount = 0;

    /**
     * Array de turistas na piscina.
     */
    private Tourist[] touristArray;

    /**
     * Variávies que indicam onde deve ser retirado e onde deve ser colocado no array de turistas.
     */
    private int in, out;

    /**
     * Semáforo que tranca modificações uma por vez nas variáveis in, out, touristCount e touristArray.
     */
    private Semaphore mutex;

    /**
     * Semáforo que indica se pode ser adicionado turistas na piscina.
     */
    private Semaphore empty;

    /**
     * Semáforo que indica se há turistas na piscina.
     */
    private Semaphore full;

    /**
     * Construtor que seta variáveis necessárias para execução do programa.
     * @param maxTourists Quantidade máxima de turistas na piscina.
     * @param batheTime Tempo de banho de um turista.
     * @param maxBirds Quantidade máxima de passárinhos na pisicna.
     * @param drinkTime Tempo que passárinho leva para beber agua.
     */
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

    /**
     * Método que recebe um turista e tenta inserir o mesmo na piscina (Array).
     * O semáforo "empty" indica se pode colocar um turista no Array, caso esteja cheio a Thread
     * será travada nesse momento. Em seguida caso um passárinho não esteja sendo inserido é feito
     * um acquire() no semáforo willAdd. A Thread entra na região critica, passando pelo mutex, e
     * adiciona o turista na piscina (array) e calcula em que posição o proximo turista será colocado
     * e imcrementa o contador de turistas na piscina. Uma mensagem é printada informando que o
     * turista entrou na piscina a Thread sai da região critica.
     * Em seguida o semáforo willAdd é liberado e a Thread é travada pelo tempo qeu o turista
     * leva para tomar banho na piscina.
     * Assim que esse tempo termina é feito o release no semáforo "full", indicando que há turistas
     * a serem consumidos.
     * @param tourist Turista tentando entrar na piscina.
     * @throws InterruptedException
     */
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

    /**
     * Método que remove um turista da piscina.
     * O semáforo full só é liberado caso haja um turista na piscina pronto para sair.
     * Em seguida, passando pelo mutex, é retirado um turista da piscina (array) e atualizado
     * as variáveis out e touristCount. Em seguida uma mensagem é impressa indicando que
     * o turista saiu da piscina.
     * Nesse momento o semáforo empty é feito o release().
     * @return
     * @throws InterruptedException
     */
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


    /**
     * Quantidade máxima de passárinhos na piscina.
     */
    private static int maxBirds;

    /**
     * Tempo que um passárinho leva para beber agua.
     */
    private static int drinkTime;

    /**
     * Quantidade de passárinhos na piscina.
     */
    public int birdCount = 0;

    /**
     * Array de passárinhos na piscina.
     */
    private Bird[] birdsArray;

    /**
     * Variáveis que indicam onde deve adicionado o passárinho e onde deve retirar.
     */
    private int birdIn, birdOut;

    /**
     * Semáforos para alterações de variáveis na região critica, remocçaõ e inserção de passárinhos.
     */
    private Semaphore birdMutex, birdEmpty, birdFull;


    /**
     * Método para inserir um passárinho na piscina.
     * Método que tenta inserir um passárinho na piscina. Primeiro é verificado se a piscina
     * esta lotada de turistas, caso verdade entra em um loop onde a mensagem de que o
     * passárinho tentou entrar mas teve que ir embora é impressa e a Thread trava pelo tempo
     * "birdConstant". Em seguida o passárinho tenta entrar de novo na piscina. Caso a piscina
     * não esteja lotada de turistas e não esteja lotada de passárinhos o loop é quebrado.
     * Em seguida o semáforo "willAdd" é travado, impedindo que turistas sejam adicionados e
     * birdEmpty também indeicando qeu será adicionado um passárinho.
     * A Thread passa pela região critica, adicionando o turista na piscina (array) e atualizando
     * as variáveis birdIn e virdCount.
     * Ao sair da região critica willAdd é liberado e a mensagem de que o passárinho começou
     * a beber é impressa. Nesse momento a Thread é travada por "walkTime" que é o tempo que
     * passárinho leva para beber agua. Quando esse tempo acaba é chamado o release() do birdfull.
     * @param bird Passárinho a ser adicionado na piscina.
     * @throws InterruptedException
     */
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

    /**
     * Método que remove um passárinho da piscina.
     * É feito o acquire no semáfaro birdFull que travará caso não haja passárinhos na piscina
     * prontos a sair. Em seguida, pela região critica, é retirado um passárinho e atualizado as
     * váriaveis birdOut e birdCount. Uma mensagem é printada informando que o passárinho já
     * bebeu agua e não volta mais. Nesse momento é feito um release no semáfaro birdEmpty
     * liberando para ser adicionado novos passárinhos na piscina.
     * @return
     * @throws InterruptedException
     */
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
