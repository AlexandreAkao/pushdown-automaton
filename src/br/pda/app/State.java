package br.pda.app;

import br.pda.interfaces.IEdge;
import br.pda.interfaces.IState;
import br.pda.interfaces.ITransition;

import java.util.*;

public class State implements IState {
    private final String name;
    private Boolean isFinal = false;
    private final List<ITransition> transitions = new ArrayList<>();

    public State(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setFinal() {
        this.isFinal = true;
    }

    @Override
    public Boolean isFinal() {
        return isFinal;
    }

    @Override
    public List<ITransition> getTransitions() {
        return transitions;
    }

    @Override
    public IState addTransition(IState state, Character c, Character pop, Character push) {
        return addTransitions(state, Util.instance(c, pop, push));
    }

    @Override
    public IState addTransitions(IState state, IEdge... edges) {
        for (IEdge edge : edges) {
            ITransition transition = Util.instance(state, edge);
            if (transitions.contains(transition))
                continue;
            transitions.add(transition);
        }
        return this;
    }

    @Override
    public Set<ITransition> transitions(Character ch, Character pop) {
        Set<ITransition> r = new HashSet<>();
        for (ITransition t : transitions) {
            IEdge e = t.getEdge();
            if (e.getC() != null && e.getC().equals(ch) && e.getPop() != null && e.getPop().equals(pop)) {
                r.add(t);
            } else if (e.getC() != null && e.getC().equals(ch) && pop == null && e.getPop() == null) {
                r.add(t);
            }
            if (ch == null && e.getC() == null && e.getPop() != null && e.getPop().equals(pop)) {
                r.add(t);
            } else if (ch == null && pop == null && e.getC() == null && e.getPop() == null) r.add(t);
        }
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IState) {
            IState s = (IState) o;
            return s.getName().equals(this.getName());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

    @Override
    public Set<IState> nextStates(Character ch) {
        Set<IState> r = new HashSet<>();

        for (ITransition t : this.transitions) {
            IEdge e = t.getEdge();

            if (e.getC() != null && e.getC().equals(ch)) {
                r.add(t.getState());
            } else {
                if (ch == null && e.getC() == null)
                    r.add(t.getState());
            }
        }

        return r;
    }
}