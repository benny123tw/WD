package syncingWork;

public class Calc {
    static double remender, n, n1, n2, TEC1, TEC2, Ct1, Ct2, R1, R2, ldle1, ldle2;

    Calc (){
        n = (Worker.l + Machine.m) / (Worker.l + Worker.w);
        remender = (Worker.l + Machine.m) % (Worker.l + Worker.w);

        if(remender != 0){
            result(n);
        } else {
            System.out.println("1. 一人負責" + (int)n + "台");
            Ct1 = Worker.l + Machine.m;
            System.out.println("週期時間：" + Ct1);
            ldle1 = Ct1 - n1 * (Worker.l + Worker.w);
            System.out.println("工人閒置" + ldle1 +"分鐘");
            R1 = (60 / (Worker.l + Machine.m)) * n;
            System.out.println("生產率: " + R1 + "個/小時");
            TEC1 = (Worker.k1 + n * Machine.k2) / R1;
            System.out.println("TEC: " + TEC1 + "$/個");

            System.out.println("一位工人須負責" + (int)n + "台機器");
        }
    }

    private void result(double n){
        n1 = Math.floor(n);

        System.out.println("1. 一人負責" + (int)n1 + "台");
        Ct1 = Worker.l + Machine.m;
        System.out.println("週期時間：" + Ct1);
        ldle1 = Ct1 - n1 * (Worker.l + Worker.w);
        System.out.println("工人閒置" + ldle1 +"分鐘");
        R1 = (60 / (Worker.l + Machine.m)) * n1;
        System.out.println("生產率: " + R1 + "個/小時");
        TEC1 = (Worker.k1 + n1 * Machine.k2) / R1;
        System.out.println("TEC: " + TEC1 + "$/個");

        n2 = Math.ceil(n);
        System.out.println("2. 一人負責" + (int)n2 + "台");
        Ct2 = n2 * (Worker.l + Worker.w);
        System.out.println("週期時間：" + Ct2);
        ldle2 = Ct2 - Ct1;
        System.out.println("機器閒置" + ldle2 +"分鐘");
        R2 = (60 / (Worker.l + Worker.w));
        System.out.println("生產率: " + R2 + "個/小時");
        TEC2 = (Worker.k1 + n2 * Machine.k2) / R2;
        System.out.println("TEC: " + TEC2 + "$/個");

        if(TEC1<TEC2){
            System.out.println("一人負責" + (int)n1 +"台比較合適");
        }
        else {
            System.out.println("一人負責" + (int)n2 +"台比較合適");
        }
    }

}
