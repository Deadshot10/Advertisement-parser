package com.test.demo.services;

import com.test.demo.Advertisement;
import com.test.demo.entity.Category;
import com.test.demo.entity.Item;
import com.test.demo.entity.Region;
import com.test.demo.repositories.CategoryRepository;
import com.test.demo.repositories.ItemRepository;
import com.test.demo.repositories.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedDeque;

import static com.test.demo.Util.log;


@Service
public class AdsMaintenanceService {

    private final ItemRepository itemRepository;
    private final RegionRepository regionRepository;
    private final CategoryRepository categoryRepository;

    private ConcurrentLinkedDeque<Advertisement> advertisements;

    AdsMaintenanceService(ItemRepository itemRepository,
                          RegionRepository regionRepository,
                          CategoryRepository categoryRepository){
        this.itemRepository = itemRepository;
        this.regionRepository = regionRepository;
        this.categoryRepository = categoryRepository;
        this.advertisements = new ConcurrentLinkedDeque<>();
    }

    public boolean itemExists(String uri){
        return (itemRepository.findOneItemByUri(uri) != null);
    }

    public Iterable<Region> getRegions(){
        return regionRepository.findAll();
    }

    public Iterable<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Region getRegionByName(String name){
        Region region = regionRepository.findOneRegionByName(name);
        if (region == null) {
            region = new Region();
            region.name = name;
            regionRepository.save(region);
        }
        return region;
    }

    public Category getCategoryByName(String name){
        Category category = categoryRepository.findOneCategoryByName(name);
        if (category == null) {
            category = new Category();
            category.name = name;
            categoryRepository.save(category);
        }
        return category;
    }


    public void addAdvertisement(Advertisement ad){
        advertisements.add(ad);
        notifyWriter();
    }
    private volatile Thread writerThread;

    public void notifyWriter(){
        if (writerThread == null || writerThread.getState() == Thread.State.TERMINATED) {
            writerThread = new Thread(writer);
            writerThread.start();
        }
    }

    private Runnable writer = new Runnable() {
        @Override
        public void run() {
            while (true) {
                while (!advertisements.isEmpty()) {
                    Advertisement advertisement = advertisements.remove();

                    Item newItem = new Item();
                    newItem.description = advertisement.description;
                    newItem.price = advertisement.price;
                    newItem.uri = advertisement.uri;
                    newItem.options_map_json = advertisement.options.toString();
                    newItem.category = getCategoryByName(advertisement.category);
                    newItem.region = getRegionByName(advertisement.region);
                    try {
                        itemRepository.save(newItem);
                    } catch (Exception e) {
                        //catch unique index or primary key violation
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (advertisements.isEmpty()) break;
            }
        }
    };
}
