package com.cosmos.controller;

import com.cosmos.entity.*;
import com.cosmos.repository.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository repo;
    @Autowired
    private CategoryRepository category_repo;
    @Autowired
    private ManufactureRepository manufacture_repo;
    @Autowired
    private FragranceRepository fragrance_repo;
    @Autowired
    private ImageRepository image_repo;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public static String uploadDirectory = System.getProperty("user.dir")+"src/main/resources/images/product";
    public static String staticDirectory = System.getProperty("user.dir")+"src/main/resources/static";

    public static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));


//    @CrossOrigin
//    @GetMapping
//    public List<Product> getListProduct(){
//        return repo.findAll();
//    }

    @CrossOrigin
    @GetMapping
    public List<Product> getListProduct(){
        try {
            List<Product> list = repo.findAll();
            for(Product item:list) {
                List<Fragrance> lf = item.getFragrances();
                List<Image> li = image_repo.getImageByIdProduct(item.getId());
                item.setImages(li);
                item.setFragrances(lf);
            }
            //return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
            return list;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
        return null;
    }

    @CrossOrigin
    @GetMapping("/get-by-category")
    public List<Product> getListProductByIdCategory(@RequestParam Integer categoryId){
        try {
            List<Product> list = repo.getProductByIdCategory(categoryId);
            for(Product item:list) {
                List<Fragrance> lf = item.getFragrances();
                List<Image> li = image_repo.getImageByIdProduct(item.getId());
                item.setImages(li);
                item.setFragrances(lf);
            }
            //return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
            return list;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
        return null;
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable("id") Integer id) {
        return repo.findById(id);
    }

    @CrossOrigin
    @GetMapping("/name={name}")
    public Product getProductByName(@PathVariable("name") String n) {
        return repo.getProductByName(n);
    }

    @CrossOrigin
    @GetMapping("/manufacture={id}")
    public List<Product> getProductByManufactureId(@PathVariable("id") Integer id) {
        return repo.getProductByIdManufacture(id);
    }

    @CrossOrigin
    @GetMapping("{id}/listfragrance")
    public List<Fragrance> getListHuong(@PathVariable("id") Integer id){
        Product sp = repo.getById(id);
        List<Fragrance> l = new ArrayList<>();
        l = sp.getFragrances();
        return l;
    }

    @CrossOrigin
    @GetMapping("{id}/list-image")
    public List<Image> getListImage(@PathVariable("id") Integer id){
        // Product sp = repo.getById(id);
        List<Image> l = new ArrayList<>();
        l = image_repo.getImageByIdProduct(id);
        return l;
    }

//    @GetMapping("{id}/listimage")
//    public List<Image> getListImage(@PathVariable("id") Integer id){
//        Product sp = repo.getById(id);
//        System.out.println("\nmmmmmmmmmmmmmmmmmmmmmmmmmmmm: "+sp.getImages()+"\n");
//        List<Image> l = new ArrayList<>();
//        l = sp.getImages();
//        return l;
//    }

    public String randomString() {
        //String key_number = "M";
        int string_length = 8;
        String stringi = "image" ;
        String possible ="abcdefghijklmnopqrstuvwxyz123456789";

        for (int i = 0; i < string_length; i++)
            stringi = stringi + possible.charAt((int) Math.floor(Math.random() * possible.length()));

        return stringi;
    }
    public  Boolean checkInArray(List<Image> l, int id){
        for(Image item: l){
            if(item.getId() == id)
                return true;
        }
        return false;
    }

    @CrossOrigin
    @PostMapping
    @ResponseBody
    public ResponseEntity<String> creatNewProduct(@Validated @RequestParam String name,
                                                  @RequestParam Integer categoryId,
                                                  @RequestParam Integer manufactureId,
                                                  @RequestParam List<Integer> idFragranceList,
                                                  @RequestParam Integer capacity,
                                                  @RequestParam Integer price,
                                                  @RequestParam String description,
                                                  @RequestParam MultipartFile[] images
    ) throws IOException {
        try {
            //category
            Category category = category_repo.getById(categoryId);
            //manufacture
            Manufacture manufacture = manufacture_repo.getById(manufactureId);
            //fragrances
            List<Fragrance> fragrancelist = new ArrayList<>();
            for(int item: idFragranceList){
                Fragrance h = fragrance_repo.getById(item);
                fragrancelist.add(h);
            }

            Product product = new Product();
            product.setName(name);
            product.setCategory(category);
            product.setManufacture(manufacture);
            product.setFragrances(fragrancelist);
            product.setCapacity(capacity);
            product.setDescription(description);
            product.setStatus(1);
            product.setPrice(price);
            product.setCost(0);
            product.setNumber(0);
            //product.setImages(imageList);
            //product.setImg(imagesPath.resolve(projectPath).resolve(imgname).toString());
            repo.save(product);

            //images
            Path staticPath = Paths.get("static");
            Path imagesPath = Paths.get("images");
            Path projectPath = Paths.get("product");
            Path m = CURRENT_FOLDER.resolve(staticPath).resolve(imagesPath).resolve(projectPath);

            //storge in there need rebuil project -> urlimg active
            //Path m = CURRENT_FOLDER.resolve(srcPath).resolve(mainPath).resolve(resourcesPath).resolve(staticPath).resolve(imagesPath).resolve(projectPath);

            if (!Files.exists(m)) {
                Files.createDirectories(m);
            }
            List<Image> imageList = new ArrayList<>();
            for (MultipartFile item: images){
                //create name + save img to project
                String imgname = randomString() + item.getOriginalFilename();
                Path imgPath = m.resolve(imgname);
                try (OutputStream os = Files.newOutputStream(imgPath)) {
                    os.write(item.getBytes());
                }
                //save img to Image Entity
                Image img = new Image();
                img.setProduct(product);
                img.setPath(imagesPath.resolve(projectPath).resolve(imgname).toString());
                image_repo.save(img);

                imageList.add(img);
            }

            return new ResponseEntity<String>("successed !!!", HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("failed !!!", HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> updateCategory(@Validated @RequestParam Integer id,
                                                 @RequestParam String name,
                                                 @RequestParam Integer categoryId,
                                                 @RequestParam Integer manufactureId,
                                                 @RequestParam List<Integer> idFragranceList,
                                                 @RequestParam Integer capacity,
                                                 @RequestParam String description,
                                                 @RequestParam List<Integer> idImagesDefaultNew,
                                                 @RequestParam(required=false) MultipartFile[] imagesAdd
    )  throws IOException{
        try {
            Product p_edit = repo.getById(id);

            //category
            Category category = category_repo.getById(categoryId);
            //manufacture
            Manufacture manufacture = manufacture_repo.getById(manufactureId);
            //fragrances
            List<Fragrance> newFragrancelist = new ArrayList<>();
            for(int item: idFragranceList){
                Fragrance h = fragrance_repo.getById(item);
                newFragrancelist.add(h);
            }

            //images default
            List<Image> imagesDefaultNew = new ArrayList<>();
            for(Integer idimg: idImagesDefaultNew){
                imagesDefaultNew.add(image_repo.getById(idimg));
            }
            //System.out.println("imagesDefaultNew: "+imagesDefaultNew);

            //list img delete
            List<Image> imagesdelete = new ArrayList<>();
            for(Image img: p_edit.getImages()){
                if( checkInArray(imagesDefaultNew, img.getId() )== false) {
                    imagesdelete.add(image_repo.getById(img.getId()));
                }
            }
            //System.out.println("imagesdelete: "+imagesdelete);

            for(Image im: imagesdelete){
                image_repo.deleteById(im.getId());
                Path staticPath = Paths.get("static");
                //Path imagesPath = Paths.get("images");
                //Path projectPath = Paths.get("product");
                Path m = CURRENT_FOLDER.resolve(staticPath);
                Path imgPath = m.resolve(im.getPath());
                boolean fileDeleted = Files.deleteIfExists(imgPath);
                if (fileDeleted) {
                    System.out.println("File deleted");
                } else {
                    System.out.println("File does not exist");
                }
            }

            //img add
            if(imagesAdd != null){
                Path staticPath = Paths.get("static");
                Path imagesPath = Paths.get("images");
                Path projectPath = Paths.get("product");
                Path m = CURRENT_FOLDER.resolve(staticPath).resolve(imagesPath).resolve(projectPath);
                if (!Files.exists(m)) {
                    Files.createDirectories(m);
                }
                List<Image> imageList = new ArrayList<>();
                for (MultipartFile item: imagesAdd){
                    //create name + save img to project
                    String imgname = randomString() + item.getOriginalFilename();
                    Path imgPath = m.resolve(imgname);
                    try (OutputStream os = Files.newOutputStream(imgPath)) {
                        os.write(item.getBytes());
                    }
                    //save img to Image Entity
                    Image img = new Image();
                    img.setProduct(p_edit);
                    img.setPath(imagesPath.resolve(projectPath).resolve(imgname).toString());
                    image_repo.save(img);

                    imageList.add(img);
                    imagesDefaultNew.add(img);//////////
                }
            }
            List<Product> list = repo.findAll();
            for(Product item:list) {
                if(item.getId() == id) {
                    item.setName(name);
                    item.setCategory(category);
                    item.setManufacture(manufacture);
                    item.setFragrances(newFragrancelist);
                    item.setCapacity(capacity);
                    item.setDescription(description);
                    item.setImages(imagesDefaultNew);

                    repo.save(item);
                    //repo.save(update);
                    return new ResponseEntity<String>("Successed" , HttpStatus.OK);
                }
            }
            return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
    }

//    @CrossOrigin
//    @PutMapping
//    public ResponseEntity<String> updateCategory(@Validated @RequestBody Product update){
//        try {
//            List<Product> list = repo.findAll();
//            for(Product item:list) {
//
//                if(item.getId() == update.getId()) {
//                    repo.save(update);
//                    return new ResponseEntity<String>("Successed" , HttpStatus.OK);
//                }
//            }
//            return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
//    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer id){
        try {
            repo.deleteById(id);
            return new ResponseEntity<String>("successed !!!" , HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
    }
    @CrossOrigin
    @GetMapping("/b")
    public List<Product> getListProductB(){ //ds san pham ban nhieu nhat
        try {
            List<Orders> orders = orderRepository.findAllByStatus();
            List<Product> products = repo.findAll();

            HashMap<Integer, Integer> list = new HashMap<>();
            for (Product product : products) {
                list.put(product.getId(), 0);
            }

            for (Orders order : orders) {
                List<Order_Detail> orderDetailList = orderDetailRepository.findAllByOrderId(order.getId());
                for (Order_Detail order_detail : orderDetailList) {
                    for (Integer key : list.keySet()) {
                        if (key.equals(order_detail.getProduct().getId())) {
                            list.put(key, list.get(key) + order_detail.getNumber());
                        }
                    }
                }
            }
            List<Product> productAfterSort = new ArrayList<>();
            Set<Map.Entry<Integer, Integer>> entries = list.entrySet();
            Comparator<Map.Entry<Integer, Integer>> comparator1 = new Comparator<Map.Entry<Integer, Integer>>() {
                @Override
                public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {

                    Integer v1 = o1.getValue();
                    Integer v2 = o2.getValue();
                    return v2.compareTo(v1);

                }
            };

            List<Map.Entry<Integer, Integer>> listEntries = new ArrayList<>(entries);

            Collections.sort(listEntries, comparator1);

            LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>(listEntries.size());
            for (Map.Entry<Integer, Integer> entry : listEntries) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
            Set<Map.Entry<Integer, Integer>> sortedEntries = sortedMap.entrySet();

            for (Map.Entry<Integer, Integer> mapping : sortedEntries) {
                Product product = repo.findById(mapping.getKey())
                        .orElseThrow(() -> new NotFoundException("product-not-found"));
                productAfterSort.add(product);
            }
            for(Product item:productAfterSort) {
                List<Fragrance> lf = item.getFragrances();
                List<Image> li = image_repo.getImageByIdProduct(item.getId());
                item.setImages(li);
                item.setFragrances(lf);
            }
            //return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
            return productAfterSort;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //return new ResponseEntity<String>("failed !!!" , HttpStatus.BAD_REQUEST);
        return null;
    }

}
