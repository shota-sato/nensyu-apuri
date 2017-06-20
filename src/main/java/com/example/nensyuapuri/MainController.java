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

/**年齢、月収、月の貯金額を入力
 * 
 * 
 * 
 * @author shotsato
 *
 */
public class MainController {

/*    public int money = 0;

    public int nensyu;
    public int kyuryo;
    public int sum;
    public int yatin;
    public int myhome;
*/    
    
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

    //ユーザーの金銭入力
    @PostMapping("/hogeinfo")
    
    public String agedisplay(Userform form, RedirectAttributes attr){
        
        if(form.getGender().equals("man")){
            jdbc.update("UPDATE person SET endage = 80"); 
        } else if(form.getGender().equals("woman")){
            jdbc.update("UPDATE person SET endage = 87");  
        }
        System.out.println(form);
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        
        jdbc.update("UPDATE person SET age = ?", form.getAge());
        jdbc.update("UPDATE person SET gessyuu = ?", form.getGessyuu());
        jdbc.update("UPDATE person SET tukityo = ?", form.getTukityo());
        person = jdbc.queryForList("SELECT * FROM person").get(0);
        jdbc.update("UPDATE person SET yatin = ?", 12*((int)person.get("endage")-form.getAge())*(form.getGessyuu()-form.getTukityo()));
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        
//        attr.addFlashAttribute("age", person.get("age"));
//        attr.addFlashAttribute("gessyuu", person.get("gessyuu"));
//        attr.addFlashAttribute("tukityo", person.get("tukityo"));
        
         attr.addFlashAttribute("movePoint", "car");
        return "redirect:/index";
    }
   
////////////////////////////////////////////////////////////////////////////////////
     
    @PostMapping("/hogecar")
    public String cardisplay(String syasyu, int carprice, int carhavestart, int carhaveend, RedirectAttributes attr) {
        
        if(syasyu.equals("kei")){
            jdbc.update("UPDATE person SET syasyu = 33"); 
        }else if(syasyu.equals("kogata")){
            jdbc.update("UPDATE person SET syasyu = 41");  
        }else if(syasyu.equals("hutuu")){
            jdbc.update("UPDATE person SET syasyu = 46");  
        }else if(syasyu.equals("nocar")){
            jdbc.update("UPDATE person SET syasyu = 0");  
        }
        jdbc.update("UPDATE person SET carhavestart = ?", carhavestart);
        jdbc.update("UPDATE person SET carhaveend = ?", carhaveend);
        jdbc.update("UPDATE person SET carprice = ?", carprice);
               
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        jdbc.update("UPDATE person SET carsum = ?", ((int)person.get("carhaveend")-(int)person.get("carhavestart"))*(int)person.get("syasyu")+(int)person.get("carprice"));

        person = jdbc.queryForList("SELECT * FROM person").get(0);
//        attr.addFlashAttribute("syasyu", (int)person.get("syasyu"));
//        attr.addFlashAttribute("carprice", (int)person.get("carprice"));
//        attr.addFlashAttribute("carsum", (int)person.get("carsum"));

        attr.addFlashAttribute("movePoint", "education");
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
//        attr.addFlashAttribute("child", person.get("child"));
//        attr.addFlashAttribute("you", person.get("you"));
//        attr.addFlashAttribute("syou", person.get("syou"));
//        attr.addFlashAttribute("tyuu", person.get("tyuu"));
//        attr.addFlashAttribute("kou", person.get("kou"));
//        attr.addFlashAttribute("dai", person.get("dai"));
//        attr.addFlashAttribute("in", person.get("in"));
        jdbc.update("UPDATE person SET birth = ?", (int)person.get("child")*50);

//        attr.addFlashAttribute("gakusum", (int)person.get("child")*((int)person.get("you")+(int)person.get("syou")+(int)person.get("tyuu")+(int)person.get("kou")+(int)person.get("dai")));
        jdbc.update("UPDATE person SET gakusum = ?",(int)person.get("child")*((int)person.get("you")+(int)person.get("syou")+(int)person.get("tyuu")+(int)person.get("kou")+(int)person.get("dai")));

        attr.addFlashAttribute("movePoint", "down");
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        return "redirect:/index";
    }
 

//テキスト質問3-3  myhome  
    @PostMapping("/hogehouse")
    public String housedisplay(int myhome, RedirectAttributes attr) {
        
//       jdbc.update("UPDATE person SET yatin = ?", yatin);
        jdbc.update("UPDATE person SET myhome = ?", myhome);
        
        
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        jdbc.update("UPDATE person SET housesum = ?", ((int)person.get("yatin")+(int)person.get("myhome")));
       //食費も考慮してる
        person = jdbc.queryForList("SELECT * FROM person").get(0);
//        attr.addFlashAttribute("myhome", (int)person.get("myhome"));
//        attr.addFlashAttribute("yatin", (int)person.get("yatin"));
//        attr.addFlashAttribute("housesum", (int)person.get("housesum"));


        
        attr.addFlashAttribute("movePoint", "marry");
        System.out.println(jdbc.queryForList("SELECT * FROM person"));

        return "redirect:/index";
    }
    @PostMapping("/hogemarry")
    public String marry(Userform form, RedirectAttributes attr){
          if(form.getMarryprice().equals("sikire")){
              jdbc.update("UPDATE person SET marryprice = 195"); 
          }else if(form.getMarryprice().equals("sikise")){
              jdbc.update("UPDATE person SET marryprice = 260");  
          }else if(form.getMarryprice().equals("sikiho")){
              jdbc.update("UPDATE person SET marryprice = 260");  
          }else if(form.getMarryprice().equals("sikige")){
              jdbc.update("UPDATE person SET marryprice = 320");  
          }else if(form.getMarryprice().equals("sikino")){
              jdbc.update("UPDATE person SET marryprice = 0");  
          }else if(form.getMarryprice().equals("sikisu")){
              jdbc.update("UPDATE person SET marryprice = 0");  
          }

          jdbc.update("UPDATE person SET marrytrip = ?", form.getMarrytrip());
      

          Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);

//          attr.addFlashAttribute("marryprice", person.get("marryprice"));
//          attr.addFlashAttribute("marrytrip", person.get("marrytrip"));
      
          person = jdbc.queryForList("SELECT * FROM person").get(0);
              jdbc.update("UPDATE person SET marrysum = ?", (int)person.get("marryprice")+(int)person.get("marrytrip"));
          person = jdbc.queryForList("SELECT * FROM person").get(0);
//              attr.addFlashAttribute("marrysum", person.get("marrysum") );
              attr.addFlashAttribute("movePoint", "education");
          System.out.println(jdbc.queryForList("SELECT * FROM person"));
      
      return "redirect:/index";
  }
 
      
 
  

 @PostMapping("/hoge1000")
     public String totalsumdisplay(RedirectAttributes attr){
         Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
         jdbc.update("UPDATE person SET totalsum = ?", (int)person.get("birth")+(int)person.get("carsum")+(int)person.get("housesum")+(int)person.get("marrysum")+(int)person.get("gakusum"));
         person = jdbc.queryForList("SELECT * FROM person").get(0);
         attr.addFlashAttribute("totalsum", (int)person.get("totalsum"));
         System.out.println(jdbc.queryForList("SELECT * FROM person"));
     return "redirect:/kekka";
     }
 
 @PostMapping("/hoge2000")
     public String risoudisplay(RedirectAttributes attr){
         Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
         
         attr.addFlashAttribute("totalsum", (int)person.get("totalsum"));
         
         jdbc.update("UPDATE person SET risou = ?", (int)person.get("totalsum")/(65-(int)person.get("age")));
         
         person = jdbc.queryForList("SELECT * FROM person").get(0);
         
         jdbc.update("UPDATE person SET risoumax = ?", (int)person.get("risou")*2-(int)person.get("gessyuu")*12);
         
         person = jdbc.queryForList("SELECT * FROM person").get(0);
         
         attr.addFlashAttribute("risou", (int)person.get("risou"));
         attr.addFlashAttribute("risoumax", (int)person.get("risoumax"));
         
         System.out.println(jdbc.queryForList("SELECT * FROM person"));
     return "redirect:/kekka";
 }
 @PostMapping("/hoge3000")
 public String syousaidisplay(RedirectAttributes attr){
     Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
     
     attr.addFlashAttribute("age", (int)person.get("age"));
     attr.addFlashAttribute("tukityo", (int)person.get("tukityo"));
     attr.addFlashAttribute("gessyuu", (int)person.get("gessyuu"));
     attr.addFlashAttribute("yatin", (int)person.get("yatin"));
     attr.addFlashAttribute("marryprice", (int)person.get("marryprice"));
     attr.addFlashAttribute("marrytrip", (int)person.get("marrytrip"));
     attr.addFlashAttribute("child", (int)person.get("child"));
     attr.addFlashAttribute("birth", (int)person.get("birth"));
     attr.addFlashAttribute("you", (int)person.get("you")*(int)person.get("child"));
     attr.addFlashAttribute("syou", (int)person.get("syou")*(int)person.get("child"));
     attr.addFlashAttribute("tyuu", (int)person.get("tyuu")*(int)person.get("child"));
     attr.addFlashAttribute("kou", (int)person.get("kou")*(int)person.get("child"));
     attr.addFlashAttribute("dai", (int)person.get("dai")*(int)person.get("child"));
     attr.addFlashAttribute("in", (int)person.get("in")*(int)person.get("child"));
     attr.addFlashAttribute("myhome", (int)person.get("myhome"));
     attr.addFlashAttribute("syasyu", (int)person.get("syasyu"));
     attr.addFlashAttribute("carprice", (int)person.get("carprice"));
     
     attr.addFlashAttribute("totalsum", (int)person.get("totalsum"));
     attr.addFlashAttribute("risou", (int)person.get("risou"));
     attr.addFlashAttribute("risoumax", (int)person.get("risoumax"));
     System.out.println(jdbc.queryForList("SELECT * FROM person"));
 return "redirect:/kekka";
}
 @Autowired
 private KeisanDao keisanDao;

    @GetMapping("/test")
    public String test(){
         System.out.println(keisanDao.findAll());
//         System.out.println(keisanDao.findByAge(20));
         return "";
    }
    
    @GetMapping("/kekka")
    public String result(){
        return "kekka";
    }

}
 

