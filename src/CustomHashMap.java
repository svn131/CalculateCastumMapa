import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomHashMap<K, V> extends HashMap<K, V> {
//    private Object[] table; // Поле table

    transient CustomNode<K, V>[] table = new CustomNode[16];

//    { table = new CustomNode[16];}
    //////////////////

    static class TreeNode<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        TreeNode<K, V> parent;

        TreeNode(int hash, K key, V value, TreeNode<K, V> left, TreeNode<K, V> right, TreeNode<K, V> parent) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    private static class CustomNode<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        CustomNode<K, V> next;

        CustomNode(int hash, K key, V value, CustomNode<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;

            if (o instanceof Map.Entry<?, ?> e &&
                    Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue())) {
                return true;
            }

            return false;
        }
    }

    ///////////////////////////////
    @Override
    public V put(K key, V value) {
        int bucketIndex = calculateBucketIndex(key);
        System.out.println("Добавление в бакет номер " + bucketIndex);

        CustomNode<K, V> node = getNode(key);
        System.out.println("Объект с ключом " + key + " добавлен в бакет номер " + bucketIndex + ", нода: " + node);

//        if (node instanceof CustomHashMap.TreeNode<K, V>) {
//            System.out.println("Бакет переродился в дерево");
//        }

        int hashss = key.hashCode();
        int buckhash = (hashss & 0x7FFFFFFF);

        CustomNode<K, V> newNode = new CustomNode<>(buckhash, key, value, null);


        return super.put(key, value);
    }

    @Override
    public V get(Object key) {
        int bucketIndex = calculateBucketIndex(key);
        System.out.println("Получение из бакета номер " + bucketIndex);

        CustomNode<K, V> node = getNode(key);
        System.out.println("Объект с ключом " + key + " получен из бакета номер " + bucketIndex + ", нода: " + node);

        return super.get(key);
    }

    private CustomNode<K, V> getNode(Object key) {
        int hash = key.hashCode();
        int bucketIndex = (hash & 0x7FFFFFFF) % this.table.length;

        System.out.println("Индексссссссссссссссссссссссссссссссссссссссссссссссссссс бакета для ноды " + bucketIndex);
        System.out.println(this.table[bucketIndex]);

        ; // Вычисление индекса бакета
        CustomNode<K, V> node = (CustomNode<K, V>) this.table[bucketIndex];
        System.out.println("Нода из бакета номер " + bucketIndex + ": " + node);
        return node;
    }

    private int calculateBucketIndex(Object key) {
        int hash = key.hashCode();

        int bucketIndex = 0;
        if (this.table != null) {
            bucketIndex = (hash & 0x7FFFFFFF) % this.table.length;


        }
        return bucketIndex;

    }


   int getCapacity() {


        try {
            return table.length;
        } catch (NullPointerException e) {

        }
       return 0;


    }
}

