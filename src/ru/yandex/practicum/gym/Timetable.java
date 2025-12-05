package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private /* как это хранить??? */ Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);
        List<TrainingSession> sessions = daySchedule.get(time);

        if (sessions == null) {
            sessions = new ArrayList<>();
            daySchedule.put(time, sessions);
        }

        sessions.add(trainingSession);
    }

    public /* непонятно, что возвращать */ List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        List<TrainingSession> result = new ArrayList<>();
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        for (List<TrainingSession> sessions : daySchedule.values()) {
            result.addAll(sessions);
        }

        return result;
    }

    public /* непонятно, что возвращать */ List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        List<TrainingSession> sessions = daySchedule.get(timeOfDay);

        if (sessions == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(sessions);
    }

    public /* непонятно, что возвращать */ List<CoachTrainingCount> getCountByCoaches() {
        Map<Coach, Integer> coachCountMap = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> daySchedule : timetable.values()) {
            for (List<TrainingSession> sessions : daySchedule.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    coachCountMap.put(coach, coachCountMap.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<CoachTrainingCount> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCountMap.entrySet()) {
            result.add(new CoachTrainingCount(entry.getKey(), entry.getValue()));
        }

        result.sort((c1, c2) -> c2.getCount() - c1.getCount());

        return result;
    }

    public static class CoachTrainingCount {
        private final Coach coach;
        private final int count;

        public CoachTrainingCount(Coach coach, int count) {
            this.coach = coach;
            this.count = count;
        }

        public Coach getCoach() {
            return coach;
        }

        public int getCount() {
            return count;
        }
    }
}