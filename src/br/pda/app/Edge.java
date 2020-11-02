package br.pda.app;

import br.pda.interfaces.IEdge;

public class Edge implements IEdge {
    private Character c;
    private Character pop;
    private Character push;

    public Edge() {
    }

    public Edge(Character c, Character pop, Character push) {
        this();
        this.c = c;
        this.pop = pop;
        this.push = push;
    }

    @Override
    public Character getC() {
        return c;
    }

    @Override
    public void setC(Character c) {
        this.c = c;
    }

    @Override
    public Character getPop() {
        return pop;
    }

    @Override
    public void setPop(Character _pop) {
        this.pop = _pop;
    }

    @Override
    public Character getPush() {
        return push;
    }

    @Override
    public void setPush(Character _push) {
        this.push = _push;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IEdge) {
            IEdge e = (IEdge) o;
            return Util.testAB(this.c, e.getC()) && Util.testAB(this.push, e.getPush()) && Util.testAB(this.pop, e.getPop());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hc = c != null ? c.hashCode() : 0;
        hc = 47 * hc + (pop != null ? pop.hashCode() : 0);
        hc = 47 * hc + (push != null ? push.hashCode() : 0);
        return hc;
    }

    @Override
    public String toString() {
        return "edge{" + "c=" + c + ",pop=" + pop + ",push=" + push + '}';
    }
}

