public class Vertex
{
    int intKey;
    Vertex left;
    Vertex right;
    Vertex parent;

    public void setParent (Vertex child , Vertex parent){
        if (child != null){
            child.parent = parent;
        }
        else return;
    }

    public void keepParent(Vertex v){
        setParent(v.left , v);
        setParent(v.right , v);
    }
}
