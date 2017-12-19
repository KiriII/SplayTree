
public class tree {
    Vertex root;

    // поворот относительно родителя
    public void rotate(Vertex parent , Vertex child){
        if ((parent == null) || (child == null)) return;
        Vertex gparent = parent.parent;
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
                Vertex ver = child.right;
                child.right = parent;
                parent.left = ver;
            } else {
                Vertex ver = child.left;
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
    public void splay(Vertex v){
        if (v.parent == null) {
            return;
        }
        Vertex parent = v.parent;
        Vertex gparent = parent.parent;
        if (gparent == null){
            rotate(parent , v);
            return;
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

    public Vertex find(Vertex v , int key){
        if (v.intKey == key) {
            splay(v);
            return v;
        }
        if (v == null) return null;
        if (key < v.intKey) {
            if (v.left == null){
                splay(v);
                return v;
            }
            return find(v.left, key);
        }
        if (key > v.intKey) {
            if (v.right == null) {
                splay(v);
                return v;
            }
            return find(v.right, key);
        }
        return null;
    }

    // делит на 2 дерева относительно ближайших к заданному значениюю в одном дереве меньше
    // заданной велечины, в другом больше
    public Vertex[] split(Vertex root , int key){
        Vertex[] result = new Vertex[2];
        if (root == null) return null;
        Vertex newRoot = find(root , key);
        if (newRoot.intKey == key){
            newRoot.setParent(newRoot.left , null);
            newRoot.setParent(newRoot.right ,null);
            result[0] = newRoot.left;
            result[1] = newRoot.right;
            return result;
        }

        if (newRoot.intKey < key){
            splay(newRoot);
            result[1] = newRoot.right;
            newRoot.setParent(newRoot.right , null );
            result[0] = newRoot;
            return result;
        }
        else {
            splay(newRoot);
            result[0] = newRoot.left;
            newRoot.setParent(newRoot.left , null);
            result[1] = newRoot;
            return result;
        }

    }

    public void insert(int key){
        Vertex[] vers = split(root , key);
        Vertex ver = new Vertex();
        ver.intKey = key;
        if (vers[0] != null) ver.left = vers[0];
        if (vers[1] != null) ver.right = vers[1];
        root = ver;
        root.keepParent(root);
    }

    // объединение деревьев (элементы правого больше)
    public Vertex merge(Vertex left , Vertex right){
        if (left == null) return right;
        if (right == null) return left;
        right = find(right , left.intKey);

        right.left = left;
        left.parent = right;
        return right;
    }

    public Vertex remove(int key){
        root = find(root , key);
        root.setParent(root.left , null);
        root.setParent(root.right , null);
        return merge(root.left, root.right);
    }

    public Vertex moveToVertex(int key){
        find(root , key);
        return root;
    }

}
