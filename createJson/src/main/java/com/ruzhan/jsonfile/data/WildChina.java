package com.ruzhan.jsonfile.data;

import com.ruzhan.jsonfile.model.Introduce;
import com.ruzhan.jsonfile.model.Movie;
import com.ruzhan.jsonfile.model.MovieDetail;
import com.ruzhan.jsonfile.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruzhan123 on 2018/7/3.
 */
public class WildChina {

    private static final int ID = 2603;
    private static final String PLAY_COUNT = "5";

    private static final String TITLE = "美丽中国";
    private static final String TAG = "野外·纪录片";

    private static final String IMAGE = "https://raw.githubusercontent.com/ruzhan123/Lion/master/json/api/image/wild-china.jpg";

    private static final String DESC = "一部有关中国自然主题的6集自然纪录片。";

    public static final Movie movie = new Movie();
    public static final MovieDetail movieDetail = new MovieDetail();

    private static final List<Introduce> introduceList = new ArrayList<>();
    private static final List<Video> videoList = new ArrayList<>();

    static {
        introduceList.add(new Introduce(Introduce.TEXT,
                DESC, ""));

        introduceList.add(new Introduce(Introduce.IMAGE, "",
                "https://raw.githubusercontent.com/ruzhan123/Lion/master/json/api/image/wild-china01.jpg"));

        introduceList.add(new Introduce(Introduce.TEXT,
                "该片以新颖的视角、独特的叙事结构和出色的拍摄，展示了中国山河风光日益秀美，人与自然和谐共处。", ""));

        introduceList.add(new Introduce(Introduce.TEXT,
                "2008年北京奥运会给BBC自然历史部提供了一次机会去制作首部关于中国自然历史的系列片。同时随着奥运会的举办，中国官方也希望能够吸引更多的游客。", ""));

        introduceList.add(new Introduce(Introduce.TEXT,
                "2005年BBC制作组得到官方许可，与中视传媒联合拍摄。这也是CCTV首度与BBC自然历史频道进行合作。", ""));

        introduceList.add(new Introduce(Introduce.TEXT,
                "制片人希望通过该系列片能改变东西方观念：我们希望中国人为他们的田园山水和野生动物感到骄傲，并致力于对其的保护。同时我们也希望能够改变西方媒体上有关中国环境负面观点的渲染。", ""));

        videoList.add(new Video(String.valueOf(ID + 11),
                "1、美丽中国之一", IMAGE, PLAY_COUNT, 1, "第一集",
                "http://player.bilibili.com/player.html?aid=10475049&cid=17298484&page=1"));

        videoList.add(new Video(String.valueOf(ID + 12),
                "2、美丽中国之二", IMAGE, PLAY_COUNT, 2, "第二集",
                "http://player.bilibili.com/player.html?aid=10475049&cid=17298485&page=2"));

        videoList.add(new Video(String.valueOf(ID + 13),
                "3、美丽中国之三", IMAGE, PLAY_COUNT, 3, "第三集",
                "http://player.bilibili.com/player.html?aid=10475049&cid=17298486&page=3"));

        videoList.add(new Video(String.valueOf(ID + 14),
                "4、美丽中国之四", IMAGE, PLAY_COUNT, 4, "第四集",
                "http://player.bilibili.com/player.html?aid=10475049&cid=17298482&page=4"));

        videoList.add(new Video(String.valueOf(ID + 15),
                "5、美丽中国之五", IMAGE, PLAY_COUNT, 5, "第五集",
                "http://player.bilibili.com/player.html?aid=10475049&cid=17298483&page=5"));

        movie.id = String.valueOf(ID);
        movie.title = TITLE;
        movie.tag = TAG;
        movie.desc = DESC;
        movie.image = IMAGE;

        movieDetail.id = String.valueOf(ID + 3);
        movieDetail.movieId = String.valueOf(ID);
        movieDetail.title = TITLE;
        movieDetail.tag = TAG;
        movieDetail.desc = DESC;
        movieDetail.image = IMAGE;

        movieDetail.introduces = new ArrayList<>();
        movieDetail.introduces.addAll(introduceList);

        movieDetail.videos = new ArrayList<>();
        movieDetail.videos.addAll(videoList);
    }
}
