package model;

/**
 * This class represents a question with an ID and four answer options.
 * 
 * @author ItayOlivcovitz
 */
public class Question {
    private int id;
    private String ans1;
    private String ans2;
    private String ans3;
    private String ans4;

   
    public Question(int id, String ans1, String ans2, String ans3, String ans4) {
        this.id = id;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
    }

  
    public int getId() {
        return id;
    }

  
    public void setId(int id) {
        this.id = id;
    }

 
    public String getAns1() {
        return ans1;
    }

   
    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    
    public String getAns2() {
        return ans2;
    }

   
    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    
    public String getAns3() {
        return ans3;
    }

   
    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

  
    public String getAns4() {
        return ans4;
    }

   
    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", ans1='" + ans1 + '\'' +
                ", ans2='" + ans2 + '\'' +
                ", ans3='" + ans3 + '\'' +
                ", ans4='" + ans4 + '\'' +
                '}';
    }
}
