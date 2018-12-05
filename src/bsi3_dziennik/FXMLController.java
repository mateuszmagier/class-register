/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsi3_dziennik;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.xml.bind.JAXBException;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class FXMLController implements Initializable {
    
    List<Student> presentStudents, absentStudents;
    List<Lesson> lessons;
    List<Student> students;
    Student studentWithActiveMarks;

    @FXML
    private TableView<Student> studentsTableView;
    @FXML
    private TableColumn<Student, String> surnameColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> studentNumberColumn;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField studentNumberTextField;
    @FXML
    private TextField surnameEditTextField;
    @FXML
    private TextField nameEditTextField;
    @FXML
    private TextField studentNumberEditTextField;
    @FXML
    private TableView<Lesson> lessonsTableView;
    @FXML
    private TableColumn<Lesson, Date> dateColumn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Student> presentStudentsTableView;
    @FXML
    private TableView<Student> absentStudentsTableView;
    @FXML
    private TableColumn<Student, String> presentSurnameColumn;
    @FXML
    private TableColumn<Student, String> presentNameColumn;
    @FXML
    private TableColumn<Student, String> presentStudentNumberColumn;
    @FXML
    private TableColumn<Student, String> absentSurnameColumn;
    @FXML
    private TableColumn<Student, String> absentNameColumn;
    @FXML
    private TableColumn<Student, String> absentStudentNumberColumn;
    @FXML
    private Label studentAbsencesNumberLabel;
    @FXML
    private ComboBox<Double> newMarkComboBox;
    @FXML
    private ComboBox<Double> editMarkComboBox;
    @FXML
    private ListView<Double> marksListView;
    @FXML
    private Label studentMarksAvgLabel;
    @FXML
    private Label groupMarksAvgLabel;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        StudentManager.setStudentsList(new ArrayList<>());
        LessonManager.setLessonsList(new ArrayList<>());
        listStudents();
        listLessons();
        initComboBoxes();
        initPresences(true);
    }

    public void listStudents() {
        ObservableList<Student> items = FXCollections.observableArrayList();
        students = StudentManager.getStudentsList();
        for(Student s: students) {
            items.add(s);
        }
        studentsTableView.itemsProperty().setValue(items);
        
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
    }
    
    public void listLessons() {
        ObservableList<Lesson> items = FXCollections.observableArrayList();
        lessons = LessonManager.getLessonsList();
        for(Lesson l: lessons) {
            items.add(l);
        }
        lessonsTableView.itemsProperty().setValue(items);
        
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    
    private void initPresences(boolean presence) {
        int studentsNumber = students.size();
        List<Boolean> defaultPresences = new ArrayList<>();
        
        for(int i = 0; i < studentsNumber; i++) {
            defaultPresences.add(presence);
        }
        
        for(Lesson l: lessons) {
            l.setPresencesList(defaultPresences);
        }
    }

    @FXML
    private void addStudentAction(ActionEvent event) {
        String surname, name, studentNumber;
        Student s;
        boolean isValid, result;
        surname = surnameTextField.getText();
        name = nameTextField.getText();
        studentNumber = studentNumberTextField.getText();
        s = new Student(name, surname, studentNumber);
        try {
            isValid = StudentManager.validateStudent(s);
            result = StudentManager.addStudent(s, isValid);
            if(!result) {
                // Student o podanym numerze indeksu znajduje się już w dzienniku.
            }
            else {
                listStudents();
                presentStudents.add(s);
                addPresencesForNewStudent();
            }
        }
        catch(Exception ex) {}
        clearAddTextFields();
        students = StudentManager.getStudentsList();
    }
    
    private void addPresencesForNewStudent() {
        for(Lesson l: lessons)
            l.addPresence(true);
    }
    
    private void removePresencesForRemovedStudent(int position) {
        for(Lesson l: lessons)
            l.getPresencesList().remove(position);
    }

    @FXML
    private void deleteStudentAction(ActionEvent event) {
        if(studentsTableView.getSelectionModel().getSelectedItem() != null) {
            Student s = studentsTableView.getSelectionModel().getSelectedItem();
            int index = students.indexOf(s);
            removePresencesForRemovedStudent(index);
            StudentManager.removeStudent(s);
            listStudents();
            students = StudentManager.getStudentsList();
        }
    }
    
    @FXML
    private void prepareUpdateStudentAction(ActionEvent event) {
        if(studentsTableView.getSelectionModel().getSelectedItem() != null) {
            Student s = studentsTableView.getSelectionModel().getSelectedItem();
            surnameEditTextField.setText(s.getSurname());
            nameEditTextField.setText(s.getName());
            studentNumberEditTextField.setText(s.getStudentNumber());
        }
    }

    @FXML
    private void updateStudentAction(ActionEvent event) {
        String surname, name, studentNumber;
        boolean validation;
        int position;
        position = studentsTableView.getSelectionModel().getSelectedIndex();
        surname = surnameEditTextField.getText();
        name = nameEditTextField.getText();
        studentNumber = studentNumberEditTextField.getText();
        validation = validateEdit(name, surname, studentNumber);
        Student s = new Student(name, surname, studentNumber);
        StudentManager.updateStudent(position, s, validation);
        clearEditTextFields();
        students = StudentManager.getStudentsList();
        studentsTableView.refresh();
    }
    
    private boolean validateEdit(String name, String surname, String studentNumber) {
        if("".equals(name) || name == null)
            return false;
        if("".equals(surname) || surname == null)
            return false;
        if("".equals(studentNumber) || studentNumber == null)
            return false;
        return true;
    }
    
    private void clearEditTextFields() {
        surnameEditTextField.setText("");
        nameEditTextField.setText("");
        studentNumberEditTextField.setText("");
    }
    
    private void clearAddTextFields() {
        surnameTextField.setText("");
        nameTextField.setText("");
        studentNumberTextField.setText("");
    }

    @FXML
    private void addLessonAction(ActionEvent event) {
        if(datePicker.getValue() != null) {
            LocalDate localDate = datePicker.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            Lesson l = initPresencesForLesson(new Lesson(date));
            
            LessonManager.addLesson(l);
            listLessons();
        }
    }
    
    private Lesson initPresencesForLesson(Lesson l) {
        int size = students.size();
        List<Boolean> presences = new ArrayList<>();
        for(int i = 0; i < size; i++)
            presences.add(Boolean.TRUE);
        l.setPresencesList(presences);
        return l;
    }
    
    @FXML
    private void showPresencesListAction(ActionEvent event) {
        Lesson l = lessonsTableView.getSelectionModel().getSelectedItem();
        
        preparePresentAndAbsentStudentsLists(l);
        
        listPresentStudents(l);
        listAbsentStudents(l);
    }
    
    private void preparePresentAndAbsentStudentsLists(Lesson l) {
        List<Boolean> presences = l.getPresencesList();
        presentStudents = new ArrayList<>();
        absentStudents = new ArrayList<>();
        
        for(int i = 0; i < presences.size(); i++) {
            if(presences.get(i)) // student obecny
                presentStudents.add(students.get(i));
            else // student nieobecny
                absentStudents.add(students.get(i));
        }
    }

    @FXML
    private void makeStudentAbsentAction(ActionEvent event) {
        if(presentStudentsTableView.getSelectionModel().getSelectedItem() != null) {
            Student s = presentStudentsTableView.getSelectionModel().getSelectedItem();
            int index = students.indexOf(s);
            Lesson l = lessonsTableView.getSelectionModel().getSelectedItem();
            l.updatePresence(index, false);
            
            showPresencesListAction(event);
        }
    }

    @FXML
    private void makeStudentPresentAction(ActionEvent event) {
        if(absentStudentsTableView.getSelectionModel().getSelectedItem() != null) {
            Student s = absentStudentsTableView.getSelectionModel().getSelectedItem();
            int index = students.indexOf(s);
            Lesson l = lessonsTableView.getSelectionModel().getSelectedItem();
            l.updatePresence(index, true);
            showPresencesListAction(event);
        }
    }

    private void listPresentStudents(Lesson l) {
        ObservableList<Student> items = FXCollections.observableArrayList();
        for(Student s: presentStudents) {
            items.add(s);
        }
        presentStudentsTableView.itemsProperty().setValue(items);
        
        presentSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        presentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        presentStudentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
    }
    
    private void listAbsentStudents(Lesson l) {
        ObservableList<Student> items = FXCollections.observableArrayList();
        for(Student s: absentStudents) {
            items.add(s);
        }
        absentStudentsTableView.itemsProperty().setValue(items);
        
        absentSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        absentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        absentStudentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
    }

    @FXML
    private void showStudentStatsAction(ActionEvent event) {
        int lessonsNumber = lessons.size();
        int studentIndex = studentsTableView.getSelectionModel().getSelectedIndex();
        int studentAbsencesNumber = StudentManager.calculateAbsencesNumber(studentIndex);
        studentAbsencesNumberLabel.setText(studentAbsencesNumber + "/" + lessonsNumber);
        
        Student s = studentsTableView.getSelectionModel().getSelectedItem();
        double avg = s.calculateAverage();
        studentMarksAvgLabel.setText(new DecimalFormat("#.00").format(avg));
        
        double groupAvg = StudentManager.calculateGroupAverage();
        groupMarksAvgLabel.setText(new DecimalFormat("#.00").format(groupAvg));
    }

    @FXML
    private void exportToXMLAction(ActionEvent event) throws JAXBException, FileNotFoundException {
        File file = new File("C:/BSI/lessons.xml");
        XMLHelper xml = new XMLHelper();
        xml.lessonsToXml(file);
    }

    @FXML
    private void importFromXMLAction(ActionEvent event) throws FileNotFoundException, JAXBException {
        File file = new File("C:/BSI/lessons.xml");
        XMLHelper xml = new XMLHelper();
        xml.xmlToLessons(file);
        listLessons();
    }
    
    private void initComboBoxes() {
        ObservableList<Double> marks = FXCollections.observableArrayList();
        List<Double> list = Arrays.asList(2.0, 3.0, 3.5, 4.0, 4.5, 5.0);
        for(Double m: list)
            marks.add(m);
        newMarkComboBox.setItems(marks);
        newMarkComboBox.setValue(2.0);
        editMarkComboBox.setItems(marks);
    }
    
    private void listMarks(Student s) {
        ObservableList<Double> items = FXCollections.observableArrayList();
        List<Double> marks = s.getMarks();
        for(Double m: marks) {
            items.add(m);
        }
        marksListView.setItems(items);
    }

    @FXML
    private void addMarkAction(ActionEvent event) {
        if(studentsTableView.getSelectionModel().getSelectedItem() != null) {
            Student s = studentsTableView.getSelectionModel().getSelectedItem();
            double mark = newMarkComboBox.getValue();
            boolean isValid = s.validateMark(mark);
            s.addMark(mark, isValid);
            listMarks(s);
        }
    }

    @FXML
    private void editMarkAction(ActionEvent event) {
        double newMark = editMarkComboBox.getValue();
        boolean isValid = Student.validateMark(newMark);
        int index = marksListView.getSelectionModel().getSelectedIndex();
        studentWithActiveMarks.updateMark(index, newMark, isValid);
        listMarks(studentWithActiveMarks);
    }

    @FXML
    private void showMarksAction(ActionEvent event) {
        studentWithActiveMarks = studentsTableView.getSelectionModel().getSelectedItem();
        listMarks(studentWithActiveMarks);
    }

    @FXML
    private void removeMarkAction(ActionEvent event) {
        double mark = marksListView.getSelectionModel().getSelectedItem();
        studentWithActiveMarks.removeMark(mark);
        listMarks(studentWithActiveMarks);
    }

    @FXML
    private void prepareEditMarkAction(ActionEvent event) {
        double mark = marksListView.getSelectionModel().getSelectedItem();
        editMarkComboBox.setValue(mark);
    }

    @FXML
    private void importStudentsFromXmlAction(ActionEvent event) throws FileNotFoundException, JAXBException {
        File file = new File("C:/BSI/students.xml");
        XMLHelper xml = new XMLHelper();
        xml.xmlToStudents(file);
        listStudents();
    }

    @FXML
    private void exportStudentsToXmlAction(ActionEvent event) throws JAXBException, FileNotFoundException {
        File file = new File("C:/BSI/students.xml");
        XMLHelper xml = new XMLHelper();
        xml.studentsToXml(file);
    }
}