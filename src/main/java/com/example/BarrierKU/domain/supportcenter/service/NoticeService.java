package com.example.BarrierKU.domain.supportcenter.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.common.response.ResponseCode;
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

@Service
@Transactional(readOnly = true)
public class NoticeService {

    public List<NoticeResponse> getNotices() {
        List<NoticeResponse> notices = new ArrayList<>();
        String siteUrl = "https://www.konkuk.ac.kr/sites/csd/index.do";

        try {
            Document doc = Jsoup.connect(siteUrl).get();
            Elements items = doc.select("div.list ul li a.subject");

            for (int i = 0; i < 3; i++) {
                Element item = items.get(i);

                String title = item.select(".subjectText span").text();
                String date = item.select(".date").text();
                String url = item.absUrl("href");

                notices.add(new NoticeResponse(title, date, url));
            }

        } catch(IOException e) {
            throw new BarrierKuException(ResponseCode.NOTICE_CRAWL_FAILED);
        }

        return notices;
    }
}
