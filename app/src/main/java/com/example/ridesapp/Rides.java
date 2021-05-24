package com.example.ridesapp;

public class Rides {

    String name;
    String from;
    String to;
    String number;
    String participants;

    Rides(){

    }

    public Rides(String name, String from, String to, String number, String participants) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.number = number;
        this.participants = participants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
}
