package com.example.sankalp.mymiwok;

/**
 * Created by Sankalp on 6/29/2017.
 */
public class word {
    public int img;
    private String mdefaulttranslation;
    private String miwoktranslation;
    private int song;
    public word(int i,String defaultTrans, String miwokTrans,int sid) {
        img=i;
        mdefaulttranslation = defaultTrans;
        miwoktranslation = miwokTrans;
        song=sid;
    }

    public word(String s, String s1,int sid) {
        mdefaulttranslation=s;
        miwoktranslation=s1;
        song=sid;
    }
    public int getimg(){return img;}
    public String getDefaultTranslation() {
        return mdefaulttranslation;
    }
    public String getMiwokTranslation() {
        return miwoktranslation;
    }
    public  int getsong(){return song;}
}
