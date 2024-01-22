-- Inserting Universities
INSERT INTO Universities (UniversityName) VALUES ('Universiti Sains Malaysia');
INSERT INTO Universities (UniversityName) VALUES ('Universiti Malaya');
INSERT INTO Universities (UniversityName) VALUES ('Universiti Kebangsaan Malaysia');
INSERT INTO Universities (UniversityName) VALUES ('Universiti Putra Malaysia');
INSERT INTO Universities (UniversityName) VALUES ('Universiti Teknologi Malaysia');


-- Inserting Admin Users
INSERT INTO Users (User_email, Password, Role) VALUES ('admin@gmail.com', 123456, 'Admin');
INSERT INTO Teachers (TeacherName, UniversityID, UserID) VALUES ('Admin-Jacky', 1, (SELECT UserID FROM Users WHERE User_email = 'admin@gmail.com'));

-- Inserting Student Users
INSERT INTO Users (User_email, Password, Role) VALUES ('test1@gmail.com', 123456, 'Student');
INSERT INTO Users (User_email, Password, Role) VALUES ('test2@gmail.com', 123456, 'Student');

-- Inserting Teacher Users
INSERT INTO Users (User_email, Password, Role) VALUES ('teacher1@gmail.com', 123456, 'Teacher');
INSERT INTO Users (User_email, Password, Role) VALUES ('teacher2@gmail.com', 123456, 'Teacher');


-- Inserting Students
INSERT INTO Students (StudentName, UniversityID, UserID)
VALUES ('Student 1', 1, (SELECT UserID FROM Users WHERE User_email = 'test1@gmail.com'));

INSERT INTO Students (StudentName, UniversityID, UserID)
VALUES ('Student 2', 1, (SELECT UserID FROM Users WHERE User_email = 'test2@gmail.com'));

-- Inserting Teachers
INSERT INTO Teachers (TeacherName, UniversityID, UserID)
VALUES ('Teacher 1', 1, (SELECT UserID FROM Users WHERE User_email = 'teacher1@gmail.com'));

INSERT INTO Teachers (TeacherName, UniversityID, UserID)
VALUES ('Teacher 2', 1, (SELECT UserID FROM Users WHERE User_email = 'teacher2@gmail.com'));

INSERT INTO Courses (CourseCode, CourseName) VALUES ('CAT201', 'CAT201 - Integrated Software Development Workshop');
INSERT INTO Courses (CourseCode, CourseName) VALUES ('CMT221', 'DATABASE ORGANISATION & DESIGN');

INSERT INTO StudentCourses (StudentID, CourseID) VALUES ((SELECT StudentID FROM Students WHERE UserID = 2), (SELECT CourseID FROM Courses WHERE CourseCode = 'CAT201'));
INSERT INTO StudentCourses (StudentID, CourseID) VALUES ((SELECT StudentID FROM Students WHERE UserID = 2), (SELECT CourseID FROM Courses WHERE CourseCode = 'CMT221'));
INSERT INTO StudentCourses (StudentID, CourseID) VALUES ((SELECT StudentID FROM Students WHERE UserID = 3), (SELECT CourseID FROM Courses WHERE CourseCode = 'CAT201'));

INSERT INTO TeachersCourses (TeacherID, CourseID) VALUES ((SELECT TeacherID FROM Teachers WHERE UserID = 4), (SELECT CourseID FROM Courses WHERE CourseCode = 'CAT201'));
INSERT INTO TeachersCourses (TeacherID, CourseID) VALUES ((SELECT TeacherID FROM Teachers WHERE UserID = 4), (SELECT CourseID FROM Courses WHERE CourseCode = 'CMT221'));
INSERT INTO TeachersCourses (TeacherID, CourseID) VALUES ((SELECT TeacherID FROM Teachers WHERE UserID = 5), (SELECT CourseID FROM Courses WHERE CourseCode = 'CMT221'));

