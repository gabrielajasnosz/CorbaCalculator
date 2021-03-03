import Calculator.*;


import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import java.util.Properties;

class CalculatorInterfaceImpl extends CalculatorInterfacePOA {
    private ORB orb;
    String result;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    public String run(String op, double a, double b, double c, double d) {

        String result = "";
        switch (op) {
            case "+":
                result = add(a, b, c, d);
                break;
            case "-":
                result = subtract(a, b, c, d);
                break;
            case "*":
                result = multiply(a, b, c, d);
                break;
            case "/":
                result = divide(a, b, c, d);
                break;
            default:
                result = "Błędny znak";


        }

        return result;
    }

    public String add(double a, double b, double c, double d) {
        double re = a + c;
        double im = b + d;
        result = "Re: " + re + " Im: " + im;
        return result;
    }

    public String subtract(double a, double b, double c, double d) {
        double re = a - c;
        double im = b - d;
        result = "Re: " + re + " Im: " + im;
        return result;
    }

    public String multiply(double a, double b, double c, double d) {
        double re = (a * c) - (b * d);
        double im = (a * d) + (b * c);
        result = "Re: " + re + " Im: " + im;
        return result;
    }

    public String divide(double a, double b, double c, double d) {
        double re = ((a * c) + (b * d)) / ((c * c) + (d * d));
        double im = ((b * c) - (a * d)) / ((c * c) + (d * d));
        result = "Re: " + re + " Im: " + im;
        return result;
    }

    public void shutdown() {
        orb.shutdown(false);
    }
}

public class CalculatorServer {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            CalculatorInterfaceImpl CalculatorImpl = new CalculatorInterfaceImpl();
            CalculatorImpl.setORB(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(CalculatorImpl);
            CalculatorInterface href = CalculatorInterfaceHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Calculator";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);
            System.out.println("Serwer uruchomiony.");

            orb.run();
        } catch (Exception e) {
            System.out.println("Blad!");
        }
        System.out.println("\nWyjscie...");
    }
}
