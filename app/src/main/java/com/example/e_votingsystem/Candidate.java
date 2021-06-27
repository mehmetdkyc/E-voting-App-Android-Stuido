package com.example.e_votingsystem;

public class Candidate {
    public String fullNameCandidate,age,nationality,identityNumberofCandidate,whichCandidate;
    public int countVote;
    public Candidate(){

    }
    public Candidate(String fullNameCandidate,int countVote,String age,String nationality,String identityNumberofCandidate,String whichCandidate){
        this.fullNameCandidate=fullNameCandidate;
        this.countVote=countVote;
        this.age=age;
        this.nationality=nationality;
        this.identityNumberofCandidate=identityNumberofCandidate;
        this.whichCandidate=whichCandidate;

    }

    public String getFullNameCandidate() {
        return fullNameCandidate;
    }

    public void setFullNameCandidate(String fullNameCandidate) {
        this.fullNameCandidate = fullNameCandidate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdentityNumberofCandidate() {
        return identityNumberofCandidate;
    }

    public void setIdentityNumberofCandidate(String identityNumberofCandidate) {
        this.identityNumberofCandidate = identityNumberofCandidate;
    }

    public String getWhichCandidate() {
        return whichCandidate;
    }

    public void setWhichCandidate(String whichCandidate) {
        this.whichCandidate = whichCandidate;
    }

    public int getCountVote() {
        return countVote;
    }

    public void setCountVote(int countVote) {
        this.countVote = countVote;
    }
}
