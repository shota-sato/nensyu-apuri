package com.example.nensyuapuri;


import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    public int money = 0;

    public int nensyu;
    public int kyuryo;
    public int sum;
    public int yatin;
    public int myhome;
    
    
/////////////////////////////////////////データベースに値を挿入　data.sql参照///////////////////////////////////////    
    @Autowired///////////////////////データベースjdbcを使用します
    private JdbcTemplate jdbc;
    
    @GetMapping("/h2")///////////→①　　h2に飛ぶときにデータベースにデータ保存 
    public String h2(){
    System.out.println(jdbc.queryForList("select * from person"));//コンソールに表示
        return "index";//
    }
///////////////////////////////////////////////////////////////////////////////////////////
    @Value("${app.name}")
    private String hoge;

/////////////////////////////////////////////////////////////////////////////////    
  public String plus(int age, Model model){
        jdbc.queryForList("SELECT * FROM person WHERE id = ?", age);
        
        return "index";
      }

///////////////////////////////////////////    
    /*index.html を/htmlで表示するメソッド*/
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    
    
    
    @GetMapping("/kakunin")
    public String kakunin(){
        return "kakunin";
    }

    @PostMapping("/hogeinfo")
    public String agedisplay(int age,int gessyuu,int tukityo, RedirectAttributes attr){
        jdbc.update("UPDATE person SET age = ?", age);
        jdbc.update("UPDATE person SET gessyuu = ?", gessyuu);
        jdbc.update("UPDATE person SET tukityo = ?", tukityo);
        
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        attr.addFlashAttribute("age", person.get("age"));
        attr.addFlashAttribute("gessyuu", person.get("gessyuu"));
        attr.addFlashAttribute("tukityo", person.get("tukityo"));
        return "redirect:/index";
    }
   
////////////////////////////////////////////////////////////////////////////////////
     
  
    @PostMapping("/hogemarry")
    public String post(String marryprice, int marrytrip, RedirectAttributes attr){
        if(marryprice.equals("sikire")){
            jdbc.update("UPDATE person SET marryprice = 195"); 
        }else if(marryprice.equals("sikise")){
            jdbc.update("UPDATE person SET marryprice = 260");  
        }else if(marryprice.equals("sikiho")){
            jdbc.update("UPDATE person SET marryprice = 260");  
        }else if(marryprice.equals("sikige")){
            jdbc.update("UPDATE person SET marryprice = 320");  
        }else if(marryprice.equals("sikino")){
            jdbc.update("UPDATE person SET marryprice = 0");  
        }else if(marryprice.equals("sikisu")){
            jdbc.update("UPDATE person SET marryprice = 0");  
        }

        jdbc.update("UPDATE person SET marrytrip = ?", marrytrip);
        

        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);

        attr.addFlashAttribute("marryprice", person.get("marryprice"));
        attr.addFlashAttribute("marrytrip", person.get("marrytrip"));
        
        person = jdbc.queryForList("SELECT * FROM person").get(0);
        jdbc.update("UPDATE person SET marrysum = ?", (int)person.get("marryprice")+(int)person.get("marrytrip"));
        person = jdbc.queryForList("SELECT * FROM person").get(0);
        attr.addFlashAttribute("marrysum", person.get("marrysum") );
        
        attr.addFlashAttribute("movePoint", "marriage");
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        
        return "redirect:/index";
    }

    @PostMapping("/hogegaku")
    public String gakudisplay(int child, String you, String syou, String tyuu, String kou, String dai, String in,  RedirectAttributes attr){
        //子供入力
        jdbc.update("UPDATE person SET child = ?", child);
        //幼稚園入力
        if(you.equals("yoko")){
            jdbc.update("UPDATE person SET you = 69"); 
        } else if(you.equals("yosi")){
            jdbc.update("UPDATE person SET you = 146");  
        }
        //小学校
        if(syou.equals("syoko")){
            jdbc.update("UPDATE person SET syou = 183"); 
        } else if(syou.equals("syosi")){
            jdbc.update("UPDATE person SET syou = 853");  
        }
        
        if(tyuu.equals("tyuuko")){
            jdbc.update("UPDATE person SET tyuu = 135"); 
        } else if(tyuu.equals("tyuusi")){
            jdbc.update("UPDATE person SET tyuu = 388");  
        }
        if(kou.equals("kouko")){
            jdbc.update("UPDATE person SET kou = 116"); 
        } else if(kou.equals("kousi")){
            jdbc.update("UPDATE person SET kou = 290");  
        }

        if(dai.equals("daikokuzi")){
            jdbc.update("UPDATE person SET dai = 457"); 
        }else if(dai.equals("daikokuhi")){
            jdbc.update("UPDATE person SET dai = 1002");  
        }else if(dai.equals("daisibuzi")){
            jdbc.update("UPDATE person SET dai = 675");  
        }else if(dai.equals("daisibuhi")){
            jdbc.update("UPDATE person SET dai = 1220");  
        }else if(dai.equals("daisirizi")){
            jdbc.update("UPDATE person SET dai = 818");  
        }else if(dai.equals("daisirihi")){
            jdbc.update("UPDATE person SET dai = 1362");  
        }else if(dai.equals("daisihazi")){
            jdbc.update("UPDATE person SET dai = 3068");  
        }else if(dai.equals("daisihahi")){
            jdbc.update("UPDATE person SET dai = 3468");  
        }else if(dai.equals("daino")){
            jdbc.update("UPDATE person SET dai = 0");  
        }
        
        if(in.equals("inkoku")){
            jdbc.update("UPDATE person SET in = 82"); 
        }else if(in.equals("insibu")){
            jdbc.update("UPDATE person SET in = 115");  
        }else if(in.equals("insiri")){
            jdbc.update("UPDATE person SET in = 149");  
        }else if(in.equals("insiha")){
            jdbc.update("UPDATE person SET in = 466");  
        }else if(in.equals("inno")){
            jdbc.update("UPDATE person SET in = 0");  
        }
        
    
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        attr.addFlashAttribute("child", person.get("child"));
        attr.addFlashAttribute("you", person.get("you"));
        attr.addFlashAttribute("syou", person.get("syou"));
        attr.addFlashAttribute("tyuu", person.get("tyuu"));
        attr.addFlashAttribute("kou", person.get("kou"));
        attr.addFlashAttribute("dai", person.get("dai"));
        attr.addFlashAttribute("in", person.get("in"));
        

        attr.addFlashAttribute("gakusum", (int)person.get("child")*((int)person.get("you")+(int)person.get("syou")+(int)person.get("tyuu")+(int)person.get("kou")+(int)person.get("dai")));
        jdbc.update("UPDATE person SET gakusum = ?",(int)person.get("child")*((int)person.get("you")+(int)person.get("syou")+(int)person.get("tyuu")+(int)person.get("kou")+(int)person.get("dai")));

        attr.addFlashAttribute("movePoint", "education");
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        return "redirect:/index";
  
    }

    
    @GetMapping("/form31")
    public String nannengodisplay(int nannengo, Model model) {
        // 一行目のchildをupdate
        jdbc.update("UPDATE person SET nannengo = ?", nannengo);
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        //personのchild(get(0))を取得
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        // 取得したageをhtmlに表示　to model
        model.addAttribute("nannengo", person.get("nannengo"));

        return "index"; //kakuninn だと飛ぶ　結果も表示される
    }
    
//テキスト質問3-2   yatin 
    @GetMapping("/form32")
    public String yatindisplay(int yatin, Model model) {
        // 一行目のchildをupdate
        jdbc.update("UPDATE person SET yatin = ?", yatin);
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        //personのchild(get(0))を取得
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        // 取得したageをhtmlに表示　to model
        model.addAttribute("yatin", person.get("yatin"));

        return "index"; //kakuninn だと飛ぶ　結果も表示される
    }
    

//テキスト質問3-3  myhome  
    @PostMapping("/hoge33")
    public String myhomedisplay(int myhome, RedirectAttributes attr) {
        // 一行目のchildをupdate
        jdbc.update("UPDATE person SET myhome = ?", myhome);
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        attr.addFlashAttribute("myhome", (int)person.get("myhome"));
        attr.addFlashAttribute("movePoint", "house");
        return "redirect:/index";
    }
   
 
 
    @PostMapping("/hoge310")
    public String housesumdisplay(int housesum, RedirectAttributes attr) {
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        jdbc.update("UPDATE person SET housesum = ?", (int)person.get("myhome"));
        person = jdbc.queryForList("SELECT * FROM person").get(0);
        attr.addFlashAttribute("housesum", (int)person.get("housesum"));
        attr.addFlashAttribute("movePoint", "house");
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        return "redirect:/index";
    }
   
    
    
    
    
    
    public int myhome1;
    public int myhome2;
    public int myhome3=myhome1+myhome2;
    

  
    

    
//テキスト質問4-1  carprice  
   @GetMapping("/form41")
    public String carpricedisplay(int carprice, Model model) {
        // 一行目のchildをupdate
        jdbc.update("UPDATE person SET carprice = ?", carprice);
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        //personのchild(get(0))を取得
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        // 取得したageをhtmlに表示　to model
        model.addAttribute("carprice", person.get("carprice"));

        return "index"; //kakuninn だと飛ぶ　結果も表示される
    }
    
   
    
    //kekkaにnensyu+kyuryo=moneyを設定
 @GetMapping("/form100")
            public String sumdisplay(int yatin, int myhome, Model model) {
                sum = yatin + myhome;
            return "index";
            }
        
 @PostMapping("/hoge1000")
     public String totalsumdisplay(int totalsum, RedirectAttributes attr){
         Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
         jdbc.update("UPDATE person SET totalsum = ?", (int)person.get("housesum")+(int)person.get("marrysum")+(int)person.get("gakusum"));
         person = jdbc.queryForList("SELECT * FROM person").get(0);
         attr.addFlashAttribute("totalsum", (int)person.get("totalsum"));
         System.out.println(jdbc.queryForList("SELECT * FROM person"));
     return "redirect:/kekka";
     }
 
 @PostMapping("/hoge2000")
 public String risoudisplay(int risou, RedirectAttributes attr){
     Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
     jdbc.update("UPDATE person SET risou = ?", ((((int)person.get("totalsum")/(65-(int)person.get("age")))/12-(int)person.get("tukityo"))+(int)person.get("gessyuu"))*12);
     person = jdbc.queryForList("SELECT * FROM person").get(0);
     attr.addFlashAttribute("risou", (int)person.get("risou"));
     System.out.println(jdbc.queryForList("SELECT * FROM person"));
 return "redirect:/kekka";
 }
 
 @Autowired
 private KeisanDao keisanDao;

    @GetMapping("/test")
    public String test(){
         System.out.println(keisanDao.findAll());
         System.out.println(keisanDao.findByAge(25));
         return "";
    }
    
    @GetMapping("/kekka")
    public String result(){
        return "kekka";
    }

}
 

