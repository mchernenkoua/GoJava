package ua.goit.kyrychok.kickstarter.mvc.view;

import ua.goit.kyrychok.kickstarter.Output;
import ua.goit.kyrychok.kickstarter.model.Project;
import ua.goit.kyrychok.kickstarter.mvc.model.CategoryModel;

import java.util.Date;
import java.util.List;

import static ua.goit.kyrychok.kickstarter.Utils.*;

public class CategoryView {
    private Output output;

    public CategoryView(Output output) {
        this.output = output;
    }

    public void render(CategoryModel model) {
        output.writeLine(model.getName());
        List<Project> projects = model.getProjects();
        Project project;
        for (int counter = 0; counter < projects.size(); counter++) {
            project = projects.get(counter);
            output.writeLine(String.format("[%s]. %s", counter + 1, project.getName()));
            output.writeLine(String.format("     Short Description: %s", project.getShortDescription()));
            output.writeLine(String.format("     Goal: %s", getMoney(project.getGoal())));
            output.writeLine(String.format("     Balance: %s", getMoney(project.getBalance())));
            output.writeLine(String.format("     %s", getDiffDate(project.getDeadlineDate(), new Date())));
        }
        output.writeLine(CHOICE_MESSAGE);
    }
}