package Birds;

/**
 * Created by yellow-umbrella on 17/05/17.
 */
public class Bird {
    private static int idCounter = 0;
    public int id;

    public Bird() {
        this.id = idCounter++;
    }
}
