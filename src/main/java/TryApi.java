import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class TryApi {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    String sXML ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:spel=\"http://speller.yandex.net/services/spellservice\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <spel:CheckTextRequest lang=\"en\" options=\"0\" format=\"\">" +
            "         <spel:text>%s</spel:text>" +
            "      </spel:CheckTextRequest>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";


    public void getPostGetQuery(String word) throws IOException {
        HttpGet httpGet = new HttpGet("http://speller.yandex.net/services/spellservice");
        CloseableHttpResponse resp1 = httpClient.execute(httpGet);
        System.out.println(resp1.getStatusLine());
        String sResp = EntityUtils.toString(resp1.getEntity());
        System.out.println(sResp);

        HttpPost httpPost = new HttpPost("http://speller.yandex.net/services/spellservice");
        httpPost.addHeader("Accept-Encoding","gzip,deflate");
        httpPost.addHeader("Content-Type","text/xml;charset=UTF-8");
        httpPost.addHeader("SOAPAction", "http://speller.yandex.net/services/spellservice/checkText");
        httpPost.addHeader("Connection","Keep-Alive");


        StringEntity strEnt = new StringEntity(String.format(sXML,word)); // где sXML – body нашего запроса
        httpPost.setEntity(strEnt);

        resp1 = httpClient.execute(httpPost);
        System.out.println(EntityUtils.toString (resp1.getEntity()));
        resp1.close();
    }
}
