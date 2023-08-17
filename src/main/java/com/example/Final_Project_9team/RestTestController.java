package com.example.Final_Project_9team;
import com.example.Final_Project_9team.entity.Items;
import com.example.Final_Project_9team.repository.ItemsRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@RestController
public class RestTestController {
    private final ItemsRepository itemsRepository;

    @Autowired
    public RestTestController(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @GetMapping("apitest")
    public String callApiHttp() throws IOException {
        String result = "";
        try {

            String urlStr = "http://apis.data.go.kr/B551011/KorService1/areaBasedList1?" +
                    "serviceKey=vqoXwkq9RMCWANTOOUUOJVPQ%2FDtls8Z099FreqNacdFobJPBCviYv10hegz5KtPrVxci7OYYwEBNv%2ByS7hZ9%2Fw%3D%3D" +
                    "&numOfRows=1000" +
                    "&pageNo=1" +
                    "&MobileOS=ETC" +
                    "&MobileApp=AppTest" +
                    "&_type=json" +
                    "&listYN=Y" +
                    "&arrange=A" +
                    "&contentTypeId=39" +
                    "&areaCode=1";

            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            StringBuilder resultBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                resultBuilder.append(line + "\n");
            }
            result = resultBuilder.toString();
            log.info(result);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject response = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray itemArr = (JSONArray) items.get("item");

            for (int i = 0; i < itemArr.size(); i++) {
                JSONObject tmp = (JSONObject) itemArr.get(i);
                Items item = new Items();
                item.setContentId((String) tmp.get("contentid"));
                item.setContentTypeId((String) tmp.get("contenttypeid"));
                item.setTitle((String) tmp.get("title"));
                item.setCreatedTime((String) tmp.get("createdtime"));
                item.setModifiedTime((String) tmp.get("modifiedtime"));
                item.setTel((String) tmp.get("tel"));
                item.setHomePage((String) tmp.get("homepage"));
                item.setBookTour((String) tmp.get("booktour"));
                item.setFirstImage((String) tmp.get("firstimage"));
                item.setFirstImage2((String) tmp.get("firstimage2"));
                item.setCpyrgtDivCd((String) tmp.get("cpyrhtDivCd"));
                item.setAreaCode((String) tmp.get("areacode"));
                item.setSiGunGuCode((String) tmp.get("sigungucode"));
                item.setCat1((String) tmp.get("cat1"));
                item.setCat2((String) tmp.get("cat2"));
                item.setCat3((String) tmp.get("cat3"));
                item.setAddr1((String) tmp.get("addr1"));
                item.setAddr2((String) tmp.get("addr2"));
                item.setZipCode((String) tmp.get("zipcode"));
                item.setMapX((String) tmp.get("mapx"));
                item.setMapY((String) tmp.get("mapy"));
                item.setMLevel((String) tmp.get("mlevel"));
                item.setOverView((String) tmp.get("overview"));

                // Save the item to the database
                itemsRepository.save(item);
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result+"";
    }
}


//import com.example.Final_Project_9team.entity.Items;
//        import com.example.Final_Project_9team.repository.ItemsRepository;
//        import org.json.simple.JSONArray;
//        import org.json.simple.JSONObject;
//        import org.json.simple.parser.JSONParser;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.web.bind.annotation.GetMapping;
//        import org.springframework.web.bind.annotation.RestController;
//
//        import java.io.BufferedReader;
//        import java.io.IOException;
//        import java.io.InputStreamReader;
//        import java.net.HttpURLConnection;
//        import java.net.URL;
//
//@RestController
//public class RestTestController {
//    private final ItemsRepository itemsRepository;
//
//    @Autowired
//    public RestTestController(ItemsRepository itemsRepository) {
//        this.itemsRepository = itemsRepository;
//    }
//
//    @GetMapping("apitest")
//    public String callApiHttp() throws IOException {
//        String result = "";
//        try {
//
//            String urlStr = "http://apis.data.go.kr/B551011/KorService1/detailCommon1?" +
//                    "serviceKey=tEtm7VS0D2fOdYQL6kXItj%2FLsPOAwX3LaSlMwzyaovxJ3TKVElVGjB940YX%2FVAyOvxWuD60ClLDsAwr0KFSZ8A%3D%3D" +
//                    "&MobileOS=ETC" +
//                    "&MobileApp=AppTest" +
//                    "&_type=json" +
//                    "&contentId=126508" +
//                    "&defaultYN=Y" +
//                    "&firstImageYN=Y" +
//                    "&areacodeYN=Y" +
//                    "&catcodeYN=Y" +
//                    "&addrinfoYN=Y" +
//                    "&mapinfoYN=Y" +
//                    "&overviewYN=Y" +
//                    "&numOfRows=10" +
//                    "&pageNo=1";
//
//            URL url = new URL(urlStr);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
//            result = br.readLine();
//
//            JSONParser jsonParser = new JSONParser();
//            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
//            if (jsonObject.containsKey("response")) {
//                JSONObject response = (JSONObject) jsonObject.get("response");
//                if (response.containsKey("header") && response.containsKey("body")) {
//                    JSONObject body = (JSONObject) response.get("body");
//                    if (body.containsKey("items")) {
//                        JSONObject items = (JSONObject) body.get("items");
//                        JSONArray itemArr = (JSONArray) items.get("item");
//
//                        for (int i = 0; i < itemArr.size(); i++) {
//                            JSONObject tmp = (JSONObject) itemArr.get(i);
//                            Items item = new Items();
//                            item.setContentId((String) tmp.get("contentid"));
//                            item.setContentTypeId((String) tmp.get("contenttypeid"));
//                            item.setTitle((String) tmp.get("title"));
//                            item.setCreatedTime((String) tmp.get("createdtime"));
//                            item.setModifiedTime((String) tmp.get("modifiedtime"));
//                            item.setTel((String) tmp.get("tel"));
//                            item.setHomePage((String) tmp.get("homepage"));
//                            item.setBookTour((String) tmp.get("booktour"));
//                            item.setFirstImage((String) tmp.get("firstimage"));
//                            item.setFirstImage2((String) tmp.get("firstimage2"));
//                            item.setCpyrgtDivCd((String) tmp.get("cpyrhtDivCd"));
//                            item.setAreaCode((String) tmp.get("areacode"));
//                            item.setSiGunGuCode((String) tmp.get("sigungucode"));
//                            item.setCat1((String) tmp.get("cat1"));
//                            item.setCat2((String) tmp.get("cat2"));
//                            item.setCat3((String) tmp.get("cat3"));
//                            item.setAddr1((String) tmp.get("addr1"));
//                            item.setAddr2((String) tmp.get("addr2"));
//                            item.setZipCode((String) tmp.get("zipcode"));
//                            item.setMapX((String) tmp.get("mapx"));
//                            item.setMapY((String) tmp.get("mapy"));
//                            item.setMLevel((String) tmp.get("mlevel"));
//                            item.setOverView((String) tmp.get("overview"));
//
//                            // Save the item to the database
//                            itemsRepository.save(item);
//                        }
//                    }
//                }
//            }
//            urlConnection.disconnect();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return result+"</json>";
//    }
//}

