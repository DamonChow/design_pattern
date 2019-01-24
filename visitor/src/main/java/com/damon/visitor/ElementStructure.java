package com.damon.visitor;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 功能：对象结构
 *
 * @author Damon
 * @since 2019-01-07 16:23
 */
@Data
public class ElementStructure {

    private List<Element> list = Lists.newArrayList();

    public void addElement(Element element){
        list.add(element);
    }

    public void accept(ElementVisitor visitor) {
        for (Element elem : list) {
            elem.accept(visitor);
        }
    }
}
