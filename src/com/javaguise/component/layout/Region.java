package com.javaguise.component.layout;

/**Indicates a region of a larger area in internationalized relative terms.
@author Garret Wilson
*/
public enum Region
{

	/**At the beginning of a line; "left" in left-to-right, top-to-botom orientation.*/
	LINE_START,

	/**At the end of a line; "right" in left-to-right, top-to-botom orientation.*/
	LINE_END,

	/**At the beginning of a page; "top" in left-to-right, top-to-botom orientation.*/
	PAGE_START,

	/**At the end of a page; "bottom" in left-to-right, top-to-botom orientation.*/
	PAGE_END,
	
	/**In the center of the region.*/
	CENTER;

	/**The number of regions for each of the the line and page flows.*/
	public final static int FLOW_REGION_COUNT=3;

	/**The three regions for each axis/direction combination.*/
	protected final static Region[][][] REGIONS=new Region[Axis.values().length][Orientation.Direction.values().length][FLOW_REGION_COUNT];

	/**Determines the corresponding region for an orientation flow and absolute region number.
	For example, a left-to-right, top-to-bottom page flow of index <code>2</code> will yield {@link #PAGE_END},
	while a right-to-left, top-to-bottom line flow of index <code>2</code> will yield {@link #LINE_START}.
	@param orientation The component orientation.
	@param flow The flow (line or page).
	@param regionIndex The absolute region index (0, 1, or 2) from the upper left-hand corner.
	@exception IllegalArgumentException if the given region index is less than <code>0</code> or greater than <code>2</code>. 
	*/
	public static Region getRegion(final Orientation orientation, final Orientation.Flow flow, final int regionIndex)
	{
		if(regionIndex<0 || regionIndex>=FLOW_REGION_COUNT)	//if an invalid region index was given
		{
			throw new IllegalArgumentException("Illegal region index: "+regionIndex);
		}
		return REGIONS[orientation.getAxis(flow).ordinal()][orientation.getDirection(flow).ordinal()][regionIndex];	//look up the region in the table
	}

	static
	{
			//initialize the regions lookup table
		REGIONS[Axis.X.ordinal()][Orientation.Direction.INCREASING.ordinal()]=new Region[]{LINE_START, CENTER, LINE_END};
		REGIONS[Axis.X.ordinal()][Orientation.Direction.DECREASING.ordinal()]=new Region[]{LINE_END, CENTER, LINE_START};
		REGIONS[Axis.Y.ordinal()][Orientation.Direction.INCREASING.ordinal()]=new Region[]{PAGE_START, CENTER, PAGE_END};
		REGIONS[Axis.Y.ordinal()][Orientation.Direction.DECREASING.ordinal()]=new Region[]{PAGE_END, CENTER, PAGE_START};		
		REGIONS[Axis.Z.ordinal()][Orientation.Direction.INCREASING.ordinal()]=new Region[]{null, null, null};	//we don't currently use the Z axis
		REGIONS[Axis.Z.ordinal()][Orientation.Direction.DECREASING.ordinal()]=new Region[]{null, null, null};
	}
}
