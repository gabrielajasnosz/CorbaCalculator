import Calculator.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

import java.util.Scanner;
import java.lang.*;

public class CalculatorClient {
    static CalculatorInterface CalculatorImpl;

    public static void main(String args[]) {

        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            ;
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Calculator";
            CalculatorImpl = CalculatorInterfaceHelper.narrow(ncRef.resolve_str(name));
            double a, b, c, d;
            String x;

            System.out.println("Witaj!");
            System.out.println("Dozwolone operacje: + , - , / , * ");
            Scanner scan = new Scanner(System.in);
            System.out.println("Liczba 1, czesc rzeczywista: ");
            a = scan.nextDouble();
            System.out.println("Liczba 1, czesc urojona: ");
            b = scan.nextDouble();
            System.out.println("Liczba 2, czesc rzeczywista: ");
            c = scan.nextDouble();
            System.out.println("Liczba 2, czesc urojona: ");
            d = scan.nextDouble();

            System.out.println("Podaj znak operacji ktora chcesz wykonac:");
            x = scan.next();
            System.out.println("Wynik: " + CalculatorImpl.run(x, a, b, c, d));

            CalculatorImpl.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
