package it.polimi.ingsw.GC_18.utils;

public interface EasterEggs {

    /**
     * 
     * @param str the string to check.
     * @return true if the parameter doesn't contain in adjacent positions the
     *         letters that compose "CARLOS"
     */
    static boolean containsAdjacentCarlosAnagram(String str) {
        final String carlos = "carlos";
        String str1 = str.toLowerCase();
        for (int i = 0; i < str1.length() - carlos.length() + 1; i++) {
            if (areAnagrams(carlos, str1.substring(i, i + carlos.length())))
                return true;
        }
        return false;
    }

    static boolean areAnagrams(String str1, String str2) {
        if (str1.length() != str2.length())
            return false;
        int[] freq = new int[256];
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        for (int i = 0; i < ch1.length; i++) {
            freq[ch1[i]]++;
        }
        for (int i = 0; i < ch2.length; i++) {
            freq[ch2[i]]--;
            if (freq[ch2[i]] < 0)
                return false;
        }
        return true;
    }

}
