package seedu.address.ui.academics;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.academics.Assessment;
import seedu.address.model.academics.Submission;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays minimal information of a {@code Assessment}.
 */
public class AssessmentCardReport extends UiPart<Region> {

    private static final String FXML = "AssessmentCardReport.fxml";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Assessment assessment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;
    @FXML
    private Label submissionTracker;
    @FXML
    private Label markingTracker;
    @FXML
    private Label median;
    @FXML
    private Label average;
    @FXML
    private Label submissionListTitle;
    @FXML
    private ListView<Submission> submissionListView;

    /**
     * Constructor to create the assessment card controller.
     */
    public AssessmentCardReport(Assessment assessment, int displayedIndex) {
        super(FXML);
        this.assessment = assessment;
        id.setText(displayedIndex + ". ");
        description.setText(assessment.getDescription());

        // tags
        if (assessment.getType().equals("homework")) {
            tags.getChildren().add(new Label("Homework"));
        } else if (assessment.getType().equals("exam")) {
            tags.getChildren().add(new Label("Exam"));
        }
        if (assessment.getSubmissionTracker().size() == assessment.noOfSubmittedStudents()) {
            tags.getChildren().add(new Label("All Submitted"));
        }
        if (assessment.getSubmissionTracker().size() == assessment.noOfMarkedSubmissions()) {
            tags.getChildren().add(new Label("Completed Marking"));
        }

        // submissions & marking
        submissionTracker.setText("Submissions: " + assessment.noOfSubmittedStudents()
                + " / " + assessment.getSubmissionTracker().size());
        markingTracker.setText("Marked: " + assessment.noOfMarkedSubmissions()
                + " / " + assessment.getSubmissionTracker().size());

        // statistics
        median.setText("Median Score: " + String.format("%.1f", assessment.medianScore()));
        average.setText("Average Score: " + String.format("%.1f", assessment.averageScore()));

        // submissions panel
        ObservableList<Submission> submissionsList =
                FXCollections.observableArrayList(assessment.getSubmissionTracker());
        submissionListTitle.setText("Submissions");
        submissionListView.setItems(submissionsList);
        submissionListView.setCellFactory(listView -> new SubmissionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Submission} using an {@code SubmissionCard}.
     */
    class SubmissionListViewCell extends ListCell<Submission> {
        @Override
        protected void updateItem(Submission submission, boolean empty) {
            super.updateItem(submission, empty);

            if (empty || submission == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SubmissionCard(submission).getRoot());
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssessmentCardReport)) {
            return false;
        }

        // state check
        AssessmentCardReport card = (AssessmentCardReport) other;
        return id.getText().equals(card.id.getText())
                && assessment.equals(card.assessment);
    }
}
