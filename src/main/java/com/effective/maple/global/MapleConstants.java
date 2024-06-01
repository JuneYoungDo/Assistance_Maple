package com.effective.maple.global;

import com.effective.maple.global.exception.BaseException;
import com.effective.maple.global.exception.errorCode.CharacterErrorCode;
import java.util.HashMap;
import java.util.Map;

public class MapleConstants {

    private MapleConstants() {

    }

    // 직업 이름을 변수 종류에 매핑하는 맵
    private static final Map<String, String> jobToVariableTypeMap = new HashMap<>();

    // 변수 종류를 실제 변수 문자열에 매핑하는 맵
    private static final Map<String, String> variableTypeToVariablesMap = new HashMap<>();

    static {
        variableTypeToVariablesMap.put("warrior", "str dex attack_power 공격력");
        variableTypeToVariablesMap.put("mage", "int luk magic_power 마력");
        variableTypeToVariablesMap.put("archer", "dex str attack_power 공격력");
        variableTypeToVariablesMap.put("thief", "luk dex attack_power 공격력");
        variableTypeToVariablesMap.put("str pirate", "str dex attack_power 공격력");
        variableTypeToVariablesMap.put("dex pirate", "dex str attack_power 공격력");

        variableTypeToVariablesMap.put("duCaSha", "제논듀카섀");
        variableTypeToVariablesMap.put("zenon", "제논듀카섀");

        // 직업과 변수 종류 매핑 초기화
        // Warrior jobs
        jobToVariableTypeMap.put("검사", "warrior");
        jobToVariableTypeMap.put("파이터", "warrior");
        jobToVariableTypeMap.put("크루세이더", "warrior");
        jobToVariableTypeMap.put("히어로", "warrior");
        jobToVariableTypeMap.put("페이지", "warrior");
        jobToVariableTypeMap.put("나이트", "warrior");
        jobToVariableTypeMap.put("팔라딘", "warrior");
        jobToVariableTypeMap.put("스피어맨", "warrior");
        jobToVariableTypeMap.put("버서커", "warrior");
        jobToVariableTypeMap.put("다크나이트", "warrior");
        jobToVariableTypeMap.put("소울마스터", "warrior");
        jobToVariableTypeMap.put("미하일", "warrior");
        jobToVariableTypeMap.put("블래스터", "warrior");
        jobToVariableTypeMap.put("데몬슬레이어", "warrior");
        jobToVariableTypeMap.put("데몬어벤져", "warrior");
        jobToVariableTypeMap.put("아란", "warrior");
        jobToVariableTypeMap.put("카이저", "warrior");
        jobToVariableTypeMap.put("아델", "warrior");
        jobToVariableTypeMap.put("제로", "warrior");

        // Mage jobs
        jobToVariableTypeMap.put("매지션", "mage");
        jobToVariableTypeMap.put("위자드(불,독)", "mage");
        jobToVariableTypeMap.put("메이지(불,독)", "mage");
        jobToVariableTypeMap.put("아크메이지(불,독)", "mage");
        jobToVariableTypeMap.put("위자드(썬,콜)", "mage");
        jobToVariableTypeMap.put("메이지(썬,콜)", "mage");
        jobToVariableTypeMap.put("아크메이지(썬,콜)", "mage");
        jobToVariableTypeMap.put("클레릭", "mage");
        jobToVariableTypeMap.put("프리스트", "mage");
        jobToVariableTypeMap.put("비숍", "mage");
        jobToVariableTypeMap.put("플레임위자드", "mage");
        jobToVariableTypeMap.put("배틀메이지", "mage");
        jobToVariableTypeMap.put("에반", "mage");
        jobToVariableTypeMap.put("루미너스", "mage");
        jobToVariableTypeMap.put("일리움", "mage");
        jobToVariableTypeMap.put("라라", "mage");
        jobToVariableTypeMap.put("키네시스", "mage");

        // Archer jobs
        jobToVariableTypeMap.put("아처", "archer");
        jobToVariableTypeMap.put("헌터", "archer");
        jobToVariableTypeMap.put("레인저", "archer");
        jobToVariableTypeMap.put("보우마스터", "archer");
        jobToVariableTypeMap.put("사수", "archer");
        jobToVariableTypeMap.put("저격수", "archer");
        jobToVariableTypeMap.put("신궁", "archer");
        jobToVariableTypeMap.put("와일드헌터", "archer");
        jobToVariableTypeMap.put("윈드브레이커", "archer");
        jobToVariableTypeMap.put("메르세데스", "archer");
        jobToVariableTypeMap.put("패스파인더", "archer");
        jobToVariableTypeMap.put("카인", "archer");

        // Thief jobs
        jobToVariableTypeMap.put("로그", "thief");
        jobToVariableTypeMap.put("어쌔신", "thief");
        jobToVariableTypeMap.put("허밋", "thief");
        jobToVariableTypeMap.put("나이트로드", "thief");
        jobToVariableTypeMap.put("슬래셔", "thief");
        jobToVariableTypeMap.put("듀얼블레이더", "thief");
        jobToVariableTypeMap.put("팬텀", "thief");
        jobToVariableTypeMap.put("나이트워커", "thief");
        jobToVariableTypeMap.put("제논", "zenon");
        jobToVariableTypeMap.put("호영", "thief");
        jobToVariableTypeMap.put("칼리", "thief");

        jobToVariableTypeMap.put("시프", "duCaSha");
        jobToVariableTypeMap.put("시프마스터", "duCaSha");
        jobToVariableTypeMap.put("섀도어", "duCaSha");
        jobToVariableTypeMap.put("듀얼블레이드", "duCaSha");
        jobToVariableTypeMap.put("세미듀어러", "duCaSha");
        jobToVariableTypeMap.put("듀어러", "duCaSha");
        jobToVariableTypeMap.put("듀얼마스터", "duCaSha");
        jobToVariableTypeMap.put("카데나", "duCaSha");

        // Str Pirate jobs
        jobToVariableTypeMap.put("해적", "str pirate");
        jobToVariableTypeMap.put("인파이터", "str pirate");
        jobToVariableTypeMap.put("버커니어", "str pirate");
        jobToVariableTypeMap.put("바이퍼", "str pirate");
        jobToVariableTypeMap.put("스트라이커", "str pirate");
        jobToVariableTypeMap.put("캐논슈터", "str pirate");
        jobToVariableTypeMap.put("캐논마스터", "str pirate");
        jobToVariableTypeMap.put("캐논블래스터", "str pirate");
        jobToVariableTypeMap.put("은월", "str pirate");
        jobToVariableTypeMap.put("아크", "str pirate");

        // Dex Pirate jobs
        jobToVariableTypeMap.put("건슬링거", "dex pirate");
        jobToVariableTypeMap.put("발키리", "dex pirate");
        jobToVariableTypeMap.put("캡틴", "dex pirate");
        jobToVariableTypeMap.put("메카닉", "dex pirate");
        jobToVariableTypeMap.put("엔젤릭버스터", "dex pirate");

    }

    public static boolean isZenonAndDuCaSha(String key) {
        return variableTypeToVariablesMap.get(jobToVariableTypeMap.get(key)).equals("제논듀카섀");
    }

    public static String getValue(String key) {
        if (jobToVariableTypeMap.containsKey(key)) {
            return variableTypeToVariablesMap.get(jobToVariableTypeMap.get(key));
        } else {
            throw new BaseException(CharacterErrorCode.INVALID_JOB);
        }
    }
}
