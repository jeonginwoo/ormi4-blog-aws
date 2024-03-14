package com.estsoft.blogjpa.external;

import com.estsoft.blogjpa.dto.ArticleRequest;
import com.estsoft.blogjpa.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Component
public class ExternalApiParser {
    private final BlogService blogService;

    public ExternalApiParser(BlogService blogService) {
        this.blogService = blogService;
    }

    // 외부 API 호출 -> json 받아오기
    public void parserAndSave() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/posts";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("body: {}", response.getBody());
            List<LinkedHashMap<String, Object>> list = response.getBody();

            // title, body만 필요
            List<ArticleRequest> insertList = new ArrayList<>();
            for (LinkedHashMap<String, Object> map : list) {
                String title = (String) map.get("title");
                String content = (String) map.get("body");

                // todo : parser, save 코드 분리
                blogService.save(new ArticleRequest(title, content));
            }
        }
    }
}
