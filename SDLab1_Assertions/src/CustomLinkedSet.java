/** An implementation of linked set allowing "add to head",
 * "remove from tail" and "remove specific element" operations.
 *
 * An E class must be a proper hashable data
 * (with proper equals() and hashCode() methods)
 *
 * @author Mikhail Smirnov
 */
public interface CustomLinkedSet<E> {

    /** Checks if specific element contains at the set
     *
     * @param elem element to check for existence
     * @return true if an element exists at the set,
     *         false otherwise
     */
    boolean contains(E elem);

    /** Returns size of the set
     *
     * @return number of elements at the set
     */
    int size();

    /** Adds new element to the head if this element is missing,
     * or does nothing otherwise
     *
     * @param elem element to add to
     * @return true if element added successfully (no equal elements at the set before),
     *         false otherwise
     */
    boolean addFirst(E elem);

    /** Removes an element from the tail.
     *
     * @return removed element
     * @throws java.util.NoSuchElementException if the set is empty
     */
    E removeLast();

    /** Removes specific element from the set.
     *
     * @param elem element to remove from
     * @return true if element removed successfully (equal element existed at the set),
     *         false otherwise
     */
    boolean remove(E elem);

}
