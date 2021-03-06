package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEMPERATURE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.academics.AcademicsEditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.student.StudentEditCommand;
import seedu.address.model.Model;
import seedu.address.model.academics.Assessment;
import seedu.address.model.academics.DescriptionContainsKeywordsPredicate;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Date;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
import seedu.address.model.student.TeaPet;
import seedu.address.testutil.academics.EditAssessmentDescriptorBuilder;
import seedu.address.testutil.student.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ATTENDANCE_AMY = "Present";
    public static final String VALID_ATTENDANCE_BOB = "Absent";
    public static final String VALID_TEMPERATURE_AMY = "36.5";
    public static final String VALID_TEMPERATURE_BOB = "37.0";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_NOK_AMY = "Joseph-Father-98765432";
    public static final String VALID_NOK_BOB = "Johnny-Father-94765432";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TEMPERATURE_DESC_AMY = " " + PREFIX_TEMPERATURE + VALID_TEMPERATURE_AMY;
    public static final String TEMPERATURE_DESC_BOB = " " + PREFIX_TEMPERATURE + VALID_TEMPERATURE_BOB;
    public static final String ATTENDANCE_DESC_AMY = " " + PREFIX_ATTENDANCE + VALID_ATTENDANCE_AMY;
    public static final String ATTENDANCE_DESC_BOB = " " + PREFIX_ATTENDANCE + VALID_ATTENDANCE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String NOK_DESC_AMY = " " + PREFIX_NOK + VALID_NOK_AMY;
    public static final String NOK_DESC_BOB = " " + PREFIX_NOK + VALID_NOK_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James97"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TEMPERATURE_DESC_1 = " " + PREFIX_TEMPERATURE + "3a.5"; //"a" not allowed in
    // temperature
    public static final String INVALID_TEMPERATURE_DESC_2 = " " + PREFIX_TEMPERATURE + "41.1";
    public static final String INVALID_ATTENDANCE_DESC = " " + PREFIX_ATTENDANCE + "Present!"; //"!" not allowed in
    // attendance
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final StudentEditCommand.EditStudentDescriptor DESC_AMY;
    public static final StudentEditCommand.EditStudentDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTemperature(VALID_TEMPERATURE_AMY).withAttendance(VALID_ATTENDANCE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTemperature(VALID_TEMPERATURE_BOB).withAttendance(VALID_ATTENDANCE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    // academics
    public static final String VALID_DESCRIPTION_MATH_ASSIGNMENT = "Math Assignment";
    public static final String VALID_DESCRIPTION_SCIENCE_EXAM = "Science Assignment";
    public static final String VALID_TYPE_MATH_ASSIGNMENT = "homework";
    public static final String VALID_TYPE_SCIENCE_EXAM = "exam";
    public static final String VALID_DATE_MATH_ASSIGNMENT = "2020-03-05";
    public static final String VALID_DATE_SCIENCE_EXAM = "2020-05-25";
    public static final String VALID_STUDENT_NAME_SHARADH_RAJARAMAN = "Sharadh Rajaraman";
    public static final String VALID_STUDENT_NAME_GRACE_PAN = "Grace Pan";
    public static final String VALID_SUBMITTED_SHARADH_RAJARAMAN = "false";
    public static final String VALID_SUBMITTED_GRACE_PAN = "true";
    public static final String VALID_MARKED_SHARADH_RAJARAMAN = "false";
    public static final String VALID_MARKED_GRACE_PAN = "true";
    public static final String VALID_SCORE_SHARADH_RAJARAMAN = "20";
    public static final String VALID_SCORE_GRACE_PAN = "99";
    public static final String VALID_MARKING_SHARADH_RAJARAMAN = VALID_STUDENT_NAME_SHARADH_RAJARAMAN + "-"
            + VALID_SCORE_SHARADH_RAJARAMAN;
    public static final String VALID_MARKING_GRACE_PAN = VALID_STUDENT_NAME_GRACE_PAN + "-" + VALID_SCORE_GRACE_PAN;

    public static final String DESCRIPTION_MATH_ASSIGNMENT = " "
            + PREFIX_ASSESSMENT_DESCRIPTION + VALID_DESCRIPTION_MATH_ASSIGNMENT;
    public static final String DESCRIPTION_SCIENCE_EXAM = " "
            + PREFIX_ASSESSMENT_DESCRIPTION + VALID_DESCRIPTION_SCIENCE_EXAM;
    public static final String TYPE_MATH_ASSIGNMENT = " " + PREFIX_ASSESSMENT_TYPE + VALID_TYPE_MATH_ASSIGNMENT;
    public static final String TYPE_SCIENCE_EXAM = " " + PREFIX_ASSESSMENT_TYPE + VALID_TYPE_SCIENCE_EXAM;
    public static final String DATE_MATH_ASSIGNMENT = " " + PREFIX_ASSESSMENT_DATE + VALID_DATE_MATH_ASSIGNMENT;
    public static final String DATE_SCIENCE_EXAM = " " + PREFIX_ASSESSMENT_DATE + VALID_DATE_SCIENCE_EXAM;
    public static final String SUBMISSION_SHARADH_RAJARAMAN = " " + PREFIX_STUDENT
            + VALID_STUDENT_NAME_SHARADH_RAJARAMAN;
    public static final String SUBMISSION_GRACE_PAN = " " + PREFIX_STUDENT + VALID_STUDENT_NAME_GRACE_PAN;
    public static final String MARKING_SHARADH_RAJARAMAN = " " + PREFIX_STUDENT + VALID_STUDENT_NAME_SHARADH_RAJARAMAN
            + "-" + VALID_SCORE_SHARADH_RAJARAMAN;
    public static final String MARKING_GRACE_PAN = " " + PREFIX_STUDENT + VALID_STUDENT_NAME_GRACE_PAN
            + "-" + VALID_SCORE_GRACE_PAN;

    public static final String INVALID_ASSESSMENT_DESC = " " + PREFIX_NAME + ""; // empty description is not allowed
    public static final String INVALID_TYPE_DESC = " " + PREFIX_ASSESSMENT_TYPE + "assignment"; // homework OR exam
    public static final String INVALID_DATE_DESC = " " + PREFIX_ASSESSMENT_DATE + "2020/03/04"; // homework OR exam
    public static final String INVALID_MARKING = " " + PREFIX_STUDENT + VALID_STUDENT_NAME_GRACE_PAN;

    public static final AcademicsEditCommand.EditAssessmentDescriptor DESC_MATH_ASSIGNMENT;
    public static final AcademicsEditCommand.EditAssessmentDescriptor DESC_SCIENCE_EXAM;

    static {
        DESC_MATH_ASSIGNMENT = new EditAssessmentDescriptorBuilder().withDescription(VALID_DESCRIPTION_MATH_ASSIGNMENT)
                .withType(VALID_TYPE_MATH_ASSIGNMENT).withDate(VALID_DATE_MATH_ASSIGNMENT).build();
        DESC_SCIENCE_EXAM = new EditAssessmentDescriptorBuilder().withDescription(VALID_DESCRIPTION_SCIENCE_EXAM)
                .withType(VALID_TYPE_SCIENCE_EXAM).withDate(VALID_DATE_SCIENCE_EXAM).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TeaPet expectedTeaPet = new TeaPet(actualModel.getTeaPet());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedTeaPet, actualModel.getTeaPet());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code RuntimeException} is thrown <br>
     * - the RuntimeException message matches {@code expectedMessage} <br>
     * - the admin, filtered date list and selected date in {@code actualModel} remain unchanged
     */
    public static void assertRuntimeFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Admin expectedAdmin = new Admin(actualModel.getAdmin());
        List<Date> expectedFilteredList = new ArrayList<>(actualModel.getFilteredDateList());

        assertThrows(RuntimeException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAdmin, actualModel.getAdmin());
        assertEquals(expectedFilteredList, actualModel.getFilteredDateList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }
    /**
     * Updates {@code model}'s filtered list to show only the assessment at the given {@code targetIndex} in the
     * {@code model}'s academics.
     */
    public static void showAssessmentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredAcademicsList().size());

        Assessment assessment = model.getFilteredAcademicsList().get(targetIndex.getZeroBased());
        final String[] splitDescription = assessment.getDescription().split("\\s+");
        model.updateFilteredAcademicsList(new DescriptionContainsKeywordsPredicate(Arrays.asList(splitDescription[0])));

        assertEquals(1, model.getFilteredAcademicsList().size());
    }
}
