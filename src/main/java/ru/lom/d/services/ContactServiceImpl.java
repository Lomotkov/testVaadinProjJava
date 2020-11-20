package ru.lom.d.services;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.lom.d.configurations.HibernateConfig;
import ru.lom.d.data.Contact;

import java.util.List;

public class ContactServiceImpl implements ContactService {

    private final Logger logger = Logger.getLogger(ContactServiceImpl.class);

    @Override
    public void addContact(Contact contact) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(contact);
            transaction.commit();
            logger.info("Contact " + contact.getFirstName() + " " + contact.getLastName() + " was added");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Contact> getAllContact() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            List<Contact> result;
            Transaction transaction = session.beginTransaction();
            result = session.createQuery("from Contact", Contact.class).getResultList();
            transaction.commit();
            logger.info("Count of contacts: " + result.size());
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void removeContact(Contact contact) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(contact);
            transaction.commit();
            logger.info("Contact " + contact.getFirstName() + " " + contact.getLastName() + " was deleted");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Contact> getContactsByRegex(String searchParam) {
        if (searchParam.isEmpty()) {
            return getAllContact();
        }
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            List<Contact> result;
            Transaction transaction = session.beginTransaction();
            result = session.createQuery("from Contact c where :param = cast(c.id as string) " +
                    "or :param in c.firstName " +
                    "or :param in c.lastName " +
                    "or c.email in :param " +
                    "or c.phoneNumber in :param").setParameter("param", searchParam).getResultList();
            transaction.commit();
            logger.info("Count of contacts searched: " + result.size());
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void updateContact(Contact selectedContact) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(selectedContact);
            transaction.commit();
            logger.info("Contact " + selectedContact.getFirstName() + " " + selectedContact.getLastName() + " was updated");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
