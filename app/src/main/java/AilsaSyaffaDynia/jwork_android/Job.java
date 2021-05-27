package AilsaSyaffaDynia.jwork_android;

public class Job {
    private int id;
    private String name;
    private Recruiter recruiter;
    private int fee;
    private String category;

    public Job(int id, String name, Recruiter recruiter, int fee, String category){
        this.id = id;
        this.name = name;
        this.recruiter = recruiter;
        this.fee = fee;
        this.category = category;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Recruiter getRecruiter(){
        return recruiter;
    }

    public int getFee(){
        return fee;
    }

    public String getCategory(){
        return category;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setRecruiter(Recruiter recruiter){
        this.recruiter = recruiter;
    }

    public void setFee(int fee){
        this.fee = fee;
    }

    public void setCategory(String category){
        this.category = category;
    }

    @Override
    public  String toString(){
        return"Job"+
                "\nId     : " + id +
                "\nName      : " + name +
                "\nFee         : " + fee +
                "\nCategory   : " + category +
                "\nRecruiter      : " + recruiter +
                "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
    }

}