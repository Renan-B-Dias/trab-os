package Tourist;

import Lake.Lake;

import java.util.Date;

/**
 * Created by yellow-umbrella on 19/05/17.
 * Classe que define uma Thread que contem um turista.
 * Essa classe vai tentar inserir um turista a cada "tourist.id * birdConstant"
 */
public class ThreadTourist implements Runnable {

    /**
     * Constante dada no começo do programa para calcular o tempo de "geração" de cada passturista.
     */
    public static int touristConstant;

    /**
     * Tempo que turista demora para chegar a margem da piscina.
     */
    public static int walkTime;

    /**
     * Instância de um turista.
     */
    private Tourist tourist;

    /**
     * Instância, compartilhada, de uma piscina (Lake).
     */
    private Lake lake;

    /**
     * Construtor que recebe instância, compartilhada, da piscina (Lake).
     * @param lake
     */
    public ThreadTourist(Lake lake) {
        tourist = new Tourist();
        this.lake = lake;
    }

    /**
     * Método run() que começa a execução da Thread.
     * Nesse método a thread fica "travada" por "tourist.id * touristConstant", que é o tempo
     * de geração de cada turista.
     * Em seguida é impresso uma mensagem informando que o turista começou a caminhada à margem.
     * A thread, mais uma vez, é travada desta vez por "walkTime".
     * Assim que esse tempo acaba é impresso a mensagem informando que o turista terminou a
     * caminhada e chegou na margem da piscina, nesse momento, atráves do método insertTourist()
     * da classe Lake, a thread tenta inserir um turista na piscina.
     */
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
