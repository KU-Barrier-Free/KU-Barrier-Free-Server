package com.example.BarrierKU.domain.building.service;

import com.example.BarrierKU.domain.indoor.Room;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomNumberComparator implements Comparator<Room> {

    @Override
    public int compare(Room r1, Room r2) {
        ParsedRoomNumber p1 = parseRoomNumber(r1.getRoomNumber());
        ParsedRoomNumber p2 = parseRoomNumber(r2.getRoomNumber());

        // prefix 비교
        int prefixCompare = p1.prefix.compareTo(p2.prefix);
        if (prefixCompare != 0) return prefixCompare;

        // 숫자 비교
        int mainCompare = Integer.compare(p1.main, p2.main);
        if (mainCompare != 0) return mainCompare;

        // suffix 비교
        int suffixCompare = p1.suffix.compareTo(p2.suffix);
        if (suffixCompare != 0) return suffixCompare;

        // sub 비교
        int subCompare = Integer.compare(p1.sub, p2.sub);
        if (subCompare != 0) return subCompare;

        // 문자열 전체 비교
        return r1.getRoomNumber().compareTo(r2.getRoomNumber());
    }

    private static class ParsedRoomNumber {
        String prefix = ""; // 숫자 앞 알파벳
        int main = Integer.MAX_VALUE; // 숫자 부분
        String suffix = ""; // 숫자 뒤 알파벳
        int sub = -1; // 하이픈 뒤 숫자
    }

    private ParsedRoomNumber parseRoomNumber(String number) {
        ParsedRoomNumber parsed = new ParsedRoomNumber();
        Matcher matcher = Pattern.compile("^([A-Za-z]*)(\\d+)([A-Za-z]*)?(?:-(\\d+))?$").matcher(number);

        if (matcher.matches()) {
            parsed.prefix = matcher.group(1) == null ? "" : matcher.group(1);
            parsed.main = Integer.parseInt(matcher.group(2));
            parsed.suffix = matcher.group(3) == null ? "" : matcher.group(3);
            if (matcher.group(4) != null) {
                parsed.sub = Integer.parseInt(matcher.group(4));
            }
        } else {
            parsed.prefix = number;
        }
        return parsed;
    }
}
