/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jrpc.example.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.jrpc.example.data.User;
import me.jrpc.example.data.UserFilter;

/**
 *
 * @author Owner
 */
public class UserManager {

    static List<User> users;
    static Random random = new Random();

    public static User[] getAll() {
        if (users == null) {
            generate();
        }
        User[] userArray = new User[users.size()];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            userArray[i] = user;
        }
        return userArray;
    }

    public static User[] getByFilters(UserFilter[] filters) {
        if (filters.length == 0){
            return getAll();
        }
        List<User> filteredUsers = new ArrayList<>();
        for (User user : users) {
            boolean allFiltersMatch = true;
            for (UserFilter filter : filters) {
                if ("gender".equalsIgnoreCase(filter.type)) {
                    if ((filter.value == 1 && !user.isMale) || (filter.value == 0 && user.isMale) ){
                        allFiltersMatch = false;
                        break;
                    }
                } else {
                    if (user.age < filter.value){
                         allFiltersMatch = false;
                        break;
                    }
                }
            }
            if (allFiltersMatch) {
                filteredUsers.add(user);
            }
        }
        User[] userArray = new User[filteredUsers.size()];
        for (int i = 0; i < filteredUsers.size(); i++) {
            User user = filteredUsers.get(i);
            userArray[i] = user;
        }
        return userArray;
    }

    private static void generate() {
        users = new ArrayList<>();
        String[] maleNames = {"John Smith", "Justin Timber", "Joe Doe", "Jihm Kerry", "Brad Pitt"};
        String[] femaleNames = {"Jina Lango", "Judith Boder", "Edit Piaf", "Cristina Arbakayte", "Madonna"};

        int count = 0;
        for (String name : maleNames) {
            int age = getRandomInt(10, 80);
            if (count == 2){
                age = getRandomInt(10, 18);
            }
            User user = new User(name, age, true);
            users.add(user);
            count++;
        }
        count = 0;
        for (String name : femaleNames) {
            int age = getRandomInt(10, 80);
            if (count == 2){
                age = getRandomInt(10, 18);
            }
            User user = new User(name, age, false);
            users.add(user);
            count++;
        }
    }

    public static int getRandomInt(int min, int max) {
        return min + random.nextInt(max - min);
    }

}
