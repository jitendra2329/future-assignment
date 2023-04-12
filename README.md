# future-assignment
## Student Grades

Student Grades is a Scala program that parses a CSV file containing student grades and calculates the average grades for each student as well as the class average.

## Usage

The StudentGrades object contains the following methods:
parseCsv

      def parseCsv(path: String): Future[List[Map[String, String]]]

This method takes the path to a CSV file and returns a Future that resolves to a list of maps, where each map represents a row in the CSV file. The keys of the map correspond to the headers of the CSV file, and the values correspond to the values in the corresponding row.
calculateStudentAverages


      def calculateStudentAverages(parsedData: Future[List[Map[String, String]]]): Future[List[(String, Double)]]

This method takes a Future that resolves to a list of maps (as returned by parseCsv) and returns a Future that resolves to a list of tuples. Each tuple contains the ID of a student and their average grade across all subjects.
calculateClassAverage

      def calculateClassAverage(studentAverages: Future[List[(String, Double)]]): Future[Double]

This method takes a Future that resolves to a list of student averages (as returned by calculateStudentAverages) and returns a Future that resolves to the class average.
calculateGrades


      def calculateGrades(path: String): Future[Double]

This method is a convenience method that combines the functionality of parseCsv, calculateStudentAverages, and calculateClassAverage into a single method. It takes the path to a CSV file, parses it, calculates the student averages, and calculates the class average. It returns a Future that resolves to the class average.

## Error Handling

The program uses Try and Future to handle errors. If an error occurs during parsing or calculation, a Failure is returned, which can be handled using standard Scala error handling techniques.


## One of the outputs-

![Screenshot from 2023-04-12 11-57-35](https://user-images.githubusercontent.com/124979608/231370019-a8eeccb4-b706-4dba-b100-f1cfe40f4798.png)
