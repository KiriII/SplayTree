import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class VertexTest {

    @Test
    public void SetParentTest(){
        Vertex ver = new Vertex();
        ver.intKey = 3;
        Vertex ver1 = new Vertex();
        ver1.intKey = 4;
        ver1.setParent(ver1 , ver);
        assertEquals(3 , ver1.parent.intKey);
        // если поставить .intKey от будет NullPointedExeption
        assertEquals(null , ver.parent);
    }

    @Test
    public void keepParentTest(){
        Vertex ver = new Vertex();
        ver.intKey = 3;
        Vertex ver1 = new Vertex();
        ver1.intKey = 4;
        Vertex ver2 = new Vertex();
        ver1.intKey = 5;
        ver.left = ver1;
        ver.right = ver2;
        ver.keepParent(ver);
        assertEquals(3 , ver1.parent.intKey);
        assertEquals(3 , ver2.parent.intKey);
        Vertex ver4 = new Vertex();
        ver4.intKey = 1;
        Vertex ver5 = new Vertex();
        ver5.intKey = 2;
        ver4.right = ver5;
        ver4.keepParent(ver4);
        assertEquals(2 , ver4.right.intKey);
        assertEquals(null , ver4.left);
    }
}
