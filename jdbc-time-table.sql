-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 22, 2024 at 07:05 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jdbc-time-table`
--

-- --------------------------------------------------------

--
-- Table structure for table `announcements`
--

CREATE TABLE `announcements` (
  `AnnouncementID` int(11) NOT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `AnnouncementTitle` text DEFAULT NULL,
  `AnnouncementDetails` text DEFAULT NULL,
  `PostDate` date DEFAULT NULL,
  `PostByName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `announcements`
--

INSERT INTO `announcements` (`AnnouncementID`, `CourseID`, `AnnouncementTitle`, `AnnouncementDetails`, `PostDate`, `PostByName`) VALUES
(1, 2, 'Test 2 Result Release', 'I have just send the results to all student\'s email, good luck.', '2024-01-21', 'Teacher 2'),
(2, 2, 'Class Location Change', 'Noted that next week\'s class will be moved to DKSK4 instead of DKG31', '2024-01-21', 'Teacher 1');

-- --------------------------------------------------------

--
-- Table structure for table `classcancellations`
--

CREATE TABLE `classcancellations` (
  `CancellationID` int(11) NOT NULL,
  `ClassID` int(11) DEFAULT NULL,
  `CancellationDate` date DEFAULT NULL,
  `CancellationTitle` text DEFAULT NULL,
  `CancellationDetails` text DEFAULT NULL,
  `PostDate` date DEFAULT NULL,
  `PostByName` varchar(255) DEFAULT NULL,
  `IsRescheduled` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `classes`
--

CREATE TABLE `classes` (
  `ClassID` int(11) NOT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `ClassName` varchar(50) DEFAULT NULL,
  `ClassTime` time DEFAULT NULL,
  `ClassDay` varchar(10) DEFAULT NULL,
  `ClassVenue` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `classschedules`
--

CREATE TABLE `classschedules` (
  `ScheduleID` int(11) NOT NULL,
  `ClassID` int(11) DEFAULT NULL,
  `ScheduleDate` date DEFAULT NULL,
  `ScheduleStartTime` time DEFAULT NULL,
  `ScheduleEndTime` time DEFAULT NULL,
  `ScheduleTitle` text DEFAULT NULL,
  `ScheduleDetails` text DEFAULT NULL,
  `PostDate` date DEFAULT NULL,
  `PostByName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `CourseID` int(11) NOT NULL,
  `CourseCode` varchar(10) DEFAULT NULL,
  `CourseName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`CourseID`, `CourseCode`, `CourseName`) VALUES
(1, 'CAT201', 'Integrated Software Development Workshop'),
(2, 'CMT221', 'DATABASE ORGANISATION & DESIGN');

-- --------------------------------------------------------

--
-- Table structure for table `duedates`
--

CREATE TABLE `duedates` (
  `DueDateID` int(11) NOT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `DueDate` date DEFAULT NULL,
  `DueDateTitle` text DEFAULT NULL,
  `DueDateText` text DEFAULT NULL,
  `PostDate` date DEFAULT NULL,
  `PostByName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `duedates`
--

INSERT INTO `duedates` (`DueDateID`, `CourseID`, `DueDate`, `DueDateTitle`, `DueDateText`, `PostDate`, `PostByName`) VALUES
(1, 2, '2024-02-02', 'Assignment 2 DueDate', 'Pls Complete before 2/2/24', '2024-01-21', 'Teacher 1'),
(2, 2, '2024-01-24', 'Class Activity', 'Please complete the class activity before this wednesday', '2024-01-21', 'Teacher 2'),
(3, 1, '2024-01-24', 'Tutorial 2', 'Late submission will lead to penalty.', '2024-01-21', 'Teacher 1');

-- --------------------------------------------------------

--
-- Table structure for table `studentcourses`
--

CREATE TABLE `studentcourses` (
  `StudentID` int(11) NOT NULL,
  `CourseID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `studentcourses`
--

INSERT INTO `studentcourses` (`StudentID`, `CourseID`) VALUES
(1, 1),
(1, 2),
(2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `StudentID` int(11) NOT NULL,
  `StudentName` varchar(255) DEFAULT NULL,
  `UniversityID` int(11) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`StudentID`, `StudentName`, `UniversityID`, `UserID`) VALUES
(1, 'Student 1', 1, 2),
(2, 'Student 2', 1, 3),
(3, 'Teh Hong Jun', 1, 6),
(4, 'Andrew Tee', 5, 8),
(5, 'Axler', 4, 9),
(6, 'Kok Chek', 5, 10),
(7, 'Tony', 2, 11),
(8, 'Jonny', 2, 12),
(9, 'Meimei', 3, 13),
(10, 'Lily', 5, 14),
(11, 'Geogina', 1, 15);

-- --------------------------------------------------------

--
-- Table structure for table `teachers`
--

CREATE TABLE `teachers` (
  `TeacherID` int(11) NOT NULL,
  `TeacherName` varchar(255) DEFAULT NULL,
  `UniversityID` int(11) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`TeacherID`, `TeacherName`, `UniversityID`, `UserID`) VALUES
(1, 'Admin-Jacky', 1, 1),
(2, 'Teacher 1', 1, 4),
(3, 'Teacher 2', 1, 5),
(4, 'Jackson', 4, 7);

-- --------------------------------------------------------

--
-- Table structure for table `teacherscourses`
--

CREATE TABLE `teacherscourses` (
  `TeacherCourseID` int(11) NOT NULL,
  `TeacherID` int(11) DEFAULT NULL,
  `CourseID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teacherscourses`
--

INSERT INTO `teacherscourses` (`TeacherCourseID`, `TeacherID`, `CourseID`) VALUES
(1, 2, 1),
(2, 2, 2),
(3, 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `timetableclasses`
--

CREATE TABLE `timetableclasses` (
  `TimetableID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `RowIndex` int(11) DEFAULT NULL,
  `ColumnIndex` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `timetableclasses`
--

INSERT INTO `timetableclasses` (`TimetableID`, `UserID`, `CourseID`, `RowIndex`, `ColumnIndex`) VALUES
(1, 2, 1, 0, 1),
(2, 2, 2, 4, 2),
(3, 2, 1, 3, 3),
(4, 2, 1, 3, 4),
(5, 2, 2, 0, 5);

-- --------------------------------------------------------

--
-- Table structure for table `timetablecolour`
--

CREATE TABLE `timetablecolour` (
  `TimetableColourID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `Class_code` varchar(255) DEFAULT NULL,
  `Colour` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `timetablecolour`
--

INSERT INTO `timetablecolour` (`TimetableColourID`, `UserID`, `Class_code`, `Colour`) VALUES
(1, NULL, 'CAT201', '0xffccffff'),
(2, NULL, 'CAT201', '0xffff80ff'),
(3, NULL, 'CMT221', '0xccb3ffff');

-- --------------------------------------------------------

--
-- Table structure for table `timetabledays`
--

CREATE TABLE `timetabledays` (
  `TimetableDayID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `StartDay` int(11) DEFAULT NULL,
  `EndDay` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `timetabledays`
--

INSERT INTO `timetabledays` (`TimetableDayID`, `UserID`, `StartDay`, `EndDay`) VALUES
(1, 2, 0, 4);

-- --------------------------------------------------------

--
-- Table structure for table `timetabletime`
--

CREATE TABLE `timetabletime` (
  `TimetableTimeID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `StartTime` int(11) DEFAULT NULL,
  `EndTime` int(11) DEFAULT NULL,
  `IntervalTime` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `timetabletime`
--

INSERT INTO `timetabletime` (`TimetableTimeID`, `UserID`, `StartTime`, `EndTime`, `IntervalTime`) VALUES
(1, 2, 9, 14, 60);

-- --------------------------------------------------------

--
-- Table structure for table `universities`
--

CREATE TABLE `universities` (
  `UniversityID` int(11) NOT NULL,
  `UniversityName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `universities`
--

INSERT INTO `universities` (`UniversityID`, `UniversityName`) VALUES
(1, 'Universiti Sains Malaysia'),
(2, 'Universiti Malaya'),
(3, 'Universiti Kebangsaan Malaysia'),
(4, 'Universiti Putra Malaysia'),
(5, 'Universiti Teknologi Malaysia');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` int(11) NOT NULL,
  `User_email` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Role` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `User_email`, `Password`, `Role`) VALUES
(1, 'admin@gmail.com', '123456', 'Admin'),
(2, 'test1@gmail.com', '123456', 'Student'),
(3, 'test2@gmail.com', '123456', 'Student'),
(4, 'teacher1@gmail.com', '123456', 'Teacher'),
(5, 'teacher2@gmail.com', '123456', 'Teacher'),
(6, 'test3@gmail.com', '123456', 'Student'),
(7, 'teacher3@gmail.com', '123456', 'Teacher'),
(8, 'test4@gmail.com', '123456', 'Student'),
(9, 'test5@gmail.com', '123456', 'Student'),
(10, 'test6@gmail.com', '123456', 'Student'),
(11, 'test7@gmail.com', '123456', 'Student'),
(12, 'test8@gmail.com', '123456', 'Student'),
(13, 'test9@gmail.com', '123456', 'Student'),
(14, 'test10@gmail.com', '123456', 'Student'),
(15, 'test11@gmail.com', '123456', 'Student');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `announcements`
--
ALTER TABLE `announcements`
  ADD PRIMARY KEY (`AnnouncementID`),
  ADD KEY `CourseID` (`CourseID`);

--
-- Indexes for table `classcancellations`
--
ALTER TABLE `classcancellations`
  ADD PRIMARY KEY (`CancellationID`),
  ADD KEY `ClassID` (`ClassID`);

--
-- Indexes for table `classes`
--
ALTER TABLE `classes`
  ADD PRIMARY KEY (`ClassID`),
  ADD KEY `CourseID` (`CourseID`);

--
-- Indexes for table `classschedules`
--
ALTER TABLE `classschedules`
  ADD PRIMARY KEY (`ScheduleID`),
  ADD KEY `ClassID` (`ClassID`);

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`CourseID`);

--
-- Indexes for table `duedates`
--
ALTER TABLE `duedates`
  ADD PRIMARY KEY (`DueDateID`),
  ADD KEY `CourseID` (`CourseID`);

--
-- Indexes for table `studentcourses`
--
ALTER TABLE `studentcourses`
  ADD PRIMARY KEY (`StudentID`,`CourseID`),
  ADD KEY `CourseID` (`CourseID`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`StudentID`),
  ADD UNIQUE KEY `UserID` (`UserID`),
  ADD KEY `UniversityID` (`UniversityID`);

--
-- Indexes for table `teachers`
--
ALTER TABLE `teachers`
  ADD PRIMARY KEY (`TeacherID`),
  ADD UNIQUE KEY `UserID` (`UserID`),
  ADD KEY `UniversityID` (`UniversityID`);

--
-- Indexes for table `teacherscourses`
--
ALTER TABLE `teacherscourses`
  ADD PRIMARY KEY (`TeacherCourseID`),
  ADD KEY `TeacherID` (`TeacherID`),
  ADD KEY `CourseID` (`CourseID`);

--
-- Indexes for table `timetableclasses`
--
ALTER TABLE `timetableclasses`
  ADD PRIMARY KEY (`TimetableID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `CourseID` (`CourseID`);

--
-- Indexes for table `timetablecolour`
--
ALTER TABLE `timetablecolour`
  ADD PRIMARY KEY (`TimetableColourID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `timetabledays`
--
ALTER TABLE `timetabledays`
  ADD PRIMARY KEY (`TimetableDayID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `timetabletime`
--
ALTER TABLE `timetabletime`
  ADD PRIMARY KEY (`TimetableTimeID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `universities`
--
ALTER TABLE `universities`
  ADD PRIMARY KEY (`UniversityID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`),
  ADD UNIQUE KEY `User_email` (`User_email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `announcements`
--
ALTER TABLE `announcements`
  MODIFY `AnnouncementID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `classes`
--
ALTER TABLE `classes`
  MODIFY `ClassID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `CourseID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `duedates`
--
ALTER TABLE `duedates`
  MODIFY `DueDateID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `StudentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `teachers`
--
ALTER TABLE `teachers`
  MODIFY `TeacherID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `teacherscourses`
--
ALTER TABLE `teacherscourses`
  MODIFY `TeacherCourseID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `timetableclasses`
--
ALTER TABLE `timetableclasses`
  MODIFY `TimetableID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `timetablecolour`
--
ALTER TABLE `timetablecolour`
  MODIFY `TimetableColourID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `timetabledays`
--
ALTER TABLE `timetabledays`
  MODIFY `TimetableDayID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `timetabletime`
--
ALTER TABLE `timetabletime`
  MODIFY `TimetableTimeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `universities`
--
ALTER TABLE `universities`
  MODIFY `UniversityID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `announcements`
--
ALTER TABLE `announcements`
  ADD CONSTRAINT `announcements_ibfk_1` FOREIGN KEY (`CourseID`) REFERENCES `courses` (`CourseID`);

--
-- Constraints for table `classcancellations`
--
ALTER TABLE `classcancellations`
  ADD CONSTRAINT `classcancellations_ibfk_1` FOREIGN KEY (`ClassID`) REFERENCES `classes` (`ClassID`);

--
-- Constraints for table `classes`
--
ALTER TABLE `classes`
  ADD CONSTRAINT `classes_ibfk_1` FOREIGN KEY (`CourseID`) REFERENCES `courses` (`CourseID`);

--
-- Constraints for table `classschedules`
--
ALTER TABLE `classschedules`
  ADD CONSTRAINT `classschedules_ibfk_1` FOREIGN KEY (`ClassID`) REFERENCES `classes` (`ClassID`);

--
-- Constraints for table `duedates`
--
ALTER TABLE `duedates`
  ADD CONSTRAINT `duedates_ibfk_1` FOREIGN KEY (`CourseID`) REFERENCES `courses` (`CourseID`);

--
-- Constraints for table `studentcourses`
--
ALTER TABLE `studentcourses`
  ADD CONSTRAINT `studentcourses_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `students` (`StudentID`),
  ADD CONSTRAINT `studentcourses_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `courses` (`CourseID`);

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`UniversityID`) REFERENCES `universities` (`UniversityID`),
  ADD CONSTRAINT `students_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);

--
-- Constraints for table `teachers`
--
ALTER TABLE `teachers`
  ADD CONSTRAINT `teachers_ibfk_1` FOREIGN KEY (`UniversityID`) REFERENCES `universities` (`UniversityID`),
  ADD CONSTRAINT `teachers_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);

--
-- Constraints for table `teacherscourses`
--
ALTER TABLE `teacherscourses`
  ADD CONSTRAINT `teacherscourses_ibfk_1` FOREIGN KEY (`TeacherID`) REFERENCES `teachers` (`TeacherID`),
  ADD CONSTRAINT `teacherscourses_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `courses` (`CourseID`);

--
-- Constraints for table `timetableclasses`
--
ALTER TABLE `timetableclasses`
  ADD CONSTRAINT `timetableclasses_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`),
  ADD CONSTRAINT `timetableclasses_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `courses` (`CourseID`);

--
-- Constraints for table `timetablecolour`
--
ALTER TABLE `timetablecolour`
  ADD CONSTRAINT `timetablecolour_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);

--
-- Constraints for table `timetabledays`
--
ALTER TABLE `timetabledays`
  ADD CONSTRAINT `timetabledays_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);

--
-- Constraints for table `timetabletime`
--
ALTER TABLE `timetabletime`
  ADD CONSTRAINT `timetabletime_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
