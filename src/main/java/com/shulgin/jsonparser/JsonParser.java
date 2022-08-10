package com.shulgin.jsonparser;

import com.shulgin.entity.Student;

import java.io.*;
import java.util.*;

/**
 * Класс JsonParser получает на вход файл в формате
 * <pre>{
 *     "students": [
 *          {
 *              "id": 1,
 *              "name": "student1"
 *          },
 *          {
 *              "id": 2,
 *              "name": "student2"
 *          },
 *     ]
 * }</pre>
 * и преобразует его в List и обратно в файл json.
 */
public class JsonParser {

    private final String HEAD = "{\n" +
            "  \"students\" : [";
    private final String FOOTER = "\n\t]" +
            "\n}";

    private File file;
    StringBuilder json = new StringBuilder();

    /**
     * Конструктор принимает на вход файл json.
     * @param file json файл.
     * @throws IOException ошибка чтения файла.
     */
    public JsonParser(File file) throws IOException{
        this.file = file;
        json = readFile();
    }

    /**
     * Метод читает файл и записывает в StringBuilder.
     * @return StringBuilder объект.
     * @throws IOException ошибка во время чтения.
     */
    private StringBuilder readFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            while(reader.ready()) {
                String str = reader.readLine();
                sb.append(str);
            }
        }
        return sb;
    }

    /**
     * Метод читает файл и возвращает список студентов.
     * @return список студентов.
     */
    public List<Student> parse() {
        List<Student> students = new ArrayList<>();
        int index = 0;
        while(json.charAt(index) != '[') {
            index++;
        }
        boolean open = false;
        int start = index;
        int end = index;
        while(json.charAt(index) != ']') {
            char ch = json.charAt(index);
            if (open) {
                if (ch == '}') {
                    open = false;
                    students.add(parseStudent(json, start, end));
                    start = index + 1;
                    end = index + 1;
                } else {
                    end++;
                }
            } else {
                if(ch == '{') {
                    open = true;
                    start = index + 1;
                    end = index + 1;
                }
            }
            index++;
        }
        return students;
    }

    /**
     * Метод разбирает строку с параметрами студента.
     * @param sb содержимое файла json.
     * @param start указатель на начало параметров студента.
     * @param end указатель на окончание параметров студента.
     * @return объект студента.
     */
    public Student parseStudent(StringBuilder sb, int start, int end) {
        Map<String, String> params = new HashMap<>();
        int index = start;
        while(index < end) {
            if(sb.charAt(index) == ',') {
                Map.Entry<String, String> param = parseParam(sb, start, index);
                params.put(param.getKey(), param.getValue());
                start = index + 1;
            }
            index++;
        }
        Map.Entry<String, String> param = parseParam(sb, start, end);
        params.put(param.getKey(), param.getValue());
        int id = Integer.parseInt(params.get("id"));
        String name = params.get("name");
        return new Student(id, name);
    }

    /**
     * Метод получает строку, содержащую атрибут студента и преобразует его в ключ и значение.
     * @param sb содержимое файла json.
     * @param start указатель на начало параметра.
     * @param end указатель на окончание параметра.
     * @return сущность ключ-значение.
     */
    public Map.Entry<String, String> parseParam(StringBuilder sb, int start, int end) {
        int index = start;
        int startKey = index;
        int endKey = index;
        boolean isKey = false;
        boolean isKeyOpen = false;
        boolean isVal = false;
        int startVal = index;
        int endVal = index;

        while(index < end) {
            char ch = sb.charAt(index);
            if(!isKey) {
                if(ch == '"' && !isKeyOpen) {
                    startKey = index + 1;
                    endKey = index + 1;
                    isKeyOpen = true;
                } else if(ch == '"') {
                    isKeyOpen = false;
                    isKey = true;
                }
                else {
                    endKey++;
                }
            } else {
                if(isVal) {
                    endVal++;
                }else {
                    if(ch == ':') {
                        isVal = true;
                        startVal = index + 1;
                        endVal = index + 1;
                    }
                }
            }

            index++;
        }
        String key = sb.substring(startKey, endKey);
        String value = sb.substring(startVal, endVal).replaceAll("\"", "").trim();
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    /**
     * Метод сохраняет Список студентов в файл.
     * @param students список студентов.
     * @throws IOException ошибка записи в файл.
     */
    public void saveStudents(List<Student> students) throws IOException{
        StringBuilder sb = new StringBuilder();
        sb.append(HEAD);
        StringJoiner sj = new StringJoiner(",");
        for (Student student : students) {
            int id = student.getId();
            String name = student.getName();
            String str = "\n\t\t{"
                    + "\n\t\t\t\"id\": " + id + ",\n"
                    + "\t\t\t\"name\": \"" + name
                    + "\"\n\t\t}";
            sj.add(str);
        }
        sb.append(sj);
        sb.append(FOOTER);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            writer.write(sb.toString());
            writer.flush();
        }
    }
}
