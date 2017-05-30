package Tourist;

/**
 * Created by yellow-umbrella on 17/05/17.
 * Classe que define um turista com um id único.
 */
public class Tourist {

    /**
     *  Váriavel que define o id do turista gerado.
     *  É imcrementado a cada novo passárinho.
     */
    private static int idCounter = 0;

    /**
     * Atributo que define unicamente um turista.
     */
    public int id;

    /**
     * Construtor padrão que seta o id do turista.
     */
    public Tourist() {
        id = idCounter++;
    }
}
