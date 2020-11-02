package br.pda.interfaces;

import br.pda.app.ActiveState;

import java.util.Set;

public interface IPDA {

    boolean run(String w);

    void log(String w, int k, Set<ActiveState> qs);
}
