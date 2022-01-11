package com.huawei.util;


import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //根节点
    private TireNode root = new TireNode();

    private final String REPLACEMENT="***";

    @PostConstruct
    private void init() {
        try (
                //字节流转换成字符流再转换成缓冲流
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive.txt");
                BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
        ) {
            String word;
            while ((word = buffer.readLine()) != null) {
                addWordToTire(word);
            }
        } catch (IOException e) {
            logger.error("读取敏感词词典数据失败：" + e.getMessage());
        }
    }


    public String filter(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        //指针1 指向前缀树根节点
        TireNode cur = root;
        int len = str.length();
        int begin = 0;
        int end = 0;
        while (begin < len) {

            if (end < len) {
                char c = str.charAt(end);
                //特殊字符
                if (isSymbol(c)) {
                    if (cur == root) {
                        res.append(c);
                        begin++;
                    }
                    end++;
                    continue;
                }
                TireNode subNode = cur.getSubNode(c);
                if (subNode == null) {
                    res.append(str.charAt(begin));
                    end = ++begin;
                    cur = root;
                } else if (subNode.isWordsEnd()) {
                    //是敏感词的结尾
                    res.append(REPLACEMENT);
                    begin = ++end;
                    cur = root;
                } else {
                    cur = subNode;
                    end++;
                }

            }else {
                res.append(str.charAt(begin));
                end = ++begin;
                cur = root;
            }
        }
        return res.toString();
    }

    private boolean isSymbol(Character c) {
        //  东亚字符集码 0x9FFF~0x2E80
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }


    /**
     * 添加敏感词到前缀树
     *
     * @param word
     */
    private void addWordToTire(String word) {
        TireNode cur = root;
        int len = word.length();
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            TireNode subNode = cur.getSubNode(c);
            if (subNode == null) {
                subNode = new TireNode();
                cur.addSubNode(c, subNode);
            }
            //当前字符存在于子节点上,指向下一个节点
            cur = subNode;
            if (i == len - 1) {
                subNode.setIsWordsEnd(true);
            }
        }
    }


    private class TireNode {

        private boolean isWordsEnd = false;

        private Map<Character, TireNode> subNodes = new HashMap<>();


        public boolean isWordsEnd() {
            return isWordsEnd;
        }

        public void setIsWordsEnd(boolean wordsEnd) {
            isWordsEnd = wordsEnd;
        }

        public void addSubNode(Character c, TireNode tireNode) {
            subNodes.put(c, tireNode);
        }

        public TireNode getSubNode(Character c) {
            return subNodes.get(c);
        }


    }


}
