import less1.Integral;
import less2.Sum;

public class Applic {
    public static void main(String[] args) {
        System.out.println("a)sum = " + new Sum(1, 5, 1., 'a').run());
        System.out.println("b)sum = " + new Sum(1, 5, 'b').run());
        System.out.println("c)sum = " + new Sum(1, 5, 'c').run());

        System.out.println("a)integral = " +
                new Integral(-1, 1, 10, 'a').run());

        System.out.println("b)integral = " +
                new Integral(0, 3.14/2., 10, 'b').run());

        System.out.println("c)integral = " +
                new Integral(1, 1000, 10, 'c').run());
    }
}
