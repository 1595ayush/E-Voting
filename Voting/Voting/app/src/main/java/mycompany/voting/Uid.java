package mycompany.voting;

/**
 * Created by abc on 11-12-2015.
 */
public class Uid {
    private String name,dob,gender,clocation,slocation;
    private String uid,password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    int verified;
    private int election_city,election_country,election_state;
    public Uid() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClocation() {
        return clocation;
    }

    public void setClocation(String clocation) {
        this.clocation = clocation;
    }

    public String getSlocation() {
        return slocation;
    }

    public void setSlocation(String slocation) {
        this.slocation = slocation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getElection_city() {
        return election_city;
    }

    public void setElection_city(int election_city) {
        this.election_city = election_city;
    }

    public int getElection_country() {
        return election_country;
    }

    public void setElection_country(int election_country) {
        this.election_country = election_country;
    }

    public int getElection_state() {
        return election_state;
    }

    public void setElection_state(int election_state) {
        this.election_state = election_state;
    }

    public double getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }
}
