package Birds;

import Lake.Lake;

/**
 * Created by yellow-umbrella on 20/05/17.
 * Classe que define um consumidor de passárinho.
 * Essa classe possui uma Thread própria onde o mesmo tenta consumir um passárinho da classe Lake
 */
public class BirdEater implements Runnable {

    /**
     * Váriavel que conta quantos passárinhos deverão ser consumidos.
     */
    private int consume;

    /**
     * Instância, compartilhada, da piscina (Classe Lake).
     */
    private Lake lake;

    /**
     * Consturtor padrão, que recebe uma instância da piscina (Lake) e a quantidade de passárinhos
     * a ser consumido.
     * @param lake Instância, compartilhada, da piscina.
     * @param consume Quantidade de ppassárinhos a serem "consumidos".
     */
    public BirdEater(Lake lake, int consume) {
        this.lake = lake;
        this.consume = consume;
    }

    /**
     * Método run() que é chamado, pelo start(), para iniciar a Thread.
     * Dentro desse método há um loop o qual não é quebrado enquanto essa classe não tenha consumido
     * "consume" passárinhos.
     * Dentro desse loop, atráves do método removeBird() da classe Lake, é removido um passárinho
     * da piscina.
     *
     * No final do método caso a o método run() da classe TouristEater tenha terminado primeiro
     * é impreso a mensagem de final da observação.
     */
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
