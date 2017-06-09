package com.example.nensyuapuri;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class MainController {

    public int money = 0;
    public int age;
    public int nensyu;
    public int kyuryo;
    public int sum;
    public int yatin;
    public int myhome;
/////////////////////////////////////////えデータベースに値を挿入　data.sql参照///////////////////////////////////////    
    @Autowired///////////////////////データベースjdbcを使用します
    private JdbcTemplate jdbc;
    
    @GetMapping("/h2")///////////→①　　h2に飛ぶときにデータベースにデータ保存 
    public String h2(){
    
    System.out.println(jdbc.queryForList("select * from person"));//コンソールに表示
        return "index";//
    }
/////////////////////////////////////////////////////////////////////////////////    
    
    @Value("${app.name}")
    private String hoge;
    
/////////////////////////////////////////////////////////////////////////////////    

 /*   public String plus(int num1, int num2, int num3, Model model){
        jdbc.update("insert into person (num1, num2, num3)values("+ num1 + "," + num2 + "," + num3 + ")");
        
        return "index";
      }
 */  
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

        // html にageを表示　to model
        model.addAttribute("age", age);

        return "index";
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

  //子供(kodomo)をindexで入力して、indexで表示
    @GetMapping("/form1")
    public String kodomodisplay(int kodomo, Model model){
    model.addAttribute("kodomo", kodomo);
    return "index";
    }
    
    @GetMapping("/form31")
    public String nannengodisplay(int nannengo, Model model){
    model.addAttribute("nannengo", nannengo);
    return "index";
    }
    
    @GetMapping("/form32")
    public String yatindisplay(int yatin, Model model){
        jdbc.update("UPDATE person SET yatin = ?", yatin);// 一行目のyatinをupdate
        model.addAttribute("yatin", yatin);
        System.out.println(jdbc.queryForList("SELECT * FROM person"));
    return "index";
    }
    
    @GetMapping("/form33")
    public String myhomedisplay(int myhome, Model model){
    model.addAttribute("myhome", myhome);
    return "index";
    }
    
/*    @GetMapping("/form34")
    public String myhomedisplay(int myhome1,int myhome2, Model model){
    model.addAttribute("myhome3", myhome1+myhome2);
    return "index";
}
*/    
    
    public int myhome1;
    public int myhome2;
    public int myhome3=myhome1+myhome2;
    

  
    
    @GetMapping("/form41")
    public String carpricedisplay(int carprice, Model model){
        jdbc.update("UPDATE person SET carprice = ?", carprice);// 一行目のcarpriceをupdate
 //       jdbc.update("insert into person (age, name, gender,carprice,num1, num2, num3) values (age,'syota','male',?,0,5,5)",carprice);
        System.out.println(jdbc.queryForList("SELECT * FROM person"));//コンソールに表示
        model.addAttribute("carprice", carprice);//htmlに表示
    return "index";
    }
    
    
   
    
    
    //kekkaにnensyu+kyuryo=moneyを設定
 @GetMapping("/form100")
            public String sumdisplay(int yatin, int myhome, Model model) {
                sum = yatin + myhome;
            return "index";
            }
        
 
    
}
