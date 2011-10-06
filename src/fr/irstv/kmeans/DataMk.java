package fr.irstv.kmeans;

import java.util.LinkedList;
import java.util.List;

import fr.irstv.dataModel.DataPoint;
import fr.irstv.dataModel.MkDataPoint;
import fr.irstv.dataModel.Segment;

/**
 * specific class for reading MK points data files
 * see in XML file, no DTD or schema yet
 *
 * @author moreau
 *
 */
public class DataMk extends DataCorpus {

	/**
	 *@author Cedric Telegone & Leo Collet
	 *reading data from pg.data.group
	 *
	 *modified on March 7th 2011 by Elsa Arrou-Vignod & Florent Buisson
	 *(added MkCorpus in order not to lose the segment information)
	 *@param a pg.data.group 
	 */

	protected LinkedList<MkDataPoint> MkCorpus;
	
	public LinkedList<MkDataPoint> getMkCorpus() {
		return MkCorpus;
	}

	public DataMk(List<fr.ecn.common.core.geometry.Segment> segmentsList) {
		super();
		MkCorpus = new LinkedList<MkDataPoint>();
		
		for (fr.ecn.common.core.geometry.Segment segment : segmentsList) {
			DataPoint dp1 = new DataPoint(2);
			DataPoint dp2 = new DataPoint(2);

			dp1.set(0, segment.getP1().getX());
			dp1.set(1, segment.getP1().getY());
			dp2.set(0, segment.getP2().getX());
			dp2.set(1, segment.getP2().getY());
			Segment s = new Segment(dp1,dp2,null);
			MkDataPoint mkp = new MkDataPoint(s.getHPoint(),s);
			corpus.add(mkp);
			MkCorpus.add(mkp);
		}
		
	}

}
