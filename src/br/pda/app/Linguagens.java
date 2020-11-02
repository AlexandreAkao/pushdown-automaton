package br.pda.app;

import br.pda.interfaces.IPDA;
import br.pda.interfaces.IState;

import java.util.Arrays;
import java.util.List;

public class Linguagens {// br.unifor.app.PDA = (Q, Σ, δ, {qi}, F)

    public static void se() {
        char[] sigma = " abcdefghijklmnopqrstuvxywz".toCharArray();
        System.out.println("*****************************\nProcessamento de linguagem");
        IState q = new State("q");
        IState qi = new State("qi");
        IState qf = new State("qf");
        IState qOpenPar = new State("qOpenPar");
        IState t0 = new State("t0");
        IState t1 = new State("t1");
        IState t2 = new State("t2");
        IState t3 = new State("t3");
        IState t4 = new State("t4");
        IState qClosePar = new State("qClosePar");
        IState qOpenKey = new State("qOpenKey");
        IState qCloseKey = new State("qCloseKey");
        IState qif = new State("qif");
        List<IState> qs = Arrays.asList(q, qi, qf, qOpenPar, t0, t1, t2, t3, t4, qClosePar, qOpenKey, qCloseKey, qif);
        qif.setFinal();

        q.addTransition(qi, null, null, '$');
        qi.addTransition(qf, 'i', null, null);
        qf.addTransition(qOpenPar, 'f', null, null);
        qOpenPar.addTransition(t0, '(', null, null);
        t1.addTransition(t2, '=', null, null);
        t1.addTransition(qClosePar, ')', null, null);
        t2.addTransition(t3, '=', null, null);

        t4.addTransition(qClosePar, ')', null, null);

        qClosePar.addTransition(qOpenKey, '{', null, '{');
        qOpenKey.addTransition(qOpenKey, '{', null, '{');

        qOpenKey.addTransition(qCloseKey, '}', '{', null);
        qOpenKey.addTransition(qf, 'i', null, null);

        qCloseKey.addTransition(qCloseKey, '}', '{', null);
        qCloseKey.addTransition(qif, null, '$', null);

        for (char c : sigma) {
            t0.addTransition(t1, c, null, null);
            t1.addTransition(t1, c, null, null);
            t3.addTransition(t4, c, null, null);
            t4.addTransition(t4, c, null, null);
            qOpenKey.addTransition(qOpenKey, c, null, null); //para aceitar quarquer simbolo do alfabeto depois de {
        }
        for (IState s : qs) {
            s.addTransition(s, '\n', null, null);
        }
        // aceitar espacos
        qClosePar.addTransition(qClosePar, ' ', null, null);
        qOpenPar.addTransition(qOpenPar, ' ', null, null);
        qCloseKey.addTransition(qCloseKey, ' ', null, null);

        String w = "if(teste) {"
                + "  if(a==b){ if(x){ if(joao) { faco algo }}"
                + "  }"
                + "}";

        IPDA pda = new PDA(q, 'Z');
//        pda.makeLog();
        Util.checkout(pda.run(w), w);
        System.out.println("*****************************");
    }

    public static void se2() {
        /*
         * S -> if(T){T}
         * T -> D | D==D | Îµ
         * D -> aD | bD | .... | zD | Îµ
         */
        char[] sigma = " abcdefghijklmnopqrstuvxywz".toCharArray();
        IState qini = new State("qini");
        IState qs = new State("qs");
        IState qloop = new State("qloop");
        IState qf = new State("qf");

        IState q1 = new State("q1");
        IState q2 = new State("q2");
        IState q3 = new State("q3");
        IState q4 = new State("q4");
        IState q5 = new State("q5");
        IState q6 = new State("q6");
        IState q7 = new State("q7");
        IState q8 = new State("q8");
        IState q9 = new State("q9");
        IState q10 = new State("q10");
        qf.setFinal();
        //Base
        qini.addTransition(qs, null, null, '$');
        qs.addTransition(qloop, null, null, 'S');
        qloop.addTransition(qf, null, '$', null);

        qloop.addTransition(qloop, '(', '(', null);
        qloop.addTransition(qloop, ')', ')', null);
        qloop.addTransition(qloop, '{', '{', null);
        qloop.addTransition(qloop, '}', '}', null);
        qloop.addTransition(qloop, '=', '=', null);
        for (char c : sigma) qloop.addTransition(qloop, c, c, null);

        qloop.addTransition(q1, null, 'S', '}');
        q1.addTransition(q2, null, null, 'T');
        q2.addTransition(q3, null, null, '{');
        q3.addTransition(q4, null, null, ')');
        q4.addTransition(q5, null, null, 'T');
        q5.addTransition(q6, null, null, '(');
        q6.addTransition(q7, null, null, 'f');
        q7.addTransition(qloop, null, null, 'i');

        qloop.addTransition(qloop, null, 'T', null);
        qloop.addTransition(qloop, null, 'T', 'D');
        qloop.addTransition(q8, null, 'T', 'D');
        q8.addTransition(q9, null, null, '=');
        q9.addTransition(q10, null, null, '=');
        q10.addTransition(qloop, null, null, 'D');

        qloop.addTransition(qloop, null, 'D', null);
        int i = 50;
        for (char c : sigma) {
            IState s = new State("q" + (i++));
            qloop.addTransition(s, null, 'D', 'D');
            s.addTransition(qloop, null, null, c);
        }

        String w = "if(ifa){ ab }";

        IPDA pda = new PDA(qini, 'Z');
//        pda.makeLog();
        Util.checkout(pda.run(w), w);
        System.out.println("*****************************");
    }

    public static void reverso() {
        /*
         * L = { ww^R | w in Σ^*}
         */
        char[] sigma = "abcdefghijklmnopqrstuvxywz".toCharArray();
        System.out.println("*****************************\nProcessamento de linguagem");
        IState q0 = new State("q0");
        IState q1 = new State("q1");
        IState q2 = new State("q2");
        IState q3 = new State("q3");
        q3.setFinal();

        q0.addTransition(q1, null, null, '$');

        q1.addTransition(q1, 'a', null, 'a');
        q1.addTransition(q1, 'b', null, 'b');
//        for (char c : sigma)
//            q1.addTransition(q1, c, null, c);
        q1.addTransition(q2, null, null, null);

        q2.addTransition(q2, 'a', 'a', null);
        q2.addTransition(q2, 'b', 'b', null);
//        for (char c : sigma)
//            q2.addTransition(q2, c, c, null);
        q2.addTransition(q3, null, '$', null);

        String w = "abaa";
        IPDA pda = new PDA(q0, 'Z');
//        pda.makeLog();
        Util.checkout(pda.run(w), w);
        System.out.println("*****************************");
    }

    public static void n0n1() {
        /*
         * L = { 0^n1^n in Σ^* | n>0 }
         */
        System.out.println("*****************************\nProcessamento de linguagem");
        IState q1 = new State("q1");
        IState q2 = new State("q2");
        IState q3 = new State("q3");
        IState q4 = new State("q4");

        q4.setFinal();

        q1.addTransition(q2, null, null, '$');
        q2.addTransition(q2, '0', null, '0');
        q2.addTransition(q3, '1', '0', null);
        q3.addTransition(q3, '1', '0', null);
        q3.addTransition(q4, null, '$', null);

        String w = "11";
        IPDA pda = new PDA(q1, 'Z');
//        pda.makeLog();
        Util.checkout(pda.run(w), w);
        System.out.println("*****************************");
    }

    public static void l3() {
        /*
         * L = { a^n in Σ^* | n par pode terminar com p, ou n divisivel por 3 e pode terminar com i}
         */
        System.out.println("*****************************\nProcessamento de L3:");

        IState q0 = new State("q0");
        IState q1 = new State("q1");
        IState q2 = new State("q2");
        IState q3 = new State("q3");
        IState q4 = new State("q4");
        IState q5 = new State("q5");

        IState q6 = new State("q6");
        IState q7 = new State("q7");
        IState q8 = new State("q8");

        q0.addTransition(q1, null, null, null);
        q0.addTransition(q2, null, null, null);
        q0.addTransition(q8, 'a', null, null);

        q1.addTransition(q3, 'a', null, null);
        q3.addTransition(q1, 'a', null, null);
        q2.addTransition(q4, 'a', null, null);
        q4.addTransition(q5, 'a', null, null);
        q5.addTransition(q2, 'a', null, null);

        q1.addTransition(q6, null, null, null);
        q2.addTransition(q7, null, null, null);

        q6.addTransition(q6, 'p', null, null);
        q7.addTransition(q7, 'i', null, null);

        q6.setFinal();
        q7.setFinal();
        /*
         * Def.: e-closure de um estado p, ECLOSURE(p):
         * - conjunto dos estados alcancados a partir de
         * p (incluindo p) por caminhos rotulados por 'e'.
         */
        String w = "aap";
        IPDA pda = new PDA(q0, 'Z');
        Util.checkout(pda.run(w), w);
        System.out.println("*****************************");
    }
}
