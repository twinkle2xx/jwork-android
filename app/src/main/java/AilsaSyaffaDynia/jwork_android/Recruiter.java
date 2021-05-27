package AilsaSyaffaDynia.jwork_android;

public class Recruiter {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private Location location;

    public Recruiter(int id, String name, String email, String phoneNumber, Location location){
        this.id = id;
        this.name = name;
        this.email = email ;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getphoneNumber(){
        return phoneNumber;
    }

    public Location getlocation(){
        return location;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    @Override
    public  String toString(){
        return"Recruiter"+
                "\nId     : " + id +
                "\nName      : " + name +
                "\nEmail         : " + email +
                "\nPhone Number   : " + phoneNumber +
                "\nLocation      : " + location.getCity();
    }
}