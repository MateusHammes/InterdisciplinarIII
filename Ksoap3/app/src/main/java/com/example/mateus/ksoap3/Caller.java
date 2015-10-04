package com.example.mateus.ksoap3;

public class Caller extends Thread {
    public CallSoap call;
    public  void run(){
        try{
            call = new CallSoap();
            //GroupList.lsNamesGrupos = call.SelectGrupos();
            MainActivity.lsNamesGrupos.add(call.GetWord());

            MainActivity.keep =false;
        }catch (Exception e){
                    /*List<String> lsErros = new ArrayList<String>();
                    lsErros.add(e.toString());*/
            MainActivity.lsNamesGrupos.add(e.toString());
        }
    }
}