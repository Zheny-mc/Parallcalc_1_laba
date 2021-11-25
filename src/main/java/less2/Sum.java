package less2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.StrictMath.pow;

public class Sum {
    private Integer numA;
    private Integer numB;
    private double x;
    private char mode;

    public Sum(Integer numA, Integer numB, char mode) {
        this.numA = numA;
        this.numB = numB;
        this.mode = mode;
    }

    public Sum(Integer numA, Integer numB, double x, char mode) {
        this.numA = numA;
        this.numB = numB;
        this.x = x;
        this.mode = mode;
    }

    Integer fuctorial(int n) {
        Integer pr = 1;
        for (int i = 1; i < n; ++i) {
            pr *= i;
        }
        return pr;
    }

    public Double funcA(Integer n) {
        return pow(x, n) / fuctorial(n);
    }
    public Double funcB(Integer n) {
        return n / pow(2, n);
    }
    public Double funcC(Integer n) {
        return 1 / pow(n+6, 1./3.);
    }

    public Double func(Integer n) {
        Double res = 0.;
        switch(mode) {
            case 'a':
                res = funcA(n);
                break;
            case 'b':
                res = funcB(n);
                break;
            case 'c':
                res = funcC(n);
                break;
        }
        return res;
    }

    public Double run() {
        Double sum = 0.;
        //------------------------------------------------------------
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<Double>> callables =
                new ArrayList<Callable<Double>>();

        final int numbThread = 2;

        final int diff = (numB - numA)+1;
        final int step = numbThread;

        for (int i = numA; i <= numB; i+=step) {
            callables.add(
                    callablePart(i, i+step-1));
        }
        if (diff % numbThread == 1)
            callables.add(
                    callablePart(diff, diff));

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

    private Callable callablePart(int leftBoard , int rightBoard) {
        return () -> {
            Double partsum = 0.;
            for (int i = leftBoard; i <= rightBoard; ++i)
                partsum += func(i);
            return partsum;
        };
    }
}
