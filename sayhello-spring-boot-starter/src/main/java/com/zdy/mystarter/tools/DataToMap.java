package com.zdy.mystarter.tools;

import com.alibaba.fastjson.JSON;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 将JSON字符串转换成Map
 * <pre>
 *     利用阿里巴巴封装的FastJSON来转换json字符串
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/2/8 12:28
 * @desc
 */
public class DataToMap {

    /**
     * JSON字符串转换为Map
     * @param str
     * @return
     */
    public static Map parseJsonToMap(String str){
        Map maps = (Map) JSON.parse(str);
        return maps;
    }


    /**
     * todo 方式1-XML格式字符串转换为Map
     * 要求：
     *  1.将每一个<></>中的元素均解析成key-value保存到map中，采用递归处理
     *
     * @param xml XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, Object> parseXmlToMap(String xml) {
        try {
            Map<String, Object> data = new HashMap<>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            //todo 仅仅是两层结构解析，待扩展调整
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            stream.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * todo 方式2-将xml报文转换为Map对象
     * @param xml
     * @return
     */
    public static Map<String, Object> parseXmlToMap2(String xml) {
        // 报文转成doc对象
        org.dom4j.Document  doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("解析xml报文内容异常：报文内容如下：\n"+xml);
        }
        // 获取根元素，准备递归解析这个XML树
        org.dom4j.Element root = doc.getRootElement();
        Map<String, Object> map = getCode(root);
        return map;
    }

    /**
     * todo 递归处理xml节点
     * @param root
     * @return
     */
    public static Map<String, Object> getCode(org.dom4j.Element root) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (root.elements() != null) {
            List<org.dom4j.Element> list = root.elements();// 如果当前跟节点有子节点，找到子节点
            for (org.dom4j.Element e : list) {// 遍历每个节点
                if (e.elements().size() > 0) {
                    //如果Map里有数据，判断该节点这是一个List集合
                    if(null!=map.get(e.getName())){
                        String eleName = e.getName();
                        Object object = map.get(eleName);
                        if(object instanceof List){
                            //List就直接加入
                            List list3 = (List)(object);
                            //递归解析
                            list3.add(getCode(e));
                            map.put(eleName,list3);
                        }else if(object instanceof Map){
                            //Map就新new一个List加入
                            List<Map> list3 = new ArrayList<>();
                            //递归解析
                            list3.add((Map)object);
                            list3.add(getCode(e));
                            map.put(eleName,list3);
                        }
                    }else{
                        // 当前节点不为空的话，递归遍历子节点；
                        map.put(e.getName(),getCode(e));
                    }
                }
                // 如果为叶子节点，那么直接把名字和值放入map
                else if (e.elements().size() == 0) {
                    //map.put(e.getName(), e.getTextTrim());
                    String eleName = e.getName();
                    String text = e.getTextTrim();
                    Object object = map.get(eleName);
                    if(object instanceof List){
                        //List就直接加入
                        List list3 = (List)(object);
                        list3.add(text);
                        map.put(eleName,list3);
                    }else if(object instanceof String){
                        //String就新new一个List加入
                        List list3 = new ArrayList<>();
                        list3.add(object);
                        list3.add(text);
                        map.put(eleName,list3);
                    }else{
                        map.put(eleName,text);
                    }
                }
            }
        }
        return map;
    }
}
