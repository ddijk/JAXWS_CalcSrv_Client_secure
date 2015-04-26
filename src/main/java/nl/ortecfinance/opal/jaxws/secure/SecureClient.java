package nl.ortecfinance.opal.jaxws.secure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import nl.Calculator;
import nl.Calculator_Service;
import weblogic.wsee.security.unt.ClientUNTCredentialProvider;
import weblogic.xml.crypto.wss.WSSecurityContext;
import weblogic.xml.crypto.wss.provider.CredentialProvider;

public class SecureClient {

    public static void main(String[] args) {
        Calculator_Service calcSrv = new Calculator_Service();

        Calculator calculator = calcSrv.getCalculatorPort();
        Map requestContext = ((BindingProvider) calculator).getRequestContext();
        final String USERNAME = "ServiceTest1";
        final String PASSWORD = USERNAME;

        requestContext.put(BindingProvider.USERNAME_PROPERTY, USERNAME);

        requestContext.put(BindingProvider.PASSWORD_PROPERTY, USERNAME);

        List<CredentialProvider> credProviders = new ArrayList<>();

        credProviders.add(
                new ClientUNTCredentialProvider(USERNAME.getBytes(), PASSWORD.getBytes()));
        requestContext.put(WSSecurityContext.CREDENTIAL_PROVIDER_LIST, credProviders);

////        requestContext.put(WSSecurityContext.TRUST_MANAGER, new TrustManager() {
////            @Override
////            public boolean certificateCallback(X509Certificate[] chain, int validateErr) {
////                System.out.println("certificateCallback called.");
////                return true;
////            }
////        });
        System.out.println("About to call calculator");
        int result = calculator.add(1, 3);

        System.out.println(
                "result is " + result);
    }
}
