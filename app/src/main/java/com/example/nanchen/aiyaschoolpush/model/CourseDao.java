package com.example.nanchen.aiyaschoolpush.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.model
 * @date 2016/12/08  09:08
 */

public class CourseDao {
    public static List<CourseModel>[] getCourseData() {

        List<CourseModel> courseModels[] = new ArrayList[7];

        for (int i = 0; i < courseModels.length; i++) {
            courseModels[i] = new ArrayList<>();
        }

        List<CourseModel> models_1 = new ArrayList<>();
        models_1.add(new CourseModel(0, "C语言", 1, 2, 1, "A401", (int) (Math.random() * 10)));
        models_1.add(new CourseModel(1, "Ruby", 3, 3, 1, "A453", (int) (Math.random() * 10)));
        models_1.add(new CourseModel(1, "PHP", 6, 3, 1, "A483", (int) (Math.random() * 10)));
        courseModels[0].addAll(models_1);

        List<CourseModel> models_2 = new ArrayList<>();
        models_2.add(new CourseModel(2, "Swift", 2, 2, 2, "A222", (int) (Math.random() * 10)));
        models_2.add(new CourseModel(3, "JavaScript", 6, 2, 2, "A777", (int) (Math.random() * 10)));
        courseModels[1].addAll(models_2);

        List<CourseModel> models_3 = new ArrayList<>();
        models_3.add(new CourseModel(2, "Python", 1, 2, 3, "A342", (int) (Math.random() * 10)));
        models_3.add(new CourseModel(3, "Visual Basic .NET", 5, 2, 3, "A737", (int) (Math.random() * 10)));
        courseModels[2].addAll(models_3);

        List<CourseModel> models_4 = new ArrayList<>();
        models_4.add(new CourseModel(4, "C#", 1, 3, 4, "A666", (int) (Math.random() * 10)));
        models_4.add(new CourseModel(5, "R语言", 5, 2, 4, "A888", (int) (Math.random() * 10)));
        models_4.add(new CourseModel(5, "Java", 9, 2, 4, "A828", (int) (Math.random() * 10)));
        courseModels[3].addAll(models_4);

        List<CourseModel> models_5 = new ArrayList<>();
        models_5.add(new CourseModel(6, "Android", 1, 2, 5, "A466", (int) (Math.random() * 10)));
        models_5.add(new CourseModel(7, "Groovy", 3, 2, 5, "A434", (int) (Math.random() * 10)));
        models_5.add(new CourseModel(8, "Objective-C", 6, 2, 5, "A411", (int) (Math.random() * 10)));
        courseModels[4].addAll(models_5);

        List<CourseModel> models_6 = new ArrayList<>();
        models_6.add(new CourseModel(9, "C++", 1, 2, 6, "A422", (int) (Math.random() * 10)));
        models_6.add(new CourseModel(10, "SQL", 5, 3, 6, "A402", (int) (Math.random() * 10)));
        courseModels[5].addAll(models_6);

        return courseModels;

    }
}
