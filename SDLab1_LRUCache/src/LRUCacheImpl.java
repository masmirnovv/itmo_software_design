import java.util.HashMap;

public class LRUCacheImpl<K, V> implements LRUCache<K, V> {

    private final int sizeLimit;

    private final HashMap<K, V> map = new HashMap<>();
    private final CustomLinkedSet<K> keyLinkedSet = new CustomLinkedSetImpl<>();

    public LRUCacheImpl(int sizeLimit) {
        if (sizeLimit <= 0)
            throw new IllegalArgumentException("Expected sizeLimit > 0");
        this.sizeLimit = sizeLimit;
    }


    @Override
    public boolean add(K key, V value) {
        assert keySyncAssertion(key);
        if (map.containsKey(key)) {
            return false;
        } else {
            map.put(key, value);
            keyLinkedSet.addFirst(key);
            if (map.size() > sizeLimit) {
                map.remove(keyLinkedSet.removeLast());
            }
            assert sizeSyncAssertion();
            assert sizeAssertion();
            return true;
        }
    }

    @Override
    public boolean put(K key, V value) {
        assert keySyncAssertion(key);
        boolean verdict = true;
        if (map.containsKey(key)) {
            map.remove(key);
            keyLinkedSet.remove(key);
            verdict = false;
        }
        map.put(key, value);
        keyLinkedSet.addFirst(key);
        if (map.size() > sizeLimit) {
            map.remove(keyLinkedSet.removeLast());
        }
        assert sizeSyncAssertion();
        assert sizeAssertion();
        return verdict;
    }

    @Override
    public V getOrPut(K key, V defaultValue) {
        assert keySyncAssertion(key);
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            put(key, defaultValue);
            return defaultValue;
        }
    }

    @Override
    public V get(K key) {
        assert keySyncAssertion(key);
        return map.getOrDefault(key, null);
    }


    private boolean keySyncAssertion(K key) {
        return map.containsKey(key) == keyLinkedSet.contains(key);
    }

    private boolean sizeSyncAssertion() {
        return map.size() == keyLinkedSet.size();
    }

    private boolean sizeAssertion() {
        return map.size() <= sizeLimit;
    }

}
