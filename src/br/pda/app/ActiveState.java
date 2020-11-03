package br.pda.app;

import br.pda.interfaces.IEdge;
import br.pda.interfaces.IState;
import br.pda.interfaces.ITransition;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ActiveState {
    private Stack<Character> stack;
    private IState state;

    public ActiveState(IState state, Character initialC) {
        this.stack = new Stack<>();
        this.stack.push(initialC);
        this.state = state;
    }

    public ActiveState(IState state, Stack<Character> stack) {
        this.stack = stack;
        this.state = state;
    }

    public Stack<Character> getStack() {
        return stack;
    }

    public void setStack(Stack<Character> stack) {
        this.stack = stack;
    }

    public IState getState() {
        return state;
    }

    public void setState(IState state) {
        this.state = state;
    }

    public Set<ActiveState> nextStates(Character ch) {
        Set<ActiveState> r = new HashSet<>();

        for (ITransition t : this.state.getTransitions()) {
            IEdge e = t.getEdge();

            if (ch == null) {
                if (e.getC() == null) {
                    if (e.getPop() == null) {
                        Stack<Character> newStack = (Stack<Character>) this.getStack().clone();

                        if (e.getPush() != null) {
                            newStack.push(e.getPush());
                        }

                        r.add(new ActiveState(t.getState(), newStack));
                    } else if (e.getPop().equals(this.getStack().peek())) {
                        Stack<Character> newStack = (Stack<Character>) this.getStack().clone();
                        newStack.pop();

                        if (e.getPush() != null) {
                            newStack.push(e.getPush());
                        }

                        r.add(new ActiveState(t.getState(), newStack));
                    }
                }
            } else {
                if (e.getC() != null && e.getC().equals(ch)) {
                    if (e.getPop() != null && e.getPop().equals(this.getStack().peek())) {
                        Stack<Character> newStack = (Stack<Character>) this.getStack().clone();
                        newStack.pop();

                        if (e.getPush() != null) {
                            newStack.push(e.getPush());
                        }

                        r.add(new ActiveState(t.getState(), newStack));
                    } else if (e.getPop() == null) {
                        Stack<Character> newStack = (Stack<Character>) this.getStack().clone();

                        if (e.getPush() != null) {
                            newStack.push(e.getPush());
                        }

                        r.add(new ActiveState(t.getState(), newStack));
                    }
                }
//                else if (e.getC() == null) {
//                    if (e.getPop() != null && e.getPop().equals(this.getStack().peek())) {
//                        Stack<Character> newStack = (Stack<Character>) this.getStack().clone();
//                        newStack.pop();
//
//                        if (e.getPush() != null) {
//                            newStack.push(e.getPush());
//                        }
//
//                        r.add(new ActiveState(t.getState(), newStack));
//                    } else if (e.getPop() == null) {
//                        Stack<Character> newStack = (Stack<Character>) this.getStack().clone();
//
//                        if (e.getPush() != null) {
//                            newStack.push(e.getPush());
//                        }
//
//                        r.add(new ActiveState(t.getState(), newStack));
//                    }
//                }
            }
        }

//        System.out.println(ch + " -> " + this.getState().getName() + ": " + r.isEmpty());

        return r;
    }

    public Set<ActiveState> nextStates(Character ch, Character pop) {
        Set<ActiveState> r = new HashSet<>();

        for (ITransition t : this.state.getTransitions()) {
            IEdge e = t.getEdge();

            if (ch == null) {
                if (pop == null) {
                    Stack<Character> newStack = (Stack<Character>) this.getStack().clone();

                    if (e.getPush() != null) {
                        newStack.push(e.getPush());
                    }

                    r.add(new ActiveState(t.getState(), newStack));
                } else {
                    if (pop.equals(e.getPop())) {
                        Stack<Character> newStack = (Stack<Character>) this.getStack().clone();
                        newStack.pop();

                        if (e.getPush() != null) {
                            newStack.push(e.getPush());
                        }

                        r.add(new ActiveState(t.getState(), newStack));
                    }
                }
            } else {
                if (pop == null) {
                    if (e.getC().equals(ch)) {
                        Stack<Character> newStack = (Stack<Character>) this.getStack().clone();

                        if (e.getPush() != null) {
                            newStack.push(e.getPush());
                        }

                        r.add(new ActiveState(t.getState(), newStack));
                    }
                } else {
                    if (e.getC().equals(ch) && e.getPop().equals(pop)) {
                        Stack<Character> newStack = (Stack<Character>) this.getStack().clone();
                        newStack.pop();

                        if (e.getPush() != null) {
                            newStack.push(e.getPush());
                        }

                        r.add(new ActiveState(t.getState(), newStack));
                    }
                }
            }
        }

        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ActiveState) {
            ActiveState s = (ActiveState) o;
            return s.getState().equals(this.getState()) && s.getStack().equals(this.getStack());
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hc = state != null ? state.hashCode() : 0;
        hc = 47 * hc + (stack != null ? stack.hashCode() : 0);

        return hc;
    }
}
