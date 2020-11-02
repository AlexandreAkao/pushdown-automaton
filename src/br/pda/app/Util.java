package br.pda.app;

import br.pda.interfaces.IEdge;
import br.pda.interfaces.IState;
import br.pda.interfaces.ITransition;

public class Util {

    public static boolean testAB(Character a, Character b) {
        if (a != null) return a.equals(b);
        return b == null;
    }

    public static IEdge instance(Character c, Character _pop, Character _push) {
        return new Edge(c, _pop, _push);
    }

    public static ITransition instance(IState state, IEdge edge) {
        return new Transition(state, edge);
    }

    public static void checkout(boolean b, String w) {
        if (b)
            System.out.println("reconheceu: " + w);
        else
            System.out.println("Não reconheceu: " + w);
    }
}
