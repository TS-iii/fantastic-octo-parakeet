package com.example.tsone;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class PersonInfo implements Serializable, Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.name);
        dest.writeString(this.contents);
        dest.writeString(this.mac);

    }

    String name;
    String contents;
    String mac;

    public PersonInfo(String name, String contents,String mac){
        this.name=name;
        this.contents=contents;
        this.mac=mac;

    }

    public PersonInfo(Parcel in){
        this.name=in.readString();
        this.contents=in.readString();
        this.mac=in.readString();
    }


    public String getName(){return name;}
    public void setName(String name) { this.name=name;}



    public String getContents(){return contents;}
    public void setContents(String contents){this.contents=contents;}

    public String getMac(){return mac;}
    public void setMac(String mac){this.mac=mac;}

    @Override
    public String toString(){
        return "PersonInfo{"+
                "name='" + name + '\'' +
                ", contents='" + contents + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }


    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR=new Parcelable.Creator(){

        @Override
        public PersonInfo createFromParcel(Parcel in){
            return new PersonInfo(in);
        }

        @Override
        public PersonInfo[] newArray(int size){
            return new PersonInfo[size];
        }

    };
}
