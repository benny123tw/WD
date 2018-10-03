package syncingWork;

public class Worker {

    static double l, w, k1;

    Worker(double l, double w, double k1){
        this.l = l; this.w = w; this.k1 = k1;
    }

    /**
     * @param l the l to set
     */
    public void setL(double l) {
        this.l = l;
    }

    /**
     * @param w the w to set
     */
    public void setW(double w) {
        this.w = w;
    }

    /**
     * @param k1 the k1 to set
     */
    public void setK1(double k1) {
        this.k1 = k1;
    }

    /**
     * @return the l
     */
    public double getL() {
        return l;
    }

    /**
     * @return the w
     */
    public double getW() {
        return w;
    }

    /**
     * @return the k1
     */
    public double getK1() {
        return k1;
    }
}
