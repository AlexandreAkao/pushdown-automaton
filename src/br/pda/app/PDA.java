package br.pda.app;

import br.pda.interfaces.IPDA;
import br.pda.interfaces.IState;

import java.util.HashSet;
import java.util.Set;


public class PDA implements IPDA {

    private ActiveState q;

    public PDA(IState q, Character c) {
        this.q = new ActiveState(q, c);
    }

    @Override
    public boolean run(String w) {
        Set<ActiveState> states = new HashSet<>();
        states.add(q);
        states = eclosure(states);

        for (int k = 0; k < w.length(); k++) {
            char ch = w.charAt(k);
            Set<ActiveState> newStates = new HashSet<>();
            System.out.println("--------------------");
            logActiveStates(states);

            for (ActiveState s : states) {
//              Só para visão não interfere na lógica
                System.out.print("[" + s.getState().getName() + "]");
                Set<ActiveState> a = s.nextStates(ch);
//                a = eclosure(a);
                log(w, k, a);
//              end

                newStates = merge(newStates, s.nextStates(ch));
//                logActiveStates(newStates);

                newStates = eclosure(newStates);
            }

            states = newStates;

            if (states.size() == 0) break;
        }

        System.out.println("--------------------");
        logActiveStates(states);

        return valid(states);// verificar se existe algum estado final em states
    }

    private static Set<ActiveState> merge(Set<ActiveState> a, Set<ActiveState> b) {
        Set<ActiveState> r = new HashSet<>();

        for (ActiveState s : a) if (!r.contains(s)) r.add(s);
        for (ActiveState s : b) if (!r.contains(s)) r.add(s);

        return r;
    }

    public static Set<ActiveState> eclosure(Set<ActiveState> qs) {
        Set<ActiveState> r = merge(new HashSet<>(), qs);

        for (ActiveState s : qs) {

            Set<ActiveState> a = s.nextStates(null);
            Set<ActiveState> b = eclosure(a);

            r = merge(r, a);
            r = merge(r, b);
        }

        return r;
    }

    public boolean valid(Set<ActiveState> qs) {
        if (qs == null || qs.size() == 0) return false;

        for (ActiveState s : qs)
            if (s.getState().isFinal())
                return true;

        return false;
    }

    @Override
    public void log(String w, int k, Set<ActiveState> qs) {
        System.out.print(w.substring(0, k) + "{ ");

        for (ActiveState s : qs) {
            System.out.print(s.getState().getName() + " ");
        }

        System.out.println("}" + w.substring(k));
    }

    public void logActiveStates(Set<ActiveState> qs) {
        if (qs.isEmpty()) {
            System.out.println("[]");
            return;
        }

        for (ActiveState s : qs) {
            System.out.print("[" + s.getState().getName() + "]");
        }
        System.out.println();
    }
}









/*


package br.pda.app;

        import br.pda.interfaces.IPDA;
        import br.pda.interfaces.IState;

        import java.util.HashMap;
        import java.util.HashSet;
        import java.util.Set;
        import java.util.Stack;

public class PDA implements IPDA {

    private IState q;
    private Character initialStackSymbol;

    public PDA(IState q, Character c) {
        this.q = q;
        this.initialStackSymbol = c;
    }

    @Override
    public boolean run(String w) {
        Set<ActiveState> states = new HashSet<>();
        ActiveState initialState = new ActiveState(this.q, initialStackSymbol);
        states.add(q);
        states = eclosure(states);

        for (int k = 0; k < w.length(); k++) {
            char ch = w.charAt(k);

            Set<IState> newStates = new HashSet<>();

            for (IState s : states) {
                //Só para visão não interfere na lógica
//                System.out.print("[" + s.getName() + "]");
//                Set<IState> a = s.states(ch);
//                a = eclosure(a);
//                log(w, k, a);
                //end

                newStates = merge(newStates, s.nextStates(ch));
                newStates = eclosure(newStates);
            }

            states = newStates;

            if (states.size() == 0) break;
        }

//        System.out.println();
//        System.out.println("--------------------");
//        for (IState s : states)
//            System.out.print(s.getName() + " ");
//        System.out.println();

        return valid(states);// verificar se existe algum estado final em states
    }

    private static Set<IState> merge(Set<IState> a, Set<IState> b) {
        Set<IState> r = new HashSet<>();

        for (IState s : a) if (!r.contains(s)) r.add(s);
        for (IState s : b) if (!r.contains(s)) r.add(s);

        return r;
    }

    public static Set<IState> eclosure(Set<IState> qs) {
        Set<IState> r = merge(new HashSet<>(), qs);

        for (IState s : qs) {
            Set<IState> a = s.nextStates(null);
            Set<IState> b = eclosure(a);

            r = merge(r, a);
            r = merge(r, b);
        }

        return r;
    }

    public boolean valid(Set<IState> qs) {
        if (qs == null || qs.size() == 0) return false;

        for (IState s : qs)
            if (s.isFinal())
                return true;

        return false;
    }

    @Override
    public void log(String w, int k, Set<IState> qs) {
        System.out.print(w.substring(0, k) + "{ ");
        for (IState s : qs) {
            System.out.print(s.getName() + " ");
        }
        System.out.println("}" + w.substring(k));
    }
}


*/

