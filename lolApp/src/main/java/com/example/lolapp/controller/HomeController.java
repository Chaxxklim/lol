package com.example.lolapp.controller;

import com.example.lolapp.vo.MatchVO;
import com.example.lolapp.vo.SummonerVO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class HomeController {
    private final static String API_KEY = "RGAPI-2c23a3d8-abc6-42af-8b8c-fc1a8a6794d4";

    @RequestMapping("/index")
    public String index(){

        return "webapp/views/index";
    }

    @RequestMapping("/test")
    public String test(){

        return "webapp/views/test";
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchKeyword, Model model) {
        ObjectMapper objectMapper = new ObjectMapper();
        Gson gson = new Gson();
        SummonerVO summonerVO = new SummonerVO();
        List<MatchVO> matchVOList = new ArrayList<>();

        String requestURL = "";


        String SummonerName = searchKeyword.replaceAll(" ", "%20");
        requestURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + SummonerName + "?api_key=" + API_KEY;
        String tftURL = "/tft/match/v1/matches/by-puuid/{puuid}/ids";
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);

            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                summonerVO = objectMapper.readValue(body, SummonerVO.class);
                // String to Object로 변환
                model.addAttribute("summonerVO", summonerVO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            requestURL = "https://asia.api.riotgames.com/tft/match/v1/matches/by-puuid/" +
                    summonerVO.getPuuid() + "/ids?count=20&api_key=" + API_KEY;
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);

            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                List<String> matchList = new ArrayList<>();
                String replace1 = body.replace("[", "");
                String replace2 = replace1.replace("]", "");
                String finalReplace = replace2.replace("\"", "");
                String[] split = finalReplace.split(",");

                matchList = Arrays.asList(split);

                for (String str : matchList) {
                    try {
                        MatchVO matchVO = new MatchVO();
                        requestURL = "https://asia.api.riotgames.com/tft/match/v1/matches/" +
                                str + "?api_key=" + API_KEY;
                        client = HttpClientBuilder.create().build();
                        getRequest = new HttpGet(requestURL);
                        response = client.execute(getRequest);
                        if (response.getStatusLine().getStatusCode() == 200) {
                            handler = new BasicResponseHandler();
                            body = handler.handleResponse(response);
                            objectMapper = new ObjectMapper();
                            matchVO = objectMapper.readValue(body, MatchVO.class);
                            matchVOList.add(matchVO);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(matchVOList);
        return "webapp/views/searchResult";
    }
}
