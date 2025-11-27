// package Builder;

// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.time.temporal.ChronoUnit;

// import GameEventLog;

// import java.time.ZoneId;

// public class LogBuilder {
//     private static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//     private GameEventLog log = new GameEventLog();

//     public LogBuilder(String caseId, String playerId, String action){ // this will build generic log used for system actions
//         String timestamp = createTimestamp(); // get time on init
//         this.log = new GameEventLog(caseId, playerId, action, timestamp);
//     }

//     private String createTimestamp(){
//         ZoneId zone = ZoneId.of("America/Port_of_Spain");
//         LocalDateTime now = LocalDateTime.now(zone).truncatedTo(ChronoUnit.SECONDS);
//         String timestamp = formatter.format(now);
//         return timestamp;   // in the format yyyy-mm-ddTHH:mm:ss as shown in the sample log
//     }

//     // added on for custom log creation + will always return Builder object until getResult() is called
//     public LogBuilder addCategory(String category) {
//         this.log.setCategory(category);
//         return this;
//     }

//     public LogBuilder value(int value) {
//         this.log.setValue(value);
//         return this;
//     }

//     public LogBuilder answerResultScore(String answer, boolean result, int newScore) {
//         this.log.setAnswerResultScore(answer, result, newScore);
//         return this;
//     }

//     public GameEventLog getResult() {
//         return this.log;
//     }
// }
