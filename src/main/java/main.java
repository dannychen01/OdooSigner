import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        if (args.length < 3) {
            System.out.println("Please input session_id, start date and end date!");
            System.out.println("Date format will be like yyyy-MM-dd");
        } else {
            OdooSigner odooSigner = new OdooSigner();
            odooSigner.run(args[0], args[1], args[2]);
        }
    }
}
