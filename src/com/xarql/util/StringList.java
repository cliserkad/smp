package com.xarql.util;

public class StringList extends BestList<String> {
    private static final long serialVersionUID = 1L;

    public StringList(String... strings) {
        super(strings);
    }

    public String find(String target) {
        for(String s : this)
            if(s.contains(target))
                return s;
        return null;
    }

    public String findNear(String target, int limit) {
        for(String s : this)
            if(Levenshtein.isNear(target, s, limit))
                return s;
        return null;
    }

    public StringList findAll(String target) {
        StringList output = new StringList();
        for(String s : this)
            if(s.contains(target))
                output.add(s);
        return output;
    }

    public StringList findAllNear(String target, int limit) {
        StringList output = new StringList();
        for(String s : this)
            if(Levenshtein.isNear(target, s, limit))
                output.add(s);
        return output;
    }

    public String findNearest(String target) {
        String bestFit = "";
        int bestDist = Integer.MAX_VALUE;
        int dist;
        for(String s : this) {
            if(target.length() < s.length())
                dist = Levenshtein.distance(target, s.substring(0, target.length()));
            else
                dist = Levenshtein.distance(target, s);
            if(dist < bestDist) {
                bestDist = dist;
                bestFit = s;
            }
        }
        return bestFit;
    }

    /**
     * Like findNearest, but doesn't truncate the strings in this list to match the
     * length of the input
     * 
     * @see StringList#findNearest(String)
     * @param target what to look for
     * @return String w/ lowest Levenshtein distance
     */
    public String findAbsoluteNearest(String target) {
        String bestFit = "";
        int bestDist = Integer.MAX_VALUE;
        int dist;
        for(String s : this) {
            dist = Levenshtein.distance(target, s);
            if(dist < bestDist) {
                bestDist = dist;
                bestFit = s;
            }
        }
        return bestFit;
    }

    public String findFuzzy(String target) {
        if(contains(target))
            return target;

        return findNearest(target);
    }

}
