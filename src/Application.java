public class Application {
    public static void main(String[] args) {
        args = new String[]{
                "X = 1",
                "2 * X = 5",
                "5 = 0"
        };
        if (args.length > 0) {
            for (String arg : args) {
                System.out.println("Your equation: \"" + arg + "\"");
                Polynomial.setOrigin(arg);
                if (Polynomial.getWellFormatted() == true) {
                    System.out.print("Reduced form of equation: \"");
                    Polynomial.ShowReducedForm();
                    System.out.println("\"");
                    if (Polynomial.getDegreeMax() > 2)
                        System.out.println("I can't solve it, degree of equation is bigger than needed :(");
                    else if (Polynomial.getDegreeMin() < 0)
                        System.out.println("I can't solve it, one of X in equation has less degree than needed :(");
                    else
                        Polynomial.Solve();
                }
            }
        }
        else
            System.out.println("You need to to write at least one equation!");
    }
}
