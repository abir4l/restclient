import com.google.gson.Gson;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

/**
 * Created by abiral on 3/28/18.
 */
public class RestClient {

    static String url = "http://localhost:3000/";
    ClientRequest request;
    ClientResponse response;


    public static void main(String[] args) {
        System.out.println(new RestClient().get(url,new Data()));
}



    public <T extends Object>T get(String url,T clazz){
        request = new ClientRequest(url);
        request.accept("application/json");
        Gson gson = new Gson();
        Object object = null;
        try {
            response = request.get(String.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            object = response.getEntity();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T) gson.fromJson(object.toString(),clazz.getClass().getGenericSuperclass());

    }



    public void post(String url){
        request = new ClientRequest(url);
        request.accept("application/json");
        Data data = new Data();
        data.setData("hello world");
        request.body("application/json",new Gson().toJson(data));
        try {
            response = request.post(String.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            Object object = response.getEntity();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static class Data{
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "data='" + data + '\'' +
                    '}';
        }
    }
}
