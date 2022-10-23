package com.personal.blog.utils;



import com.personal.blog.pojo.Pager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
* @description:
* @author: luxinlong
**/

public class BuildPager {


    //不指定条数,默认5
    public static Pager biuldPager(int total, Integer startPage) {

        return biuldPager(total, startPage, 5);
    }

    /*自定义每页显示条数 number*/
    public static Pager biuldPager(int total, Integer startPage, int number) {

        //创建pager
        Pager pager = new Pager();
        pager.setTotalPage(total);
        pager.setStartPage(startPage);
        pager.setDataNumber(number);
        //页数
        //如果每页展示的条数==总条数那么返回1页
        if (total == number)
            pager.setPageQuantity(1);
            //如果为0则刚好承载
        else if (total % number == 0)
            pager.setPageQuantity(total / number);
            //需要添加一页进行承载
        else
            pager.setPageQuantity((total / number) + 1);

        //获取pages
        pager.setPages(getNumber(startPage,pager.getPageQuantity()));

        return pager;
    }

    // 生成页
    public static List<Integer> getNumber(Integer startPage, int totalpage) {

        List<Integer> pages = new ArrayList<>(6);
        int totalPage = totalpage;

        int pageNumber = 6;

        // 当前页小于5 先取前(排除首页和当前页) , 再取后
        if (startPage < 5) {
            int prepage = startPage;
            int nextpage = startPage;
            for (int i = 0; i < pageNumber; i++) {
                if ((prepage - 1) > 1) {
                    prepage--;
                    pages.add(prepage);
                } else {
                    nextpage++;
                    if (nextpage < totalPage) {
                        pages.add(nextpage);
                    }
                }
            }
        } else if (startPage >= 5 && startPage <= (totalPage - 5)) {
            // 如果当前页大于4 并且 小于总页数-4 取前三 后三
            int plus = 1;
            int minus = 1;
            for (int i = 1; i <= pageNumber; i++) {
                if (i <= pageNumber/2) {
                    pages.add(startPage - minus);
                    minus ++;
                } else {
                    pages.add(startPage + plus);
                    plus++;
                }
            }
        } else {    // 尽量取后(排除最后一页和当前页)
            int prepage = startPage;
            int nextpage = startPage;
            for (int i = 0; i < pageNumber; i++) {
                if (nextpage < totalPage - 1) {
                    nextpage++;
                    pages.add(nextpage);
                } else {
                    if ((prepage - 1) >= 1) {
                        prepage--;
                        pages.add(prepage);
                    }
                }
            }
        }

        //排序
        Collections.sort(pages);

        return pages;
    }

    public static void main(String[] args) {
        //List<Integer> pages = getNumber(21, 21);
//        for (Integer page : pages) {
//            System.out.println(page);
//        }
    }

    /**
     * Static方法是类方法，先于任何的实例（对象）存在。即Static方法在类加载时就已经存在了（JAVA虚拟机初始化时），
     * 但是对象是在创建时才在内存中生成。而this指代的是当前的对象。
     */

}
