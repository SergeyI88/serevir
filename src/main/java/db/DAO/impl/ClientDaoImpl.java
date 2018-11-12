package db.DAO.impl;

import db.DAO.ClientDao;
import db.connection.ConnectionPostgres;
import db.entity.Client;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

@Repository
@SuppressWarnings("Duplicates")
public class ClientDaoImpl implements ClientDao {

    @Override
    public Client getClientById(long id) {
        Client client = new Client();
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from client where client_id = ?");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                client.setId(set.getLong("client_id"));
                client.setToken(set.getString("token"));
                client.setCompanyName(set.getString("company_name"));
                client.setUuid(set.getString("uuid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Client getClientByUuid(String uuid) {
        Client client = new Client();
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from client where uuid = ?");
            statement.setString(1, uuid);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                client.setId(set.getLong("client_id"));
                client.setToken(set.getString("token"));
                client.setCompanyName(set.getString("company_name"));
                client.setUuid(set.getString("uuid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Client getClientByTokenAndUpdateWasAlert(String token) {
        Client client = new Client();
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from client where token = ?");
            statement.setString(1, token);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                client.setWasAlert(set.getBoolean("was_alert"));
                client.setEnabled(set.getBoolean("is_enabled"));
                client.setId(set.getLong("client_id"));
                client.setToken(set.getString("token"));
                client.setCompanyName(set.getString("company_name"));
                client.setUuid(set.getString("uuid"));
            } else {
                client.setEnabled(null);
            }
            if (!client.isWasAlert()) {
                PreparedStatement statement2 = connection.prepareStatement("update client SET was_alert = TRUE WHERE token = ?");
                statement2.setString(1, token);
                statement2.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public boolean createClient(String token, String company_name, String uuid, Connection connection) {
        PreparedStatement statement;
        int result = 0;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("INSERT INTO client VALUES(DEFAULT, ?, ?, ?, DEFAULT)");
            statement.setString(1, token);
            statement.setString(2, company_name);
            statement.setString(3, uuid);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 1;
    }

    @Override
    public void removeClient(String userUuid) {
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE from client where uuid = ?");
            statement.setString(1, userUuid);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setIsEnable(String uuid, Boolean isEnabled) {
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE client set is_enabled = ? " +
                    "where uuid = ?");
            statement.setBoolean(1, isEnabled);
            statement.setString(2, uuid);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean getIsEnable(String token) {
        boolean res = false;
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select is_enabled from client where token = ?");
            statement.setString(1, token);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                res = set.getBoolean("is_enabled");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean getWasAlert(String token) {
        boolean res = false;
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select was_alert from client where token = ?");
            statement.setString(1, token);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                res = set.getBoolean("was_alert");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void setWasAlert(String token) {
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("update client SET was_alert = TRUE WHERE token = ?");
            statement.setString(1, token);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void main(String[] args) {

        Optional<Integer> optional = Optional.empty();
        Stream.of(optional.orElse(1))
                .peek(System.out::println)
                .filter(i -> i + "".length() == 3)
                .forEach(integer -> System.out.println(integer));
        Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
        ClientDaoImpl clientDao = new ClientDaoImpl();
        Queue<Integer> d = new ArrayDeque<>();

        d.offer(1);
        d.offer(2);
        d.offer(3);
        d.remove(1);
//        System.out.println(d.pollFirst());
        System.out.println(d.poll());
        System.out.println(d.poll());
        System.out.println(d.poll());
        System.out.println(d.peek());
        System.out.println("============");

        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.remove(new Integer(2));
        stack.push(3);
        stack.add(4);
        System.out.println(stack.peek());
//        System.out.println(stack.pop());
//        stack.offer(10);
//        stack.element(11);
//        stack.poll();
//        Queue<Integer> queue = new ConcurrentLinkedDeque<>();
//        queue.add(1);
//        queue.offer();
//        queue.peek();
//        queue.poll();
//        queue.remove();
//        queue.element();
//        queue.pop();
//        queue.push();

        System.out.println(Arrays.asList('w', 'o', 'l', 'f')
                .parallelStream()
                .reduce("", (c, s1) -> c + s1,
                        (s2, s3) -> s2 + s3));

//        System.out.println(d.pollLast());
//        Map<Integer, Optional<Character>> map =
//                clientDao.getIntegerOptionalMap(ohMy);
//        System.out.println(map);
        class A1 {
        }


    }
}

class Account {
    private String id;

    public Account(String id) {
        this.id = id;
    }
}

class BankAccount extends Account {
    public double getBalance() {
        return balance;
    }

    private double balance;

    public BankAccount(String id, double balance) {
        super(id);
        this.balance = balance;
    }


    public static void main(String[] args) throws IOException {
        int res = Stream.of(1,2,3,4).max(Integer::compare).get();
        System.out.println(res);
        new File("rep").mkdir();
        File file = new File("222.data");
        file.createNewFile();
        Console console = System.console();
        PrintStream out = new PrintStream(System.out);
        out.println(1);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        BufferedInputStream in2 = new BufferedInputStream(in);
        ObjectInputStream objectInputStream = new ObjectInputStream(in);

        Map<String, Account> myAccts = new HashMap<>();
        myAccts.put("111", new Account("111"));
        myAccts.put("222", new BankAccount("111", 200.0));
        BiFunction<String, Account, Account> bif =
                (a1, a2) -> {
                    System.out.println(a1);
                    return a2 instanceof BankAccount ? new BankAccount(a1, 300.0) : new Account(a1);
                };
        myAccts.computeIfPresent("222", bif);
        BankAccount ba = (BankAccount) myAccts.get("222");
        System.out.println(ba.getBalance());
        System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
    }
}

//    private void getIntegerOptionalMap(Stream<String> ohMy) {
////        System.out.println(ohMy.collect());
//        System.out.println(ohMy.collect(Collectors.groupingBy(s -> s.charAt(s.toCharArray().length - 1), Collectors.mapping(String::length, Collectors.toList()))));
//    }
//}
