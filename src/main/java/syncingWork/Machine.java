package syncingWork;

public class Machine {
    static double m, k2;

    Machine(double m, double k2){
        this.m = m; this.k2 = k2;
    }

    /**
     * @param m the m to set
     */
    public static void setM(double m) {
        Machine.m = m;
    }

    /**
     * @param k2 the k2 to set
     */
    public static void setK2(double k2) {
        Machine.k2 = k2;
    }

    /**
     * @return the m
     */
    public static double getM() {
        return m;
    }

    /**
     * @return the k2
     */
    public static double getK2() {
        return k2;
    }
}
