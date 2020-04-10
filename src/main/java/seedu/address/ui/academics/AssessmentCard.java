package seedu.address.ui.academics;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Iterator;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
public class AssessmentCard extends UiPart<Region> {

    private static final String FXML = "AssessmentCard.fxml";
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
    private Label date;
    @FXML
    private Label submissionTracker;
    @FXML
    private Label markingTracker;

    /**
     * Constructor to create the assessment card controller.
     */
    public AssessmentCard(Assessment assessment, int displayedIndex) {
        super(FXML);
        this.assessment = assessment;
        id.setText(displayedIndex + ". ");
        description.setText(assessment.getDescription());
        if (assessment.getType().equals("homework")) {
            tags.getChildren().add(new Label("Homework"));
            date.setText("Deadline: "
                    + assessment.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        } else if (assessment.getType().equals("exam")) {
            tags.getChildren().add(new Label("Exam"));
            date.setText("Exam Date: "
                    + assessment.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        }
        if (assessment.getSubmissionTracker().size() == assessment.noOfSubmittedStudents()) {
            tags.getChildren().add(new Label("All Submitted"));
        }
        if (assessment.getSubmissionTracker().size() == assessment.noOfMarkedSubmissions()) {
            tags.getChildren().add(new Label("Completed Marking"));
        }
        int submitted = 0;
        int marked = 0;
        ObservableList<Submission> submissionsList =
                FXCollections.observableArrayList(assessment.getSubmissionTracker());

        Iterator<Submission> iterator = submissionsList.iterator();
        while (iterator.hasNext()) {
            Submission next = iterator.next();
            submitted = next.hasSubmitted() ? submitted + 1 : submitted;
            marked = next.isMarked() ? marked + 1 : marked;
        }
        submissionTracker.setText("Submissions: " + submitted
                + " / " + assessment.getSubmissionTracker().size());
        markingTracker.setText("Marked: " + marked
                + " / " + assessment.getSubmissionTracker().size());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssessmentCard)) {
            return false;
        }

        // state check
        AssessmentCard card = (AssessmentCard) other;
        return id.getText().equals(card.id.getText())
                && assessment.equals(card.assessment);
    }
}
