package com.lklk.examination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 查找字符串中，由相同且相邻的字符之间构成所有序列中，长度最长的字符序列。
 * 例如："aabccddddnnmcgggg"的结果为：dddd=4 gggg=4
 */
public class LongestSequenceFinderOfRepeatedChars {

    public static void main(String[] args) {
        LongestSequenceFinderOfRepeatedChars finder = new LongestSequenceFinderOfRepeatedChars();

        String originalStr = "aacasdbbbajajdbbbbdasdgebkzbmmmmccggggccccbbbb";
        System.out.println(finder.findFrom(originalStr));

        originalStr = "a";
        System.out.println(finder.findFrom(originalStr));
    }




    public LongestSequences findFrom(String target) {

        if (target == null || target.isEmpty()) {
            throw new IllegalArgumentException("target string cannot be null nor empty");
        }

        char[] chars = target.toCharArray();
        LongestSequences longestSequences = new LongestSequences(target);
//        var longestSequences = new LongestSequencesWithoutDuplicate(target);


        if (chars.length == 1) {
            longestSequences.accept(0, 1);
            return longestSequences;
        }


        // 假设第一个字母已被遍历过了
        char charOfLastSeq = chars[0];
        int countOfLastSeq = 1, beginIdxOfLastSeq = 0;

        /* 从第二个字母开始往后遍历，
         * 若发现与上一个字符charOfLastSeq不一致，说明从beginIdxOfLastSeq的位置开始，截至charOfLastSeq的位置为止可算作一个序列
         * */
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] != charOfLastSeq) {
                longestSequences.accept(beginIdxOfLastSeq, countOfLastSeq);
                charOfLastSeq = chars[i];
                beginIdxOfLastSeq = i;
                countOfLastSeq = 1;
            }
            else {
                countOfLastSeq ++;
            }
        }
        // 还需检测最后一个序列是否是最长的
        longestSequences.accept(beginIdxOfLastSeq, countOfLastSeq);
        return longestSequences;
    }







    public static class LongestSequences {

        private final String target;

        private List<Integer> beginIndexes = new ArrayList<>();     // 有需要的话可根据target字符串中该下标对应的字符进行去重
        private int length;                                         // 当前最长序列的长度

        private LongestSequences(String target) {
            this.target = target;
        }

        /**
         * <p>检测目标字符串中，以 {@code beginIdxOfSeq} 为起始下标，长度为 {@code length} 的字符序列，并对当前结果做增量更新
         * <ol>
         *  <li> 如果其长度比所有当前检测过的字符序列的都要长则只保留该序列，并刷新{@link LongestSequences#length}；
         *  <li> 如果其长度刚好等于{@link LongestSequences#length}，则将其保存到当前结果中；
         *  <li> 否则无需做任何操作。
         * </ol>
         */
        private void accept(int beginIndex, int length) {
            if (length < this.length)
                return;

            if (length > this.length) {
                this.length = length;
                beginIndexes.clear();
            }
            beginIndexes.add(beginIndex);
        }

        // Getter、Setter

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Integer beginIdx : beginIndexes) {
                sb.append(target.subSequence(beginIdx, beginIdx + length)).append(" = ").append(length)
                        .append(", ");
            }
            sb.setLength(sb.length() - 2);
            return sb.toString();
        }
    }




    public static class LongestSequencesWithoutDuplicate {

        private final String target;

        private Map<Character, Integer> charBeginIdxMap = new HashMap<>();
        private int length;

        private LongestSequencesWithoutDuplicate(String target) {
            this.target = target;
        }

        private void accept(int beginIdxOfSeq, int length) {

            if (length < this.length)
                return;

            if (length > this.length) {
                this.length = length;
                charBeginIdxMap.clear();
            }

            charBeginIdxMap.put(target.charAt(beginIdxOfSeq), beginIdxOfSeq);
        }

        // Getter、Setter

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Integer beginIdx : charBeginIdxMap.values()) {
                sb.append(target.subSequence(beginIdx, beginIdx + length)).append(" = ").append(length)
                        .append(", ");
            }
            sb.setLength(sb.length() - 2);
            return sb.toString();
        }
    }

}
