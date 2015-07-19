package goit.nz.kickstartermvc;

import goit.nz.kickstartermvc.controller.CategoryController;
import goit.nz.kickstartermvc.controller.MainPageController;
import goit.nz.kickstartermvc.controller.ProjectController;
import goit.nz.kickstartermvc.input.Input;
import goit.nz.kickstartermvc.model.CategoryModel;
import goit.nz.kickstartermvc.model.MainPageModel;
import goit.nz.kickstartermvc.model.ProjectModel;
import goit.nz.kickstartermvc.output.Output;
import goit.nz.kickstartermvc.view.CategoryView;
import goit.nz.kickstartermvc.view.MainPageView;
import goit.nz.kickstartermvc.view.ProjectView;

public class Kickstarter {

	private MainPageController mainPageController;
	private CategoryController categoryController;
	private ProjectController projectController;
	private Input input;
	private Dispatcher dispatcher;

	public Kickstarter(DataStorage storage, Output output, Input input) {
		this.input = input;
		mainPageController = new MainPageController(
				new MainPageModel(storage),
				new MainPageView(output));
		categoryController = new CategoryController(new CategoryModel(storage),
				new CategoryView(output), mainPageController);
		projectController = new ProjectController(new ProjectModel(),
				new ProjectView(output), categoryController);
		dispatcher = new Dispatcher();
	}

	public void run() {
		input.registerListener(dispatcher);
		registerDispatcherListeners();
		mainPageController.onAppStart();
		input.listenInput();
	}

	private void registerDispatcherListeners() {
		dispatcher.registerListener(mainPageController);
		dispatcher.registerListener(categoryController);
		dispatcher.registerListener(projectController);
	}

}
