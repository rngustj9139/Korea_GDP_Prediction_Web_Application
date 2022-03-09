package project.gdp;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Controller
public class ConnectionWithFlaskApi {

    @GetMapping("/elasticnet/gdp")
    public String elasticnetGdpGet(Model model) {
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
                log.info("OK");
            }
            log.info("{}",  sb.toString());
            br.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        model.addAttribute("result", sb.toString());

        return "full-video-cover";
    }

    @PostMapping("/elasticnet/gdp")
    public String elasticnetGdpPost(@ModelAttribute("domain") Domain domain, RedirectAttributes redirectAttributes) throws IOException {
        Long economicalActivePopulation = domain.getEconomicalActivePopulation(); // 경제활동인구
        Long equipmentInvestment = domain.getEquipmentInvestment(); // 설비투자지수

        log.info("경제활동인구수: {}", economicalActivePopulation);
        log.info("설비투자지수: {}", equipmentInvestment);

        JSONObject jobject = new JSONObject(); // google simple-json을 의존성 추가해주어야함
        log.info("===============");
        jobject.put("population", economicalActivePopulation);
        jobject.put("investment", equipmentInvestment);
        log.info("{}", jobject.toString());
        log.info("===============");

        try {
            String url = "http://127.0.0.1:5000/tospring";
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            log.info("conn.toString(): {}", conn.toString());
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            log.info("conn.getRequestMethod(): {}", conn.getRequestMethod());

            // 데이터 전송
            OutputStream os = conn.getOutputStream();
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));
            bWriter.write(jobject.toString()); // JSON string을 전송
            log.info("POST전송 JSON: {}", bWriter.toString());
            log.info("{}", bWriter.equals(jobject.toString()));

            // 응답 결과를 읽어옴
    //      String inputLine = null;
    //      StringBuffer stringBuffer = new StringBuffer();
    //      BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
    //      while((inputLine=bReader.readLine())!=null){
    //          stringBuffer.append(inputLine);
    //      }
    //      bReader.close();
            bWriter.flush();
            bWriter.close();
            os.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "redirect:/elasticnet/gdp";
    }

}
