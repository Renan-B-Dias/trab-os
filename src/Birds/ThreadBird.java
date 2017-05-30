package Birds;

import Lake.Lake;

import java.util.Date;

/**
 * Created by yellow-umbrella on 20/05/17.
 * Classe que define uma Thread que contem um passárinho.
 * Essa classe vai tentar inserir um passárinho a cada "bird.id * birdConstant"
 */
public class ThreadBird implements Runnable {

    /**
     * Contante dada no começo do programa para gerar o tempo de "geração" de cada passárinho.
     */
    public static int birdConstant;

    /**
     * Instância de passárinho.
     */
    private Bird bird;

    /**
     * Instância compartilhada da psicina (Lake).
     */
    private Lake lake;

    /**
     * Construtor que recebe a instância compartilhada da piscina (Lake).
     * @param lake
     */
    public ThreadBird(Lake lake) {
        this.lake = lake;
        this.bird = new Bird();
    }

    /**
     * Método run() que começa a execução da Thread.
     * Nesse método a Thread ficara travada por "bird.id * birdConstant", que é o tempo de geração
     * de cada passárinho.
     * Em seguida, atráves do método insertBird(Bird bird) da classe Lake, é tentado inserir um pass
     * árinho na piscina.
     */
    public void run() {
        try {
            Thread.sleep(this.bird.id * birdConstant);  // Time to generate bird

            System.out.println(new Date() + " O passarinho " + bird.id + " chegou a margem da piscina pela primeira vez [" + lake.birdCount + " passarinhos]");
            lake.insertBird(bird);
        } catch(InterruptedException e) {
            // TODO
        }
    }
}
