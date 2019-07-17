package ca.jrvs.apps.trading.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.params.CookieSpecPNames;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class TradingUtil {

    public static final int HTTP_OK = 200;
    // service constants
    public static final String INVALID_EX_MSG = "Received a tweet with no content";

    public static final String SERVICE_USAGE_TEMP = "Usage: java -jar java_apps.jar";

    public static final String COOKIE_DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";
    public static final String POST_SUCCESS_MSG_FORMAT = "# Success: a new tweet was posted (id: %s)\n";

    public static final int CMD_ARG_INDEX = 2;
    // Json object mapper singleton
    private static ObjectMapper mapper;

    // class not instantiable
    private TradingUtil() {}

    /**
     * Convert a java object to JSON string
     * @param object input object
     * @return JSON string
     * @throws JsonProcessingException
     */
    public static String toJson(Object object, boolean prettyJson, boolean includeNullValues) throws JsonProcessingException {
         /*return getMapper().writer().withDefaultPrettyPrinter()
                .writeValueAsString(object);*/
       return getMapper().writeValueAsString(object);
    }

    /**
     *Parse JSON string to a object
     * @param json JSON str
     * @param _class object class
     * @param <T> type
     * @return object
     * @throws IOException
     */
    public static <T> T toObjectFromJson(String json, Class _class) throws IOException {
        return (T) getMapper().readValue(json, _class);
    }

    /**
     * Parse JSON stream to a object
     * @param jsonIStream JSON stream
     * @param _class object class
     * @param <T> type
     * @return object
     * @throws IOException
     */
    public static <T> T toObjectFromJson(InputStream jsonIStream, Class _class) throws IOException {
        return (T) getMapper().readValue(jsonIStream, _class);
    }

    /**
     * Singleton object mapper
     * initialize and configure if necessary
     * @return singleton object mapper
     */
    private static ObjectMapper getMapper() {
        if(mapper == null) {
            mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return mapper;
    }

    /**
     * checks http status
     * @param response server response
     * @return true if status was OK
     */
    public static boolean checkStatus(HttpResponse response) {
        int status = response.getStatusLine().getStatusCode();
        if(status != TradingUtil.HTTP_OK) {
            System.err.println("Server responded with error " + status);
            return false;
        }
        return true;
    }

    public static void setRequestHeaders(HttpRequestBase request) {
        request.getParams().setParameter(CookieSpecPNames.DATE_PATTERNS, Arrays.asList(COOKIE_DATE_FORMAT));
    }

    public static Iterable<? extends JsonNode> readTree(String json) throws IOException {
        return getMapper().readTree(json);
    }
}
