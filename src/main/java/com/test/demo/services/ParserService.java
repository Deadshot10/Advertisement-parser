package com.test.demo.services;

import com.google.gson.JsonObject;
import com.test.demo.Advertisement;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;

import static com.test.demo.Util.log;

@Service
public class ParserService {

    private static final String BASE_URL = "https://www.doska.ru";

    @Autowired
    AdsMaintenanceService adsMaintenanceService;

    private Document mainPage;

    public ParserService() {

    }

    public boolean connect(){
        try {
            mainPage = Jsoup.connect(BASE_URL).get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void startParse(){
        try {
            Elements categories = mainPage.select("a[id^='mtd_']");
            for (Element category: categories) {
                //category url (name) used to check is already parsed
                String categoryLink = category.attr("href");

                Document categoryPage = Jsoup.connect(BASE_URL + categoryLink).get();

                parsePage(categoryPage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parsePage(Document page) throws IOException {
        log("Start parse: ", page.location());
        //CHECK WHAT WE HAVE - SUBCATEGORY OR GOODS LISTS
        Elements subCategories = page.select("[id^='sc_']");
        Elements items = page.select("[id^='tr_']");
        if (items.size() != 0){
            for (Element item: items){
                String uri = item.select(".msga2>a").attr("href");
                if (!adsMaintenanceService.itemExists(uri))
                    adsMaintenanceService.addAdvertisement(slurpItemToObject(item));
            }
        } else {
            if (subCategories.size() != 0) {
                for (Element subCategory : subCategories) {
                    String subCategoryLink = subCategory.attr("href");

                    Connection connection = Jsoup.connect(BASE_URL + subCategoryLink);
                    connection.timeout(120000);//java.net.SocketException: Connection reset
                    Document subCategoryPage = connection.get();
                    parsePage(subCategoryPage);
                }
            }
        }
    }

    private Advertisement slurpItemToObject(Element element) throws IOException {
        Advertisement advertisement = new Advertisement();
        //Attributes from preview
        advertisement.region = element.select("div.ads_region").text();
        advertisement.uri = element.select(".msga2>a").attr("href");

        Document itemPage = Jsoup.connect(BASE_URL + advertisement.uri).get();
        //Attributes from item page
        advertisement.description = itemPage.select("div#msg_div_msg").text();
        advertisement.price = itemPage.select("span#tdo_8").text();

        Elements categoryLevels = itemPage.select(".headtitle>a");
        StringBuilder categorySB = new StringBuilder("/");
        for (Element catLvl : categoryLevels) {
            categorySB.append(catLvl.text()).append("/");
        }
        advertisement.category = categorySB.toString();

        advertisement.options = new JsonObject();
        Elements options = itemPage.select("td[id^='tdo_']");
        for (Element option: options){
            advertisement.options.addProperty(
                    option.siblingElements().get(0).text(), //key
                    option.text()                           //value
            );
        }

        return advertisement;
    }


    private static int count = 5;
    private static void BREAKING_THE_WALL () throws Exception {
        if (count > 0) {
            count--;
        } else {
            throw new Exception();
        }
    }
}
