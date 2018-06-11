package com.example.nensyuapuri;


import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller


public class MainController {

    
    
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
    
    
    @PostMapping("/name")
    public String name(String name, RedirectAttributes attr){
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person").get(0);
        
        if(jdbc.queryForList("SELECT * FROM person WHERE name = ?", name).size() == 0){
         jdbc.update("insert into person (name) values (?)", name);
 //        System.out.println(jdbc.queryForList("SELECT * FROM person WHERE name = ?", name));
         attr.addFlashAttribute("userName", jdbc.queryForList("SELECT name FROM person WHERE name = ?", name).get(0).get("name"));
         attr.addFlashAttribute("movePoint", "info");} 
        else{ 
            attr.addFlashAttribute("dualname", "「" + name + "」は使用済みです。別のニックネームで再度入力してください。");
        }
        
        return "redirect:/index";
    }

///////////////////////////////////////////////////////////////////////////

    /**
     * 「現状について」における入力された値をデータベースpersonに更新するメソッドあ
     * textboxには正の値を入力
     * selectboxには回答を選択してもらい、それぞれの項目の値をpersonに保存
     * 正の値以外の数字や、文字は入力できないようになっている
     * 全ての質問が入力し終わったら、決定ボタンを押してもらい、personに保存。次の質問（車について）の頭に飛ぶ
     * @param name ログイン
     * @param form formクラスの引数を使用
     * @param attr リダイレクトを使用
     * @return index.htmlに返る
     */    
  @PostMapping("/hogeinfo:{name}")
    public String agedisplay(@PathVariable String name,Userform form, RedirectAttributes attr){
        
        if(form.getGender().equals("man")){
            jdbc.update("UPDATE person SET endage = 80 WHERE name = ?", name); 
            jdbc.update("UPDATE person SET hokenyears = 24 WHERE name = ?", name);
        } else if(form.getGender().equals("woman")){
            jdbc.update("UPDATE person SET endage = 87 WHERE name = ?", name);
            jdbc.update("UPDATE person SET hokenyears = 18 WHERE name = ?", name);
        }
        jdbc.update("UPDATE person SET age = ? WHERE name = ?", form.getAge(), name);
        jdbc.update("UPDATE person SET gessyuu = ? WHERE name = ?", form.getGessyuu(), name);
        jdbc.update("UPDATE person SET tukityo = ? WHERE name = ?", form.getTukityo(), name);
        
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person where name = ?",name).get(0);
        jdbc.update("UPDATE person SET yatin = ? WHERE name = ?", 12*((int)person.get("endage")-(int)person.get("age"))*((int)person.get("gessyuu")-(int)person.get("tukityo")), name);
        jdbc.update("UPDATE person SET hoken = ? WHERE name = ?", (int)person.get("hokenyears")*((int)person.get("endage")-(int)person.get("age")), name);
        
        System.out.println(jdbc.queryForList("SELECT * FROM person WHERE name = ?", name));
        
        attr.addFlashAttribute("userName", name);
        attr.addFlashAttribute("movePoint", "car");
        return "redirect:/index";
    }
   
////////////////////////////////////////////////////////////////////////////////////
    /**
     * 「車について」における入力された値をデータベースpersonに更新するメソッド
     * textboxには正の値を入力
     * selectboxには回答を選択してもらい、それぞれの項目の値をpersonに保存
     * 正の値以外の数字や、文字は入力できないようになっている
     * 全ての質問が入力し終わったら、決定ボタンを押してもらい、personに保存。次の質問（住宅について）の頭に飛ぶ
     * @param syasyu 車種と所有期間から、維持費を計算
     * @param carprice 車の値段を参考サイトより入力
     * @param carhavestart 車種と所有期間から、維持費を計算
     * @param carhaveend 車種と所有期間から、維持費を計算
     */
    @PostMapping("/hogecar:{name}")
    public String cardisplay(@PathVariable String name,String syasyu, int carprice, int carhavestart, int carhaveend, RedirectAttributes attr) {
        
        if(syasyu.equals("kei")){
            jdbc.update("UPDATE person SET syasyu = 33 where name = ?", name); 
        }else if(syasyu.equals("kogata")){
            jdbc.update("UPDATE person SET syasyu = 41 where name = ?", name);  
        }else if(syasyu.equals("hutuu")){
            jdbc.update("UPDATE person SET syasyu = 46 where name = ?", name);  
        }else if(syasyu.equals("nocar")){
            jdbc.update("UPDATE person SET syasyu = 0 where name = ?", name);  
        }
        jdbc.update("UPDATE person SET carhavestart = ? where name = ?", carhavestart ,name);
        jdbc.update("UPDATE person SET carhaveend = ? where name = ?", carhaveend, name);
        jdbc.update("UPDATE person SET carprice = ? where name = ?", carprice,name);

        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person where name = ?",name).get(0);
        jdbc.update("UPDATE person SET carsum = ? where name = ?", ((int)person.get("carhaveend")-(int)person.get("carhavestart"))*(int)person.get("syasyu")+(int)person.get("carprice"),name);

        person = jdbc.queryForList("SELECT * FROM person where name = ?", name).get(0);

        attr.addFlashAttribute("movePoint", "house");
        attr.addFlashAttribute("userName", name);
 //       System.out.println(jdbc.queryForList("SELECT * FROM person where name = ?", name));
        return "redirect:/index";
    } 
//////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 「教育について」における入力された値をデータベースpersonに更新するメソッド
     * textboxには正の値を入力
     * selectboxには回答を選択してもらい、それぞれの項目の値をpersonに保存
     * 正の値以外の数字や、文字は入力できないようになっている
     * 全ての質問が入力し終わったら、決定ボタンを押してもらい、personに保存。ページの末尾に飛ぶ
     * @param child 出産にかかる費用を計算
     */

    @PostMapping("/hogegaku:{name}")
    public String gakudisplay(@PathVariable String name,int child, String you, String syou, String tyuu, String kou, String dai, String in,  RedirectAttributes attr){
        //子供入力
        jdbc.update("UPDATE person SET child = ? where name = ?", child , name);
        //幼稚園入力
        if(you.equals("yoko")){
            jdbc.update("UPDATE person SET you = 69 where name = ?", name); 
        } else if(you.equals("yosi")){
            jdbc.update("UPDATE person SET you = 146 where name = ?", name);  
        }
        //小学校入力
        if(syou.equals("syoko")){
            jdbc.update("UPDATE person SET syou = 183 where name = ?", name); 
        } else if(syou.equals("syosi")){
            jdbc.update("UPDATE person SET syou = 853 where name = ?", name);  
        }
        
        if(tyuu.equals("tyuuko")){
            jdbc.update("UPDATE person SET tyuu = 135 where name = ?", name); 
        } else if(tyuu.equals("tyuusi")){
            jdbc.update("UPDATE person SET tyuu = 388 where name = ?", name);  
        }
        if(kou.equals("kouko")){
            jdbc.update("UPDATE person SET kou = 116 where name = ?", name); 
        } else if(kou.equals("kousi")){
            jdbc.update("UPDATE person SET kou = 290 where name = ?", name);  
        }

        if(dai.equals("daikokuzi")){
            jdbc.update("UPDATE person SET dai = 457 where name = ?", name); 
        }else if(dai.equals("daikokuhi")){
            jdbc.update("UPDATE person SET dai = 1002 where name = ?", name);  
        }else if(dai.equals("daisibuzi")){
            jdbc.update("UPDATE person SET dai = 675 where name = ?", name);  
        }else if(dai.equals("daisibuhi")){
            jdbc.update("UPDATE person SET dai = 1220 where name = ?", name);  
        }else if(dai.equals("daisirizi")){
            jdbc.update("UPDATE person SET dai = 818 where name = ?", name);  
        }else if(dai.equals("daisirihi")){
            jdbc.update("UPDATE person SET dai = 1362 where name = ?", name);  
        }else if(dai.equals("daisihazi")){
            jdbc.update("UPDATE person SET dai = 3068 where name = ?", name);  
        }else if(dai.equals("daisihahi")){
            jdbc.update("UPDATE person SET dai = 3468 where name = ?", name);  
        }else if(dai.equals("daino")){
            jdbc.update("UPDATE person SET dai = 0 where name = ?", name);  
        }
        
        if(in.equals("inkoku")){
            jdbc.update("UPDATE person SET in = 82 where name = ?", name); 
        }else if(in.equals("insibu")){
            jdbc.update("UPDATE person SET in = 115 where name = ?", name);  
        }else if(in.equals("insiri")){
            jdbc.update("UPDATE person SET in = 149 where name = ?", name);  
        }else if(in.equals("insiha")){
            jdbc.update("UPDATE person SET in = 466 where name = ?", name);  
        }else if(in.equals("inno")){
            jdbc.update("UPDATE person SET in = 0 where name = ?", name);  
        }
        
    
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person where name = ?",name).get(0);
        jdbc.update("UPDATE person SET birth = ? where name = ?", (int)person.get("child")*50 ,name);//出産の費用計算
        
        jdbc.update("UPDATE person SET gakusum = ? where name = ?",(int)person.get("child")*((int)person.get("you")+(int)person.get("syou")+(int)person.get("tyuu")+(int)person.get("kou")+(int)person.get("dai")),name);
        attr.addFlashAttribute("userName", name);
        attr.addFlashAttribute("movePoint", "down");
 //       System.out.println(jdbc.queryForList("SELECT * FROM person where name = ?", name));
        return "redirect:/index";
    }
/////////////////////////////////////////////////////////////////////////////////////////    
    /**
     * 「住宅について」における入力された値をデータベースpersonに更新するメソッド
     * textboxには正の値を入力
     * selectboxには回答を選択してもらい、それぞれの項目の値をpersonに保存
     * 正の値以外の数字や、文字は入力できないようになっている
     * 全ての質問が入力し終わったら、決定ボタンを押してもらい、personに保存。「結婚について」の頭に飛ぶ
     * @param myhome 参考サイトから入力
     */
 
    @PostMapping("/hogehouse:{name}")
    public String housedisplay(@PathVariable String name,int myhome, RedirectAttributes attr) {
        Map<String, Object> person = jdbc.queryForList("SELECT * FROM person where name = ?", name).get(0);
        jdbc.update("UPDATE person SET myhome = ? where name = ?", myhome, name);
                
        person = jdbc.queryForList("SELECT * FROM person where name = ?", name).get(0);
        jdbc.update("UPDATE person SET housesum = ? where name = ?", (int)person.get("myhome"),name);

        person = jdbc.queryForList("SELECT * FROM person where name = ?", name).get(0);
        attr.addFlashAttribute("userName", name);
        attr.addFlashAttribute("movePoint", "marriage");
  //      System.out.println(jdbc.queryForList("SELECT * FROM person where name = ?", name));

        return "redirect:/index";
    }
    
/////////////////////////////////////////////////////////////////////////////////////////    
/**
* 「結婚について」における入力された値をデータベースpersonに更新するメソッド
* textboxには正の値を入力
* selectboxには回答を選択してもらい、それぞれの項目の値をpersonに保存
* 正の値以外の数字や、文字は入力できないようになっている
* 全ての質問が入力し終わったら、決定ボタンを押してもらい、personに保存。「教育について」の頭に飛ぶ
* @param form　formクラスの引数を使用
*/
    
    @PostMapping("/hogemarry:{name}")
    public String marry(@PathVariable String name,Userform form, RedirectAttributes attr){
          if(form.getMarryprice().equals("sikire")){
              jdbc.update("UPDATE person SET marryprice = 195 where name = ?", name); 
          }else if(form.getMarryprice().equals("sikise")){
              jdbc.update("UPDATE person SET marryprice = 260 where name = ?", name);  
          }else if(form.getMarryprice().equals("sikiho")){
              jdbc.update("UPDATE person SET marryprice = 260 where name = ?", name);  
          }else if(form.getMarryprice().equals("sikige")){
              jdbc.update("UPDATE person SET marryprice = 320 where name = ?", name);  
          }else if(form.getMarryprice().equals("sikino")){
              jdbc.update("UPDATE person SET marryprice = 0 where name = ?", name);  
          }else if(form.getMarryprice().equals("sikisu")){
              jdbc.update("UPDATE person SET marryprice = 0 where name = ?", name);  
          }

          jdbc.update("UPDATE person SET marrytrip = ? where name = ?", form.getMarrytrip(),name);
      

          Map<String, Object> person = jdbc.queryForList("SELECT * FROM person where name = ?", name).get(0);
      
          person = jdbc.queryForList("SELECT * FROM person where name = ?", name).get(0);
              jdbc.update("UPDATE person SET marrysum = ? where name = ?", (int)person.get("marryprice")+(int)person.get("marrytrip"),name);
          person = jdbc.queryForList("SELECT * FROM person where name = ?", name).get(0);
              attr.addFlashAttribute("movePoint", "education");
  //        System.out.println(jdbc.queryForList("SELECT * FROM person where name = ?", name));
          attr.addFlashAttribute("userName", name);
      return "redirect:/index";
  }
 
      
/////////////////////////////////////////////////////////////////////////////////////////    
/**
* personに保存された値から、費用の総計totalsumを計算し、保存
* index.htmlから「総計」ボタンを押し、kekka.htmlにて総計を表示
* personに保存されたtotalsumから「理想の年収」risoumaxを計算し、保存
* risou=totalsum/労働年数（65－現在の年齢(age)）を計算し、保存
* risoumax=2*risou-現在の年収 とする
* kekka.htmlにて「理想の年収」を表示
*/

 @PostMapping("/hoge1000:{name}")
     public String totalsumdisplay(@PathVariable String name,RedirectAttributes attr){
         Map<String, Object> person = jdbc.queryForList("SELECT * FROM person where name = ?", name).get(0);
         jdbc.update("UPDATE person SET totalsum = ? where name = ?", (int)person.get("hoken")+(int)person.get("birth")+(int)person.get("carsum")+(int)person.get("housesum")+(int)person.get("marrysum")+(int)person.get("gakusum"),name);
         person = jdbc.queryForList("SELECT * FROM person where name = ?", name).get(0);
         attr.addFlashAttribute("totalsum", (int)person.get("totalsum"));

         jdbc.update("UPDATE person SET risou = ? where name = ?", ((int)person.get("totalsum")/(65-(int)person.get("age"))-12*(int)person.get("tukityo")+12*(int)person.get("gessyuu")),name);
         
         person = jdbc.queryForList("SELECT * FROM person where name = ?", name).get(0);
         
         jdbc.update("UPDATE person SET risoumax = ? where name = ?", (int)person.get("risou")*2-(int)person.get("gessyuu")*12,name);
         
         person = jdbc.queryForList("SELECT * FROM person where name = ?", name).get(0);
         
         attr.addFlashAttribute("risoumax", (int)person.get("risoumax"));
       
         person = jdbc.queryForList("SELECT * FROM person where name = ?", name).get(0);
         attr.addFlashAttribute("name", person.get("name"));
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
         
         
 //        System.out.println(jdbc.queryForList("SELECT * FROM person where name = ?", name));         
         attr.addFlashAttribute("userName", name);
     return "redirect:/kekka";
     }
 @PostMapping("/hogeend")
 public String end(String userName, RedirectAttributes attr){
  //  System.out.println(userName);
     jdbc.update("DELETE FROM person where name = ?",userName);
     
 return "redirect:/index";
 }

 
/////////////////////////////////////////////////////////////////////////////////////////    

 @Autowired
 private KeisanDao keisanDao;

    @GetMapping("/test")
    public String test(){
         System.out.println(keisanDao.findAll());
         return "";
    }
    
    @GetMapping("/kekka")
    public String result(){
        return "kekka";
    }

}
 

