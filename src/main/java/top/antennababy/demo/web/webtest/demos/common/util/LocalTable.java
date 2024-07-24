package top.antennababy.demo.web.webtest.demos.common.util;


import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LocalTable<T, T1, T2> extends LinkedHashMap<T,LinkedHashMap<T1,T2>>{

    /**
     * Returns {@code true} if the table contains a mapping with the specified row and column keys.
     *
     * @param rowKey    key of row to search for
     * @param columnKey key of column to search for
     */
    public boolean contains(Object rowKey, Object columnKey) {
        LinkedHashMap<T1, T2> map = super.get(rowKey);
        return map != null && map.containsKey(columnKey);
    }

    /**
     * Returns {@code true} if the table contains a mapping with the specified row key.
     *
     * @param rowKey key of row to search for
     */
    public boolean containsRow(Object rowKey) {
        return super.containsKey(rowKey);
    }

    /**
     * Returns {@code true} if the table contains a mapping with the specified column.
     *
     * @param columnKey key of column to search for
     */
    public boolean containsColumn(Object columnKey) {
        for (LinkedHashMap<T1, T2> value : super.values()) {
            if (value.containsKey(columnKey)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns {@code true} if the table contains a mapping with the specified value.
     *
     * @param value value to search for
     */
    @Override
    public boolean containsValue(Object value) {
        for (LinkedHashMap<T1, T2> value1 : super.values()) {
            if (value1.containsValue(value)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value corresponding to the given row and column keys, or {@code null} if no such
     * mapping exists.
     *
     * @param rowKey    key of row to search for
     * @param columnKey key of column to search for
     */
    public T2 get(Object rowKey, Object columnKey) {
        LinkedHashMap<T1, T2> map = super.get(rowKey);
        if (map != null){
            return map.get(columnKey);
        }
        return null;
    }

    /**
     * Returns {@code true} if the table contains no mappings.
     */
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Returns the number of row key / column key / value mappings in the table.
     */
    @Override
    public int size() {
      return  super.values().stream().mapToInt(LinkedHashMap::size).sum();
    }

    /**
     * Removes all mappings from the table.
     */
    @Override
    public void clear() {
        super.clear();
    }

    /**
     * Associates the specified value with the specified keys. If the table already contained a
     * mapping for those keys, the old value is replaced with the specified value.
     *
     * @param rowKey    row key that the value should be associated with
     * @param columnKey column key that the value should be associated with
     * @param value     value to be associated with the specified keys
     * @return the value previously associated with the keys, or {@code null} if no mapping existed
     * for the keys
     */
    public T2 put(T rowKey, T1 columnKey, T2 value) {
        LinkedHashMap<T1, T2> map = super.computeIfAbsent(rowKey, k -> new LinkedHashMap<>());
        return map.put(columnKey,value);
    }



    /**
     * Returns a view of all mappings that have the given row key. For each row key / column key /
     * value mapping in the table with that row key, the returned map associates the column key with
     * the value. If no mappings in the table have the provided row key, an empty map is returned.
     *
     * <p>Changes to the returned map will update the underlying table, and vice versa.
     *
     * @param rowKey key of row to search for in the table
     * @return the corresponding map from column keys to values
     */
    public Map<T1, T2> row(T rowKey) {
        return super.get(rowKey);
    }

    /**
     * Returns a view of all mappings that have the given column key. For each row key / column key /
     * value mapping in the table with that column key, the returned map associates the row key with
     * the value. If no mappings in the table have the provided column key, an empty map is returned.
     *
     * <p>Changes to the returned map will update the underlying table, and vice versa.
     *
     * @param columnKey key of column to search for in the table
     * @return the corresponding map from row keys to values
     */
    public Map<T, T2> column(T1 columnKey) {
        LinkedHashMap<T, T2> result = new LinkedHashMap<>();
        for (Map.Entry<T, LinkedHashMap<T1, T2>> entry : super.entrySet()) {
            T key = entry.getKey();
            T2 value = entry.getValue().get(columnKey);
            if (value != null){
                result.put(key,value);
            }
        }
        return result;
    }



    /**
     * Returns a set of row keys that have one or more values in the table. Changes to the set will
     * update the underlying table, and vice versa.
     *
     * @return set of row keys
     */
    public Set<T> rowKeySet() {
        return super.keySet();
    }

    /**
     * Returns a set of column keys that have one or more values in the table. Changes to the set will
     * update the underlying table, and vice versa.
     *
     * @return set of column keys
     */
    public Set<T1> columnKeySet() {
        Set<T1> result = new HashSet<>();
        for (LinkedHashMap<T1, T2> value : super.values()) {
            result.addAll(value.keySet());
        }
        return result;
    }
    /**
     * Returns a view that associates each column key with the corresponding map from row keys to
     * values. Changes to the returned map will update this table. The returned map does not support
     * {@code put()} or {@code putAll()}, or {@code setValue()} on its entries.
     *
     * <p>In contrast, the maps returned by {@code columnMap().get()} have the same behavior as those
     * returned by {@link #column}. Those maps may support {@code setValue()}, {@code put()}, and
     * {@code putAll()}.
     *
     * @return a map view from each column key to a secondary map from row keys to values
     */
    public Map<T1, Map<T, T2>> columnMap() {
        LinkedHashMap<T1, Map<T, T2>> result = new LinkedHashMap<>();
        for (Map.Entry<T, LinkedHashMap<T1, T2>> entry : super.entrySet()) {
            T rowKey = entry.getKey();
            for (Map.Entry<T1, T2> entry1 : entry.getValue().entrySet()) {
                T1 columnKey = entry1.getKey();
                T2 value = entry1.getValue();
                if (!result.containsKey(columnKey)){
                    result.put(columnKey,new LinkedHashMap<>());
                }
                result.get(columnKey).put(rowKey,value);
            }
        }
        return result;
    }

}
