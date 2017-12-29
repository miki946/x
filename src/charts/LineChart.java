package charts;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author fourplus <fourplus1718@gmail.com>
 * @since 1.0
 * @version 11 Changes done
 */
public class LineChart extends ApplicationFrame {
	public LineChart(float[] data, String description) {
		super("Profit " + description);
		JFreeChart lineChart = ChartFactory.createLineChart("", "Bets", "Units", createDataset(data),
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
	}

	private DefaultCategoryDataset createDataset(float[] data) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < data.length; i++) {
			dataset.addValue(data[i], "", new Float(i));
		}
		return dataset;
	}

	public static void draw(float[] data, String description) {
		LineChart chart = new LineChart(data, description);

		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
}
