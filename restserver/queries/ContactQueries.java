package it.unical.dimes.reti.restserver.queries;

import it.unical.dimes.reti.restserver.model.Contact;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContactQueries {

    String dbFileLocation;
    File db;
    Map<Long, Contact> contatti = new HashMap<>();

    public ContactQueries() {
        dbFileLocation = "databaseContatti.json";
        db = new File(dbFileLocation);
        try {
            if (db.createNewFile()) {
                System.out.println("Database has been created.");
            } else {
                Files.lines(Paths.get(dbFileLocation))
                        .map(line -> new Contact(line)).forEach(c -> contatti.put(c.getId(), c));
                System.out.println("Database file has been loaded.");
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void insertOrUpdate(Contact c) {
        contatti.put(c.getId(), c);
        flushDB();
    }

    public Contact select(long id) {
        return contatti.get(id);
    }

    public void delete(long id) {
        contatti.remove(id);
        flushDB();
    }

    public Collection<Contact> getAll() {
        return contatti.values();
    }

    private void flushDB() {
        try {
            db = new File(dbFileLocation);
            List<String> dati = this.contatti.values().stream().map(Contact::toString).collect(Collectors.toList());
            Files.write(Paths.get(dbFileLocation), dati);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ContactQueries query = new ContactQueries();

        Contact contact = new Contact();
        contact.setId(1);
        contact.setName("Pippo");
        contact.setSurname("Rossi");
        contact.setAddress("Indirizzo 1");
        contact.setPhone("12323454245");

        query.insertOrUpdate(contact);

        Collection<Contact> list = query.getAll();
        for (Contact c : list) {
            System.out.println(c);
        }

    }


    public Collection<Contact> getByName(String name) {
        final String text = name.toLowerCase();
        return this.contatti.values().stream()
                .filter(c -> c.getName().toLowerCase().contains(text)
                        || c.getSurname().contains(text))
                .collect(Collectors.toList());
    }
}
