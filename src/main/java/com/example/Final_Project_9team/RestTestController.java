package com.example.Final_Project_9team;
import com.example.Final_Project_9team.entity.embeded.Location;
import com.example.Final_Project_9team.entity.item.Accommodation;
import com.example.Final_Project_9team.entity.item.Attraction;
import com.example.Final_Project_9team.entity.item.Restaurant;
import com.example.Final_Project_9team.repository.AccommodationRepository;
import com.example.Final_Project_9team.repository.AttractionRepository;
import com.example.Final_Project_9team.repository.RestaurantRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
public class RestTestController {
    private final AttractionRepository attractionRepository;
    private final AccommodationRepository accommodationRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestTestController(AttractionRepository attractionRepository, AccommodationRepository accommodationRepository, RestaurantRepository restaurantRepository) {
        this.attractionRepository = attractionRepository;
        this.accommodationRepository = accommodationRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("apisave-commondata")
    public String commonDateSave() throws IOException {
        String result = "";
        int numOfRows = 500; // Set your desired batch size
        int pageNo = 1;

        try {
            String baseUrl = "http://apis.data.go.kr/B551011/KorService1/areaBasedList1?";
            String apiKey = "vqoXwkq9RMCWANTOOUUOJVPQ%2FDtls8Z099FreqNacdFobJPBCviYv10hegz5KtPrVxci7OYYwEBNv%2ByS7hZ9%2Fw%3D%3D";
            String urlStr;
            String[] contentTypeIds = {"12", "14", "32", "39"};

            JSONObject body;
            for(String contentTypeId : contentTypeIds) {


            do {
                urlStr = baseUrl +
                        "serviceKey=" + apiKey +
                        "&numOfRows=" + numOfRows +
                        "&pageNo=" + pageNo +
                        "&MobileOS=ETC" +
                        "&MobileApp=AppTest" +
                        "&_type=json" +
                        "&listYN=Y" +
                        "&arrange=A" +
                        "&contentTypeId=" + contentTypeId + //12, 14, 32, 39
                        "&areaCode=39";

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

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONObject response = (JSONObject) jsonObject.get("response");
                body = (JSONObject) response.get("body");
                JSONObject items = (JSONObject) body.get("items");
                JSONArray itemArr = (JSONArray) items.get("item");

                for (int i = 0; i < itemArr.size(); i++) {
                    JSONObject tmp = (JSONObject) itemArr.get(i);
                    if (tmp.get("contenttypeid").equals("12") || tmp.get("contenttypeid").equals("14")) {
                        Attraction attraction = new Attraction();
                        Location location = Location.builder()
                                .sido((String) tmp.get("areacode"))
                                .sigungu((String) tmp.get("sigungucode"))
                                .upmyundong((String) tmp.get("addr2"))
                                .latitude((String) tmp.get("mapy"))
                                .longitude((String) tmp.get("mapx"))
                                .fullAddress((String) tmp.get("addr1"))
                                .mLevel((String) tmp.get("mlevel"))
                                .build();
                        // createdtime과 modifiedtime 문자열을 가져옴
                        String createdTimeStr = (String) tmp.get("createdtime");
                        String modifiedTimeStr = (String) tmp.get("modifiedtime");

                        // 문자열을 LocalDateTime으로 변환
                        LocalDateTime createdTime = LocalDateTime.parse(createdTimeStr, DateTimeFormatter.ofPattern("yyyyMMddHH"));
                        LocalDateTime modifiedTime = LocalDateTime.parse(modifiedTimeStr, DateTimeFormatter.ofPattern("yyyyMMddHH"));

                        // Attraction 엔티티에 설정
                        attraction.setCreatedAt(createdTime);
                        attraction.setModifiedAt(modifiedTime);
                        attraction.setContentId((String) tmp.get("contentid"));
                        attraction.setContentTypeId((String) tmp.get("contenttypeid"));
                        attraction.setName((String) tmp.get("title"));
                        attraction.setHomePage((String) tmp.get("homepage"));
                        attraction.setFirstImage((String) tmp.get("firstimage"));
                        attraction.setFirstImage2((String) tmp.get("firstimage2"));
                        attraction.setCat1((String) tmp.get("cat1"));
                        attraction.setCat2((String) tmp.get("cat2"));
                        attraction.setCat3((String) tmp.get("cat3"));
                        attraction.setOverView((String) tmp.get("overview"));
                        attraction.setTel((String) tmp.get("tel"));
                        attraction.setCpyrgtDivCd((String) tmp.get("cpyrhtDivCd"));
                        attraction.setLocation(location);
                        attractionRepository.save(attraction);

                    } else if (tmp.get("contenttypeid").equals("32")) {
                        Accommodation accommodation = new Accommodation();
                        Location location = Location.builder()
                                .sido((String) tmp.get("areacode"))
                                .sigungu((String) tmp.get("sigungucode"))
                                .upmyundong((String) tmp.get("addr2"))
                                .latitude((String) tmp.get("mapy"))
                                .longitude((String) tmp.get("mapx"))
                                .fullAddress((String) tmp.get("addr1"))
                                .mLevel((String) tmp.get("mlevel"))
                                .build();
                        // createdtime과 modifiedtime 문자열을 가져옴
                        String createdTimeStr = (String) tmp.get("createdtime");
                        String modifiedTimeStr = (String) tmp.get("modifiedtime");

                        // 문자열을 LocalDateTime으로 변환
                        LocalDateTime createdTime = LocalDateTime.parse(createdTimeStr, DateTimeFormatter.ofPattern("yyyyMMddHH"));
                        LocalDateTime modifiedTime = LocalDateTime.parse(modifiedTimeStr, DateTimeFormatter.ofPattern("yyyyMMddHH"));

                        // accommodation 엔티티에 설정
                        accommodation.setCreatedAt(createdTime);
                        accommodation.setModifiedAt(modifiedTime);
                        accommodation.setContentId((String) tmp.get("contentid"));
                        accommodation.setContentTypeId((String) tmp.get("contenttypeid"));
                        accommodation.setName((String) tmp.get("title"));
                        accommodation.setHomePage((String) tmp.get("homepage"));
                        accommodation.setFirstImage((String) tmp.get("firstimage"));
                        accommodation.setFirstImage2((String) tmp.get("firstimage2"));
                        accommodation.setCat1((String) tmp.get("cat1"));
                        accommodation.setCat2((String) tmp.get("cat2"));
                        accommodation.setCat3((String) tmp.get("cat3"));
                        accommodation.setOverView((String) tmp.get("overview"));
                        accommodation.setTel((String) tmp.get("tel"));
                        accommodation.setCpyrgtDivCd((String) tmp.get("cpyrhtDivCd"));
                        accommodation.setLocation(location);
                        accommodationRepository.save(accommodation);
                    } else if (tmp.get("contenttypeid").equals("39")) {
                        Restaurant restaurant = new Restaurant();
                        Location location = Location.builder()
                                .sido((String) tmp.get("areacode"))
                                .sigungu((String) tmp.get("sigungucode"))
                                .upmyundong((String) tmp.get("addr2"))
                                .latitude((String) tmp.get("mapy"))
                                .longitude((String) tmp.get("mapx"))
                                .fullAddress((String) tmp.get("addr1"))
                                .mLevel((String) tmp.get("mlevel"))
                                .build();
                        // createdtime과 modifiedtime 문자열을 가져옴
                        String createdTimeStr = (String) tmp.get("createdtime");
                        String modifiedTimeStr = (String) tmp.get("modifiedtime");

                        // 문자열을 LocalDateTime으로 변환
                        LocalDateTime createdTime = LocalDateTime.parse(createdTimeStr, DateTimeFormatter.ofPattern("yyyyMMddHH"));
                        LocalDateTime modifiedTime = LocalDateTime.parse(modifiedTimeStr, DateTimeFormatter.ofPattern("yyyyMMddHH"));

                        // restaurant 엔티티에 설정
                        restaurant.setCreatedAt(createdTime);
                        restaurant.setModifiedAt(modifiedTime);
                        restaurant.setContentId((String) tmp.get("contentid"));
                        restaurant.setContentTypeId((String) tmp.get("contenttypeid"));
                        restaurant.setName((String) tmp.get("title"));
                        restaurant.setHomePage((String) tmp.get("homepage"));
                        restaurant.setFirstImage((String) tmp.get("firstimage"));
                        restaurant.setFirstImage2((String) tmp.get("firstimage2"));
                        restaurant.setCat1((String) tmp.get("cat1"));
                        restaurant.setCat2((String) tmp.get("cat2"));
                        restaurant.setCat3((String) tmp.get("cat3"));
                        restaurant.setOverView((String) tmp.get("overview"));
                        restaurant.setTel((String) tmp.get("tel"));
                        restaurant.setCpyrgtDivCd((String) tmp.get("cpyrhtDivCd"));
                        restaurant.setLocation(location);
                        restaurantRepository.save(restaurant);
                    }
                }

                urlConnection.disconnect();

                pageNo++; // Increment pageNo to fetch the next batch
            } while (pageNo <= Math.ceil(Integer.parseInt(body.get("totalCount").toString()) / (double) numOfRows));
                pageNo = 1;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result + "";
    }
}


//    @GetMapping("apisave-accommodation")
//    public String accommodationSave() throws IOException {
//        String result = "";
//        try {
//            String urlStr = "http://apis.data.go.kr/B551011/KorService1/searchStay1?" +
//                    "serviceKey=vqoXwkq9RMCWANTOOUUOJVPQ%2FDtls8Z099FreqNacdFobJPBCviYv10hegz5KtPrVxci7OYYwEBNv%2ByS7hZ9%2Fw%3D%3D" +
//                    "&numOfRows=1000" +
//                    "&pageNo=1" +
//                    "&MobileOS=ETC" +
//                    "&MobileApp=AppTest" +
//                    "&_type=json" +
//                    "&listYN=Y" +
//                    "&arrange=A" +
//                    //"&contentTypeId=32" +
//                    "&areaCode=1";
//
//            URL url = new URL(urlStr);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
//            StringBuilder resultBuilder = new StringBuilder();
//            String line;
//            while ((line = br.readLine()) != null) {
//                resultBuilder.append(line + "\n");
//            }
//            result = resultBuilder.toString();
//            //log.info(result);
//
//            JSONParser jsonParser = new JSONParser();
//            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
//            JSONObject response = (JSONObject) jsonObject.get("response");
//            JSONObject body = (JSONObject) response.get("body");
//            JSONObject items = (JSONObject) body.get("items");
//            JSONArray itemArr = (JSONArray) items.get("item");
//            urlConnection.disconnect();
//            for (int i = 0; i < itemArr.size(); i++) {
//                JSONObject tmp = (JSONObject) itemArr.get(i);
//
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return result + "";
//    }

