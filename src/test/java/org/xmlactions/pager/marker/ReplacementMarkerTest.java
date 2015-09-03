package org.xmlactions.pager.marker;


import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.pager.marker.Location;

import junit.framework.TestCase;

public class ReplacementMarkerTest extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(ReplacementMarkerTest.class);
	public void testReplacement()
	{
		log.debug(".");
		List<Location> markers = new ArrayList<Location>();
		markers.add(new Location(4,17,"two[.....]"));
		markers.add(new Location(4,5,"(:::)"));
		markers.add(new Location(2,1,"xxoxx"));
		markers.add(new Location(2,1,"ooxooo"));
		markers.add(new Location(8,1,"B"));
		String page = "one <...two <...>...>...";
		String b = page;
		String c;
		int xOffset = 0;
		for (int loop = 0 ; loop < markers.size() ; loop++)
		{
			Location marker = markers.get(loop);
			c = b.substring(0, marker.getStart()+xOffset);
			c += marker.getReplacementContent();
			c += b.substring(marker.getEnd()+xOffset);
			b = c;
			xOffset += marker.getStart();
			log.debug("xOffset:" + xOffset + " result(" + c + ")");
		}
	}
}
