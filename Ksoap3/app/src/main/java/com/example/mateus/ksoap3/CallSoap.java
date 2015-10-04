package com.example.mateus.ksoap3;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



public class CallSoap
{
    public String GetWord(){
        String nameSpace = "http://Server/";
        String metodoWeb = "palavra";
        String soapAction ="http://Server/palavra";
        String url = "http://192.168.0.106:8080/WebService/WService?wsdl";


        SoapObject request = new SoapObject(nameSpace,metodoWeb);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(url);

        String palavra = "Erro!";

        try
        {
            httpTransport.call(soapAction, envelope);

            SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
            palavra = resultsRequestSOAP.toString();
        }
        catch (Exception exception)
        {
            palavra=exception.toString();
        }

        return palavra;
    }
}
