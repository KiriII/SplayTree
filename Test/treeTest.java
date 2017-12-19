import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class treeTest {

    @Test
    public void rotateTest(){

        //           4                  2
        //          / \                / \
        //         2   5     ->       1   4
        //        / \        <-          / \
        //       1   3                  3   5
        tree tree = new tree();
        Vertex ver1 = new Vertex();
        ver1.intKey = 1;
        Vertex ver2 = new Vertex();
        ver2.intKey = 2;
        Vertex ver3 = new Vertex();
        ver3.intKey = 3;
        Vertex ver4 = new Vertex();
        ver4.intKey = 4;
        Vertex ver5 = new Vertex();
        ver5.intKey = 5;
        tree.root = ver4;
        ver4.right = ver5;
        ver4.left = ver2;
        ver2.left = ver1;
        ver2.right = ver3;
        tree.root.keepParent(ver1);
        tree.root.keepParent(ver2);
        tree.root.keepParent(ver3);
        tree.root.keepParent(ver5);
        assertEquals(4 , tree.root.intKey);
        tree.rotate(ver4 , ver2);
        assertEquals(2 , tree.root.intKey);
        assertEquals(1 , tree.root.left.intKey);
        assertEquals(4 , tree.root.right.intKey);
        assertEquals(3 , tree.root.right.left.intKey);
        assertEquals(5 , tree.root.right.right.intKey);
        tree.rotate(ver2 , ver4);
        assertEquals(4 , tree.root.intKey);
        assertEquals(2 , tree.root.left.intKey);
        assertEquals(5 , tree.root.right.intKey);
        assertEquals(1 , tree.root.left.left.intKey);
        assertEquals(3 , tree.root.left.right.intKey);
        //     4              4
        //    / \            / \
        //   2   5     ->   1   5
        //  / \              \
        // 1   3              2
        //                     \
        //                      3
        tree.rotate(ver2 , ver1);
        assertEquals(1 , tree.root.left.intKey);
        assertEquals(2 , tree.root.left.right.intKey);
        assertEquals(3 , tree.root.left.right.right.intKey);
    }

    @Test
    public void splayTest(){
        //     4
        //    / \
        //   2   5
        //  / \
        // 1   3
        tree tree = new tree();
        Vertex ver1 = new Vertex();
        ver1.intKey = 1;
        Vertex ver2 = new Vertex();
        ver2.intKey = 2;
        Vertex ver3 = new Vertex();
        ver3.intKey = 3;
        Vertex ver4 = new Vertex();
        ver4.intKey = 4;
        Vertex ver5 = new Vertex();
        ver5.intKey = 5;
        tree.root = ver4;
        ver4.right = ver5;
        ver4.left = ver2;
        ver2.left = ver1;
        ver2.right = ver3;
        tree.root.keepParent(ver1);
        tree.root.keepParent(ver2);
        tree.root.keepParent(ver3);
        tree.root.keepParent(ver5);
        tree.root.keepParent(ver4);
        tree.splay(ver3);
        //     3
        //    / \
        //   2   4
        //  /     \
        // 1       5
        assertEquals(3 , tree.root.intKey);
        assertEquals(4 , tree.root.right.intKey);
        assertEquals(5 , tree.root.right.right.intKey);
        assertEquals(2, tree.root.left.intKey);
        assertEquals(1, tree.root.left.left.intKey);
        tree.splay(ver1);
        // 1
        //  \
        //   2
        //    \
        //     3
        //      \
        //       4
        //        \
        //         5
        assertEquals(1 , tree.root.intKey);
        assertEquals(2 , tree.root.right.intKey);
        assertEquals(3 , tree.root.right.right.intKey);
        assertEquals(4 , tree.root.right.right.right.intKey);
        assertEquals(5 , tree.root.right.right.right.right.intKey);
        tree.splay(ver2);
        //     2
        //    / \
        //   1   3
        //        \
        //         4
        //          \
        //           5
        assertEquals(2 , tree.root.intKey);
        assertEquals(1 , tree.root.left.intKey);
        assertEquals(3 , tree.root.right.intKey);
        assertEquals(4 , tree.root.right.right.intKey);
        assertEquals(5 , tree.root.right.right.right.intKey);
        tree.splay(ver5);
        //          5
        //         /
        //        2
        //       / \
        //      1   4
        //         /
        //        3
        assertEquals(5 , tree.root.intKey);
        assertEquals(2 , tree.root.left.intKey);
        assertEquals(1 , tree.root.left.left.intKey);
        assertEquals(4 , tree.root.left.right.intKey);
        assertEquals(3 , tree.root.left.right.left.intKey);
        // оставлю чтобы были все 1-5 ну и мы вернулись назад
        tree.splay(ver4);
        //          4
        //         / \
        //        2   5
        //       / \
        //      1   3
        assertEquals(4 , tree.root.intKey);
        assertEquals(2 , tree.root.left.intKey);
        assertEquals(1 , tree.root.left.left.intKey);
        assertEquals(3 , tree.root.left.right.intKey);
        assertEquals(5 , tree.root.right.intKey);
    }

    @Test
    public void findTest(){
        // тоже что и play, только возвращает саму вершину если найдет
        //     4
        //    / \
        //   2   5
        //  / \
        // 1   3
        tree tree = new tree();
        Vertex ver1 = new Vertex();
        ver1.intKey = 1;
        Vertex ver2 = new Vertex();
        ver2.intKey = 2;
        Vertex ver3 = new Vertex();
        ver3.intKey = 3;
        Vertex ver4 = new Vertex();
        ver4.intKey = 4;
        Vertex ver5 = new Vertex();
        ver5.intKey = 5;
        tree.root = ver4;
        ver4.right = ver5;
        ver4.left = ver2;
        ver2.left = ver1;
        ver2.right = ver3;
        tree.root.keepParent(ver1);
        tree.root.keepParent(ver2);
        tree.root.keepParent(ver3);
        tree.root.keepParent(ver5);
        tree.root.keepParent(ver4);
        //     3
        //    / \
        //   2   4
        //  /     \
        // 1       5
        tree.find(tree.root , 3);
        assertEquals(3 , tree.find(tree.root , 3).intKey);
        assertEquals(3 , tree.root.intKey);
        assertEquals(4 , tree.root.right.intKey);
        assertEquals(5 , tree.root.right.right.intKey);
        assertEquals(2, tree.root.left.intKey);
        assertEquals(1, tree.root.left.left.intKey);
        // 1
        //  \
        //   2
        //    \
        //     3
        //      \
        //       4
        //        \
        //         5
        tree.find(tree.root , 1);
        assertEquals(1 , tree.find(tree.root , 1).intKey);
        assertEquals(1 , tree.root.intKey);
        assertEquals(2 , tree.root.right.intKey);
        assertEquals(3 , tree.root.right.right.intKey);
        assertEquals(4 , tree.root.right.right.right.intKey);
        assertEquals(5 , tree.root.right.right.right.right.intKey);
        //     2
        //    / \
        //   1   3
        //        \
        //         4
        //          \
        //           5
        tree.find(tree.root , 2);
        assertEquals(2 , tree.find(tree.root , 2).intKey);
        assertEquals(2 , tree.root.intKey);
        assertEquals(1 , tree.root.left.intKey);
        assertEquals(3 , tree.root.right.intKey);
        assertEquals(4 , tree.root.right.right.intKey);
        assertEquals(5 , tree.root.right.right.right.intKey);
        //          5
        //         /
        //        2
        //       / \
        //      1   4
        //         /
        //        3
        tree.find(tree.root , 5);
        assertEquals(5 , tree.find(tree.root , 5).intKey);
        assertEquals(5 , tree.root.intKey);
        assertEquals(2 , tree.root.left.intKey);
        assertEquals(1 , tree.root.left.left.intKey);
        assertEquals(4 , tree.root.left.right.intKey);
        assertEquals(3 , tree.root.left.right.left.intKey);
        //          4
        //         / \
        //        2   5
        //       / \
        //      1   3
        tree.find(tree.root , 4);
        assertEquals(4 , tree.find(tree.root , 4).intKey);
        assertEquals(4 , tree.root.intKey);
        assertEquals(2 , tree.root.left.intKey);
        assertEquals(1 , tree.root.left.left.intKey);
        assertEquals(3 , tree.root.left.right.intKey);
        assertEquals(5 , tree.root.right.intKey);
    }

    @Test
    public void splitTest(){
        //     4
        //    / \
        //   2   5
        //  / \
        // 1   3
        tree tree = new tree();
        Vertex ver1 = new Vertex();
        ver1.intKey = 1;
        Vertex ver2 = new Vertex();
        ver2.intKey = 2;
        Vertex ver3 = new Vertex();
        ver3.intKey = 3;
        Vertex ver4 = new Vertex();
        ver4.intKey = 4;
        Vertex ver5 = new Vertex();
        ver5.intKey = 5;
        tree.root = ver4;
        ver4.right = ver5;
        ver4.left = ver2;
        ver2.left = ver1;
        ver2.right = ver3;
        tree.root.keepParent(ver1);
        tree.root.keepParent(ver2);
        tree.root.keepParent(ver3);
        tree.root.keepParent(ver5);
        tree.root.keepParent(ver4);
        //    2
        //   / \      5
        //  1   3
        assertEquals(2 , tree.split(tree.root , 4)[0].intKey);
        assertEquals(5 , tree.split(tree.root , 4)[1].intKey);
        //
        //   1      3
        //
        assertEquals(1 , tree.split(tree.root , 2)[0].intKey);
        assertEquals(3 , tree.split(tree.root , 2)[1].intKey);
        //     4
        //    / \
        //   2   5
        //  / \
        // 1   3
        tree.root = ver4;
        ver4.right = ver5;
        ver4.left = ver2;
        ver2.left = ver1;
        ver2.right = ver3;
        tree.root.keepParent(ver1);
        tree.root.keepParent(ver2);
        tree.root.keepParent(ver3);
        tree.root.keepParent(ver5);
        tree.root.keepParent(ver4);
        //             4
        //    1       / \
        //           3   5
        assertEquals(1 , tree.split(tree.root , 2)[0].intKey);
        assertEquals(4 , tree.split(tree.root , 2)[1].intKey);
    }

    @Test
    public void insertTest(){
        // в основе лежит метод split, который мы уже проверили
        tree tree = new tree();
        Vertex ver = new Vertex();
        ver.intKey = 1;
        tree.root = ver;
        tree.insert( 3);
        assertEquals(3 , tree.root.intKey);
        assertEquals(1 , tree.root.left.intKey);
        assertEquals(3 , tree.root.left.parent.intKey);
        tree.insert(2);
        assertEquals(2 , tree.root.intKey);
        assertEquals(1 , tree.root.left.intKey);
        assertEquals(3 , tree.root.right.intKey);
        tree.insert(5);
        assertEquals(5 , tree.root.intKey);
        assertEquals(3 , tree.root.left.intKey);
        assertEquals(2 , tree.root.left.left.intKey);
        assertEquals(1 , tree.root.left.left.left.intKey);
        tree.insert(4);
        assertEquals(4 , tree.root.intKey);
    }

    @Test
    public void mergeTest(){
        //     4              8               7
        //    / \            / \             / \
        //   3   6    +     7   9     =     4   8
        //  / \                            /     \
        // 1   5                          3       9
        //                               / \
        //                              1   5
        tree tree1 = new tree();
        Vertex ver1 = new Vertex();
        ver1.intKey = 4;
        Vertex ver2 = new Vertex();
        ver2.intKey = 3;
        Vertex ver3 = new Vertex();
        ver3.intKey = 6;
        Vertex ver4 = new Vertex();
        ver4.intKey = 1;
        Vertex ver5 = new Vertex();
        ver5.intKey = 5;
        tree1.root = ver1;
        ver1.right = ver3;
        ver1.left = ver2;
        ver2.left = ver4;
        ver2.right = ver5;
        tree1.root.keepParent(ver1);
        tree1.root.keepParent(ver2);
        tree1.root.keepParent(ver3);
        tree1.root.keepParent(ver5);
        tree1.root.keepParent(ver4);

        tree tree2 = new tree();
        Vertex ver21 = new Vertex();
        ver21.intKey = 8;
        Vertex ver22 = new Vertex();
        ver22.intKey = 7;
        Vertex ver23 = new Vertex();
        ver23.intKey = 9;
        tree2.root = ver21;
        ver21.right = ver23;
        ver21.left = ver22;
        tree2.root.keepParent(ver21);
        tree2.root.keepParent(ver22);
        tree2.root.keepParent(ver23);
        tree2.merge(tree1.root , tree2.root);
        assertEquals(7 , tree2.root.intKey);
        assertEquals(4 , tree2.root.left.intKey);
        assertEquals(8 , tree2.root.right.intKey);
        assertEquals(9 , tree2.root.right.right.intKey);
        assertEquals(3 , tree2.root.left.left.intKey);
        assertEquals(1 , tree2.root.left.left.left.intKey);
        assertEquals(5 , tree2.root.left.left.right.intKey);
    }

    @Test
    public void removeTest(){
        //     4
        //    / \
        //   2   5
        //  / \
        // 1   3
        tree tree = new tree();
        Vertex ver1 = new Vertex();
        ver1.intKey = 1;
        Vertex ver2 = new Vertex();
        ver2.intKey = 2;
        Vertex ver3 = new Vertex();
        ver3.intKey = 3;
        Vertex ver4 = new Vertex();
        ver4.intKey = 4;
        Vertex ver5 = new Vertex();
        ver5.intKey = 5;
        tree.root = ver4;
        ver4.right = ver5;
        ver4.left = ver2;
        ver2.left = ver1;
        ver2.right = ver3;
        tree.root.keepParent(ver1);
        tree.root.keepParent(ver2);
        tree.root.keepParent(ver3);
        tree.root.keepParent(ver5);
        tree.root.keepParent(ver4);
        tree.root = tree.remove(5);
        assertEquals(4 , tree.root.intKey);
        assertEquals(null , tree.root.right);
        tree.root = tree.remove(2);
        //     3
        //    / \
        //   1   4
        assertEquals(3 , tree.root.intKey);
        assertEquals(4 , tree.root.right.intKey);
        assertEquals(1 , tree.root.left.intKey);
        tree.root = tree.remove(3);
        //     4
        //    /
        //   1
        assertEquals(4 , tree.root.intKey);
        assertEquals(1 , tree.root.left.intKey);
        tree.root = tree.remove(4);
        //     1
        assertEquals(1 , tree.root.intKey);
    }
}
