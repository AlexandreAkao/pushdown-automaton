package br.pda.app;

import br.pda.interfaces.IPDA;
import br.pda.interfaces.IState;

import java.io.IOException;
import java.util.ArrayList;
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

        String w = "if(aa==aa){\n" +
                "if(a==a){}\n" +
                "}";

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

        for (char c : sigma)
            q1.addTransition(q1, c, null, c);
        q1.addTransition(q2, null, null, null);

        for (char c : sigma)
            q2.addTransition(q2, c, c, null);
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

    public static void syntax() throws IOException {
        System.out.println("*****************************\nProcessamento de Syntax:");
        char[] letter = "ABCDEFGHIJKLMNOPQRSTUVXYWZabcdefghijklmnopqrstuvxywz".toCharArray();
        char[] number = "0123456789".toCharArray();

        IState qInitial = new State("qInitial");
        IState q = new State("q");
        IState qM = new State("qM");
        IState qA = new State("qA");
        IState qI = new State("qI");
        IState qN = new State("qN");
        IState qMainPO = new State("qMainPO"); //qMainParenthesesOpen
        IState qMainPC = new State("qMainPC"); //qMainParenthesesClose
        IState qMainBO = new State("qMainBO"); //qMainBracketsOpen
        IState qMainBC = new State("qMainBO"); //qMainBracketsOpen
        IState qFinal = new State("qFinal"); //qMainBracketsOpen

        IState qMainInside = new State("qMainInside");

        IState qIntI = new State("qIntI");
        IState qIntN = new State("qIntN");
        IState qIntT = new State("qIntT");
        IState qInt_ = new State("qInt_");
        IState qIntName = new State("qIntName");
        IState qIntEqual = new State("qIntEqual");
        IState qIntNumber = new State("qIntNumber");
        IState qIntSemiColon = new State("qIntSemiColon");

        IState qBooleanB = new State("qBooleanB");
        IState qBooleanO1 = new State("qBooleanO1");
        IState qBooleanO2 = new State("qBooleanO2");
        IState qBooleanL = new State("qBooleanL");
        IState qBooleanE = new State("qBooleanE");
        IState qBooleanA = new State("qBooleanA");
        IState qBooleanN = new State("qBooleanN");
        IState qBoolean_ = new State("qBoolean_");
        IState qBooleanName = new State("qBooleanName");
        IState qBooleanEqual = new State("qBooleanEqual");

        IState qBooleanTT = new State("qBooleanFT");
        IState qBooleanTR = new State("qBooleanFR");
        IState qBooleanTU = new State("qBooleanFU");
        IState qBooleanTE = new State("qBooleanFE");

        IState qBooleanFF = new State("qBooleanFF");
        IState qBooleanFA = new State("qBooleanFA");
        IState qBooleanFL = new State("qBooleanFL");
        IState qBooleanFS = new State("qBooleanFS");
        IState qBooleanFE = new State("qBooleanFE");

        IState qBooleanSemiColon = new State("qBooleanSemiColon");

        IState qIfI = new State("qIfI");
        IState qIfF = new State("qIfF");
        IState qIfPO = new State("qIfPO");
        IState qIfV1 = new State("qIfV1");
        IState qIfEqual1 = new State("qIfEqual1");
        IState qIfEqual2 = new State("qIfEqual2");
        IState qIfV2 = new State("qIfV2");
        IState qIfPC = new State("qIfPC");
        IState qIfBO = new State("qIfBO");
        IState qIfBC = new State("qIfBC");

        IState qWhileW = new State("qWhileW");
        IState qWhileH = new State("qWhileH");
        IState qWhileI = new State("qWhileI");
        IState qWhileL = new State("qWhileL");
        IState qWhileE = new State("qWhileE");
        IState qWhilePO = new State("qWhilePO");
        IState qWhilePC = new State("qWhilePC");
        IState qWhileBO = new State("qWhileBO");
        IState qWhileBC = new State("qWhileBC");

        IState qForF = new State("qForF");
        IState qForO = new State("qForO");
        IState qForR = new State("qForR");
        IState qForPO = new State("qForPO");
        IState qForPC = new State("qForPC");
        IState qForBO = new State("qForBO");
        IState qForBC = new State("qForBC");
        IState qForIncrementSemiColon = new State("qForIncrementSemiColon");
        IState qForIncrement = new State("qForIncrement");
        IState qForIncrementP1 = new State("qForIncrementP1");
        IState qForIncrementP2 = new State("qForIncrementP2");

        List<IState> qs = Arrays.asList(
                qInitial, q, qM, qA, qI, qN, qMainPO, qMainPC, qMainBO, qMainBC, qFinal, qMainInside,                                                   //Main
                qIntI, qIntN, qIntT, qInt_, qIntName, qIntEqual, qIntNumber, qIntSemiColon,                                                             //Int
                qBooleanB, qBooleanO1, qBooleanO2, qBooleanL, qBooleanE, qBooleanA, qBooleanN, qBoolean_, qBooleanName,                                 //Boolean
                qBooleanEqual, qBooleanTT, qBooleanTR, qBooleanTU, qBooleanTE,                                                                          //Boolean - true
                qBooleanFF, qBooleanFA, qBooleanFL, qBooleanFS, qBooleanFE, qBooleanSemiColon,                                                          //Boolean - false
                qIfI, qIfF, qIfPO, qIfV1, qIfEqual1, qIfEqual2, qIfV2, qIfPC, qIfBO, qIfBC,                                                             //If
                qWhileW, qWhileH, qWhileI, qWhileL, qWhileE, qWhilePO, qWhilePC, qWhileBO, qWhileBC,                                                    //While
                qForF, qForO, qForR, qForPO, qForPC, qForBO, qForBC, qForIncrementSemiColon, qForIncrement, qForIncrementP1, qForIncrementP2            //For
        );

        for (IState s : qs) {
            s.addTransition(s, '\n', null, null);
            s.addTransition(s, '\r', null, null);
            s.addTransition(s, ' ', null, null);
        }

        qFinal.setFinal();

        qInitial.addTransition(q, null, null, '$');
        q.addTransition(qM, 'm', null, null);
        qM.addTransition(qA, 'a', null, null);
        qA.addTransition(qI, 'i', null, null);
        qI.addTransition(qN, 'n', null, null);
        qN.addTransition(qMainPO, '(', null, null);
        qMainPO.addTransition(qMainPC, ')', null, null);
        qMainPC.addTransition(qMainBO, '{', null, '{');
        qMainBO.addTransition(qMainBC, '}', '{', null);
        qMainBC.addTransition(qFinal, null, '$', null);

        qMainBC.addTransition(qMainBC, '}', '{', null);
        qMainBO.addTransition(qMainInside, null, null, null);

        qMainInside.addTransition(qIntI, 'i', null, null);
        qIntI.addTransition(qIntN, 'n', null, null);
        qIntN.addTransition(qIntT, 't', null, null);
        qIntT.addTransition(qInt_, ' ', null, null);

        for (char l : letter) {
            qInt_.addTransition(qIntName, l, null, null);
            qIntName.addTransition(qIntName, l, null, null);
        }

        qIntName.addTransition(qIntSemiColon, ';', null, null);
        qIntName.addTransition(qIntEqual, '=', null, null);

        for (char n : number) {
            qIntEqual.addTransition(qIntNumber, n, null, null);
            qIntNumber.addTransition(qIntNumber, n, null, null);
        }

        qIntNumber.addTransition(qIntSemiColon, ';', null, null);
        qIntSemiColon.addTransition(qMainBC, '}', '{', null);
        qIntSemiColon.addTransition(qMainInside, null, null, null);

        for (char l : letter) {
            qIntSemiColon.addTransition(qIfV1, l, null, l);
        }

        qMainInside.addTransition(qBooleanB, 'b', null, null);
        qBooleanB.addTransition(qBooleanO1, 'o', null, null);
        qBooleanO1.addTransition(qBooleanO2, 'o', null, null);
        qBooleanO2.addTransition(qBooleanL, 'l', null, null);
        qBooleanL.addTransition(qBooleanE, 'e', null, null);
        qBooleanE.addTransition(qBooleanA, 'a', null, null);
        qBooleanA.addTransition(qBooleanN, 'n', null, null);
        qBooleanN.addTransition(qBoolean_, ' ', null, null);

        for (char l : letter) {
            qBoolean_.addTransition(qBooleanName, l, null, null);
            qBooleanName.addTransition(qBooleanName, l, null, null);
        }

        qBooleanName.addTransition(qBooleanSemiColon, ';', null, null);
        qBooleanName.addTransition(qBooleanEqual, '=', null, null);

        qBooleanEqual.addTransition(qBooleanTT, 't', null, null);
        qBooleanTT.addTransition(qBooleanTR, 'r', null, null);
        qBooleanTR.addTransition(qBooleanTU, 'u', null, null);
        qBooleanTU.addTransition(qBooleanTE, 'e', null, null);
        qBooleanTE.addTransition(qBooleanSemiColon, ';', null, null);

        qBooleanEqual.addTransition(qBooleanFF, 'f', null, null);
        qBooleanFF.addTransition(qBooleanFA, 'a', null, null);
        qBooleanFA.addTransition(qBooleanFL, 'l', null, null);
        qBooleanFL.addTransition(qBooleanFS, 's', null, null);
        qBooleanFS.addTransition(qBooleanFE, 'e', null, null);
        qBooleanFE.addTransition(qBooleanSemiColon, ';', null, null);

        qBooleanSemiColon.addTransition(qMainBC, '}', '{', null);
        qBooleanSemiColon.addTransition(qMainInside, null, null, null);

        qMainInside.addTransition(qIfI, 'i', null, 'i');
        qIfI.addTransition(qIfF, 'f', null, null);
        qIfF.addTransition(qIfPO, '(', null, null);

        for (char l : letter) {
            qIfPO.addTransition(qIfV1, l, null, l);
            qIfV1.addTransition(qIfV1, l, null, l);
        }

        qIfV1.addTransition(qIfEqual1, '=', null, null);
        qIfEqual1.addTransition(qIfEqual2, '=', null, null);

        for (char l : letter) {
            qIfEqual2.addTransition(qIfV2, l, l, null);
            qIfV2.addTransition(qIfV2, l, l, null);
        }

        qIfV2.addTransition(qIfPC, ')', 'i', null);
        qIfV2.addTransition(qForIncrementSemiColon, ';', null, null);

        qIfPC.addTransition(qIfBO, '{', null, '{');

        qIfBO.addTransition(qIfI, 'i', null, 'i');
        qIfBO.addTransition(qMainInside, null, null, null);

        for (char l : letter) {
            qIfBO.addTransition(qIfBO, l, null, null);
        }

        qIfBO.addTransition(qIfBC, '}', '{', null);
        qIfBC.addTransition(qIfBC, '}', '{', null);
        qIfBC.addTransition(qMainBC, '}', '{', null);

        qIfBC.addTransition(qMainInside, null, null, null);

        qMainInside.addTransition(qWhileW, 'w', null, 'w');
        qWhileW.addTransition(qWhileH, 'h', null, null);
        qWhileH.addTransition(qWhileI, 'i', null, null);
        qWhileI.addTransition(qWhileL, 'l', null, null);
        qWhileL.addTransition(qWhileE, 'e', null, null);
        qWhileE.addTransition(qWhilePO, '(', null, null);

        for (char l : letter) {
            qWhilePO.addTransition(qIfV1, l, null, l);
        }

        qIfV2.addTransition(qWhilePC, ')', 'w', null);
        qWhilePC.addTransition(qWhileBO, '{', null, '{');
        qWhileBO.addTransition(qWhileW, 'w', null, 'w');

        for (char l : letter) {
            qWhileBO.addTransition(qWhileBO, l, null, null);
        }

        qWhileBO.addTransition(qWhileBO, '}', '{', null);
        qWhileBO.addTransition(qWhileBC, '}', '{', null);
        qWhileBO.addTransition(qMainInside, null, null, null);
        qWhileBC.addTransition(qMainBC, '}', '{', null);
        qWhileBC.addTransition(qMainInside, null, null, null);

        qMainInside.addTransition(qForF, 'f', null, null);
        qForF.addTransition(qForO, 'o', null, null);
        qForO.addTransition(qForR, 'r', null, null);
        qForR.addTransition(qForPO, '(', null, null);
        qForPO.addTransition(qIntI, 'i', null, null);

        for (char l : letter) {
            qForIncrementSemiColon.addTransition(qForIncrement, l, null, null);
            qForIncrement.addTransition(qForIncrement, l, null, null);
        }

        qForIncrement.addTransition(qForIncrementP1, '+', null, null);
        qForIncrementP1.addTransition(qForIncrementP2, '+', null, null);
        qForIncrementP2.addTransition(qForPC, ')', null, null);
        qForPC.addTransition(qForBO, '{', null, '{');

        for (char l : letter) {
            qForBO.addTransition(qForBO, l, null, null);
        }

        qForBO.addTransition(qForBC, '}', '{', null);
        qForBO.addTransition(qMainInside, null, null, null);
        qForBO.addTransition(qForF, 'f', null, null);
        qForBC.addTransition(qForBC, '}', '{', null);
        qForBC.addTransition(qMainBC, '}', '{', null);

        String w = Util.readFile("source.txt");
//        String w =
//        "main(){\n" +
//            "while(a==a){\n" +
//                "int a;\n" +
//                "boolean b;\n" +
//                "int a=1;\n" +
//                "if(a==a){\n" +
//                    "int a=1;\n" +
//                    "int a=1;\n" +
//                    "int a=1;\n" +
//                    "for(int a=1;a==a;a++){\n" +
//                        "int b=2;\n" +
//                    "}\n" +
//                "}\n" +
//            "}\n" +
//        "}\n";

        IPDA pda = new PDA(qInitial, 'Z');
        Util.checkout(pda.run(w), w);
        System.out.println("*****************************");
    }
}
