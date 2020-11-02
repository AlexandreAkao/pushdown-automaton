package br.pda.interfaces;

public interface ITransition {
    void setEdge(IEdge e);

    void setState(IState s);

    IEdge getEdge();

    IState getState();
}
