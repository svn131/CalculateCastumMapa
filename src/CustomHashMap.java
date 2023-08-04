import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomHashMap<K, V> extends HashMap<K, V> {

    @SuppressWarnings("unchecked")
    transient CustomNode<K, V>[] table = (CustomNode<K, V>[]) new CustomNode<?, ?>[16];


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
        CustomNode<K, V> newNode = new CustomNode<>(key.hashCode(), key, value, null);
        CustomNode<K, V> existingNode = this.table[bucketIndex];

        if (existingNode == null) {
            this.table[bucketIndex] = newNode;
        } else {
            int nodeIndex = 1;
            while (existingNode.next != null) {
                existingNode = existingNode.next;
                nodeIndex++;
            }
            existingNode.next = newNode;
            nodeIndex++;
            System.out.println("Объект с ключом " + key + " добавлен в бакет номер " + bucketIndex + ", нода номер: " + nodeIndex + " содержит ноду " + newNode);
        }

        // Проверяем, достигнуто ли пороговое значение для перестройки массива
        if (size() >= table.length * 0.75) {
            System.out.println("Перестройка хем мапы");
            resizeMap();
        }

        return super.put(key, value);
    }

    private void resizeMap() {
        int newCapacity = table.length * 2;
        CustomNode<K, V>[] newTable = (CustomNode<K, V>[]) new CustomNode<?, ?>[newCapacity];

        // Перехеширование элементов в новый массив
        for (CustomNode<K, V> oldNode : table) {
            while (oldNode != null) {
                CustomNode<K, V> nextNode = oldNode.next;
                int newIndex = calculateBucketIndex(oldNode.key, newCapacity);
                oldNode.next = newTable[newIndex];
                newTable[newIndex] = oldNode;
                oldNode = nextNode;
            }
        }

        // Обновляем ссылку на новый массив
        table = newTable;
    }

    @Override
    public V get(Object key) {
        int bucketIndex = calculateBucketIndex(key);
//        System.out.println("Получение из бакета номер " + bucketIndex);
//
      getNode(key);
//        int nodeIndex = 0;
//        // Если нода не null, выведите ее содержимое с порядковым номером
//        if (node != null) {
//              nodeIndex = 0;
//            while (node != null) {
//                System.out.println("Нода номер " + nodeIndex + " из бакета номер " + bucketIndex + ": " + node);
//                node = node.next;
//                nodeIndex++;
//            }
//        } else {
//            System.out.println("Нода из бакета номер " + bucketIndex + ": " + node);
//        }
//
//        System.out.println("Объект с ключом " + key + " получен из бакета номер " + bucketIndex + ", нода: " + nodeIndex);

        return super.get(key);
    }


    private CustomNode<K, V> getNode(Object key) {
        int bucketIndex = calculateBucketIndex(key);
        System.out.println("Индекс бакета для ноды " + bucketIndex);
        CustomNode<K, V> node = this.table[bucketIndex];

        if (node != null) {
            int nodeIndex = 0;
            CustomNode<K, V> currentNode = node;
            while (currentNode != null) {
                System.out.println("получение объекта - Нода номер " + nodeIndex + " из бакета номер " + bucketIndex + ": " + currentNode);
                currentNode = currentNode.next;
                nodeIndex++;
            }
        } else {
            System.out.println("Нода из бакета номер " + bucketIndex + " нода была null - её содержимое: " + node);
        }

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
private int calculateBucketIndex(Object key, int capacity) {
    int hash = key.hashCode();
    if (table == null) {
        return 0;
    }
    return (hash & 0x7FFFFFFF) % capacity;
}


   int getCapacity() {


        try {
            return table.length;
        } catch (NullPointerException e) {

        }
       return 0;


    }
}

