/** An implementation of LRU cache data structure.
 *
 * A map which stores only limited number of
 * key-value pairs. If cache size exceeds limit, the
 * least recently used key is removed (LRU policy).
 *
 * The K class must be a proper hashable data
 * (with proper equals() and hashCode() methods)
 *
 * @author Mikhail Smirnov
 */
public interface LRUCache<K, V> {

    /** Adds given key-value pair to the cache.
     * Does not override an existing key-value pair.
     *
     * @return true if key & value added successfully
     *         (key didn't exist in the cache before),
     *         false otherwise
     */
    boolean add(K key, V value);

    /** Adds given key-value pair to the cache.
     * Will override an existing key-value pair.
     *
     * @return true if key & value added successfully
     *         (key didn't exist in the cache before),
     *         false otherwise
     */
    boolean put(K key, V value);

    /** Returns value from the cache associated with
     * given key. If key doesn't exist in cache, puts &
     * returns a given default value
     */
    V getOrPut(K key, V defaultValue);

    /** Returns value from the cache associated with
     * given key. If key doesn't exist in cache, returns null.
     */
    V get(K key);

}