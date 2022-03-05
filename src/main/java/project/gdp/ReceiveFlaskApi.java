package project.gdp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Controller
public class ReceiveFlaskApi {

    @GetMapping("/elasticnet/gdp")
    public String elasticnetGdp(Model model) {
        String url = "http://127.0.0.1:5000/tospring";
        String sb = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            String line = null;

            while ((line = br.readLine()) != null) {
                sb = sb + line + "\n";
            }
            log.info("========br======== {}", sb.toString());
            if (sb.toString().contains("ok")) {
                log.info("{}", sb.toString());
            }
            br.close();
            log.info("{}",  sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        model.addAttribute("result", sb.toString());

        return "full-video-cover";
    }

}
