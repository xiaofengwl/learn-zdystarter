package com.zdy.mystarter.tools;

import com.sun.jmx.remote.internal.Unmarshal;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * TODO XML格式String和实体类的相互转换的工具了
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/15 17:02
 * @desc
 */
public class XmlPraser {

    private static final Logger Log= LoggerFactory.getLogger(XmlPraser.class);


    /**
     * Object实例转换成xml字符串
     * @param object
     * @return
     */
    public static String toXmlString(Object object){
        String xmlString=null;
        try{
            StringWriter stringWriter=new StringWriter();
            JAXBContext context=JAXBContext.newInstance(object.getClass());
            Marshaller marshaller=context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object,stringWriter);
            xmlString=stringWriter.toString();
        }catch (JAXBException e){
            Log.error("XmlPraser 实例转换xmlString失败。",e);
        }
        return xmlString;
    }

    /**
     * xmlString 转换成Object实例
     * @param clazz
     * @param xmlString
     * @param <T>
     * @return
     */
    public static <T extends Object> T toObject(Class<T> clazz,String xmlString){
        T object=null;
        try{
            JAXBContext context=JAXBContext.newInstance(clazz);
            Unmarshaller unmarshal=context.createUnmarshaller();
            StringReader stringReader=new StringReader(xmlString);
            object=(T)unmarshal.unmarshal(stringReader);
        }catch (JAXBException e){
            Log.error("XmlPraser xmlString转换实例失败。",e);
        }
        return object;
    }



}
