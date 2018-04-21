package p450;


import java.io.*;

public class QuizCard implements Serializable {
    /*
     * Serializable接口是启用其序列化功能的接口
     * 实现此接口即代表该对象可以被序列化存储
     */
     private String uniqueID;
     private String category;
     private String question;
     private String answer;
     private String hint;

     /*
      * 下面都是一些简单内容，就不在多加描述
      * 该对象还留下了一些可供扩展的更多的参数
      */
     public QuizCard(String q, String a) {
         question = q;
         answer = a;
    }
     

     public void setUniqueID(String id) {
        uniqueID = id;
     }

     public String getUniqueID() {
        return uniqueID;
     }

     public void setCategory(String c) {
        category = c;
     }

     public String getCategory() {
         return category;
     }
     
     public void setQuestion(String q) {
        question = q;
     }

     public String getQuestion() {
        return question;
     }

     public void setAnswer(String a) {
        answer = a;
     }

     public String getAnswer() {
        return answer;
     }

     public void setHint(String h) {
        hint = h;
     }
 
     public String getHint() {
        return hint;
     }

}     

