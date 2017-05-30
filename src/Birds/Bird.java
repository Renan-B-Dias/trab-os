package Birds;

/**
 * Created by yellow-umbrella on 17/05/17.
 * Classe que definie um passárinho.
 */
public class Bird {

    /**
     *  Váriavel que define o id do passárinho gerado
     *  É imcrementado a cada novo passárinho.
     */
    private static int idCounter = 0;

    /**
     * Atributo que define unicamente um Passarinho.
     */
    public int id;

    /**
     * Construtor padrão que seta o id do passárinho.
     */
    public Bird() {
        this.id = idCounter++;
    }
}
