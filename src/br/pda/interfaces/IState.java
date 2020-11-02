package br.pda.interfaces;

import java.util.List;
import java.util.Set;

public interface IState {
    Boolean isFinal();

    List<ITransition> getTransitions();

    IState addTransition(IState state, Character c, Character pop, Character push);

    IState addTransitions(IState state, IEdge... edges);

    Set<ITransition> transitions(Character ch, Character pop);

    void setFinal();

    Object getName();

    Set<IState> nextStates(Character ch);
}
