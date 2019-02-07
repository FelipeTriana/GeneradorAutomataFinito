package ListClass;

/**
 * Clase encargada de la construccion del objeto Node
 * @author Felipe Triana
 */
public class Node {

    private String data,s;
    private int num;
    private Node link1, link2;

    public Node(String s,String data, int num) {
        this.s = s;
        this.num = num;
        this.data = data;
        link1 = null;
        link2 = null;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Node getLink1() {
        return link1;
    }

    public Node getLink2() {
        return link2;
    }

    public void setLink1(Node link1) {
        this.link1 = link1;
    }

    public void setLink2(Node link2) {
        this.link2 = link2;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
