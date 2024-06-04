package com.effective.maple.domain.service;

import com.effective.maple.domain.dto.CompareDto.ItemInformation;
import com.effective.maple.domain.dto.OptimizeDto.OptimizeStat;
import com.effective.maple.domain.dto.ResponseDto.ItemInfo;
import com.effective.maple.domain.usecase.SearchUseCase;
import com.effective.maple.global.MapleConstants;
import com.effective.maple.global.exception.BaseException;
import com.effective.maple.global.exception.errorCode.CharacterErrorCode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService implements SearchUseCase {

    private final MapleService mapleService;

    private static int COUNT = 0;

    @Override
    public List<ItemInfo> getItemListUp(ItemInformation itemInformation)
        throws IOException, ParseException, InterruptedException {
        log.info("호출 횟수 : " + ++COUNT);
        OptimizeStat optimizeStat = itemInformation.getCustomAllPart().getOptimizeStat();
        String nickname = removeWhitespace(itemInformation.getCustomAllPart().getNickname());
        validateString(nickname);
        log.info("닉네임 : " + nickname);
        String preset = itemInformation.getCustomAllPart().getPreset();
        JSONObject jsonObject = mapleService.getStatInformation(nickname);
        optimizeStat.setLevel(((Number) jsonObject.get("level")).intValue());
        return getAllItemInformation(itemInformation.getCustomItemList(), optimizeStat, jsonObject,
            preset);
    }

    private List<ItemInfo> getAllItemInformation(List<ItemInfo> customItems,
        OptimizeStat optimizeStat, JSONObject jsonObject,
        String preset) {
        List<ItemInfo> itemInfoList = new ArrayList<>();
        if (customItems != null) {
            for (ItemInfo customItem : customItems) {
                customItem.setValue(customItem.getValue() * 10);
                itemInfoList.add(customItem);
            }
        }
        String characterClass = (String) jsonObject.get("character_class");
        log.info(characterClass);
        if (MapleConstants.isZenonAndDuCaSha(characterClass)) {
            log.info("적용 스탯 - 제논듀카섀");
            itemInfoList = getAllItemInformationForZenonAndDuCaSha(customItems, characterClass,
                optimizeStat,
                jsonObject, preset);
        } else {
            String type = MapleConstants.getValue(characterClass);
            log.info("적용 스탯 - " + type);
            List<String> statList = splitString(type);
            String mainStat = statList.get(0);
            String subStat = statList.get(1);
            String attackType = statList.get(2);
            String potentialAttackType = statList.get(3);
            JSONArray itemPreset = (JSONArray) jsonObject.get("item_equipment_preset_" + preset);
            for (Object o : itemPreset) {
                JSONObject item = (JSONObject) o;
                if (isWeaponOrEmblem(item)) {
                    continue;
                }
                ItemInfo itemInfo = createItemInfo(item, optimizeStat, mainStat, subStat,
                    attackType,
                    potentialAttackType);
                itemInfoList.add(itemInfo);
            }
            sortByValueDescending(itemInfoList);
            itemInfoList.forEach(i -> changeToProperResultForPercent(optimizeStat, i));
        }
        return itemInfoList;
    }

    private List<ItemInfo> getAllItemInformationForZenonAndDuCaSha(List<ItemInfo> customItems,
        String characterClass,
        OptimizeStat optimizeStat, JSONObject jsonObject,
        String preset) {
        List<ItemInfo> itemInfoList = new ArrayList<>();
        if (customItems != null) {
            for (ItemInfo customItem : customItems) {
                customItem.setValue(customItem.getValue() * 10);
                itemInfoList.add(customItem);
            }
        }
        JSONArray itemPreset = (JSONArray) jsonObject.get("item_equipment_preset_" + preset);

        for (Object o : itemPreset) {
            JSONObject item = (JSONObject) o;
            if (isWeaponOrEmblem(item)) {
                continue;
            }
            ItemInfo itemInfo;
            if (characterClass.equals("제논")) {
                itemInfo = createItemInfoForZenonAndDuCaSha(item, optimizeStat, true);
            } else {
                itemInfo = createItemInfoForZenonAndDuCaSha(item, optimizeStat, false);
            }
            itemInfoList.add(itemInfo);
        }
        sortByValueDescending(itemInfoList);
        itemInfoList.forEach(i -> changeToProperResultForPercent(optimizeStat, i));
        return itemInfoList;
    }

    private boolean isWeaponOrEmblem(JSONObject item) {
        String slot = (String) item.get("item_equipment_slot");
        return slot.equals("무기") || slot.equals("보조무기") || slot.equals("엠블렘");
    }

    private ItemInfo createItemInfo(JSONObject item, OptimizeStat optimizeStat, String mainStat,
        String subStat, String attackType, String potentialAttackType) {
        String img = (String) item.get("item_icon");
        String name = (String) item.get("item_name");

        double basicStat = calculateAddOption(optimizeStat, mainStat, subStat, attackType,
            (JSONObject) item.get("item_base_option"), true);
        double addStat = calculateAddOption(optimizeStat, mainStat, subStat, attackType,
            (JSONObject) item.get("item_add_option"), true);
        double etcStat = calculateAddOption(optimizeStat, mainStat, subStat, attackType,
            (JSONObject) item.get("item_etc_option"), false);
        double exceptionalStat = calculateAddOption(optimizeStat, mainStat, subStat, attackType,
            (JSONObject) item.get("item_exceptional_option"), false);
        double starforceStat = calculateAddOption(optimizeStat, mainStat, subStat, attackType,
            (JSONObject) item.get("item_starforce_option"), false);
        double potentialStat = potentialCalculate(optimizeStat, mainStat, subStat,
            (String) item.get("potential_option_1"), (String) item.get("potential_option_2"),
            (String) item.get("potential_option_3"));
        double additionalStat = additionalCalculate(optimizeStat, mainStat, subStat,
            potentialAttackType, (String) item.get("additional_potential_option_1"),
            (String) item.get("additional_potential_option_2"),
            (String) item.get("additional_potential_option_3"));

        int starforce = Integer.parseInt((String) item.get("starforce"));
        double totalStat =
            basicStat + addStat + etcStat + exceptionalStat + starforceStat + potentialStat
                + additionalStat;

        return new ItemInfo(img, name, basicStat, etcStat + exceptionalStat, addStat, potentialStat,
            additionalStat, starforce, starforceStat, totalStat);
    }

    private ItemInfo createItemInfoForZenonAndDuCaSha(JSONObject item, OptimizeStat optimizeStat,
        boolean isZenon) {
        String img = (String) item.get("item_icon");
        String name = (String) item.get("item_name");

        double basicStat = calculateAddOptionForZenonAndDuCaSha(optimizeStat,
            (JSONObject) item.get("item_base_option"), true, isZenon);
        double addStat = calculateAddOptionForZenonAndDuCaSha(optimizeStat,
            (JSONObject) item.get("item_add_option"), true, isZenon);
        double etcStat = calculateAddOptionForZenonAndDuCaSha(optimizeStat,
            (JSONObject) item.get("item_etc_option"), false, isZenon);
        double exceptionalStat = calculateAddOptionForZenonAndDuCaSha(optimizeStat,
            (JSONObject) item.get("item_exceptional_option"), false, isZenon);
        double starforceStat = calculateAddOptionForZenonAndDuCaSha(optimizeStat,
            (JSONObject) item.get("item_starforce_option"), false, isZenon);
        double potentialStat = potentialCalculateForZenonAndDuCaSha(optimizeStat, isZenon,
            (String) item.get("potential_option_1"), (String) item.get("potential_option_2"),
            (String) item.get("potential_option_3"));
        double additionalStat = additionalCalculateForZenonAndDuCaSha(optimizeStat, isZenon,
            (String) item.get("additional_potential_option_1"),
            (String) item.get("additional_potential_option_2"),
            (String) item.get("additional_potential_option_3"));

        int starforce = Integer.parseInt((String) item.get("starforce"));
        double totalStat =
            basicStat + addStat + etcStat + exceptionalStat + starforceStat + potentialStat
                + additionalStat;

        return new ItemInfo(img, name, basicStat, etcStat + exceptionalStat, addStat, potentialStat,
            additionalStat, starforce, starforceStat, totalStat);
    }

    private double calculateAddOption(OptimizeStat optimizeStat, String mainS,
        String subS, String attackType, JSONObject jsonObject, boolean isAdd) {
        String mainStat = (String) jsonObject.get(mainS);
        String subStat = (String) jsonObject.get(subS);
        String attack = (String) jsonObject.get(attackType);
        String allStat;
        double totalStat;
        if (isAdd) {
            allStat = (String) jsonObject.get("all_stat");
            totalStat =
                Integer.parseInt(mainStat) * optimizeStat.getMain()
                    + Integer.parseInt(subStat) * optimizeStat.getSub()
                    + Integer.parseInt(attack) * optimizeStat.getAttack()
                    + Integer.parseInt(allStat) * optimizeStat.getAllPer();
        } else {
            totalStat =
                Integer.parseInt(mainStat) * optimizeStat.getMain()
                    + Integer.parseInt(subStat) * optimizeStat.getSub()
                    + Integer.parseInt(attack) * optimizeStat.getAttack();
        }
        return roundToTwoDecimalPlace(totalStat);
    }

    private double calculateAddOptionForZenonAndDuCaSha(OptimizeStat optimizeStat,
        JSONObject jsonObject, boolean isAdd, boolean isZenon) {
        String str = (String) jsonObject.get("str");
        String dex = (String) jsonObject.get("dex");
        String luk = (String) jsonObject.get("luk");
        String attack = (String) jsonObject.get("attack_power");
        String allStat;
        double totalStat = 0;
        if (isAdd) {
            allStat = (String) jsonObject.get("all_stat");
            totalStat +=
                (Integer.parseInt(allStat) * optimizeStat.getAllPer());
        }
        if (isZenon) {
            totalStat +=
                (Integer.parseInt(str) * optimizeStat.getMain() +
                    Integer.parseInt(dex) * optimizeStat.getMain() +
                    Integer.parseInt(luk) * optimizeStat.getMain() +
                    Integer.parseInt(attack) * optimizeStat.getAttack());
        } else {
            totalStat +=
                (Integer.parseInt(str) * optimizeStat.getSub() +
                    Integer.parseInt(dex) * optimizeStat.getSub() +
                    Integer.parseInt(luk) * optimizeStat.getMain() +
                    Integer.parseInt(attack) * optimizeStat.getAttack());
        }
        return roundToTwoDecimalPlace(totalStat);
    }

    private double additionalCalculate(OptimizeStat optimizeStat, String mainStat, String subStat,
        String potentialAttackType, String... pots) {
        return calculatePotentialStat(optimizeStat, mainStat, subStat, potentialAttackType, true,
            pots);
    }

    private double potentialCalculate(OptimizeStat optimizeStat, String mainStat, String subStat,
        String... pots) {
        return calculatePotentialStat(optimizeStat, mainStat, subStat, null, false, pots);
    }

    private double additionalCalculateForZenonAndDuCaSha(OptimizeStat optimizeStat, boolean isZenon,
        String... pots) {
        return calculatePotentialStatForZenon(optimizeStat, true, isZenon, pots);
    }

    private double potentialCalculateForZenonAndDuCaSha(OptimizeStat optimizeStat, boolean isZenon,
        String... pots) {
        return calculatePotentialStatForZenon(optimizeStat, false, isZenon, pots);
    }


    private double calculatePotentialStat(OptimizeStat optimizeStat, String mainStat,
        String subStat,
        String potentialAttackType, boolean isAdditional, String... pots) {
        double totalStat = 0;

        for (String pot : pots) {
            if (pot != null) {
                totalStat += calculateSingleStat(optimizeStat, mainStat, subStat,
                    potentialAttackType, isAdditional, pot);
            }
        }

        return roundToTwoDecimalPlace(totalStat);
    }

    private double calculateSingleStat(OptimizeStat optimizeStat, String mainStat, String subStat,
        String potentialAttackType, boolean isAdditional, String pot) {
        double stat = 0;
        if (compareFirstThreeChars(mainStat, pot)) {
            stat += calculateMainOrSubStat(optimizeStat.getMain(), optimizeStat.getMainPer(), pot);
        } else if (compareFirstThreeChars(subStat, pot)) {
            stat += calculateMainOrSubStat(optimizeStat.getSub(), optimizeStat.getSubPer(), pot);
        } else if (pot.startsWith("올스탯")) {
            stat += calculateAllStat(optimizeStat, pot);
        } else if (pot.startsWith("크리티컬 데미지")) {
            stat += optimizeStat.getCritical() * extractNumberBetweenPlusAndPercent(pot);
        } else if (isAdditional && pot.startsWith(potentialAttackType)) {
            stat += optimizeStat.getAttack() * extractNumberAfterPlus(pot);
        } else if (isAdditional && pot.startsWith("캐릭터")) {
            if (pot.contains(mainStat.toUpperCase())) {
                stat += ((optimizeStat.getLevel() / 9) * extractNumberAfterPlus(pot));
            } else if (pot.contains(subStat.toUpperCase())) {
                stat += ((optimizeStat.getLevel() / 9) * extractNumberAfterPlus(pot)
                    * optimizeStat.getSub());
            }
        }

        return stat;
    }

    private double calculatePotentialStatForZenon(OptimizeStat optimizeStat, boolean isAdditional,
        boolean isZenon, String... pots) {
        double totalStat = 0;

        for (String pot : pots) {
            if (pot != null) {
                if (isZenon) {
                    totalStat += calculateSingleStatForZenonAndDuCaSha(optimizeStat, isAdditional,
                        true,
                        pot);
                } else {
                    totalStat += calculateSingleStatForZenonAndDuCaSha(optimizeStat, isAdditional,
                        false,
                        pot);
                }
            }
        }

        return roundToTwoDecimalPlace(totalStat);
    }

    private double calculateSingleStatForZenonAndDuCaSha(OptimizeStat optimizeStat,
        boolean isAdditional,
        boolean isZenon, String pot) {
        double stat = 0;
        if (compareFirstThreeChars("str", pot)) {
            if (isZenon) {
                stat += calculateMainOrSubStat(optimizeStat.getMain(), optimizeStat.getMainPer(),
                    pot);
            } else {
                stat += calculateMainOrSubStat(optimizeStat.getSub(), optimizeStat.getSubPer(),
                    pot);
            }
        } else if (compareFirstThreeChars("dex", pot)) {
            if (isZenon) {
                stat += calculateMainOrSubStat(optimizeStat.getMain(), optimizeStat.getMainPer(),
                    pot);
            } else {
                stat += calculateMainOrSubStat(optimizeStat.getSub(), optimizeStat.getSubPer(),
                    pot);
            }
        } else if (compareFirstThreeChars("luk", pot)) {
            stat += calculateMainOrSubStat(optimizeStat.getMain(), optimizeStat.getMainPer(), pot);
        } else if (pot.startsWith("올스탯")) {
            stat += calculateAllStat(optimizeStat, pot);
        } else if (pot.startsWith("크리티컬 데미지")) {
            stat += optimizeStat.getCritical() * extractNumberBetweenPlusAndPercent(pot);
        } else if (isAdditional && pot.startsWith("공격력")) {
            stat += optimizeStat.getAttack() * extractNumberAfterPlus(pot);
        } else if (isAdditional && pot.startsWith("캐릭터")) {
            if (pot.contains("STR")) {
                if (isZenon) {
                    stat += ((optimizeStat.getLevel() / 9) * extractNumberAfterPlus(pot))
                        * optimizeStat.getMain();
                } else {
                    stat += ((optimizeStat.getLevel() / 9) * extractNumberAfterPlus(pot))
                        * optimizeStat.getSub();
                }
            } else if (pot.contains("DEX")) {
                if (isZenon) {
                    stat += ((optimizeStat.getLevel() / 9) * extractNumberAfterPlus(pot))
                        * optimizeStat.getMain();
                } else {
                    stat += ((optimizeStat.getLevel() / 9) * extractNumberAfterPlus(pot))
                        * optimizeStat.getSub();
                }
            } else if (pot.contains("LUK")) {
                stat += ((optimizeStat.getLevel() / 9) * extractNumberAfterPlus(pot))
                    * optimizeStat.getMain();
            }
        }

        return stat;
    }

    private double calculateMainOrSubStat(double oneStat, double perStat, String pot) {
        if (pot.endsWith("%")) {
            return perStat * extractNumberBetweenPlusAndPercent(pot);
        } else {
            return extractNumberAfterPlus(pot) * oneStat;
        }
    }

    private double calculateAllStat(OptimizeStat optimizeStat, String pot) {
        if (pot.endsWith("%")) {
            return optimizeStat.getAllPer() * extractNumberBetweenPlusAndPercent(pot);
        } else {
            double number = extractNumberAfterPlus(pot);
            return number + optimizeStat.getSub() * number * optimizeStat.getMain();
        }
    }

    private int extractNumberAfterPlus(String str) {
        int plusIndex = str.indexOf('+');
        String numberStr = str.substring(plusIndex + 1);
        return Integer.parseInt(numberStr);
    }

    private int extractNumberBetweenPlusAndPercent(String str) {
        int plusIndex = str.indexOf('+');
        int percentIndex = str.indexOf('%');
        String numberStr = str.substring(plusIndex + 1, percentIndex);
        return Integer.parseInt(numberStr);
    }

    private boolean compareFirstThreeChars(String str1, String str2) {
        String substr1 = str1.substring(0, 3).toLowerCase();
        String substr2 = str2.substring(0, 3).toLowerCase();
        return substr1.equals(substr2);
    }

    private List<String> splitString(String input) {
        return Arrays.asList(input.split(" "));
    }

    private double roundToTwoDecimalPlace(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private void sortByValueDescending(List<ItemInfo> itemList) {
        itemList.sort((o1, o2) -> Double.compare(o2.getValue(), o1.getValue()));
    }

    private void changeToProperResultForPercent(OptimizeStat optimizeStat, ItemInfo itemInfo) {
        itemInfo.setPotential(
            roundToTwoDecimalPlace(itemInfo.getPotential() / 10));
        itemInfo.setAdditional(
            roundToTwoDecimalPlace(itemInfo.getAdditional() / 10));
        itemInfo.setStarForceStat(
            roundToTwoDecimalPlace(itemInfo.getStarForceStat() / 10));
        itemInfo.setValue(roundToTwoDecimalPlace(itemInfo.getValue() / 10));
    }

    private static String removeWhitespace(String str) {
        // 문자열에서 모든 공백을 제거하고 반환
        return str.replaceAll("\\s", "");
    }

    private void validateString(String str) {
        if (str.length() < 2) {
            throw new BaseException(CharacterErrorCode.INVALID_NICKNAME_INPUT);
        }
        // 정규 표현식을 사용하여 문자열이 한글, 영어, 숫자, 공백만 포함하는지 확인
        if (!str.matches("[\\p{L}0-9\\s]+")) {
            throw new BaseException(CharacterErrorCode.INVALID_NICKNAME_INPUT);
        }
    }
}
