import java.util.*;

public class ArrayMap<K,V> extends AbstractMap<K,V> {
    //clasa generica
    //modeleaza un dictionar realizat ca o colectie de obiecte ArrayMapEntry
    ArrayList<ArrayMapEntry> dictionary;

    public ArrayMap() {
        dictionary = new ArrayList<ArrayMapEntry> ();
    }

    @Override
    public V put (K key, V value) {
        ArrayMapEntry <K, V> pair = new ArrayMapEntry <K, V> (key, value);
        dictionary.add(pair);
        return value;
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.get(i).key.equals(key))
                return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        for(int i = 0; i < dictionary.size(); i++) {
            if(dictionary.get(i).key.equals(key))
                return (V) dictionary.get(i).value;
        }
        return null;
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

    @Override
    public int size() {
        return dictionary.size();
    }

    @Override
    public Set<Entry<K,V>> entrySet() {
        Set h = new HashSet();
        h.addAll(dictionary);
        return h;
    }


    class ArrayMapEntry<K,V> implements Map.Entry<K,V> {
        private K key;
        private V value;

        private ArrayMapEntry (K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey()
        {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }

        @Override
        public String toString() {
            return value.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof ArrayMapEntry)) {
                return false;
            }
            else {
                ArrayMapEntry<K, V> obj1 = this;
                ArrayMapEntry<K, V> obj2 = (ArrayMapEntry<K,V>) obj;
                return (obj1.getKey()==null ? obj2.getKey()==null : obj1.getKey().equals(obj2.getKey()))  &&
                        (obj1.getValue()==null ? obj2.getValue()==null : obj1.getValue().equals(obj2.getValue()));
            }
        }

        @Override
        public int hashCode() {
            return (this.getKey() == null ? 0 : this.getKey().hashCode()) ^ (this.getValue() == null ? 0
                    : this.getValue().hashCode());
        }
    }
}

