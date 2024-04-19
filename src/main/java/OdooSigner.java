import com.google.gson.Gson;
import dto.*;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OdooSigner {
    private static final String url = "https://192.168.78.224/web/dataset/call_kw/hr.attendance/create";
    private static final String SIGN_IN = "sign_in";
    private static final String SIGN_OUT = "sign_out";
    private long account_uid = 49L;
    public OdooSigner() {
    }
    public void run(String session_id, String startDateStr, String endDateStr) throws IOException, NoSuchAlgorithmException, KeyManagementException {

        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
            DateTime startDateTime = new DateTime(startDate);
            DateTime endDateTime = new DateTime(endDate);
            for (DateTime date = startDateTime; date.isBefore(endDateTime.plusDays(1)); date = date.plusDays(1)) {
                signIn(session_id, date);
                signOut(session_id, date);
            }
        } catch (ParseException | InterruptedException e) {
            System.out.println("Wrong start date format!");
            throw new RuntimeException(e);
        }
    }

    private void signIn(String session_id, DateTime date) throws NoSuchAlgorithmException, IOException, KeyManagementException, InterruptedException {
        dto.request request = prepareRequest(calculateSignInDateTime(date), SIGN_IN);
        postToOdoo(session_id, request);
    }

    private void signOut(String session_id, DateTime date) throws NoSuchAlgorithmException, IOException, KeyManagementException, InterruptedException {
        dto.request request = prepareRequest(calculateSignOutDateTime(date), SIGN_OUT);
        postToOdoo(session_id, request);
    }

    private request prepareRequest(String date, String action) {
        args arg = new args(57, date, action, false);
        context context = new context("en_US", "Asia/Taipei", account_uid, 1L, new HashMap<>() {{
            put("action", 169L);
        }});
        kwargs kwargs = new kwargs(context);
        params params = new params("hr.attendance", "create", List.of(arg), kwargs);
        request request = new request("2.0", "call", params, (int) (Math.random() * 100000000));
        return request;
    }

    private static void postToOdoo(String session_id, request request) throws NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/json");
        httppost.addHeader("Cookie", "session_id=" + session_id);

        SSLContextBuilder sslContextBuilder = SSLContextBuilder.create();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build(), new String[]{"TLSv1.1", "TLSv1.2"}, null, new NoopHostnameVerifier());
        HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(sslConnectionSocketFactory).build();
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

        Gson gson = new Gson();
        StringEntity stringEntity = new StringEntity(gson.toJson(request));
        httppost.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(httppost);
        System.out.println("Response Code:"+response.getCode()+" Date:" + request.getParams().getArgs().get(0).getName() +" Action:"+request.getParams().getArgs().get(0).getAction());
        Thread.sleep(500);
    }

    private String calculateSignInDateTime(DateTime targetDate){
        targetDate = targetDate.plusHours(1);
        targetDate = targetDate.plusMinutes((int) (Math.random() * 10));
        targetDate =targetDate.plusSeconds((int) (Math.random() * 60));
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return fmt.print(targetDate);
    }

    private String calculateSignOutDateTime(DateTime targetDate){
        targetDate = targetDate.plusHours(10);
        targetDate = targetDate.plusMinutes((int) (Math.random() * 10 + 5));
        targetDate =targetDate.plusSeconds((int) (Math.random() * 60));
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return fmt.print(targetDate);
    }
}
