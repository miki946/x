package predictions;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ListModel;

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author fourplus <fourplus1718@gmail.com>
 * @since 1.0
 * @version 11 Changes done
 */

public class DualListBox extends JPanel {

	private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

	private static final String ADD_BUTTON_LABEL = "Add >>";

	private static final String REMOVE_BUTTON_LABEL = "<< Remove";

	private static final String RUN_BUTTON_LABEL = "RUN";

	private static final String DEFAULT_SOURCE_CHOICE_LABEL = "Available Choices";

	private static final String DEFAULT_DEST_CHOICE_LABEL = "Your Choices";

	private JLabel sourceLabel;

	private JList sourceList;

	private boolean predictOrUpdate;

	private SortedListModel<Object> sourceListModel;

	private JList destList;

	private SortedListModel<Object> destListModel;

	private JLabel destLabel;

	private JButton addButton;

	private JButton removeButton;

	private JButton runButton;

	private ButtonGroup chooseButton;

	public DualListBox() {
		initScreen();
	}

	public String getSourceChoicesTitle() {
		return sourceLabel.getText();
	}

	public void setSourceChoicesTitle(String newValue) {
		sourceLabel.setText(newValue);
	}

	public String getDestinationChoicesTitle() {
		return destLabel.getText();
	}

	public void setDestinationChoicesTitle(String newValue) {
		destLabel.setText(newValue);
	}

	public void clearSourceListModel() {
		sourceListModel.clear();
	}

	public void clearDestinationListModel() {
		destListModel.clear();
	}

	public void addSourceElements(ListModel newValue) {
		fillListModel(sourceListModel, newValue);
	}

	public void setSourceElements(ListModel newValue) {
		clearSourceListModel();
		addSourceElements(newValue);
	}

	public void addDestinationElements(ListModel newValue) {
		fillListModel(destListModel, newValue);
	}

	private void fillListModel(SortedListModel<Object> model, ListModel newValues) {
		int size = newValues.getSize();
		for (int i = 0; i < size; i++) {
			model.add(newValues.getElementAt(i));
		}
	}

	public void addSourceElements(Object newValue[]) {
		fillListModel(sourceListModel, newValue);
	}

	public void setSourceElements(Object newValue[]) {
		clearSourceListModel();
		addSourceElements(newValue);
	}

	public void addDestinationElements(Object newValue[]) {
		fillListModel(destListModel, newValue);
	}

	private void fillListModel(SortedListModel<Object> model, Object newValues[]) {
		model.addAll(newValues);
	}



	private void clearSourceSelected() {
		Object selected[] = sourceList.getSelectedValues();
		for (int i = selected.length - 1; i >= 0; --i) {
			sourceListModel.removeElement(selected[i]);
		}
		sourceList.getSelectionModel().clearSelection();
	}

	private void clearDestinationSelected() {
		Object selected[] = destList.getSelectedValues();
		for (int i = selected.length - 1; i >= 0; --i) {
			destListModel.removeElement(selected[i]);
		}
		destList.getSelectionModel().clearSelection();
	}

	public ArrayList<String> getDestinationList() {
		ArrayList<String> result = new ArrayList<>();
		for (Object i : destListModel.model.toArray()) {
			result.add((String) i);
		}

		return result;
	}

	private void initScreen() {
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new GridBagLayout());
		sourceLabel = new JLabel(DEFAULT_SOURCE_CHOICE_LABEL);
		sourceListModel = new SortedListModel<Object>();
		sourceList = new JList(sourceListModel);
		add(sourceLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		add(new JScrollPane(sourceList), new GridBagConstraints(0, 1, 1, 5, .5, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, EMPTY_INSETS, 0, 0));

		chooseButton = new ButtonGroup();
		JToggleButton button1 = new JToggleButton("Predict");
		JToggleButton button2 = new JToggleButton("Update");
		chooseButton.add(button1);
		chooseButton.add(button2);
		add(button1, new GridBagConstraints(0, 0, 1, 2, 0.5, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		add(button2, new GridBagConstraints(0, 1, 1, 2, 0.5, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		button1.addActionListener(new PredictToggleListener());
		button2.addActionListener(new UpdateToggleListener());

		addButton = new JButton(ADD_BUTTON_LABEL);
		add(addButton, new GridBagConstraints(1, 6, 1, 2, 0, .25, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		addButton.addActionListener(new AddListener());
		removeButton = new JButton(REMOVE_BUTTON_LABEL);
		add(removeButton, new GridBagConstraints(1, 8, 1, 2, 0, .25, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 5, 0, 5), 0, 0));
		removeButton.addActionListener(new RemoveListener());

		runButton = new JButton(RUN_BUTTON_LABEL);
		add(runButton, new GridBagConstraints(1, 10, 1, 2, 0, .25, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		runButton.addActionListener(new RunListener());

		destLabel = new JLabel(DEFAULT_DEST_CHOICE_LABEL);
		destListModel = new SortedListModel<Object>();
		destList = new JList(destListModel);
		add(destLabel, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		add(new JScrollPane(destList), new GridBagConstraints(2, 1, 1, 5, .5, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, EMPTY_INSETS, 0, 0));
	}

	public static void init(ArrayList<String> rs) {
		JFrame f = new JFrame("Dual List Box Tester");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DualListBox dual = new DualListBox();
		dual.addSourceElements(rs.toArray());
		f.getContentPane().add(dual, java.awt.BorderLayout.CENTER);
		f.setSize(400, 300);
		f.setVisible(true);
	}

	private class AddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object selected[] = sourceList.getSelectedValues();
			addDestinationElements(selected);
			clearSourceSelected();
		}
	}

	private class RemoveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object selected[] = destList.getSelectedValues();
			addSourceElements(selected);
			clearDestinationSelected();
		}
	}

	private class RunListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ArrayList<String> list = getDestinationList();
//			if (predictOrUpdate)
//				try {
//					Predictions.predictions(2017, DataType.ODDSPORTAL, UpdateType.AUTOMATIC, OnlyTodayMatches.TRUE);
//				} catch (InterruptedException | ExecutionException | IOException e1) {
//					e1.printStackTrace();
//				}
//			else
//				try {
////					Scraper.updateInParallel(list, 1, OnlyTodayMatches.TRUE, UpdateType.AUTOMATIC);
//				} catch (IOException | InterruptedException e1) {
//					e1.printStackTrace();
//				}

			// System.exit(0);
		}
	}

	private class PredictToggleListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			predictOrUpdate = true;
		}
	}

	private class UpdateToggleListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			predictOrUpdate = false;
		}
	}

}

class SortedListModel<T> extends AbstractListModel<T> {

	SortedSet<T> model;

	public SortedListModel() {
		model = new TreeSet<T>();
	}

	public int getSize() {
		return model.size();
	}

	public T getElementAt(int index) {
		T t = (T)(model.toArray()[index]);
		return t;
	}

	public void add(T element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}

	public void addAll(Object elements[]) {
		Collection<T> c = (Collection<T>) Arrays.asList(elements);
		model.addAll(c);
		fireContentsChanged(this, 0, getSize());
	}

	public void clear() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
	}

	public boolean contains(Object element) {
		return model.contains(element);
	}

	public T firstElement() {
		return model.first();
	}

	public Iterator<T> iterator() {
		return model.iterator();
	}

	public T lastElement() {
		return model.last();
	}

	public boolean removeElement(Object element) {
		boolean removed = model.remove(element);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		return removed;
	}
}