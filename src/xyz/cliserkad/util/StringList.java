package xyz.cliserkad.util;

import java.io.Serial;

public class StringList extends BestList<String> {

	@Serial
	private static final long serialVersionUID = 1L;

	public StringList(final String... strings) {
		super(strings);
	}

	public String find(final String target) {
		for(final String s : this) {
			if(s.contains(target)) {
				return s;
			}
		}
		return null;
	}

	public String findNear(final String target, final int limit) {
		for(final String s : this) {
			if(Levenshtein.isNear(target, s, limit)) {
				return s;
			}
		}
		return null;
	}

	public StringList findAll(final String target) {
		final var output = new StringList();
		for(final String s : this) {
			if(s.contains(target)) {
				output.add(s);
			}
		}
		return output;
	}

	public StringList findAllNear(final String target, final int limit) {
		final var output = new StringList();
		for(final String s : this) {
			if(Levenshtein.isNear(target, s, limit)) {
				output.add(s);
			}
		}
		return output;
	}

	public String findNearest(final String target) {
		var bestFit = "";
		var bestDist = Integer.MAX_VALUE;
		int dist;
		for(final String s : this) {
			if(target.length() < s.length()) {
				dist = Levenshtein.distance(target, s.substring(0, target.length()));
			} else {
				dist = Levenshtein.distance(target, s);
			}
			if(dist < bestDist) {
				bestDist = dist;
				bestFit = s;
			}
		}
		return bestFit;
	}

	/**
	 * Like findNearest, but doesn't truncate the strings in this list to match the length of the input
	 *
	 * @param target what to look for
	 * @return String w/ lowest Levenshtein distance
	 * @see StringList#findNearest(String)
	 */
	public String findAbsoluteNearest(final String target) {
		var bestFit = "";
		var bestDist = Integer.MAX_VALUE;
		int dist;
		for(final String s : this) {
			dist = Levenshtein.distance(target, s);
			if(dist < bestDist) {
				bestDist = dist;
				bestFit = s;
			}
		}
		return bestFit;
	}

	public String findFuzzy(final String target) {
		if(contains(target)) {
			return target;
		}

		return findNearest(target);
	}

}
