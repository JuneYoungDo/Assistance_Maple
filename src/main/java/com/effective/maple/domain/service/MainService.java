package com.effective.maple.domain.service;

import com.effective.maple.domain.dto.OptimizeDto.CustomAllPart;
import com.effective.maple.domain.dto.OptimizeDto.OptimizeStat;
import com.effective.maple.domain.dto.CompareDto.ChangeInformation;
import com.effective.maple.domain.dto.CompareDto.EdiPotentialInformation;
import com.effective.maple.domain.dto.CompareDto.EquipInformation;
import com.effective.maple.domain.dto.CompareDto.ItemInformation;
import com.effective.maple.domain.dto.CompareDto.PotentialInformation;
import com.effective.maple.domain.dto.ResponseDto.ItemInfo;
import com.effective.maple.domain.dto.ResponseDto.Result;
import com.effective.maple.domain.usecase.MainUseCase;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MainService implements MainUseCase {

    @Override
    public CustomAllPart getInitCustomAllPart() {
        CustomAllPart itemListUpReq = new CustomAllPart();
        OptimizeStat optimizeStat = new OptimizeStat();
        optimizeStat.setMain(1);
        optimizeStat.setLevel(0);
        optimizeStat.setAttack(4);
        optimizeStat.setSub(0.1);
        optimizeStat.setMainPer(10);
        optimizeStat.setSubPer(1);
        optimizeStat.setAllPer(11);
        optimizeStat.setCritical(40);
        optimizeStat.setBoss(11);
        itemListUpReq.setOptimizeStat(optimizeStat);
        itemListUpReq.setNickname("");
        itemListUpReq.setPreset("1");
        return itemListUpReq;
    }

    @Override
    public ItemInformation addItemToCharacter(ItemInformation itemInformation, int type) {
        if (type == 3) {
            if (itemInformation.getCustomItemList() == null) {
                List<ItemInfo> newCustomList = new ArrayList<>();
                itemInformation.setCustomItemList(newCustomList);
            }
            itemInformation.getCustomItemList().add(
                new ItemInfo(
                    null, "CUSTOM", null, null, null, 0d, 0d, 0, 0d,
                    Double.parseDouble(
                        itemInformation.getEfficientResult().getResult1().getAllStatPer()))
            );
        } else if (type == 4) {
            if (itemInformation.getCustomItemList() == null) {
                List<ItemInfo> newCustomList = new ArrayList<>();
                itemInformation.setCustomItemList(newCustomList);
            }
            itemInformation.getCustomItemList().add(
                new ItemInfo(
                    null, "CUSTOM", null, null, null, 0d, 0d, 0, 0d,
                    Double.parseDouble(
                        itemInformation.getEfficientResult().getResult2().getAllStatPer()))
            );
        }
        return itemInformation;
    }

    @Override
    public ItemInformation getEfficientResult(ItemInformation itemInformation, int type) {
        if (type != 1 && type != 2) {
            type = type / 2;
        }
        double mainStat = getMainStat(itemInformation, type);
        double price;
        if (type == 1) {
            price = itemInformation.getItem1().getPrice().getPrice();
        } else {
            price = itemInformation.getItem2().getPrice().getPrice();
        }
        double mainPer = Math.round(mainStat / 10 * 1000) / 1000.0;
        double priceForMainPer = Math.round(price / mainPer / 100000000 * 1000) / 1000.0;
        double mainPerForPrice = Math.round(mainPer / (price / 100000000) * 1000) / 1000.0;

        if (type == 1) {
            itemInformation.getEfficientResult()
                .setResult1(new Result(formatDouble(mainPer), formatDouble(priceForMainPer),
                    formatDouble(mainPerForPrice)));
        } else {
            itemInformation.getEfficientResult()
                .setResult2(new Result(formatDouble(mainPer), formatDouble(priceForMainPer),
                    formatDouble(mainPerForPrice)));
        }

        return itemInformation;
    }

    private double getMainStat(ItemInformation itemInformation, int type) {
        if (type == 1) {
            double mainStat = getEquipMainStat(itemInformation.getItem1().getEquipInformation(),
                itemInformation.getCustomAllPart().getOptimizeStat())
                + getPotentialMainStat(itemInformation.getItem1().getPotentialInformation(),
                itemInformation.getCustomAllPart().getOptimizeStat())
                + getEdiPotentialMainStat(itemInformation.getItem1().getEdiPotentialInformation(),
                itemInformation.getCustomAllPart().getOptimizeStat());
            double changeStat = getChangeMainStat(
                itemInformation.getItem1().getChangeInformation(),
                itemInformation.getCustomAllPart().getOptimizeStat()
            );
            return mainStat - changeStat;
        } else {
            double mainStat = getEquipMainStat(itemInformation.getItem2().getEquipInformation(),
                itemInformation.getCustomAllPart().getOptimizeStat())
                + getPotentialMainStat(itemInformation.getItem2().getPotentialInformation(),
                itemInformation.getCustomAllPart().getOptimizeStat())
                + getEdiPotentialMainStat(itemInformation.getItem2().getEdiPotentialInformation(),
                itemInformation.getCustomAllPart().getOptimizeStat());
            double changeStat = getChangeMainStat(
                itemInformation.getItem2().getChangeInformation(),
                itemInformation.getCustomAllPart().getOptimizeStat()
            );
            return mainStat + changeStat;
        }
    }


    private double getEquipMainStat(EquipInformation equipInformation, OptimizeStat optimizeStat) {
        double mainStat = equipInformation.getMain() * optimizeStat.getMain();
        double subToMain = equipInformation.getSub() * optimizeStat.getSub();
        double attackToMain = equipInformation.getAttack() * optimizeStat.getAttack();
        double allPerToMain = equipInformation.getAllPer() * optimizeStat.getAllPer();

        return mainStat + subToMain + attackToMain + allPerToMain;
    }

    private double getChangeMainStat(ChangeInformation changeInformation,
        OptimizeStat optimizeStat) {
        double mainStat = changeInformation.getMain() * optimizeStat.getMain();
        double subToMain = changeInformation.getSub() * optimizeStat.getSub();
        double attackToMain = changeInformation.getAttack() * optimizeStat.getAttack();
        double bossToMain = changeInformation.getBoss() * optimizeStat.getBoss();
        double criticalToMain = changeInformation.getCritical() * optimizeStat.getCritical();

        return mainStat + subToMain + attackToMain + bossToMain + criticalToMain;
    }

    private double getPotentialMainStat(PotentialInformation potentialInformation,
        OptimizeStat optimizeStat) {
        double mainStat = potentialInformation.getMain() * optimizeStat.getMain();
        double mainPerToMain = potentialInformation.getMainPer() * optimizeStat.getMainPer();
        double subPerToMain = potentialInformation.getSubPer() * optimizeStat.getSubPer();
        double allPerToMain = potentialInformation.getAllPer() * optimizeStat.getAllPer();
        double subToMain = potentialInformation.getSub() * optimizeStat.getSub();
        double criticalToMain = potentialInformation.getCritical() * optimizeStat.getCritical();

        return mainStat + mainPerToMain + subPerToMain + allPerToMain + subToMain + criticalToMain;
    }

    private double getEdiPotentialMainStat(EdiPotentialInformation ediPotentialInformation,
        OptimizeStat optimizeStat) {
        double mainStat = ediPotentialInformation.getMain() * optimizeStat.getMain();
        double mainPerToMain = ediPotentialInformation.getMainPer() * optimizeStat.getMainPer();
        double subPerToMain = ediPotentialInformation.getSubPer() * optimizeStat.getSubPer();
        double allPerToMain = ediPotentialInformation.getAllPer() * optimizeStat.getAllPer();
        double subToMain = ediPotentialInformation.getSub() * optimizeStat.getSub();
        double attackToMain = ediPotentialInformation.getAttack() * optimizeStat.getAttack();
        double criticalToMain = ediPotentialInformation.getCritical() * optimizeStat.getCritical();
        double levelToMain = ediPotentialInformation.getLevel() * (optimizeStat.getLevel() / 9);

        return mainStat + mainPerToMain + subPerToMain + allPerToMain + subToMain + attackToMain
            + criticalToMain + levelToMain;
    }

    private String formatDouble(double number) {
        DecimalFormat df = new DecimalFormat("#.###");
        return df.format(number);
    }
}
