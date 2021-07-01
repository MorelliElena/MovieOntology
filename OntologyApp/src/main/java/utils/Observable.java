package utils;

/**
 *Class that can be observed by the other
 * @param <X>
 */
public interface Observable <X> {

    /**
     * add a new observer
     * @param observer
     */
    void addObserver(Observer<X> observer);

    /**
     * remove observer
     * @param observer
     */
    void removeObserver(Observer<X> observer);


}
