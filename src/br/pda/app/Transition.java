package br.pda.app;

import br.pda.interfaces.IEdge;
import br.pda.interfaces.IState;
import br.pda.interfaces.ITransition;

public class Transition implements ITransition {
    private IState state;
    private IEdge edge;

    public Transition() {
    }

    public Transition(final IState state, final IEdge edge) {
        this();
        this.state = state;
        this.edge = edge;
    }

    @Override
    public void setEdge(IEdge e) {
        this.edge = e;
    }

    @Override
    public void setState(IState s) {
        this.state = s;
    }

    @Override
    public IEdge getEdge() {
        return edge;
    }

    @Override
    public IState getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ITransition) {
            ITransition t = (ITransition) o;
            return t.getEdge().equals(edge) && t.getState().equals(state);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hc = state != null ? state.hashCode() : 0;
        hc = 47 * hc + (edge != null ? edge.hashCode() : 0);
        return hc;
    }

    @Override
    public String toString() {
        return edge + " --> " + state.getName();
    }
}
