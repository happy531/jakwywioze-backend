package com.example.jakwywiozebackend.utils;

import com.example.jakwywiozebackend.dto.CityDto;
import com.example.jakwywiozebackend.entity.Point;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    /*
        Earth's radius in kilometers
    */
    private static final double EARTH_RADIUS = 6371.0;
    public static final String BASE_FRONTEND_URL = System.getenv("BASE_FRONTEND_URL") != null ? System.getenv("BASE_FRONTEND_URL") : "http://localhost:3000";

    public static double calculateRange(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public static List<Point> filterPointsByRange(List<Point> points, CityDto city, int range) {
        List<Point> pointsInRange = new ArrayList<>();
        for (Point point : points) {
            if (calculateRange(point.getLat(), point.getLon(), city.getLatitude(), city.getLongitude()) <= range) {
                pointsInRange.add(point);
            }
        }
        return pointsInRange;
    }

    public static float distanceFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (float) (earthRadius * c);
    }

    private static String createEncodedAddressString(String street, String city, String zipCode) {
        String formattedAddress = street + ", " + city + ", " + zipCode;
        return URLEncoder.encode(formattedAddress, StandardCharsets.UTF_8);
    }

    public static void setLatAndLonForDynamicPointByAddress(Point point) throws IOException, InterruptedException {
        String address = createEncodedAddressString(point.getCity(), point.getStreet(), point.getZipcode());
        String apiKey = System.getenv("GEOCODING_API_KEY") != null ? System.getenv("GEOCODING_API_KEY") : null;

        if (apiKey == null) {
            return;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://trueway-geocoding.p.rapidapi.com/Geocode?address=" + address + "&language=en"))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "trueway-geocoding.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.body());
        JsonNode resultNodeLocation = jsonResponse.get("results").get(0).get("location");

        float lat = Float.parseFloat(String.valueOf(resultNodeLocation.get("lat")));
        float lon = Float.parseFloat(String.valueOf(resultNodeLocation.get("lng")));

        point.setLat(lat);
        point.setLon(lon);
    }

    public static String getMailHtml(String text1, String text2, String url, String buttonText) {
        return "<html>\n" +
                "  <head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <title>jakwywioze.pl - rejestracja konta</title>\n" +
                "    <style>\n" +
                "    @import url('https://fonts.googleapis.com/css2?family=Roboto&display=swap');\n" +
                "      img {\n" +
                "        border: none;\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "        max-width: 100%; \n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        background-color: #f6f6f6;\n" +
                "        font-family: sans-serif;\n" +
                "        font-family: 'Roboto', sans-serif;\n" +
                "        -webkit-font-smoothing: antialiased;\n" +
                "        font-size: 14px;\n" +
                "        line-height: 1.4;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%; \n" +
                "      }\n" +
                "      \n" +
                "      * {\n" +
                "        font-family: 'Roboto', sans-serif !important;\n" +
                "        color: #212121 !important;\n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-collapse: separate;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "        width: 100%; }\n" +
                "        table td {\n" +
                "          font-family: sans-serif;\n" +
                "        font-family: 'Roboto', sans-serif;\n" +
                "          font-size: 14px;\n" +
                "          vertical-align: top; \n" +
                "      }\n" +
                "\n" +
                "      .body {\n" +
                "        background-color: #f6f6f6;\n" +
                "        width: 100%; \n" +
                "      }\n" +
                "\n" +
                "      /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */\n" +
                "      .container {\n" +
                "        display: block;\n" +
                "        margin: 0 auto !important;\n" +
                "        /* makes it centered */\n" +
                "        max-width: 580px;\n" +
                "        padding: 10px;\n" +
                "        width: 580px; \n" +
                "      }\n" +
                "\n" +
                "      /* This should also be a block element, so that it will fill 100% of the .container */\n" +
                "      .content {\n" +
                "        box-sizing: border-box;\n" +
                "        display: block;\n" +
                "        margin: 0 auto;\n" +
                "        max-width: 580px;\n" +
                "        padding: 10px; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          HEADER, FOOTER, MAIN\n" +
                "      ------------------------------------- */\n" +
                "      .main {\n" +
                "        background: #ffffff;\n" +
                "        border-radius: 0.5rem;\n" +
                "        width: 100%; \n" +
                "      }\n" +
                "\n" +
                "      .wrapper {\n" +
                "        box-sizing: border-box;\n" +
                "        padding: 20px; \n" +
                "      }\n" +
                "\n" +
                "      .content-block {\n" +
                "        padding-bottom: 10px;\n" +
                "        padding-top: 10px;\n" +
                "      }\n" +
                "\n" +
                "      .footer {\n" +
                "        clear: both;\n" +
                "        margin-top: 10px;\n" +
                "        text-align: center;\n" +
                "        width: 100%; \n" +
                "      }\n" +
                "        .footer td,\n" +
                "        .footer p,\n" +
                "        .footer span,\n" +
                "        .footer a {\n" +
                "          color: #999999;\n" +
                "          font-size: 12px;\n" +
                "          text-align: center; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          TYPOGRAPHY\n" +
                "      ------------------------------------- */\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4 {\n" +
                "        color: #212121;\n" +
                "        font-family: sans-serif;\n" +
                "        font-family: 'Roboto', sans-serif;\n" +
                "        font-weight: 400;\n" +
                "        line-height: 1.4;\n" +
                "        margin: 0;\n" +
                "        margin-bottom: 30px; \n" +
                "      }\n" +
                "      .xd {" +
                "         padding-right: 8px;" +
                "         margin-bottom: -8px;" +
                "       }  \n" +
                "      h1 {\n" +
                "        font-size: 2.125rem;\n" +
                "        font-weight: 300;\n" +
                "        text-align: center;\n" +
                "        background: #b0e8bc;\n" +
                "        padding: 1rem;\n" +
                "        border-radius: 6px;\n" +
                "        /* margin: -1.25rem -1.25rem 1.5rem -1.25rem; */\n" +
                "      }\n" +
                "\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol {\n" +
                "        font-family: sans-serif;\n" +
                "        font-family: 'Roboto', sans-serif;\n" +
                "        font-size: 1rem;\n" +
                "        font-weight: normal;\n" +
                "        margin: 0;\n" +
                "        margin-bottom: 15px; \n" +
                "        text-align: center;\n" +
                "      }\n" +
                "        p li,\n" +
                "        ul li,\n" +
                "        ol li {\n" +
                "          list-style-position: inside;\n" +
                "          margin-left: 5px; \n" +
                "      }\n" +
                "\n" +
                "      a {\n" +
                "        color: #3498db;\n" +
                "        text-decoration: underline; \n" +
                "      }\n" +
                "\n" +
                "    h1 > span::after {\n" +
                "      position: absolute;\n" +
                "      top:1px;\n" +
                "      left:3px;\n" +
                "      content: url();\n" +
                "      height: 30px;\n" +
                "      width: 30px;\n" +
                "    }" +
                "      .btn {\n" +
                "        box-sizing: border-box;\n" +
                "        width: 100%; }\n" +
                "        .btn > tbody > tr > td {\n" +
                "          padding-bottom: 15px; }\n" +
                "        .btn table {\n" +
                "          width: auto; \n" +
                "      }\n" +
                "        .btn table td {\n" +
                "          background-color: #ffffff;\n" +
                "          border-radius: 5px;\n" +
                "          text-align: center; \n" +
                "      }\n" +
                "        .btn a {\n" +
                "          background-color: #ffffff;\n" +
                "          border: solid 1px #3498db;\n" +
                "          border-radius: 5px;\n" +
                "          box-sizing: border-box;\n" +
                "          color: #3498db;\n" +
                "          cursor: pointer;\n" +
                "          display: inline-block;\n" +
                "          font-size: 1rem;\n" +
                "          font-weight: bold;\n" +
                "          margin: 0;\n" +
                "          padding: 12px 25px;\n" +
                "          text-decoration: none;\n" +
                "      }\n" +
                "\n" +
                "      .btn-primary table td {\n" +
                "        background-color: #3498db; \n" +
                "      }\n" +
                "\n" +
                "      .btn-primary a {\n" +
                "        background-color: #b0e8bc;\n" +
                "        border-color: #b0e8bc; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          OTHER STYLES THAT MIGHT BE USEFUL\n" +
                "      ------------------------------------- */\n" +
                "      .last {\n" +
                "        margin-bottom: 0; \n" +
                "      }\n" +
                "\n" +
                "      .first {\n" +
                "        margin-top: 0; \n" +
                "      }\n" +
                "\n" +
                "      .align-center {\n" +
                "        text-align: center; \n" +
                "      }\n" +
                "\n" +
                "      .align-right {\n" +
                "        text-align: right; \n" +
                "      }\n" +
                "\n" +
                "      .align-left {\n" +
                "        text-align: left; \n" +
                "      }\n" +
                "\n" +
                "      .clear {\n" +
                "        clear: both; \n" +
                "      }\n" +
                "\n" +
                "      .mt0 {\n" +
                "        margin-top: 0; \n" +
                "      }\n" +
                "\n" +
                "      .mb0 {\n" +
                "        margin-bottom: 0; \n" +
                "      }\n" +
                "\n" +
                "      .preheader {\n" +
                "        color: transparent;\n" +
                "        display: none;\n" +
                "        height: 0;\n" +
                "        max-height: 0;\n" +
                "        max-width: 0;\n" +
                "        opacity: 0;\n" +
                "        overflow: hidden;\n" +
                "        mso-hide: all;\n" +
                "        visibility: hidden;\n" +
                "        width: 0; \n" +
                "      }\n" +
                "\n" +
                "      .powered-by a {\n" +
                "        text-decoration: none; \n" +
                "      }\n" +
                "\n" +
                "      hr {\n" +
                "        border: 0;\n" +
                "        border-bottom: 1px solid #f6f6f6;\n" +
                "        margin: 20px 0; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
                "      ------------------------------------- */\n" +
                "      @media only screen and (max-width: 620px) {\n" +
                "        table.body h1 {\n" +
                "          margin-bottom: 20px !important; \n" +
                "        }\n" +
                "        table.body p,\n" +
                "        table.body ul,\n" +
                "        table.body ol,\n" +
                "        table.body td,\n" +
                "        table.body span,\n" +
                "        table.body a {\n" +
                "          font-size: 16px !important; \n" +
                "        }\n" +
                "        table.body .wrapper,\n" +
                "        table.body .article {\n" +
                "          padding: 10px !important; \n" +
                "        }\n" +
                "        table.body .content {\n" +
                "          padding: 0 !important; \n" +
                "        }\n" +
                "        table.body .container {\n" +
                "          padding: 0 !important;\n" +
                "          width: 100% !important; \n" +
                "        }\n" +
                "        table.body .main {\n" +
                "          border-left-width: 0 !important;\n" +
                "          border-radius: 0 !important;\n" +
                "          border-right-width: 0 !important; \n" +
                "        }\n" +
                "        table.body .btn table {\n" +
                "          width: 100% !important; \n" +
                "        }\n" +
                "        table.body .btn a {\n" +
                "          width: 100% !important; \n" +
                "        }\n" +
                "        table.body .img-responsive {\n" +
                "          height: auto !important;\n" +
                "          max-width: 100% !important;\n" +
                "          width: auto !important; \n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          PRESERVE THESE STYLES IN THE HEAD\n" +
                "      ------------------------------------- */\n" +
                "      @media all {\n" +
                "        .ExternalClass {\n" +
                "          width: 100%; \n" +
                "        }\n" +
                "        .ExternalClass,\n" +
                "        .ExternalClass p,\n" +
                "        .ExternalClass span,\n" +
                "        .ExternalClass font,\n" +
                "        .ExternalClass td,\n" +
                "        .ExternalClass div {\n" +
                "          line-height: 100%; \n" +
                "        }\n" +
                "        .apple-link a {\n" +
                "          color: inherit !important;\n" +
                "          font-family: inherit !important;\n" +
                "          font-size: inherit !important;\n" +
                "          font-weight: inherit !important;\n" +
                "          line-height: inherit !important;\n" +
                "          text-decoration: none !important; \n" +
                "        }\n" +
                "        #MessageViewBody a {\n" +
                "          color: inherit;\n" +
                "          text-decoration: none;\n" +
                "          font-size: inherit;\n" +
                "          font-family: inherit;\n" +
                "          font-weight: inherit;\n" +
                "          line-height: inherit;\n" +
                "        }\n" +
                "        .btn-primary table td:hover {\n" +
                "          background-color: #34495e !important; \n" +
                "        }\n" +
                "        .btn-primary a:hover {\n" +
                "          background-color: #64ce7b !important;\n" +
                "          border-color: #64ce7b !important; \n" +
                "        } \n" +
                "      }\n" +
                "\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <span class=\"preheader\"></span>\n" +
                "    <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n" +
                "      <tr>\n" +
                "        <td>&nbsp;</td>\n" +
                "        <td class=\"container\">\n" +
                "          <div class=\"content\">\n" +
                "\n" +
                "            <!-- START CENTERED WHITE CONTAINER -->\n" +
                "            <table role=\"presentation\" class=\"main\">\n" +
                "\n" +
                "              <!-- START MAIN CONTENT AREA -->\n" +
                "              <tr>\n" +
                "                <td class=\"wrapper\">\n" +
                "                  <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                    <tr>\n" +
                "                      <td>\n" +
                "                        <h1><img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Font_Awesome_5_solid_recycle.svg/1200px-Font_Awesome_5_solid_recycle.svg.png\" height=\"30px\" width=\"30px\" alt=\"\" class = 'xd'\">jakwywioze.pl</h1>\n" +
                "                        <p style=\"font-size: 1.25rem; margin-bottom: 1rem;\"><strong>Dziękujemy za rejestracje konta</strong></p>\n" +
                "                        <p class=\"mb0\">"+text1+"</p>\n" +
                "                        <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\">\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td align=\"center\" style=\"padding:1.75rem 0 !important;\">\n" +
                "                                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                                  <tbody>\n" +
                "                                    <tr>\n" +
                "                                      <td><a href=\"" + url + "\" target=\"_blank\">"+buttonText+"</a></td>\n" +
                "                                    </tr>\n" +
                "                                  </tbody>\n" +
                "                                </table>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                        <p style=\"margin-bottom: 0.325rem;\">"+text2+"</p>\n" +
                "                        <p>Wiadomość wygenerowana automatycznie.<br> Prosimy na nią nie odpowiadać.</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "\n" +
                "            <!-- END MAIN CONTENT AREA -->\n" +
                "            </table>\n" +
                "            <!-- END CENTERED WHITE CONTAINER -->\n" +
                "\n" +
                "            <!-- START FOOTER -->\n" +
                "            <div class=\"footer\">\n" +
                "              <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                <tr>\n" +
                "                  <td class=\"content-block\">\n" +
                "                    <span class=\"apple-link\"></span>\n" +
                "                    <br>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td class=\"content-block powered-by\">\n" +
                "                   \n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </table>\n" +
                "            </div>\n" +
                "            <!-- END FOOTER -->\n" +
                "\n" +
                "          </div>\n" +
                "        </td>\n" +
                "        <td>&nbsp;</td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>\n";
    }
}