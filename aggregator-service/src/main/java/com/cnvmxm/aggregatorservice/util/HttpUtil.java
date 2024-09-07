package com.cnvmxm.aggregatorservice.util;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

public class HttpUtil {

    public static byte[] downloadFile(String url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                byte[] fileBytes = responseEntity.getBody();
                if (fileBytes == null) {
                    throw new IOException("Не удалось загрузить файл: пустой ответ");
                }
                return fileBytes;
            } else {
                throw new IOException("Не удалось загрузить файл: " + responseEntity.getStatusCode());
            }
        } catch (RestClientException e) {
            throw new IOException("Не удалось загрузить файл: ошибка сети", e);
        }
    }
}
