package kickstarter.pages.modelContent;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import kickstarter.mvc.interfaces.iController;
import kickstarter.repository.facade.FileSystemRepository;
import kickstarter.repository.facade.Repository;

public class RepositoryMenuModel extends PageModel {
	private Repository inMemoryRepository;
	private Repository deserializedRepository;
	private FileSystemRepository fileSystemRepository;
	private iController icontroller;

	public RepositoryMenuModel(Repository inMemoryRepository,
			FileSystemRepository fileSystemRepository, iController icontroller) {

		this.inMemoryRepository = inMemoryRepository;
		this.fileSystemRepository = fileSystemRepository;
		this.icontroller = icontroller;
	}

	@Override
	public void updateStateOfPageModel(String message) {
		if (message.equals("p")) {
			imodel.next(CATEGORIES);
			return;
		}
		if (message.equals("m")) {
			icontroller.setInMemoryRepository();
			imodel.getViewOptions().repositoryError = false;
			imodel.next(CATEGORIES);
			return;
		}
		if (message.equals("e")) {
			imodel.next(END_PAGE);
			return;
		}
		if (message.equals("c")) {
			try (ObjectOutputStream out = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream("object.ser")))) {

				out.writeObject(inMemoryRepository);
			} catch (IOException e) {
				imodel.goToAndBack(ERROR_PAGE, REPOSITORY_MENU_PAGE);
				return;
			}
			imodel.getViewOptions().repositoryError = false;
			imodel.next(CATEGORIES);
			return;
		}
		if (message.equals("f")) {

			try (ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream("object.ser")))) {
				deserializedRepository = (Repository) in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				imodel.goToAndBack(ERROR_PAGE, REPOSITORY_MENU_PAGE);
				return;
			}
			inMemoryRepository=deserializedRepository;
			icontroller.setIRepository(deserializedRepository);
			imodel.getViewOptions().repositoryError = false;
			imodel.next(CATEGORIES);
			return;
		}
		imodel.goToAndBack(ERROR_PAGE, REPOSITORY_MENU_PAGE);
	}


}