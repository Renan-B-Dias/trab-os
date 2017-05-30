package Tourist;

import Lake.Lake;

import java.util.Date;

/**
 * Created by yellow-umbrella on 19/05/17.
 * Classe que define um consumidor de Turistas.
 * Essa classe possui uma Thread própria onde o mesmo tenta consumir um turista da classe Lake
 */
public class TouristEater implements Runnable {

    /**
     * Váriavel que conta quantos turistas deverão ser consumidos.
     */
    private int consume;

    /**
     * Instância, compartilhada, da piscina (Classe Lake).
     */
    private Lake lake;

    /**
     * * Consturtor padrão, que recebe uma instância da piscina (Lake) e a quantidade de turistas
     * a ser consumido.
     * @param lake
     * @param consume
     */
    public TouristEater(Lake lake, int consume) {
        this.lake = lake;
        this.consume = consume;
    }

    /**
     * Método run() que é chamado, pelo start(), para iniciar a Thread.
     * Dentro desse método há um loop o qual não é quebrado enquanto essa classe não tenha consumido
     * "consume" turistas.
     * Dentro desse loop, atráves do método removeTourist() da classe Lake, é removido um turista
     * da piscina.
     *
     * No final do método caso a o método run() da classe BirdEater tenha terminado primeiro
     * é impreso a mensagem de final da observação.
     */
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
