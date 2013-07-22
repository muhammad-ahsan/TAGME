/*
 * MUHAMMAD Ahsan
 * <muhammad.ahsan@gmail.com>
 */
// TAG.ME http://tagme.di.unipi.it/
// Reference : http://tagme.di.unipi.it/tagme_help.html
// Expiry Date : 31/12/2013
package tagme;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TAGME {

    static String BaseURL = "http://tagme.di.unipi.it/tag?";
    static String Key;
    static String Conf1 = "&include_abstract=false";

    private static String getRESTQuery(String str) throws FileNotFoundException, IOException {
        Key = ReadKey();
        String Content = str.replace(" ", "+");
        String Text = "text=" + Content;
        String Query = BaseURL + Text + Key + Conf1;
        return Query;
    }

    public static void main(String[] args) throws IOException {
        String Content = "The 1986 FIBA World Championship was an international "
                + "basketball competition hosted by Spain from July 5 to 19 1986. "
                + "The Final phase of the tournament was held at the Palacio de "
                + "Deportes de la Comunidad, Madrid. They were classified as the"
                + " official men's basketball event of the 1986 Goodwill Games, "
                + "held simultaneously in Moscow.";
        String Q = getRESTQuery(Content);
        System.out.println(Q);
        // Example
        // http://tagme.di.unipi.it/tag?text=I+am+born+in+Pakistan&key=4534ogfe7&include_abstract=true
        String str = httpGet(Q);
        System.out.println(str);
    }

    public static String httpGet(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }
        StringBuilder sb;
        try (BufferedReader rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            sb = new StringBuilder();
            String line;
            line = rd.readLine();
            while (line != null) {
                sb.append(line);
                line = rd.readLine();
            }
        }
        conn.disconnect();
        return sb.toString();
    }

    private static String ReadKey() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Key.txt"));
        String Password = reader.readLine();
        if (Password != null) {
            return Password;
        } else {
            throw new FileNotFoundException("No key found in file");
        }
    }
}
