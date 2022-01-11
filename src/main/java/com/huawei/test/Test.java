package com.huawei.test;

import java.util.LinkedList;

public class Test {

    public static void main(String[] args) {


    }

    private static String minWindow(String origin, String target) {
        if (origin.length() < target.length()) {
            return "";
        }
        char[] os = origin.toCharArray();
        char[] ts = target.toCharArray();
        int slow = 0;
        int fast = 0;
        int all = ts.length;
        int leftIndex = -1;
        int rightIndex = -1;
        int minLen = Integer.MAX_VALUE;
        int[] map = new int[256];
        for (char t : ts) {
            map[t]++;
        }
        while (fast < os.length) {
            map[os[fast]]--;
            System.out.println(map[os[fast]]);
            if (map[os[fast]] >= 0) {
                all--;
            }
            if (all == 0) {
                while (map[os[slow]] < 0) {
                    map[os[slow++]]++;
                }
                if (minLen > fast - slow + 1) {
                    minLen = fast - slow + 1;
                    leftIndex = slow;
                    rightIndex = fast;
                }
                all++;
                map[os[slow++]]++;
            }
            fast++;
        }
        return minLen == Integer.MAX_VALUE ? "" : origin.substring(leftIndex, rightIndex + 1);
    }


    public static boolean isPalindrome(int x) {
        String str = String.valueOf(x);
        char[] arr = str.toCharArray();
        int len = str.length();
        int mid = len / 2;
        if (mid == 0) {
            return true;
        }
        for (int i = 0; i < mid; i++) {
            if (arr[i] != (arr[len - 1 - i])) {
                return false;
            }
        }
        return false;
    }


    public static int maxSubArraySum(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }


    //窗口 L左边界  R右边界
    public static int[] getWindowMaxArr(int[] arr, int w) {
        int len = arr.length;
        //数组长度必须大于等于窗口长度
        if (len >= w) {
            int[] res = new int[len - w + 1];
            //收集窗口最大值结果数组的下标
            int index = 0;
            //放置最大值下标的双端链表 从按数值的大到小放置
            LinkedList<Integer> list = new LinkedList<>();
            for (int i = 0; i < len; i++) {
                // 数组下标放入双端链表 右边界移动 判断 当前值是否满足放置条件。链里比当前值小的弹出 然后链条尾部加入当前值
                while (!list.isEmpty() && arr[list.peekLast()] <= arr[i]) {
                    //尾部弹出
                    list.pollLast();
                }
                list.addLast(i);
                //头部弹出 （左边界消失 判断消失的值的索引在不在链里。在->弹出 ）
                if (i - w == list.peekFirst()) {
                    list.pollFirst();
                }
                //收集答案的条件即生成了窗口,然后开始移动窗口
                if (i >= w - 1) {
                    res[index++] = arr[list.peekFirst()];
                }
            }
            return res;
        }
        return new int[]{};
    }


    //一个数组中只有一个数字出现了奇数次，其余数字都出现了偶数次，求这个奇数次的数
    public static void oddNum(int[] arr) {
        int eor = 0;
        for (int item : arr) {
            eor ^= item;
        }
        System.out.println("出现奇数次的数字是：" + eor);
    }

    //一个数组中有2个不相同的数a,b 出现了奇数次，其余数字出现偶数次，求这两个奇数次的数a,b
    public static void twoOddNum(int[] arr) {
        int one = 0;
        //循环后得出a^b的结果
        for (int item : arr) {
            one ^= item;
        }
        int two = 0;
        //因为a!=b 所以a^b不等于0;所以必然a或b的二进制中的某一位必然为1
        //找到a或b的最后侧 提数的二进制中最右侧的1
        int one_1 = one & (~one + 1);
        for (int item : arr) {
            //运算结果得到 a 或 b
            if ((item & one_1) == 0) {
                two ^= item;
            }
        }
        //输出a或b
        System.out.println(two);
        //输出另一个数
        System.out.println(one ^ two);

    }
}
