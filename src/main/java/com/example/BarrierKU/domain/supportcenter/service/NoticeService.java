package com.example.BarrierKU.domain.supportcenter.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.supportcenter.dto.NoticeResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.BarrierKU.common.response.ResponseCode.NOTICE_CRAWL_FAILED;

@Service
@Transactional(readOnly = true)
public class NoticeService {

    public List<NoticeResponse> getNotices() {
        List<NoticeResponse> notices = new ArrayList<>();
        String siteUrl = "https://www.konkuk.ac.kr/csd/15238/subview.do?enc=Zm5jdDF8QEB8JTJGYmJzJTJGY3NkJTJGNTM1JTJGYXJ0Y2xMaXN0LmRvJTNG";

        try {
            Document doc = Jsoup.connect(siteUrl).get();
            Elements items = doc.select("tbody > tr:not(.notice)"); // 고정 공지 제외

            for (int i = 0; i < 3; i++) {
                Element item = items.get(i);

                String title = item.select("td.td-subject a").text();
                String date = item.select("td.td-date").text();
                String url =  item.selectFirst("td.td-subject a").absUrl("href");
                int number = Integer.parseInt(item.select("td.td-num").text());

                notices.add(new NoticeResponse(title, date, url, number));
            }

        } catch (IOException e) {
            throw new BarrierKuException(NOTICE_CRAWL_FAILED);
        }

        return notices;
    }
}
