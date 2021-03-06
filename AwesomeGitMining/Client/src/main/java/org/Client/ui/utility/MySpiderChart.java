package org.Client.ui.utility;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;

import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.data.category.CategoryDataset;
/**
 * 重写spiderWebPlot可以显示刻度及对应数据
 * @author user
 *
 */
public class MySpiderChart extends SpiderWebPlot{
	
	private static final long serialVersionUID = 1L;
	
	private int maxValue=8;
	
	public MySpiderChart(CategoryDataset categoryDataset) {
		super(categoryDataset);
		this.setSeriesPaint(Color.PINK);
		this.setSeriesOutlinePaint(Color.pink);
		this.setSeriesOutlineStroke(new BasicStroke(3));
		this.setMaxValue(8);
		this.setBackgroundAlpha(0);
		this.setAxisLinePaint(new Color(255, 255,255,0));
	}
	
	@Override
	public void drawOutline(Graphics2D g2, Rectangle2D area) {
	}
	
	//是否显示指标标签
	@Override
	public void drawLabel(final Graphics2D g2, final Rectangle2D plotArea,final double value, 
			final int cat, final double startAngle,final double extent) {
		
	}
	
	@Override
	protected void drawRadarPoly(Graphics2D g2, Rectangle2D plotArea, Point2D centre, PlotRenderingInfo info,
			int series, int catCount, double headH, double headW) {
		// TODO Auto-generated method stub
		super.drawRadarPoly(g2, plotArea, centre, info, series, catCount, 8,8);
		
		
		for (int cat = 0; cat < catCount; cat++) {

            Number dataValue = getPlotValue(series, cat);

            if (dataValue != null) {
                double value = dataValue.doubleValue();

                if (value >= 0) { // draw the polygon series...

                    // Finds our starting angle from the centre for this axis

                    double angle = getStartAngle() + (getDirection().getFactor() * cat * 360 / catCount);

                    Point2D point = getWebPoint(plotArea, angle,
                            value / this.maxValue);

                    BigDecimal bd = new BigDecimal(value);
                    bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
                    g2.setFont(new Font("Arail", Font.ITALIC, 16));
                    g2.setColor(new Color(52,52,52));
                    g2.drawString(bd+"",(float)(point.getX() - headW / 2+6),(float)( point.getY() - headH / 2+12));

                    }

                
            }
        }
	}
	
}
