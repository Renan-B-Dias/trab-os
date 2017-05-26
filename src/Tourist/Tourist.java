package Tourist;

/**
 * Created by yellow-umbrella on 17/05/17.
 */
public class Tourist {
    private static int idCounter = 0;
    public int id;

    public Tourist() {
        id = idCounter++;
    }
}
