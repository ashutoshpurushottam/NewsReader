package com.antho.newsreader.viewmodel;

import com.antho.newsreader.model.news.News;
import com.antho.newsreader.model.news.NewsThumbnail;
import com.antho.newsreader.model.popular.Popular;
import com.antho.newsreader.model.popular.PopularMedia;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.annotation.Nullable;

class TestUtility {

    public static List<News> getTestingNewsListOfSize(int size) {
        List<News> dummyList = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            News news = new News() {
                @Override
                public String title() {
                    return generateRandomString();
                }

                @Override
                public String section() {
                    return generateRandomString();
                }

                @Override
                public String subsection() {
                    return generateRandomString();
                }

                @Override
                public String url() {
                    return generateRandomString();
                }

                @Override
                public ZonedDateTime date() {
                    return ZonedDateTime.now();
                }

                @Nullable
                @Override
                public List<NewsThumbnail> multimedia() {
                    return null;
                }
            };

            dummyList.add(news);
        }

        return dummyList;
    }

    public static List<Popular> getTestingPopularListOfSize(int size) {
        List<Popular> dummyList = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            Popular popular = new Popular() {
                @Override
                public String title() {
                    return generateRandomString();
                }

                @Override
                public String section() {
                    return generateRandomString();
                }

                @Override
                public String url() {
                    return generateRandomString();
                }

                @Nullable
                @Override
                public String date() {
                    return null;
                }

                @Nullable
                @Override
                public List<PopularMedia> multimedia() {
                    return null;
                }
            };

            dummyList.add(popular);
        }

        return dummyList;
    }

    private static String generateRandomString() {
        return UUID.randomUUID().toString();
    }
}
