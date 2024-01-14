-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 14, 2024 at 05:18 PM
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
(1, 1, 'testing', 'tomorrow pls bring some shirt to school', '2024-01-14', 'Teacher 1'),
(2, 1, 'qwsajskjaw', 'asasqwq', '2024-01-14', 'Teacher 1'),
(3, 1, 'sa', 'qa', '2024-01-14', 'Teacher 1'),
(4, 2, 'sasa', 'qqs', '2024-01-14', 'Teacher 1'),
(5, 1, 'sqs', '132', '2024-01-14', 'Teacher 1');

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
(5, 2, '2024-01-14', '12', 'sqwsq', '2024-01-14', 'Teacher 1'),
(6, 1, '2024-01-21', 'jacky', 'qwqwqw', '2024-01-14', 'Teacher 1'),
(7, 1, '2024-01-07', 'jasajs', 'sas', '2024-01-14', 'Teacher 1'),
(8, 2, '2024-01-06', '21was', '12', '2024-01-14', 'Teacher 1'),
(9, 2, '2024-01-06', '1212sasas', 'sas', '2024-01-14', 'Teacher 1'),
(10, 2, '2024-01-06', '21sas', 'as', '2024-01-14', 'Teacher 1'),
(11, 2, '2024-01-07', 'df', 'asd', '2024-01-14', 'Teacher 1'),
(12, 1, '2024-01-15', 'future', 'testing 124 sdkjdkanksdaiosdjasd asyaa sasa', '2024-01-14', 'Teacher 1'),
(13, 1, '2024-01-14', 'today', 'hj jfg hbghuj', '2024-01-14', 'Teacher 1');

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
(2, 'Student 2', 1, 3);

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
(3, 'Teacher 2', 1, 5);

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
-- Table structure for table `timetable`
--

CREATE TABLE `timetable` (
  `TimetableID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `timetableclasses`
--

CREATE TABLE `timetableclasses` (
  `TimetableID` int(11) NOT NULL,
  `ClassID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `timetables`
--

CREATE TABLE `timetables` (
  `TimetableID` int(11) NOT NULL,
  `StudentID` int(11) DEFAULT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `DayOfWeek` varchar(10) DEFAULT NULL,
  `StartTime` time DEFAULT NULL,
  `EndTime` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(5, 'teacher2@gmail.com', '123456', 'Teacher');

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
-- Indexes for table `timetable`
--
ALTER TABLE `timetable`
  ADD PRIMARY KEY (`TimetableID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `timetableclasses`
--
ALTER TABLE `timetableclasses`
  ADD PRIMARY KEY (`TimetableID`,`ClassID`),
  ADD KEY `ClassID` (`ClassID`);

--
-- Indexes for table `timetables`
--
ALTER TABLE `timetables`
  ADD PRIMARY KEY (`TimetableID`),
  ADD KEY `StudentID` (`StudentID`),
  ADD KEY `CourseID` (`CourseID`);

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
  MODIFY `AnnouncementID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

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
  MODIFY `DueDateID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `StudentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `teachers`
--
ALTER TABLE `teachers`
  MODIFY `TeacherID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `teacherscourses`
--
ALTER TABLE `teacherscourses`
  MODIFY `TeacherCourseID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `timetable`
--
ALTER TABLE `timetable`
  MODIFY `TimetableID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `timetables`
--
ALTER TABLE `timetables`
  MODIFY `TimetableID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `universities`
--
ALTER TABLE `universities`
  MODIFY `UniversityID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

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
-- Constraints for table `timetable`
--
ALTER TABLE `timetable`
  ADD CONSTRAINT `timetable_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);

--
-- Constraints for table `timetableclasses`
--
ALTER TABLE `timetableclasses`
  ADD CONSTRAINT `timetableclasses_ibfk_1` FOREIGN KEY (`TimetableID`) REFERENCES `timetables` (`TimetableID`),
  ADD CONSTRAINT `timetableclasses_ibfk_2` FOREIGN KEY (`ClassID`) REFERENCES `classes` (`ClassID`);

--
-- Constraints for table `timetables`
--
ALTER TABLE `timetables`
  ADD CONSTRAINT `timetables_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `students` (`StudentID`),
  ADD CONSTRAINT `timetables_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `courses` (`CourseID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
