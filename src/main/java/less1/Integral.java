package less1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Math.*;


public class Integral {
    private double numA;
    private double numB;
    private int countN;
    private char mode;

    public Integral(double numA, double numB, int countN, char ch) {
        this.numA = numA;
        this.numB = numB;
        this.countN = countN;
        this.mode = ch;
    }

    public double funcA(double x) {
        return (2*x - 3*pow(x, 2) + 4*pow(x, 3) + 5*pow(x, 4));
    }
    public double funcB(double x) {
        return pow(12, sin(x)) * cos(x);
    }
    public double funcC(double x) {
        return 4 * pow( (x - 2*pow(x, 2) + 1.5*pow(x, 3)), 1./3.);
    }

    public double func(double x) {
        double res = 0.;
        switch(mode) {
            case 'a':
                res = funcA(x);
                break;
            case 'b':
                res = funcA(x);
                break;
            case 'c':
                res = funcA(x);
                break;
        }
        return res;
    }

    public Double run() {
        Double sum = (func(numA) + func(numB)) / 2.;
        //------------------------------------------------------------
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<Double>> callables =
                new ArrayList<Callable<Double>>();

        final int numbThread = 2;

        final double h = (numB-numA) / countN;
        final int step = numbThread;

        for (int i = 1; i <= countN-1-1; i+=step) {
            callables.add(
                    callablePart(i, i+step-1, numA, h));
        }
        if (((countN+1)-2) % numbThread == 1) {
            int count = ((countN+1)-2);
            callables.add(
                    callablePart(count, count, numA, h));
        }


        List<Future<Double>> result = null;
        try {
            result = executor.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Future<Double> i: result) {
            try {
                sum += i.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return sum;
    }

    private Callable callablePart(int leftBoard , int rightBoard, double a, double h) {
        return () -> {
            double partSum = 0;
            for (int i = leftBoard; i <= rightBoard; ++i) {
                double x = a + i * h;
                partSum += func(x);
            }
            return partSum;
        };
    }
}