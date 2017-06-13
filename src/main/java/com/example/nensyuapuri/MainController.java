package com.example.nensyuapuri;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

//年齢(age)をindexで入力して、indexで表示///////////////////////////////////////////////
    @GetMapping("/form")
    public String agedisplay(int age, Model model) {
        // 一行目のageをupdate
        jdbc.update("UPDATE person SET age = ?", age);
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        //personのage(get(0))を取得
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        // 取得したageをhtmlに表示　to model
        
        model.addAttribute("age", person.get("age"));

        return "index"; //kakuninn だと飛ぶ　結果も表示される
    }
////////////////////////////////////////////////////////////////////////////////////
    
    
    
//kekkaにnensyu+kyuryo=moneyを設定
    @GetMapping("/kekka1")
    public String moneydisplay(int kyuryo, int nensyu) {
        money = nensyu + kyuryo;
    return "index";
    }
/*kekka.html を/kekkaでmoneyを表示するメソッド*/
    @GetMapping("/kekka")
    public String kekka(Model model){
        model.addAttribute("nensyu", money);
        return "kekka";
    }

    @GetMapping("/form13")
    public String marrypricedisplay(String marryprice, Model model){
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
        }
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        model.addAttribute("marryprice", (int)person.get("marryprice"));
        return "index";
    }
    

    
  //子供(kodomo)をindexで入力して、indexで表示
   
    
    @GetMapping("/form21")
    public String childdisplay(int child, Model model) {
        // 一行目のchildをupdate
        jdbc.update("UPDATE person SET child = ?", child);
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        //personのchild(get(0))を取得
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        // 取得したageをhtmlに表示　to model
        model.addAttribute("child", person.get("child"));

        return "index"; //kakuninn だと飛ぶ　結果も表示される
    }
    

    @GetMapping("/form22")
    public String youdisplay(String you, Model model){
        if(you.equals("yoko")){
            jdbc.update("UPDATE person SET you = 69"); 
        } else if(you.equals("yosi")){
            jdbc.update("UPDATE person SET you = 146");  
        }
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        model.addAttribute("you", (int)person.get("you"));
        return "index";
    }

    @GetMapping("/form23")
    public String syoudisplay(String syou, Model model){
        if(syou.equals("syoko")){
            jdbc.update("UPDATE person SET syou = 183"); 
        } else if(syou.equals("syosi")){
            jdbc.update("UPDATE person SET syou = 853");  
        }
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        model.addAttribute("syou", (int)person.get("syou"));
        return "index";
    }

 
    
    
    @GetMapping("/form24")
    public String tyuudisplay(String tyuu, Model model){
        if(tyuu.equals("tyuuko")){
            jdbc.update("UPDATE person SET tyuu = 135"); 
        } else if(tyuu.equals("tyuusi")){
            jdbc.update("UPDATE person SET tyuu = 388");  
        }
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        model.addAttribute("tyuu", (int)person.get("tyuu"));
        return "index";
    }
    @GetMapping("/form25")
    public String koudisplay(String kou, Model model){
        if(kou.equals("kouko")){
            jdbc.update("UPDATE person SET kou = 116"); 
        } else if(kou.equals("kousi")){
            jdbc.update("UPDATE person SET kou = 290");  
        }
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        model.addAttribute("kou", (int)person.get("kou"));
        return "index";
    }
    
    @GetMapping("/form26")
    public String daidisplay(String dai, Model model){
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
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        model.addAttribute("dai", (int)person.get("dai"));
        return "index";
    }

    @GetMapping("/form27")
    public String indisplay(String in, Model model){
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
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        model.addAttribute("in", (int)person.get("in"));
        return "index";
    }

    
    @GetMapping("/form210")
    public String gakusumdisplay(String you,String syou,int gakusum,Model model) {
        
/*        if(you.equals("yoko")){
            jdbc.update("UPDATE person SET you = 69"); 
        } else if(you.equals("yosi")){
            jdbc.update("UPDATE person SET you = 146");  
        }
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        model.addAttribute("you", (int)person.get("you"));
        
        if(syou.equals("syoko")){
            jdbc.update("UPDATE person SET syou = 183"); 
        } else if(syou.equals("syosi")){
            jdbc.update("UPDATE person SET syou = 853");  
        }
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        person = jdbc.queryForList("SELECT * FROM person").get(0);
//        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        model.addAttribute("syou", (int)person.get("syou"));
        
*/ 
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
//        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        
        model.addAttribute("gakusum", (int)person.get("child")*((int)person.get("you")+(int)person.get("syou")+(int)person.get("tyuu")+(int)person.get("kou")+(int)person.get("dai")));//(int)をつけないと数値計算できない;
 
        return "index"; 
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
    @GetMapping("/form33")
    public String myhomedisplay(int myhome, Model model) {
        // 一行目のchildをupdate
        jdbc.update("UPDATE person SET myhome = ?", myhome);
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        //personのchild(get(0))を取得
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        // 取得したageをhtmlに表示　to model
        model.addAttribute("myhome", (int)person.get("myhome"));

        return "index"; //kakuninn だと飛ぶ　結果も表示される
    }
    
  //テキスト質問3-10  sum  
    @GetMapping("/form310")
    public String sumdisplay(int sum,Model model) {
        // 一行目のchildをupdate
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
        //personのchild(get(0))を取得
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        // 取得したageをhtmlに表示　to model
        model.addAttribute("sum", (int)person.get("myhome")+(int)person.get("yatin"));//(int)をつけないと数値計算できない;
 
        return "index"; //kakuninn だと飛ぶ　結果も表示される
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
    
/*    @PostMapping("/hoge")
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
 */   
    
    //kekkaにnensyu+kyuryo=moneyを設定
 @GetMapping("/form100")
            public String sumdisplay(int yatin, int myhome, Model model) {
                sum = yatin + myhome;
            return "index";
            }
        
 
    
}
