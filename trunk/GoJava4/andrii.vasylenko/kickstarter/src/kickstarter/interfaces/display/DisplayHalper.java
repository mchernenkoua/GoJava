package kickstarter.interfaces.display;

import java.io.IOException;

import kickstarter.engine.Data;
import kickstarter.interfaces.printers.Printer;
import kickstarter.interfaces.readers.Reader;
import kickstarter.storages.Storage;

public class DisplayHalper<T extends Data> {
	private Printer printer;
	private Reader reader;
	private Display<T> display;
	private Storage<T> storage;

	public DisplayHalper(Printer printer, Reader reader, Display<T> display, Storage<T> storage) {
		this.printer = printer;
		this.reader = reader;
		this.display = display;
		this.storage = storage;
	}

	public DisplayHalper(Printer printer, Reader reader, Display<T> display) {
		this.printer = printer;
		this.reader = reader;
		this.display = display;
	}

	public Data choiceItem(String head) {
		while (true) {
			try {
				return showChoiceItemDialog(head);
			} catch (IndexOutOfBoundsException ignore) {
			} catch (NumberFormatException ignore) {
			} catch (IOException ignore) {
			}
			printer.showMessage("--------------------");
			printer.showMessage("try again please");
		}
	}

	private Data showChoiceItemDialog(String head) throws NumberFormatException, IOException {
		int itemId = choiceItemId(head, getItemsDescription());
		if (itemId == Data.Defaults.EXIT.getId()) {
			return Data.Defaults.EXIT;
		}
		return getItemById(itemId);
	}

	private String getItemsDescription() {
		StringBuilder result = new StringBuilder();

		if (storage != null) {
			for (int i = 0; i < storage.size(); i++) {
				result.append(display.getDescription(storage.get(i)));
				result.append("\r\n");
			}
		}

		result.append(Data.Defaults.EXIT.getId() + " - exit");

		return result.toString();
	}

	private Data getItemById(int id) {
		if (storage == null) {
			throw new IndexOutOfBoundsException();
		}
		Data result = storage.getById(id);
		return result;
	}

	private int choiceItemId(String head, String items) throws NumberFormatException, IOException {
		printer.showMessage("--------------------");
		printer.showMessage(head);
		printer.showMessage(items);
		return Integer.parseInt(reader.getLine());
	}

}