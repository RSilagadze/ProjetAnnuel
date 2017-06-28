package interfaces;

/**
 * Created by Romaaan on 28/06/2017.
 */
public interface IPostback<T> {
    void onPostedBack(T postObject);
}
