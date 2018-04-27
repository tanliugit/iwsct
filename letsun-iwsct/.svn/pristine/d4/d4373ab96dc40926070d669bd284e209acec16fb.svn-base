package com.letsun.iwsct.itface.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.letsun.iwsct.itface.model.Gift;

/**
 * 2014-10-29 add by wayne
 * 不同概率抽奖工具包
 *
 * @author Shunli
 */
public class LotteryUtil {
    /**
     * 抽奖
     *
     * @param orignalRates 原始的概率列表，保证顺序和实际物品对应
     * @return 物品的索引
     */
    public static int lottery(List<Double> orignalRates) {
        if (orignalRates == null || orignalRates.isEmpty()) {
            return -1;
        }

        int size = orignalRates.size();

        // 计算总概率，这样可以保证不一定总概率是1
        double sumRate = 0d;
        for (double rate : orignalRates) {
            sumRate += rate;
        }

        // 计算每个物品在总概率的基础下的概率情况
        List<Double> sortOrignalRates = new ArrayList<Double>(size);
        Double tempSumRate = 0d;
        
        /**
        for (double rate : orignalRates) {
            tempSumRate += rate;
            sortOrignalRates.add(tempSumRate / sumRate);
            
            System.out.println("sortOrignalRates="+tempSumRate / sumRate);
        }
        System.out.println("sortOrignalRates1="+sortOrignalRates);
        // 根据区块值来获取抽取到的物品索引
        double nextDouble = Math.random() * sumRate; 
        sortOrignalRates.add(nextDouble);
        Collections.sort(sortOrignalRates);
        */

        double nextDouble = Math.random();
        
        //减小中奖概率
        if(sumRate>1) nextDouble=nextDouble*sumRate;
        
        //大于总概率 直接不中奖
        int index=-1;
        if(nextDouble>sumRate) return index;
        
        tempSumRate = 0d;
        for (index = 0; index < orignalRates.size(); index++) {
        	tempSumRate += orignalRates.get(index);
	        if (nextDouble <= tempSumRate) {
	        	break;
	        }
	        sortOrignalRates.add(tempSumRate / sumRate);
        }

        return index;
    }

    public static void main(String[] args) {
        List<Gift> gifts = new ArrayList<Gift>();
        // 序号==物品Id==物品名称==概率
        gifts.add(new Gift(1, "P1", "物品1", 0.1d/100));
        gifts.add(new Gift(2, "P2", "物品2", 12.67d/100));
        gifts.add(new Gift(3, "P3", "物品3", 0.05d/100));
        gifts.add(new Gift(4, "P4", "物品4", 1.2d/100));
        gifts.add(new Gift(5, "P5", "物品5", 0.5d/100));
        gifts.add(new Gift(6, "P6", "物品6", 2d/100));
        gifts.add(new Gift(7, "P7", "物品7", 2d/100));

        List<Double> orignalRates = new ArrayList<Double>(gifts.size());
        for (Gift gift : gifts) {
            double probability = gift.getProbability();
            if (probability < 0) {
                probability = 0;
            }
            orignalRates.add(probability);
        }

        int orignalIndex =-1;
        // statistics
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        double num = 100;
        for (int i = 0; i < num; i++) {
             orignalIndex = LotteryUtil.lottery(orignalRates);

             if(orignalIndex!=-1){
                 Integer value = count.get(orignalIndex);
                 count.put(orignalIndex, value == null ? 1 : value + 1);
             }
        }

        for (Entry<Integer, Integer> entry : count.entrySet()) {
            System.out.println(gifts.get(entry.getKey()) + ", count=" + entry.getValue() + ", probability=" + entry.getValue() / num);
        }
        
    }
}
