package Hoang;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.http.HttpClient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.http.HttpResponse;

import Hoang.basis.CategoryFinder;
import Hoang.basis.PageFinder;
import Hoang.basis.infobox.DynastyInfoboxExtractor;
import Hoang.basis.infobox.InfoboxExtractor;
import Hoang.crawler.BaseCrawler;
import Hoang.crawler.DynastyCrawler;
import Hoang.entity.Dynasty;
import Hoang.util.ResultUtil;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class App {
    public static void main(String[] args) {
		HttpClient client = HttpClient.newHttpClient();
		BaseCrawler crawler = new DynastyCrawler(client);
		
		crawler.crawl();
    }
}
