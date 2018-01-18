import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class Tests {
    @Test
    public void firstTest(){
        SplayTree<Integer> tree = new SplayTree();
        tree.add(4);
        tree.add(5);
        tree.add(2);
        tree.add(1);
        assertEquals(1 , (int)tree.first());
    }

    @Test
    public void lastTest() {
        SplayTree<Integer> tree = new SplayTree();
        tree.add(4);
        tree.add(5);
        tree.add(2);
        tree.add(1);
        assertEquals(5 , (int)tree.last());
    }

    @Test
    public void addTest(){
        SplayTree<Integer> tree = new SplayTree();
        assertEquals(true , tree.add(4));
        assertEquals(true , tree.add(5));
        assertEquals(true , tree.add(2));
        assertEquals(true , tree.add(1));
        assertEquals(false , tree.add(4));
        assertEquals(4 , tree.size());
    }

    @Test
    public void removeTest(){
        SplayTree<Integer> tree = new SplayTree();
        tree.add(4);
        tree.add(5);
        tree.add(2);
        tree.add(1);
        assertEquals(4 , tree.size());
        assertEquals(true , tree.remove(4));
        assertEquals(true , tree.remove(5));
        assertEquals(true , tree.remove(2));
        assertEquals(true , tree.remove(1));
        assertEquals(0 , tree.size());
    }

    @Test
    public void containsTest(){
        SplayTree<Integer> tree = new SplayTree();
        tree.add(4);
        tree.add(5);
        tree.add(2);
        tree.add(1);
        assertEquals(true , tree.contains(4));
        assertEquals(false , tree.contains(7));
    }

    @Test
    public void iteratorTest(){
        SplayTree<Integer> tree = new SplayTree();
        tree.add(4);
        tree.add(5);
        tree.add(2);
        tree.add(1);
        Iterator iter = tree.iterator();
        assertEquals(true , iter.hasNext());
        assertEquals(1 , iter.next());
        assertEquals(true , iter.hasNext());
        assertEquals(2 , iter.next());
        assertEquals(true , iter.hasNext());
        assertEquals(4 , iter.next());
        assertEquals(true , iter.hasNext());
        assertEquals(5 , iter.next());
        assertEquals(false , iter.hasNext());
    }

    @Test
    public void toArrayTest(){
        SplayTree<Integer> tree = new SplayTree();
        tree.add(4);
        tree.add(5);
        tree.add(2);
        tree.add(1);
        Object[] array = tree.toArray();
        assertEquals(1 , array[0]);
        assertEquals(2 , array[1]);
        assertEquals(4 , array[2]);
        assertEquals(5 , array[3]);
        Object[] array2 = new Object[tree.size()];
        tree.toArray(array2);
        assertEquals(1 , array2[0]);
        assertEquals(2 , array2[1]);
        assertEquals(4 , array2[2]);
        assertEquals(5 , array2[3]);
    }

    @Test
    public void ForAllTests(){
        SplayTree<Integer> tree = new SplayTree();
        Set<Integer> col = new SplayTree<>();
        col.add(4);
        col.add(5);
        col.add(2);
        col.add(1);
        assertEquals(true , tree.addAll(col));
        assertEquals(false , tree.add(4));
        assertEquals(false , tree.add(5));
        assertEquals(false , tree.add(2));
        assertEquals(false , tree.add(1));

        tree.add(7);
        tree.add(3);

        assertEquals(true , tree.containsAll(col));
        assertEquals(true , tree.removeAll(col));
        assertEquals(false , tree.containsAll(col));

        assertEquals(false , tree.contains(1));
        assertEquals(false , tree.contains(2));
        assertEquals(true , tree.contains(3));
        assertEquals(false , tree.contains(4));
        assertEquals(false , tree.contains(5));
        assertEquals(true , tree.contains(7));
    }

    @Test
    public void ForRetainAllTests() {
        SplayTree<Integer> tree = new SplayTree();
        Set<Integer> col = new SplayTree<>();
        col.add(4);
        col.add(5);
        col.add(2);
        col.add(1);
        tree.add(4);
        tree.add(5);
        tree.add(2);
        tree.add(1);
        tree.add(7);
        tree.add(3);
        assertEquals(true , tree.retainAll(col));
        assertEquals(true , tree.contains(1));
        assertEquals(true , tree.contains(2));
        assertEquals(false , tree.contains(3));
        assertEquals(true , tree.contains(4));
        assertEquals(true , tree.contains(5));
        assertEquals(false , tree.contains(7));
    }
}
