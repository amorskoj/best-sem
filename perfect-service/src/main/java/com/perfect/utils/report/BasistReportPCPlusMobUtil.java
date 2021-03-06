package com.perfect.utils.report;


import com.perfect.dto.StructureReportDTO;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Created by SubDong on 2014/8/13.
 */
public class BasistReportPCPlusMobUtil extends RecursiveTask<List<StructureReportDTO>> {

    private final int threshold = 100;

    private int endNumber;
    private int begin;
    private String userName;

    private List<StructureReportDTO> objectList;

    public BasistReportPCPlusMobUtil(List<StructureReportDTO> objects, int begin, int endNumber, String userName) {
        this.objectList = objects;
        this.endNumber = endNumber;
        this.begin = begin;
        this.userName = userName;
    }

    @Override
    protected List<StructureReportDTO> compute() {
        List<StructureReportDTO> list = new ArrayList<>();
        if ((endNumber - begin) < threshold) {
            DecimalFormat df = new DecimalFormat("#.0000");
            for (int i = begin; i < endNumber; i++) {
                objectList.get(i).setAccount(userName);
                objectList.get(i).setPcClick(objectList.get(i).getPcClick() + ((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick()));
                objectList.get(i).setPcConversion(objectList.get(i).getPcConversion() + ((objectList.get(i).getMobileConversion() == null) ? 0 : objectList.get(i).getMobileClick()));
                objectList.get(i).setPcCost(objectList.get(i).getPcCost().add((objectList.get(i).getMobileCost() == null) ? BigDecimal.valueOf(0) : objectList.get(i).getMobileCost()));
                //计算点击率
                if (((objectList.get(i).getMobileImpression() == null) ? 0 : objectList.get(i).getMobileImpression()) == 0) {
                    objectList.get(i).setPcCtr(0.00);
                    if (((objectList.get(i).getPcImpression() == null) ? 0 : objectList.get(i).getPcImpression()) == 0) {
                        objectList.get(i).setPcCtr(0d);
                    } else {
                        BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format((objectList.get(i).getPcClick().doubleValue() / objectList.get(i).getPcImpression().doubleValue()))));
                        BigDecimal big = new BigDecimal(1);
                        double divide = ctrBig.multiply(big).doubleValue();
                        divide = new BigDecimal(divide * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        objectList.get(i).setPcCtr(divide);
                    }
                } else {
                    double newNumber = Double.parseDouble(df.format((objectList.get(i).getPcClick().doubleValue() + ((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick().doubleValue())) / (objectList.get(i).getPcImpression().doubleValue() + ((objectList.get(i).getMobileImpression() == null) ? 0 : objectList.get(i).getMobileImpression().doubleValue()))));
                    BigDecimal ctrBig = new BigDecimal(newNumber);
                    BigDecimal big = new BigDecimal(1);
                    double divide = ctrBig.multiply(big).doubleValue();
                    divide = new BigDecimal(divide * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    objectList.get(i).setPcCtr(divide);
                }
                //计算平均点击价格
                if (((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick()) == 0) {
                    if (((objectList.get(i).getPcClick() == null) ? 0 : objectList.get(i).getPcClick()) == 0) {
                        objectList.get(i).setPcCpc(BigDecimal.valueOf(0));
                    } else {
                        objectList.get(i).setPcCpc((objectList.get(i).getPcCost().divide(BigDecimal.valueOf(objectList.get(i).getPcClick()), 2, BigDecimal.ROUND_UP)));
                    }
                } else {
                    BigDecimal newNumber = (objectList.get(i).getPcCost().add((objectList.get(i).getMobileCost() == null) ? BigDecimal.valueOf(0) : objectList.get(i).getMobileCost())).divide(BigDecimal.valueOf(objectList.get(i).getPcClick() + ((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick())), 2, BigDecimal.ROUND_UP);
                    objectList.get(i).setPcCpc(newNumber);
                }
                objectList.get(i).setPcImpression(objectList.get(i).getPcImpression() + ((objectList.get(i).getMobileImpression() == null) ? 0 : objectList.get(i).getMobileImpression()));
                objectList.get(i).setMobileClick(null);
                objectList.get(i).setMobileConversion(null);
                objectList.get(i).setMobileCost(null);
                objectList.get(i).setMobileCtr(null);
                objectList.get(i).setMobileCpc(null);
                objectList.get(i).setMobileImpression(null);
                list.add(objectList.get(i));
            }
            return list;
        } else {
            int midpoint = (begin + endNumber) / 2;
            BasistReportPCPlusMobUtil left = new BasistReportPCPlusMobUtil(objectList, begin, midpoint, userName);
            BasistReportPCPlusMobUtil right = new BasistReportPCPlusMobUtil(objectList, midpoint, endNumber, userName);
            left.fork();
            right.fork();
            list.addAll(left.join());
            list.addAll(right.join());
            return list;
        }
    }
}
