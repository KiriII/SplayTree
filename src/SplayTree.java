import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SplayTree<T extends Comparable<T>> implements SortedSet<T>{

    private Node root;
    private int size = 0;

    private class Node{

        private T key;
        Node left;
        Node right;
        Node parent;

        Node (T key){
            this.key = key;
        }

        private void setParent (Node child , Node parent){
            if (child != null){
                child.parent = parent;
            }
        }

        private void keepParent(Node v){
            setParent(v.left , v);
            setParent(v.right , v);
        }
    }

    private void rotate(Node parent , Node child){
        if ((parent == null) || (child == null)) return;
        Node gparent = parent.parent;
        if (gparent != null) {
            if (gparent.left == parent) {
                if (parent.left == child){
                    if (child.right == null){
                        parent.left = null;
                    }
                    parent.left = child.right;
                    gparent.left = child;
                    child.right = parent;
                }
                else{
                    if (child.left == null){
                        parent.right = null;
                    }
                    parent.right = child.left;
                    gparent.left = child;
                    child.left = parent;
                }
            }
            else {
                if (parent.left == child){
                    parent.left = child.right;
                    gparent.right = child;
                    child.right = parent;
                }
                else{
                    parent.right = child.left;
                    gparent.right = child;
                    child.left = parent;
                }

            }
            root.keepParent(gparent);
        }
        else {
            if (parent.left == child) {
                Node ver = child.right;
                child.right = parent;
                parent.left = ver;
            } else {
                Node ver = child.left;
                child.left = parent;
                parent.right = ver;
            }
        }
        root.keepParent(child);
        root.keepParent(parent);
        child.parent = gparent;
        if (root.parent != null) root = root.parent;
    }

    // "выворачивание" дерево относительно вершины v
    private void splay(Node v){
        if (v.parent == null) {
            return;
        }
        Node parent = v.parent;
        Node gparent = parent.parent;
        if (gparent == null){
            rotate(parent , v);
        }
        else {
            boolean zigzig = (gparent.left == parent) == (parent.left == v);
            if (zigzig) {
                rotate(gparent, parent);
                rotate(parent, v);
            } else {
                rotate(parent, v);
                rotate(gparent, v);
            }

            splay(v);
        }
    }

    private Node find(Node v , T key){
        if (v.key == key) {
            splay(v);
            return v;
        }
        if (v == null) return null;
        if (key.compareTo(v.key) < 0) {
            if (v.left == null){
                splay(v);
                return v;
            }
            return find(v.left, key);
        }
        else {
            if (v.right == null) {
                splay(v);
                return v;
            }
            return find(v.right, key);
        }
    }

    // делит на 2 дерева относительно ближайших к заданному значениюю в одном дереве меньше
    // заданной велечины, в другом больше
    private Node split(Node root , T key){

        Node result = new Node(key);
        if (root == null) return null;
        Node newRoot = find(root , key);
        if (newRoot.key == key){
            newRoot.setParent(newRoot.left , null);
            newRoot.setParent(newRoot.right ,null);
            result.left = newRoot.left;
            result.right = newRoot.right;
            return result;
        }

        if (newRoot.key.compareTo(key) < 0){
            splay(newRoot);
            result.right = newRoot.right;
            newRoot.setParent(newRoot.right , null );
            result.left = newRoot;
            return result;
        }
        else {
            splay(newRoot);
            result.left = newRoot.left;
            newRoot.setParent(newRoot.left , null);
            result.right = newRoot;
            return result;
        }

    }

    private void insert(T key){
        size++ ;
        Node vers = split(root , key);
        Node ver = new Node(key);
        if (vers.left != null) ver.left = vers.left;
        if (vers.right != null) ver.right = vers.right;
        root = ver;
        root.keepParent(root);
    }

    // объединение деревьев (элементы правого больше)
    private Node merge(Node left , Node right){
        if (left == null) return right;
        if (right == null) return left;
        right = find(right , left.key);

        right.left = left;
        left.parent = right;
        return right;
    }

    private Node delete(T key){
        size-- ;
        root = find(root , key);
        root.setParent(root.left , null);
        root.setParent(root.right , null);
        return merge(root.left, root.right);
    }

    private Node moveToVertex(T key){
        find(root , key);
        return root;
    }

    private Node minimum(Node start){
        Node current = start;
        while (current.left != null) current = current.left;
        find(root , current.key);
        return current;
    }

    private Node maximum(Node start){
        Node current = start;
        while (current.right != null) current = current.right;
        find(root , current.key);
        return current;
    }

    private class TreeIterator implements Iterator<T>{

        public Node first = null;
        public Node current = null;
        public Node last = null;

        private TreeIterator(Node start , Node end){
            if (root != null){
                this.first = start;
                this.last = end;
            }
        }

        public Node findNext() {
            Node next ;
            if (current == maximum(root)) return null;
            if (current == null) {
                return find(first , first.key);
            }
            else if (current.right == null && current.parent.left == current)
                return find(current.parent , current.parent.key);
            else if (current.right != null){
                next = minimum(current.right);
                return find(next , next.key);
            }
            else {
                next = current;
                while (next.key.compareTo(next.parent.key) < 0){
                    next = next.parent;
                }
                find(next , next.key);
            }
            return find(next , next.key);
        }

        @Override
        public boolean hasNext() {
            if (current == maximum(root)) return false;
            return true;
        }

        @Override
        public T next() {
            if (root == null) throw new NoSuchElementException();
            current = findNext();
            return current.key;
        }
    }

    @Nullable
    @Override
    public Comparator comparator() {
        return (Comparator<T>) Comparator.naturalOrder();
    }

    @NotNull
    @Override
    public SortedSet subSet(T fromElement, T toElement) {
        SplayTree<T> splay = new SplayTree<>();
        TreeIterator iter = new TreeIterator(minimum(root) , maximum(root));
        while (iter.hasNext()){
            splay.add(iter.current.key);
            iter.next();
        }
        return splay;
    }

    @NotNull
    @Override
    public SortedSet headSet(T toElement) {
        return subSet(minimum(root).key , toElement);
    }

    @NotNull
    @Override
    public SortedSet tailSet(T fromElement) {
        return subSet(fromElement , maximum(root).key);
    }

    @Override
    public T first() {
        if (isEmpty()) throw new NoSuchElementException();
        return minimum(root).key;
    }

    @Override
    public T last() {
        if (isEmpty()) throw new NoSuchElementException();
        return maximum(root).key;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (root == null);
    }

    @Override
    public boolean contains(Object o) {
        T t = (T) o;
        find ( root , t);
        if (root.key == t) return true;
        return false;
    }

    @NotNull
    @Override
    public Iterator iterator() {
        return new TreeIterator(minimum(root) , maximum(root));
    }

    @NotNull
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int count = 0;
        TreeIterator iter = new TreeIterator(minimum(root) , maximum(root));
        while (iter.hasNext()){
            iter.next();
            array[count] = iter.current.key;
            count++ ;

        }
        return array;
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        if (a.length < size){
            a = (T1[])java.lang.reflect.Array.newInstance(a.getClass(), size);
        }
        TreeIterator iter = new TreeIterator(minimum(root) , maximum(root));
        for (int i = 0; i < size; i++){
            a[i] = (T1)iter.next();
        }
        if (a.length > size){
            for (int i = size; i < a.length; i++){
                a[i] = null;
            }
        }
        return a;
    }

    @Override
    public boolean add(T o) {
        if (o != null) {
            if (root == null) {
                root = new Node(o);
                size = 1;
                return true;
            }
            if (find(root , o).key == o) return false; // find находит ближайший элемент(в частном случае тот который мы ищем)
            else {
                insert(o);
                return true;
            }
        }
        else return false;
    }

    @Override
    public boolean remove(Object o) {
        T t = (T) o;
        if (o != null) {
            if (find(root , t).key != o) return false;
            else {
                root = delete(t);
                return true;
            }
        }
        else return false;
    }

    @Override
    public boolean addAll(@NotNull Collection c) {
        if (c.isEmpty()) return false;
        T forCheck = null;
        if (root != null) forCheck = root.key;
        Iterator iter = c.iterator();
        while (iter.hasNext()){
            add((T)iter.next());
        }
        if (forCheck != root.key) return true;
        return false;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean removeAll(@NotNull Collection c) {
        if (c.isEmpty()) return false;
        T forCheck = null;
        if (root != null) forCheck = root.key;
        Iterator iter = c.iterator();
        while (iter.hasNext()){
            remove((T)iter.next());
        }
        if (forCheck != root.key) return true;
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection c) {
        SplayTree newTree = new SplayTree();
        int startSize = size;
        boolean forCheck = false;
        Object[] objects = c.toArray();
        for (Object o : objects){
            T t = (T) o;
            if (contains(t)){
                if (newTree.isEmpty()) newTree.root = new Node(t);
                else newTree.insert(t);
            }
            else forCheck = true;
        }
        root = newTree.root;
        if ((forCheck == true ) || (startSize != newTree.size)) return true;
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection c) {
        if (isEmpty() && c.size() > 0) return false;
        Object[] objects = c.toArray();
        for (Object o : objects) {
            T t = (T) o;
            if (!contains(t)) return false;
        }
        return true;
    }

}
