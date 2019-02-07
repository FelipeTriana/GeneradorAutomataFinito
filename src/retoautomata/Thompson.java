package retoautomata;

import ListClass.Grafo;
import ListClass.*;
import java.util.Stack;

/**
 * Clase encargada de la construccion de los grafos de Thompson
 * @author Felipe Triana
 */
public class Thompson {

    Grafo estructura = new Grafo();

    public Thompson() {
    }

    /**
     * Se encarga del diseño de la primera construccion de Thompson que
     * corresponde a un solo simbolo.
     *
     * @param r Recibe una cadena de caracteres
     * @return Retornara un un objeto tipo Grafo, la lista ligada
     * correspondiente a esta construccion
     */
    public Grafo building1(String r) {
        Node a = new Node(null, r, 0);
        Node b = new Node(null, null, 0);
        Grafo building = new Grafo();
        building.agregarAlFinal(a);
        building.agregarAlFinal(b);
        b.setLink2(a);
        building.setPrimero(a);
        building.setUltimo(b.getLink2());
        return building;
    }

    /**
     * Se encarga del diseño de la segunda construccion de Thompson que
     * corresponde a la concatenacion de dos simbolos o dos grafos.
     *
     * @param r recibe un objeto tipo Grafo
     * @param s recibe un objeto tipo Grafo
     * @return Retornara un un objeto tipo Grafo, la lista ligada
     * correspondiente a esta construccion
     */
    public Grafo building2(Grafo r, Grafo s) {
        Node prueba;
        Node ultr, ultms, estAcept, fin;
        Node evalConst;
        evalConst = r.getPrimer();
        if (evalConst.getS() == "s") {
            estAcept = r.getUltimo().getLink1();
            estAcept.setLink2(null);
            ultr = r.getUltimo();
            ultr.setLink1(s.getPrimer());
            ultms = s.getUltimo();
            r.setPrimero(r.getPrimer());
            r.setUltimo(s.getUltimo());
            fin = s.getUltimo().getLink1();
            fin.setLink2(s.getUltimo());
        } else if (evalConst.getLink1() != null && evalConst.getLink2() != null && evalConst.getLink2() == r.getUltimo().getLink1()) {
            Node p = r.getPrimer();
            while (p.getLink2() != r.getUltimo().getLink1()) {
                p = p.getLink1();
            }
            estAcept = r.getUltimo().getLink1();
            estAcept.setLink2(null);
            ultr = r.getUltimo();
            ultr.setLink1(s.getPrimer());
            ultms = s.getUltimo();
            r.setPrimero(r.getPrimer());
            r.setUltimo(s.getUltimo());
            fin = s.getUltimo().getLink1();
            fin.setLink2(s.getUltimo());
            p.setLink2(s.getPrimer());
        } else {
            Node p = r.getPrimer();
            while (p != r.getUltimo().getLink1() && p.getLink2() != r.getUltimo().getLink1()) {
                p = p.getLink1();
            }
            if (p == r.getUltimo().getLink1()) {
                estAcept = r.getUltimo().getLink1();
                estAcept.setLink2(null);
                ultr = r.getUltimo();
                ultr.setLink1(s.getPrimer());
                ultms = s.getUltimo();
                r.setPrimero(r.getPrimer());
                r.setUltimo(s.getUltimo());
                fin = s.getUltimo().getLink1();
                fin.setLink2(s.getUltimo());
            } else {
                estAcept = r.getUltimo().getLink1();
                estAcept.setLink2(null);
                ultr = r.getUltimo();
                ultr.setLink1(s.getPrimer());
                ultms = s.getUltimo();
                p.setLink2(s.getPrimer());
                r.setPrimero(r.getPrimer());
                r.setUltimo(s.getUltimo());
                fin = s.getUltimo().getLink1();
                fin.setLink2(s.getUltimo());
            }

        }
        return r;
    }

    /**
     * Se encarga del diseño de la tercera construccion de Thompson que
     * corresponde a la union.
     *
     * @param a Recibe un objeto tipo Grafo
     * @param b Recibe un objeto tipo Grafo
     * @return Retornara un un objeto tipo grafo, la lista ligada
     * correspondiente a esta construccion
     */
    public Grafo building3(Grafo a, Grafo b) {
        Node ulta, ultmb, estAcepta, estAceptb;
        Node c = new Node("s", "^", 0);
        Node d = new Node(null, "^", 0);
        Node e = new Node(null, "^", 0);
        Node f = new Node(null, "^", 0);
        Node g = new Node(null, null, 0);
        if ((a.getPrimer().getLink1() != null && a.getPrimer().getLink2() != null) && (b.getPrimer().getLink1() != null && b.getPrimer().getLink2() != null) && a.getPrimer().getS() != "s" && b.getPrimer().getS() != "s") {//Se trata de la operacion de suma para dos kleene star

            Node p = a.getPrimer();
            while (p.getLink2() != a.getUltimo().getLink1() && p != a.getUltimo().getLink1()) {
                p = p.getLink1();
            }
            Node p2 = b.getPrimer();
            while (p2.getLink2() != b.getUltimo().getLink1() && p2 != b.getUltimo().getLink1()) {
                p2 = p2.getLink1();
            }
            if (p == a.getUltimo().getLink1()) {
                c.setLink2(a.getPrimer());
                ulta = a.getUltimo();
                estAcepta = ulta.getLink1();
                estAcepta.setLink2(null);
                ulta.setLink1(d);
                d.setLink2(f);
                f.setLink1(g);
                g.setLink2(f);
                a.setUltimo(f);
                a.setPrimero(c);
            } else if (p.getLink2() == a.getUltimo().getLink1()) {
                c.setLink2(p);
                ulta = a.getUltimo();
                estAcepta = ulta.getLink1();
                estAcepta.setLink2(null);
                ulta.setLink1(d);
                p.setLink2(d);
                d.setLink2(f);
                f.setLink1(g);
                g.setLink2(f);
                a.setUltimo(f);
                a.setPrimero(c);
            }
            if (p2 == b.getUltimo().getLink1()) {
                c.setLink1(b.getPrimer());
                ultmb = b.getUltimo();
                estAceptb = ultmb.getLink1();
                estAceptb.setLink2(null);
                ultmb.setLink1(e);
                e.setLink1(f);
                f.setLink1(g);
                g.setLink2(f);
                a.setUltimo(f);
                a.setPrimero(c);
            } else if (p2.getLink2() == b.getUltimo().getLink1()) {
                c.setLink1(p2);
                ultmb = b.getUltimo();
                estAceptb = ultmb.getLink1();
                estAceptb.setLink2(null);
                ultmb.setLink1(e);
                p2.setLink2(e);
                e.setLink1(f);
                f.setLink1(g);
                g.setLink2(f);
                a.setUltimo(f);
                a.setPrimero(c);
            }
        } else if (a.getPrimer().getLink1() != null && a.getPrimer().getLink2() != null && a.getPrimer().getLink2() == a.getUltimo().getLink1()) { //Cuando se trata de una sola kleene star y cualquier otra expresion
            Node p = b.getPrimer();
            while (p != b.getUltimo().getLink1() && p.getLink2() != b.getUltimo().getLink1()) {
                p = p.getLink1();
            }
            c.setLink2(a.getPrimer());
            c.setLink1(b.getPrimer());
            ulta = a.getUltimo();
            ultmb = b.getUltimo();
            estAcepta = ulta.getLink1();
            estAceptb = ultmb.getLink1();
            estAcepta.setLink2(null);
            estAceptb.setLink2(null);
            ulta.setLink1(d);
            ultmb.setLink1(e);
            if (p == b.getUltimo().getLink1()) {
                a.getPrimer().setLink2(d);
                d.setLink2(f);
                e.setLink1(f);
                f.setLink1(g);
                g.setLink2(f);
                a.setUltimo(f);
                a.setPrimero(c);
            } else {
                a.getPrimer().setLink2(d);
                d.setLink2(f);
                e.setLink1(f);
                f.setLink1(g);
                g.setLink2(f);
                a.setUltimo(f);
                a.setPrimero(c);
                p.setLink2(e);
            }
        } else if (b.getPrimer().getLink1() != null && b.getPrimer().getLink2() != null && b.getPrimer().getLink2() == b.getUltimo().getLink1()) {//Cuando se trata de una sola kleene star que se encuentra en la parte inferior y cualquier otra expresion
            Node p = a.getPrimer();
            while (p != a.getUltimo().getLink1() && p.getLink2() != a.getUltimo().getLink1()) {
                p = p.getLink1();
            }
            c.setLink2(a.getPrimer());
            c.setLink1(b.getPrimer());
            ulta = a.getUltimo();
            ultmb = b.getUltimo();
            estAcepta = ulta.getLink1();
            estAceptb = ultmb.getLink1();
            estAcepta.setLink2(null);
            estAceptb.setLink2(null);
            ulta.setLink1(d);
            ultmb.setLink1(e);
            if (p == a.getUltimo().getLink1()) {
                b.getPrimer().setLink2(e);
                d.setLink2(f);
                e.setLink1(f);
                f.setLink1(g);
                g.setLink2(f);
                a.setUltimo(f);
                a.setPrimero(c);
            } else {
                b.getPrimer().setLink2(e);
                d.setLink2(f);
                e.setLink1(f);
                f.setLink1(g);
                g.setLink2(f);
                a.setUltimo(f);
                a.setPrimero(c);
                p.setLink2(d);
            }

        } else if (a.getPrimer().getLink2() == null | b.getPrimer().getLink2() == null) {
            boolean rarito = false;
            boolean raritob = false;
            c.setLink2(a.getPrimer());
            Node p = a.getPrimer().getLink1();
            if (a.getPrimer() != a.getUltimo()) {
                while (p != a.getUltimo()) {
                    if (p.getLink2() == a.getUltimo().getLink1()) {
                        ulta = a.getUltimo();
                        estAcepta = ulta.getLink1();
                        estAcepta.setLink2(null);
                        ulta.setLink1(d);
                        p.setLink2(d);
                        d.setLink2(f);
                        f.setLink1(g);
                        g.setLink2(f);
                        a.setUltimo(f);
                        a.setPrimero(c);
                        p = a.getUltimo();
                        rarito = true;
                    } else {
                        p = p.getLink1();
                    }
                }
            }
            if (rarito == false) {
                ulta = a.getUltimo();
                estAcepta = ulta.getLink1();
                estAcepta.setLink2(null);
                ulta.setLink1(d);
                d.setLink2(f);
                f.setLink1(g);
                g.setLink2(f);
                a.setUltimo(f);
                a.setPrimero(c);
            }
            c.setLink1(b.getPrimer());
            p = b.getPrimer().getLink1();
            if (b.getPrimer() != b.getUltimo()) {
                while (p != b.getUltimo()) {
                    if (p.getLink2() == b.getUltimo().getLink1()) {
                        ultmb = b.getUltimo();
                        estAceptb = ultmb.getLink1();
                        estAceptb.setLink2(null);
                        ultmb.setLink1(e);
                        p.setLink2(e);
                        e.setLink1(f);
                        f.setLink1(g);
                        g.setLink2(f);
                        a.setUltimo(f);
                        a.setPrimero(c);
                        p = b.getUltimo();
                        raritob = true;
                    } else {
                        p = p.getLink1();
                    }
                }
            }
            if (raritob == false) {
                ultmb = b.getUltimo();
                estAceptb = ultmb.getLink1();
                estAceptb.setLink2(null);
                ultmb.setLink1(e);
                e.setLink1(f);
                f.setLink1(g);
                g.setLink2(f);
                a.setUltimo(f);
                a.setPrimero(c);
            }
        } else if ((a.getPrimer().getLink2() != null && a.getPrimer().getLink1() != null) | (b.getPrimer().getLink2() != null && b.getPrimer().getLink1() != null)) {
            c.setLink2(a.getPrimer());
            c.setLink1(b.getPrimer());
            ulta = a.getUltimo();
            ultmb = b.getUltimo();
            estAcepta = ulta.getLink1();
            estAceptb = ultmb.getLink1();
            estAcepta.setLink2(null);
            estAceptb.setLink2(null);
            ulta.setLink1(d);
            ultmb.setLink1(e);
            d.setLink2(f);
            e.setLink1(f);
            f.setLink1(g);
            g.setLink2(f);
            a.setUltimo(f);
            a.setPrimero(c);
        }
        return a;
    }

    /**
     * Se encarga del diseño de la cuarta construccion de Thompson que
     * corresponde a la kleene star.
     *
     * @param h Recibe un objeto tipo Grafo
     * @return Retornara un un objeto tipo grafo, la lista ligada
     * correspondiente a esta construccion
     */
    public Grafo building4(Grafo h) {
        Node prueba;
        Node evalConst = h.getPrimer();
        Node ulth, estAcepth;
        Node a = new Node(null, "^", 0);
        Node b = new Node(null, "^", 0);
        Node c = new Node(null, null, 0);
        if (evalConst.getS() == "s") {
            a.setLink1(h.getPrimer());
            ulth = h.getUltimo();
            estAcepth = ulth.getLink1();
            estAcepth.setLink2(null);
            ulth.setLink1(c);
            a.setLink2(c);
            ulth.setLink2(h.getPrimer());
            c.setLink2(ulth);
            h.setPrimero(a);
            h.setUltimo(ulth);
        } else if (evalConst.getLink1() != null && evalConst.getLink2() != null) {
            Node p = h.getPrimer();
            while (p != h.getUltimo().getLink1() && p.getLink2() != h.getUltimo().getLink1()) {
                p = p.getLink1();
            }
            if (p == h.getUltimo().getLink1()) {
                a.setLink1(h.getPrimer());
                ulth = h.getUltimo();
                estAcepth = ulth.getLink1();
                estAcepth.setLink2(null);
                ulth.setLink1(b);
                b.setLink1(c);
                b.setLink2(h.getPrimer());
                a.setLink2(c);
                h.setPrimero(a);
                h.setUltimo(b);
                c.setLink2(b);
            } else {
                a.setLink1(h.getPrimer());
                ulth = h.getUltimo();
                estAcepth = ulth.getLink1();
                estAcepth.setLink2(null);
                ulth.setLink1(b);
                p.setLink2(b);
                b.setLink1(c);
                b.setLink2(h.getPrimer());
                a.setLink2(c);
                h.setPrimero(a);
                h.setUltimo(b);
                c.setLink2(b);
            }

        } else {
            Node p = h.getPrimer();
            while (p != h.getUltimo().getLink1() && p.getLink2() != h.getUltimo().getLink1()) {
                p = p.getLink1();
            }
            if (p == h.getUltimo().getLink1()) {
                a.setLink1(h.getPrimer());
                ulth = h.getUltimo();
                estAcepth = ulth.getLink1();
                estAcepth.setLink2(null);
                ulth.setLink1(b);
                b.setLink1(c);
                b.setLink2(h.getPrimer());
                a.setLink2(c);
                h.setPrimero(a);
                h.setUltimo(b);
                c.setLink2(b);
            } else {
                a.setLink1(h.getPrimer());
                ulth = h.getUltimo();
                estAcepth = ulth.getLink1();
                estAcepth.setLink2(null);
                ulth.setLink1(b);
                p.setLink2(b);
                b.setLink1(c);
                b.setLink2(h.getPrimer());
                a.setLink2(c);
                h.setPrimero(a);
                h.setUltimo(b);
                c.setLink2(b);
            }
        }
        return h;
    }

    /**
     * Se encarga del diseño de la quinta construccion de Thompson que
     * corresponde a la clausura que no incluye la secuencia nula.
     *
     * @param h Recibe un objeto tipo Grafo
     * @return Retornara un un objeto tipo grafo, la lista ligada
     * correspondiente a esta construccion
     */
    public Grafo building5(Grafo h) {
        Node prueba;
        Node evalConst = h.getPrimer();
        Node ulth, estAcepth;
        Node a = new Node(null, "^", 0);
        Node b = new Node(null, "^", 0);
        Node c = new Node(null, null, 0);
        if (evalConst.getS() == "s") {
            a.setLink1(h.getPrimer());
            ulth = h.getUltimo();
            estAcepth = ulth.getLink1();
            estAcepth.setLink2(null);
            ulth.setLink1(c);
            ulth.setLink2(h.getPrimer());
            c.setLink2(ulth);
            h.setPrimero(a);
            h.setUltimo(ulth);
        } else if (evalConst.getLink1() != null && evalConst.getLink2() != null) {
            Node p = h.getPrimer();
            while (p != h.getUltimo().getLink1() && p.getLink2() != h.getUltimo().getLink1()) {
                p = p.getLink1();
            }
            if (p == h.getUltimo().getLink1()) {
                a.setLink1(h.getPrimer());
                ulth = h.getUltimo();
                estAcepth = ulth.getLink1();
                estAcepth.setLink2(null);
                ulth.setLink1(b);
                b.setLink1(c);
                b.setLink2(h.getPrimer());
                h.setPrimero(a);
                h.setUltimo(b);
                c.setLink2(b);
            } else {
                a.setLink1(h.getPrimer());
                ulth = h.getUltimo();
                estAcepth = ulth.getLink1();
                estAcepth.setLink2(null);
                ulth.setLink1(b);
                p.setLink2(b);
                b.setLink1(c);
                b.setLink2(h.getPrimer());
                h.setPrimero(a);
                h.setUltimo(b);
                c.setLink2(b);
            }

        } else {
            Node p = h.getPrimer();
            while (p != h.getUltimo().getLink1() && p.getLink2() != h.getUltimo().getLink1()) {
                p = p.getLink1();
            }
            if (p == h.getUltimo().getLink1()) {
                a.setLink1(h.getPrimer());
                ulth = h.getUltimo();
                estAcepth = ulth.getLink1();
                estAcepth.setLink2(null);
                ulth.setLink1(b);
                b.setLink1(c);
                b.setLink2(h.getPrimer());
                h.setPrimero(a);
                h.setUltimo(b);
                c.setLink2(b);
            } else {
                a.setLink1(h.getPrimer());
                ulth = h.getUltimo();
                estAcepth = ulth.getLink1();
                estAcepth.setLink2(null);
                ulth.setLink1(b);
                p.setLink2(b);
                b.setLink1(c);
                b.setLink2(h.getPrimer());
                h.setPrimero(a);
                h.setUltimo(b);
                c.setLink2(b);
            }
        }
        return h;
    }

    /**
     * Asignamos un numero al campo num de cada nodo del grafo, numera cada nodo
     * de la construccion para luego poder recorrelo tenemos que numerar cada
     * nodo para poder construir los cierre lambda
     *
     * @param h Recibe un objeto tipo Grafo
     */
    public void AsigNum(Grafo b) {
        Node p;
        Stack pilan = new Stack();
        int c = 0;
        p = b.getPrimer();
        while (!b.endOfList(p)) {
            c++;
            if (p.getS() == "s") {
                p.setNum(c);
                pilan.push(p);
                p = p.getLink2();
            } else if (p.getLink1() == null && p.getLink2() != null) {
                p.setNum(c);
                p = (Node) pilan.pop();
                p = p.getLink1();
            } else {
                p.setNum(c);
                p = p.getLink1();
            }
        }
        c++;
        b.getUltimo().getLink1().setNum(c);
    }

}
