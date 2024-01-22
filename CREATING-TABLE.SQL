-- Create Universities Table
CREATE TABLE Universities (
    UniversityID INT AUTO_INCREMENT PRIMARY KEY,
    UniversityName VARCHAR(255)
);

CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    User_email VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role VARCHAR(255) NOT NULL
);

-- Create Students Table
CREATE TABLE Students (
    StudentID INT AUTO_INCREMENT PRIMARY KEY,
    StudentName VARCHAR(255),
    UniversityID INT,
    UserID INT UNIQUE, 
    FOREIGN KEY (UniversityID) REFERENCES Universities(UniversityID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Create Teachers Table
CREATE TABLE Teachers (
    TeacherID INT AUTO_INCREMENT PRIMARY KEY,
    TeacherName VARCHAR(255),
    UniversityID INT,
    UserID INT UNIQUE,
    FOREIGN KEY (UniversityID) REFERENCES Universities(UniversityID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Create Courses Table
CREATE TABLE Courses (
    CourseID INT AUTO_INCREMENT PRIMARY KEY,
    CourseCode VARCHAR(10),
    CourseName VARCHAR(255)
);

-- Bridge Entity for Teacher-Course Many-to-Many Relationship
CREATE TABLE TeachersCourses (
    TeacherCourseID INT AUTO_INCREMENT PRIMARY KEY,
    TeacherID INT,
    CourseID INT,
    FOREIGN KEY (TeacherID) REFERENCES Teachers(TeacherID),
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
);



-- Create StudentCourses Table for Many-to-Many Relationship
CREATE TABLE StudentCourses (
    StudentID INT,
    CourseID INT,
    PRIMARY KEY (StudentID, CourseID),
    FOREIGN KEY (StudentID) REFERENCES Students(StudentID),
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
);

CREATE TABLE Classes (
    ClassID INT AUTO_INCREMENT PRIMARY KEY,
    CourseID INT,
    ClassName VARCHAR(50),
    ClassTime TIME,
    ClassDay VARCHAR(10),
    ClassVenue VARCHAR(50),
    -- IsCanceled BOOLEAN DEFAULT FALSE,
    -- IsScheduled BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
);

-- Create Announcements Table (Bridge Entity for Teacher-Course Many-to-Many)
-- CREATE TABLE Announcements (
--     AnnouncementID INT AUTO_INCREMENT PRIMARY KEY,
--     -- TeacherID INT,
--     -- CourseID INT,
--     -- AnnouncementText TEXT,
--     -- FOREIGN KEY (TeacherID) REFERENCES Teachers(TeacherID),
--     -- FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
--     TeacherCourseID INT, -- Reference to TeachersCourses
--     AnnouncementText TEXT,
--     FOREIGN KEY (TeacherCourseID) REFERENCES TeachersCourses(TeacherCourseID)
-- );

-- Create Timetables Table
-- CREATE TABLE Timetables (
--     TimetableID INT AUTO_INCREMENT PRIMARY KEY,
--     StudentID INT,
--     CourseID INT,
--     DayOfWeek VARCHAR(10),
--     StartTime TIME,
--     EndTime TIME,
--     FOREIGN KEY (StudentID) REFERENCES Students(StudentID),
--     FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
-- );

CREATE TABLE Timetable (
    TimetableID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT,
    -- ClassID INT,
    -- DayOfWeek VARCHAR(15),
    -- You might use an ENUM for days of the week
    -- Other timetable details
    -- FOREIGN KEY (ClassID) REFERENCES Classes(ClassID)
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE TimetableClasses (
    TimetableID INT,
    ClassID INT,
    FOREIGN KEY (TimetableID) REFERENCES Timetable(TimetableID),
    FOREIGN KEY (ClassID) REFERENCES Classes(ClassID),
    PRIMARY KEY (TimetableID, ClassID)
);

CREATE TABLE DueDates (
    DueDateID INT AUTO_INCREMENT PRIMARY KEY,
    CourseID INT,
    DueDate DATE,
    DueDateTitle TEXT,
    DueDateText TEXT,
    PostDate DATE,
    PostByName VARCHAR(255),
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
);

CREATE TABLE Announcements (
    AnnouncementID INT AUTO_INCREMENT PRIMARY KEY,
    CourseID INT,
    AnnouncementTitle TEXT,
    AnnouncementDetails TEXT,
    PostDate DATE,
    PostByName VARCHAR(255),
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
);


CREATE TABLE ClassCancellations (
    CancellationID INT PRIMARY KEY,
    ClassID INT,
    CancellationDate DATE,
    CancellationTitle TEXT,
    CancellationDetails TEXT,
    PostDate DATE,
    PostByName VARCHAR(255),
    IsRescheduled BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (ClassID) REFERENCES Classes(ClassID)
);

CREATE TABLE ClassSchedules (
    ScheduleID INT PRIMARY KEY,
    ClassID INT,
    ScheduleDate DATE,
    ScheduleStartTime TIME,
    ScheduleEndTime TIME,
    ScheduleTitle TEXT,
    ScheduleDetails TEXT,
    PostDate DATE,
    PostByName VARCHAR(255),
    FOREIGN KEY (ClassID) REFERENCES Classes(ClassID)
);


-- CREATE TABLE TeacherSchedules (
--     TeacherScheduleID INT AUTO_INCREMENT PRIMARY KEY,
--     TeacherID INT,
--     CourseID INT,
--     DayOfWeek VARCHAR(10),
--     StartTime TIME,
--     EndTime TIME,
--     FOREIGN KEY (TeacherID) REFERENCES Teachers(TeacherID),
--     FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
-- );