package project.gdp;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Controller
public class WithFlaskPost {

    @PostMapping("/elasticnet/gdp")
    public String postTest(@ModelAttribute("domain") Domain domain, RedirectAttributes redirectAttributes) throws IOException {
        Long economicalActivePopulation = domain.getEconomicalActivePopulation(); // 경제활동인구
        Long equipmentInvestment = domain.getEquipmentInvestment(); // 설비투자지수

        log.info("경제활동인구수: {}", economicalActivePopulation);
        log.info("설비투자지수: {}", equipmentInvestment);

        JSONObject data = new JSONObject(); // google simple-json을 의존성 추가해주어야함
        log.info("===============");
        data.put("population", economicalActivePopulation);
        data.put("investment", equipmentInvestment);
        log.info("{}", data.toString());
        log.info("{}", data);
        log.info("===============");

        try {
            String stringUrl = "http://127.0.0.1:5000/tospring";
            HttpURLConnection conn = null;

            URL url = new URL(stringUrl);
            conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST");//POST GET
            conn.setRequestProperty("Content-Type", "application/json");

            // POST방식으로 스트링을 통한 JSON 전송
            conn.setDoOutput(true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            bw.write(data.toString());
            bw.flush();
            bw.close();

            // 서버에서 보낸 응답 데이터 수신 받기
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String returnMsg = in.readLine();
            log.info("응답메시지 : {}", returnMsg);

            //HTTP 응답 코드 수신
            int responseCode = conn.getResponseCode();
            if(responseCode == 400) {
                log.info("400 : 명령을 실행 오류");
            } else if (responseCode == 500) {
                log.info("500 : 서버 에러.");
            } else { //정상 . 200 응답코드 . 기타 응답코드
                log.info(responseCode + " : (응답코드)");
            }

            conn.disconnect();
        }catch(IOException ie) {
            log.info("IOException " + ie.getCause());
            ie.printStackTrace();
        }catch(Exception ee) {
            log.info("Exception " + ee.getCause());
            ee.printStackTrace();
        }

        return "redirect:/elasticnet/gdp";
    }

}
