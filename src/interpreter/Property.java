package interpreter;

import java.util.Observable;
import java.util.Observer;

import commands.ConnectCommand;

public class Property extends Observable implements Observer {
	private Double value;

	public Property(Double value) {
		this.value = value;
	}

	public Double getValue() {
		return value;
	}

	public synchronized void setValue(Double value) {
		if (this.value.doubleValue() != value.doubleValue()) {
			this.value = value;
			setChanged();
			notifyObservers();
			setInSimulator();
		}
	}

	private void setInSimulator() {
		String name = MyInterpreter.getBindingTable().getVarName(this);
		if (name != null)
			try {
				ConnectCommand.updateSimulator(name, this);
			} catch (Exception e) {
			}
	}

	@Override
	public void update(Observable o, Object arg) {
		Property prop = (Property) o;
		if (value == null || value.doubleValue() != prop.value.doubleValue()) {
			this.setValue(prop.getValue());

		}
	}

	public void bind(Property prop) {
		addObserver(prop);
	}

	public void bindBi(Property p) {
		if (this != p) {
			this.addObserver(p);
			p.addObserver(this);
		}
	}
}
