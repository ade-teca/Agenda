package com.keisarmind;


import com.keisarmind.dao.ContactDAOImpl;
import com.keisarmind.model.Contact;
import com.keisarmind.service.ContactService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContactService contactService = new ContactService(new ContactDAOImpl());
        Scanner scanner = new Scanner(System.in);
        int choose;

        do {
            System.out.println("\nChoose an operation:");
            System.out.println("1 - Load all contacts");
            System.out.println("2 - Find contact by ID");
            System.out.println("3 - Add a new contact");
            System.out.println("4 - Update contact data");
            System.out.println("5 - Delete contact by ID");
            System.out.println("0 - Exit");
            System.out.print("Choose an option: ");
            choose = scanner.nextInt();

            switch (choose) {
                case 1:
                    List<Contact> contacts = contactService.getAllContacts();
                    if (contacts.isEmpty()) {
                        System.out.println("No contacts found.");
                    } else {
                        contacts.forEach(contact ->
                                System.out.println(contact.getId() + ": " + contact.getName() + " - " + contact.getEmail()));
                    }
                    break;
                case 2:
                    System.out.print("Enter the ID: ");
                    int id = scanner.nextInt();
                    Optional<Contact> contact = contactService.findContactById(id);
                    if (contact.isPresent()) {
                        System.out.println("Contact found: " + contact.get().getName() + " - " + contact.get().getEmail());
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter contact name: ");
                    scanner.nextLine(); // Consume newline
                    String name = scanner.nextLine();
                    Contact newContact = new Contact();
                    newContact.setName(name);
                    System.out.print("Enter contact email: ");
                    String email = scanner.nextLine();
                    newContact.setEmail(email);
                    System.out.print("Enter contact age: ");
                    int age = scanner.nextInt();
                    newContact.setAge(age);
                    try {
                        boolean saved = contactService.saveContact(newContact);
                        if (saved) {
                            System.out.println("Contact added successfully.");
                        } else {
                            System.out.println("Failed to add contact. Please check the data.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Enter the ID to update data: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Optional<Contact> updateContact = contactService.findContactById(updateId);
                    if (updateContact.isPresent()) {
                        Contact contactToUpdate = updateContact.get();
                        System.out.print("Enter new name: ");
                        String newName = scanner.nextLine();
                        contactToUpdate.setName(newName);
                        System.out.print("Enter new email: ");
                        String newEmail = scanner.nextLine();
                        contactToUpdate.setEmail(newEmail);
                        System.out.print("Enter new age: ");
                        int newAge = scanner.nextInt();
                        contactToUpdate.setAge(newAge);
                        boolean updated = contactService.updateContact(contactToUpdate);
                        if (updated) {
                            System.out.println("Contact updated successfully.");
                        } else {
                            System.out.println("Failed to update contact. Please check the data.");
                        }
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter the ID of the contact: ");
                    int deleteId = scanner.nextInt();
                    boolean deleted = contactService.deleteContact(deleteId);
                    if (deleted) {
                        System.out.println("Contact deleted successfully.");
                    } else {
                        System.out.println("Failed to delete contact. Contact not found.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choose != 0);

        scanner.close();
    }
}

