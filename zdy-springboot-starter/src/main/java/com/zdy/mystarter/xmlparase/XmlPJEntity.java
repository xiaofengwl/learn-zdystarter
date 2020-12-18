package com.zdy.mystarter.xmlparase;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/15 16:44
 * @desc
 */
@XmlRootElement(name = "ROOT")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlPJEntity implements Serializable{

    //属性1
    @XmlElement(name = "NAME")
    private String name;

    //属性2
    @XmlElement(name = "ADDRESS")
    private String address;

    //属性3：集合
    @XmlElementWrapper(name="DETAIL_LIST_ARR")
    @XmlElement(name="STRUCT")
    private List<XmlArr> arrList;

    @XmlRootElement(name="ROOT")
    @XmlAccessorType(XmlAccessType.FIELD)
    class XmlArr{

        //arr属性1
        @XmlElement(name = "FIELD1")
        private String field1;

        //.....
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setArrList(List<XmlArr> arrList) {
        this.arrList = arrList;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<XmlArr> getArrList() {
        return arrList;
    }
}
