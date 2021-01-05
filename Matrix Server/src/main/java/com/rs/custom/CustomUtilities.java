package com.rs.custom;

public class CustomUtilities {
    public static boolean isMetalName(String metal) {
        switch(metal.toLowerCase()) {
            case "bronze":
            case "iron":
            case "steel":
            case "black":
            case "mithril":
            case "adamant":
            case "rune":
            case "dragon":
                return true;
            default:
                break;
        }
        return false;
    }
}
