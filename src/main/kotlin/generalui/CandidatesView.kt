package generalui

import common.*
import database.daos.*
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.util.StringConverter

class CandidatesView {

    @FXML private lateinit var addCandidate: Button
    @FXML private lateinit var deleteCandidate: Button
    @FXML private lateinit var updateCandidate: Button
    @FXML private lateinit var candidateFirstName: TextField
    @FXML private lateinit var candidateLastName: TextField
    @FXML private lateinit var candidateAge: ChoiceBox<Int>
    @FXML private lateinit var prevJob: TextField
    @FXML private lateinit var expectedSalary: TextField
    @FXML private lateinit var expectedJobRole: ChoiceBox<JobRoleEntity>
    @FXML private lateinit var candidateLocation: ChoiceBox<LocationEntity>

    @FXML private lateinit var addJobRole: Button
    @FXML private lateinit var jobRoleName: TextField
    @FXML private lateinit var jobRoleDescription: TextArea
    @FXML private lateinit var jobRoleSalary: TextField

    @FXML private lateinit var interviewCandidate: ChoiceBox<CandidateEntity>
    @FXML private lateinit var interviewInterviewer: ChoiceBox<InterviewerEntity>
    @FXML private lateinit var interviewType: ChoiceBox<InterviewTypeEntity>
    @FXML private lateinit var interviewMark: ChoiceBox<Int>

    @FXML private lateinit var candidatesList: ListView<CandidateEntity>
    @FXML private lateinit var interviewsList: ListView<InterviewEntity>
    @FXML private lateinit var jobRolesList: ListView<String>

    private val viewModel = CandidatesViewModel()
    private val jobRoleViewModel = JobRoleViewModel()
    private val interviewsViewModel = InterviewsViewModel()

    @FXML fun initialize() {
        bind()
        candidatesList.cellFactory = ListCellFactory<CandidateEntity> {
            if (candidatesList.items.size <= it.index) {
                return@ListCellFactory
            }
            viewModel.selectedCandidate = candidatesList.items[it.index]
        }
        candidateAge.items = FXCollections.observableArrayList((18..100).toList())

        candidateFirstName.onMouseClicked = EventHandler {
            if (candidateFirstName.text == ENTER_FIRST_NAME) {
                candidateFirstName.text = EMPTY
            }
        }
        candidateLastName.onMouseClicked = EventHandler {
            if (candidateLastName.text == ENTER_LAST_NAME) {
                candidateLastName.text = EMPTY
            }
        }
        prevJob.onMouseClicked = EventHandler {
            if (prevJob.text == ENTER_PREVIOUS_JOB) {
                prevJob.text = EMPTY
            }
        }
        expectedSalary.onMouseClicked = EventHandler {
            if (expectedSalary.text == ENTER_EXPECTED_SALARY) {
                expectedSalary.text = EMPTY
            }
        }
        expectedJobRole.converter = object : StringConverter<JobRoleEntity>() {
            override fun toString(`object`: JobRoleEntity?): String = `object`?.name ?: EMPTY

            override fun fromString(string: String?): JobRoleEntity? =
                expectedJobRole.items.find { it.name == string }
        }
        candidateLocation.converter = object : StringConverter<LocationEntity>() {
            override fun toString(`object`: LocationEntity?): String = `object`?.name ?: EMPTY

            override fun fromString(string: String?): LocationEntity? =
                candidateLocation.items.find { it.name == string }
        }
        interviewMark.items = FXCollections.observableArrayList((0..10).toList())
    }

    private fun bind() {
        addCandidate.onActionProperty().bindBidirectional(viewModel.onAddPressedProperty)
        deleteCandidate.setOnAction { viewModel.deleteCandidate() }
        updateCandidate.setOnAction { viewModel.updateCandidate() }
        candidateFirstName.textProperty().bindBidirectional(viewModel.firstNameProp)
        candidateLastName.textProperty().bindBidirectional(viewModel.lastNameProp)
        candidatesList.itemsProperty().bindBidirectional(viewModel.candidatesList)
        candidateAge.valueProperty().bindBidirectional(viewModel.selectedAgeProp)
        prevJob.textProperty().bindBidirectional(viewModel.prevJobProp)
        expectedSalary.textProperty().bindBidirectional(viewModel.expectedSalaryProp)
        expectedJobRole.itemsProperty().bindBidirectional(viewModel.expectedJobRoleItemsProp)
        expectedJobRole.setOnShown { viewModel.expectedJobRoleShowing.value = true }
        expectedJobRole.setOnHidden { viewModel.expectedJobRoleShowing.value = false }
        expectedJobRole.valueProperty().bindBidirectional(viewModel.expectedJobRoleValueProp)
        candidateLocation.itemsProperty().bindBidirectional(viewModel.locationItemsProp)
        candidateLocation.valueProperty().bindBidirectional(viewModel.locationValueProp)

        addJobRole.onActionProperty().bindBidirectional(jobRoleViewModel.onAddPressedProperty)
        jobRoleName.textProperty().bindBidirectional(jobRoleViewModel.jobRoleNameProp)
        jobRoleDescription.textProperty().bindBidirectional(jobRoleViewModel.jobRoleDescriptionProp)
        jobRoleSalary.textProperty().bindBidirectional(jobRoleViewModel.jobRoleSalaryProp)
        jobRolesList.itemsProperty().bindBidirectional(jobRoleViewModel.jobRoleListProp)

        interviewsList.itemsProperty().bindBidirectional(interviewsViewModel.interviewItemsProp)
        interviewCandidate.itemsProperty().bindBidirectional(viewModel.candidatesList)
    }
}