/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import com.amon.db.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.model.UploadedFile;

@SessionScoped
@ManagedBean(name = "acc")

public class POS implements Serializable {

    @PersistenceContext(unitName = "CSPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private List<Usergroup> usergroupList = new ArrayList<>();
    private Usergroup usergroup = new Usergroup();
    private List<User> userList = new ArrayList<>();
    private User user = new User();
    private List<Status> statusList = new ArrayList<>();
    private Status status = new Status();
    private List<Audit> auditList = new ArrayList<>();
    private Audit audit = new Audit();
    private List<Outlet> outletList = new ArrayList<>();
    private Outlet outlet = new Outlet();
    private List<Session> sessionList = new ArrayList<>();
    private Session session = new Session();
    private List<Paymentmethods> paymentmethodsList = new ArrayList<>();
    private Paymentmethods paymentmethods = new Paymentmethods();
    private List<Payments> paymentsList = new ArrayList<>();
    private Payments payments = new Payments();
    private List<Productcategory> productcategoryList = new ArrayList<>();
    private Productcategory productcategory = new Productcategory();
    private List<Products> productsList = new ArrayList<>();
    private Products products = new Products();
    private List<Producttransaction> producttransactionList = new ArrayList<>();
    private Producttransaction producttransaction = new Producttransaction();
    private List<Transactions> transactionsList = new ArrayList<>();
    private Transactions transactions = new Transactions();
    private String username = new String();
    private String password = new String();
    private boolean remember = false;
    private boolean admin = false;
    private boolean sales = false;
    private boolean accountant = false;
    private Date date;

    public POS() {
    }

    @PostConstruct
    public void init() {
        try {
            createAccidentModel();

        } catch (Exception e) {
        }
    }

    private void createAccidentModel() {

    }

    public String login() {
        try {
            user = (User) em.createQuery("select u from User u where u.username = '" + username + "' and u.pword = '" + password + "'").getSingleResult();
            session = (Session) em.createQuery("select s from Session s where s.idsession = '" + user.getSessionid().getIdsession() + "'").getSingleResult();
            if (new Date().after(session.getStartstime()) && new Date().before(session.getEndtime())) {
                usergroup = (Usergroup) getEm().createQuery("select u from Usergroup u where u.idgroups = " + user.getGroupID() + "").getSingleResult();
                if (usergroup.getName().equalsIgnoreCase("SALES")) {
                    setSales(true);
                    setAdmin(false);
                    setAccountant(false);
                    getUtx().begin();
                    getAudit().setAction("logged into the system at  " + new Date());
                    getAudit().setCreatedby(getUser().getIdusers());
                    getAudit().setTimer(new Date());
                    getEm().persist(getAudit());
                    getUtx().commit();

                    return "index2.xhtml?faces-redirect=true";
                } else if (usergroup.getName().equalsIgnoreCase("ADMIN")) {
                    setSales(false);
                    setAdmin(true);
                    setAccountant(false);
                    getUtx().begin();
                    getAudit().setAction("logged into the system at  " + new Date());
                    getAudit().setCreatedby(getUser().getIdusers());
                    getAudit().setTimer(new Date());
                    getEm().persist(getAudit());
                    getUtx().commit();

                    return "index2.xhtml?faces-redirect=true";
                } else if (usergroup.getName().equalsIgnoreCase("ACCOUNTANT")) {
                    setSales(false);
                    setAdmin(false);
                    setAccountant(true);
                    getUtx().begin();
                    getAudit().setAction("logged into the system at  " + new Date());
                    getAudit().setCreatedby(getUser().getIdusers());
                    getAudit().setTimer(new Date());
                    getEm().persist(getAudit());
                    getUtx().commit();

                    return "index2.xhtml?faces-redirect=true";
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "You have not been assigned a working group");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("msgs1", message);
                    return "index.xhtml?faces-redirect=true";
                }

            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Your working session is not within the current time interval");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("msgs1", message);
                return "index.xhtml?faces-redirect=true";
            }

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please provide correct credentials");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("msgs1", message);

            return null;
        }

    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getAttributes().clear();
        return "/index.xhtml?faces-redirect=true";
    }

    public String createOutlet() {
        try {

            getUtx().begin();
            getAudit().setAction("saved outlet " + getOutlet().getOutletname());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getOutlet());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getOutlet().getOutletname() + " saved successfully."));
            setOutlet(new Outlet());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setOutlet(new Outlet());
        return null;
    }

    public String updateOutlet() {
        try {

            Outlet outlet = getEm().find(Outlet.class, this.getOutlet().getIdoutlet());

            getUtx().begin();
            getAudit().setAction("updated outlet " + outlet.getOutletname());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(outlet);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", outlet.getOutletname() + " Updated successfully."));
            setOutlet(new Outlet());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setOutlet(new Outlet());
        return null;
    }

    public String deleteOutlet(Outlet outlet) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted Outlet");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Outlet toBeRemoved = (Outlet) getEm().merge(outlet);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            outlet = new Outlet();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Outlet deleted", "Outlet deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Outlet", success);

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Outlet", success);
        }
        setOutlet(new Outlet());
        return null;
    }

    public String createSession() {
        try {
            getSession().setCreatedby(new User(1));
            getSession().setCreatedon(new java.util.Date());

            getUtx().begin();
            getAudit().setAction("saved session " + getSession().getStartstime());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getSession());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getSession().getStartstime() + " saved successfully."));
            setSession(new Session());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setSession(new Session());
        return null;
    }

    public String updateSession() {
        try {

            Session session = getEm().find(Session.class, this.getSession().getIdsession());

            getUtx().begin();
            getAudit().setAction("updated session " + session.getStartstime());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(session);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", session.getStartstime() + " Updated successfully."));
            setSession(new Session());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setSession(new Session());
        return null;
    }

    public String deleteSession(Session session) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted Session");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Session toBeRemoved = (Session) getEm().merge(session);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            session = new Session();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Session deleted", "Session deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Session", success);

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Session", success);
        }
        setSession(new Session());
        return null;
    }

    public String createPaymentmethods() {
        try {

            getUtx().begin();
            getAudit().setAction("saved payment method " + getPaymentmethods().getMethod());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getPaymentmethods());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getPaymentmethods().getMethod() + " saved successfully."
            ));
            setPaymentmethods(new Paymentmethods());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setPaymentmethods(new Paymentmethods());
        return null;
    }

    public String updatePaymentmethods() {
        try {

            Paymentmethods paymentmethod = getEm().find(Paymentmethods.class,
                    this.getPaymentmethods().getIdpaymentmethods());
            getUtx().begin();
            getAudit().setAction("updated payment method " + paymentmethod.getMethod());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(paymentmethod);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", paymentmethod.getMethod() + " Updated successfully."));
            setPaymentmethods(new Paymentmethods());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setPaymentmethods(new Paymentmethods());
        return null;
    }

    public String deletePaymentmethods(Paymentmethods paymentmethod) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted payment method");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Paymentmethods toBeRemoved = (Paymentmethods) getEm().merge(paymentmethod);
            getEm().remove(toBeRemoved);
            getUtx().commit();

            paymentmethod = new Paymentmethods();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Paymentmethods deleted", "Paymentmethods deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Paymentmethods", success);

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Paymentmethods", success);
        }
        setPaymentmethods(new Paymentmethods());
        return null;
    }

    public String createPayments() {
        try {

            getUtx().begin();
            getAudit().setAction("saved payments " + getPayments().getTransactionID().getRetailprice());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getPayments());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getPayments().getMethodcode() + " created successfully."));
            setPayments(new Payments());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setPayments(new Payments());
        return null;
    }

    public String updatePayments() {
        try {

            Payments payments = getEm().find(Payments.class,
                    this.getPayments().getIdpayments());

            getUtx().begin();
            getAudit().setAction("updated payments " + payments.getPaymentAmount());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(payments);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", payments.getPaymentAmount() + " Updated successfully."));
            setPayments(new Payments());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setPayments(new Payments());
        return null;
    }

    public String deletePayments(Payments payments) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted payments");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Payments toBeRemoved = (Payments) getEm().merge(payments);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            payments = new Payments();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Payments deleted", "Payments deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Payments", success);

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Payments", success);
        }
        setPayments(new Payments());
        return null;
    }

    public String createProductcategory() {
        try {

            getUtx().begin();
            getAudit().setAction("saved category " + getProductcategory().getName());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getProductcategory().setCreatedBy(new User(1));
            getProductcategory().setCreatedOn(new java.util.Date());
            getEm().persist(getAudit());
            getEm().persist(getProductcategory());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getPayments().getOtherdetails() + " saved successfully."));
            setProductcategory(new Productcategory());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
            ex.printStackTrace();
        }
        setProductcategory(new Productcategory());
        return null;
    }

    public String updateProductcategory() {
        try {

            Productcategory productcategory = getEm().find(Productcategory.class,
                    this.getProductcategory().getIdproductcategory());

            getUtx().begin();
            getAudit().setAction("updated productcategory " + productcategory.getName());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(productcategory);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", productcategory.getName() + " Updated successfully."));
            setProductcategory(new Productcategory());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setProductcategory(new Productcategory());
        return null;
    }

    public String deleteProductcategory(Productcategory productcategory) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted user");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Productcategory toBeRemoved = (Productcategory) getEm().merge(productcategory);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            productcategory = new Productcategory();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Productcategory deleted", "Productcategory deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Productcategory", success);

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Productcategory", success);
        }
        setProductcategory(new Productcategory());
        return null;
    }

    private UploadedFile file;

    public String createProducts() {
        try {
            getUtx().begin();
            getAudit().setAction("saved product " + getProducts().getName());
            getAudit().setTimer(new Date());
            System.out.println(getFile().getFileName());
            getProducts().setImageurl(getFile().getFileName());
            getProducts().setCreatedOn(new java.util.Date());
            getProducts().setCreatedby(new User(1));
            getEm().persist(getAudit());
            getEm().persist(getProducts());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getProducts().getName() + " saved successfully."));
            setProducts(new Products());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
            ex.printStackTrace();
        }
        setUser(new User());
        return null;
    }

    public String updateProducts() {
        try {

            Products products = getEm().find(Products.class,
                    this.getProducts().getIdproducts());
            getUtx().begin();
            getAudit().setAction("updated products " + products.getName());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(products);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", products.getName() + " Updated successfully."));
            setProducts(new Products());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setProducts(new Products());
        return null;
    }

    public String deleteProducts(Products products) {
        try {
            getUtx().begin();
            getAudit().setAction("Deleted product");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Products toBeRemoved = (Products) getEm().merge(products);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            products = new Products();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Products deleted", "Products deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Products", success);

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Products", success);
        }

        return null;
    }

    public String createTransaction() {
        try {

            transactions.setCreatedOn(new java.util.Date());
            transactions.setRetailprice(products.getRetailprice());
            transactions.setStaffID(getUser());
            transactions.setStatusID(new Status(2));
            transactions.setWholesaleprice(products.getWholesaleprice());
            transactions.setOutletID(user.getOutlet());
            transactions.setOtherdetails(products.getOtherdetails());

            getUtx().begin();
            getAudit().setAction("saved transaction " + getTransactions().getRetailprice());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getTransactions());

            em.flush();

            producttransaction.setProductID(products);
            producttransaction.setQuantity(producttransaction.getQuantity());
            producttransaction.setStatusID(new Status(2));
            producttransaction.setTransactionID(transactions);
            producttransaction.setSessionDetails(producttransaction.getSessionDetails());
            getEm().persist(producttransaction);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getTransactions().getOtherdetails() + " saved successfully."));
            setTransactions(new Transactions());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
            ex.printStackTrace();
        }
        setTransactions(new Transactions());
        return null;
    }

    public String updateTransaction() {
        try {
            Transactions trans = getEm().find(Transactions.class, this.getTransactions().getIdtransactions());
            getUtx().begin();
            getAudit().setAction("updated transactions " + trans.getOtherdetails());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(getTransactions());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getTransactions().getOtherdetails() + " Updated successfully."));
            setTransactions(new Transactions());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setTransactions(new Transactions());
        return null;
    }

    public String deleteTransactions(Transactions transactions) {
        try {
            getUtx().begin();
            getAudit().setAction("Deleted transaction");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Transactions toBeRemoved = (Transactions) getEm().merge(transactions);
            getEm().remove(toBeRemoved);
            getUtx().commit();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Transaction deleted", "Transaction deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Transaction", success);
            transactions = new Transactions();
            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Transaction", success);
        }
        transactions = new Transactions();
        return null;
    }

    public String createProducttransaction() {
        try {
            getUtx().begin();
            getAudit().setAction("saved producttransaction " + getTransactions().getIdtransactions());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getProducttransaction());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getProducttransaction().getProductID() + " saved successfully."));
            setProducttransaction(new Producttransaction());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setProducttransaction(new Producttransaction());
        return null;
    }

    public String updateProducttransaction() {
        try {

            Producttransaction producttransaction = getEm().find(Producttransaction.class,
                    this.getProducttransaction().getIdproducttransaction());
            getUtx().begin();
            getAudit().setAction("updated producttransaction " + producttransaction.getProductID());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(producttransaction);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", producttransaction.getTransactionID() + " Updated successfully."));
            setProducttransaction(new Producttransaction());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setProducttransaction(new Producttransaction());
        return null;
    }

    public String indicatePrintedTransaction() {
        try {
            producttransactionList = em.createQuery("select p from Producttransaction p where p.statusID = '2' and p.transactionID.staffID = '" + user + "'").getResultList();
            for (Producttransaction p : producttransactionList) {
                p.setStatusID(new Status(4));
                getUtx().begin();
                getAudit().setAction("printed product transaction " + p.getProductID());
                getAudit().setCreatedby(getUser().getIdusers());
                getAudit().setTimer(new Date());
                getEm().persist(getAudit());
                getEm().merge(p);
                getUtx().commit();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", producttransaction.getTransactionID() + " Updated successfully."));
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setProducttransaction(new Producttransaction());
        return null;
    }

    public String deleteProducttransaction(Producttransaction producttransaction) {
        try {
            getUtx().begin();
            getAudit().setAction("Deleted Product transaction");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Producttransaction toBeRemoved = (Producttransaction) getEm().merge(producttransaction);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            producttransaction = new Producttransaction();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Producttransaction deleted", "Producttransaction deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Producttransaction", success);

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Producttransaction", success);
        }
        setProducttransaction(new Producttransaction());
        return null;
    }

    public String createUsergroup() {

        try {
            getUtx().begin();
            getAudit().setAction("created group");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getUsergroup().setCreatedBy(new User(1));
            getUsergroup().setCreatedAt(new java.util.Date());
            getUsergroup().setResponsibilities("ALL");
            getUsergroup().setStatusID(new Status(1));
            getEm().persist(getAudit());
            getEm().persist(getUsergroup());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getUsergroup().getName() + " saved successfully."));

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", getUsergroup().getName() + " could not create Usergroup successfully."));
            ex.printStackTrace();
        }
        setUsergroup(new Usergroup());
        return null;
    }

    public String updateUsergroup() {
        try {

            Usergroup group2 = getEm().find(Usergroup.class, getUsergroup().getIdgroups());
            getUtx().begin();
            getAudit().setAction("updated group");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(group2);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group2.getName() + " Updated successfully."));
            group2 = new Usergroup();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a group."));
        }
        setGroup1(new Usergroup());
        return null;
    }

    public String deleteUsergroup(Usergroup group) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted group");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Usergroup toBeRemoved = (Usergroup) getEm().merge(group);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            group = new Usergroup();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group.getName() + " Deleted successfully."));

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group.getName() + " failed to delete successfully."));
        }
        group = new Usergroup();
        return null;
    }

    public String createUser() {
        try {

            getUser().setCreatedAt(new java.util.Date());
            getUser().setCreatedBy(1);
            getUser().setLastLogin(new java.util.Date());
            getUser().setStatusID(1);
            getUser().setPword("1234");
            getUser().setDepartment(1);
            getUtx().begin();
            getAudit().setAction("saved user " + getUser().getUsername());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getUser());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getUser().getName() + " saved successfully."));
            setUser(new User());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
            ex.printStackTrace();
        }
        setUser(new User());
        return null;
    }

    public String updateUser() {
        try {

            User user2 = getEm().find(User.class,
                    getUser().getIdusers());
            user2.setCreatedAt(new java.util.Date());
            user2.setCreatedBy(1);
            user2.setDepartment(1);
            user2.setEmail(getUser().getEmail());
            user2.setGroupID(getUser().getGroupID());
            user2.setLastLogin(new java.util.Date());
            user2.setName(getUser().getName());
            user2.setPhone(getUser().getPhone());
            user2.setPword("1234");
            user2.setStaffID(getUser().getStaffID());
            user2.setStatusID(1);
            user2.setUsername(getUser().getUsername());

            getUtx().begin();
            getAudit().setAction("updated user " + getUser().getIdusers());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(user2);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getUser().getName() + " Updated successfully."));
            setUser(new User());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setUser(new User());
        return null;
    }

    public String deleteUser(User user) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted user");
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            User toBeRemoved = (User) getEm().merge(user);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            user = new User();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User deleted", "User deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("User", success);

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("User", success);
        }
        user = new User();
        return null;
    }

    public String createStatus() {
        try {

            getStatus().setCreatedBy(getUser().getIdusers());
            getUtx().begin();
            getAudit().setAction("created status");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getStatus());
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getStatus().getName() + " saved successfully."));
            setStatus(new Status());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setStatus(new Status());
        return null;
    }

    public String updateStatus() {
        try {

            Status statu = getEm().find(Status.class, this.getStatus().getIdstatus());

            statu.setCreatedBy(getUser().getIdusers());
            statu.setDescription(getStatus().getDescription());
            statu.setName(getStatus().getName());

            getUtx().begin();
            getAudit().setAction("updated status");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());

            getEm().persist(getAudit());
            getEm().merge(statu);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getStatus().getName() + " Updated successfully."));
            setStatus(new Status());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a status."));
        }
        setStatus(new Status());
        return null;
    }

    public String deleteStatus(Status status) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted status");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Status toBeRemoved = (Status) getEm().merge(status);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            status = new Status();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Status deleted", "Status deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);
        }
        status = new Status();
        return null;
    }

    public List<Usergroup> getUsergroupList() {
        usergroupList = getEm().createQuery("select u from Usergroup u").getResultList();
        return usergroupList;
    }

    /**
     * @param usergroupList the usergroupList to set
     */
    public void setUsergroupList(List<Usergroup> usergroupList) {
        this.setUsergroupList(usergroupList);
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the userList
     */
    public List<User> getUserList() {
        userList = getEm().createQuery("select u from User u").getResultList();
        return userList;
    }

    /**
     * @param userList the userList to set
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the statusList
     */
    public List<Status> getStatusList() {
        statusList = getEm().createQuery("select s from Status s").getResultList();
        return statusList;
    }

    /**
     * @param statusList the statusList to set
     */
    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    /**
     * @return the audit
     */
    public Audit getAudit() {
        return audit;
    }

    /**
     * @param audit the audit to set
     */
    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    /**
     * @return the auditList
     */
    public List<Audit> getAuditList() {
        auditList = getEm().createQuery("select a from Audit a").getResultList();
        return auditList;
    }

    /**
     * @param auditList the auditList to set
     */
    public void setAuditList(List<Audit> auditList) {
        this.auditList = auditList;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGroup1List(List<Usergroup> usergroupList) {
        this.setUsergroupList(usergroupList);
    }

    /**
     * @param em the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * @return the utx
     */
    public UserTransaction getUtx() {
        return utx;
    }

    /**
     * @param utx the utx to set
     */
    public void setUtx(UserTransaction utx) {
        this.utx = utx;
    }

    public Usergroup getUsergroup() {
        return usergroup;
    }

    /**
     * @param usergroup thgetEm()usergroup to set
     */
    public void setGroup1(Usergroup usergroup) {
        this.setUsergroup(usergroup);
    }

    /**
     * @return the outletList
     */
    public List<Outlet> getOutletList() {
        outletList = getEm().createQuery("select o from Outlet o").getResultList();
        return outletList;
    }

    /**
     * @param outletList the outletList to set
     */
    public void setOutletList(List<Outlet> outletList) {
        this.outletList = outletList;
    }

    /**
     * @return the outlet
     */
    public Outlet getOutlet() {
        return outlet;
    }

    /**
     * @param outlet the outlet to set getEm()
     */
    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    /**
     * @return the paymentsList
     */
    public List<Paymentmethods> getPaymentmethodsList() {
        paymentmethodsList = getEm().createQuery("select p from Paymentmethods p").getResultList();
        return paymentmethodsList;
    }

    /**
     * @param paymentmethodsList the paymentmethodsList to set
     */
    public void setPaymentmethodsList(List<Paymentmethods> paymentmethodsList) {
        this.paymentmethodsList = paymentmethodsList;
    }

    /**
     * @return the paymentmethods
     */
    public Paymentmethods getPaymentmethods() {
        return paymentmethods;
    }

    /**
     * @param paymentmethods the paymentmethods to set
     */
    public void setPaymentmethods(Paymentmethods paymentmethods) {
        this.paymentmethods = paymentmethods;
    }

    /**
     * @return the paymentsList
     */
    public List<Payments> getPaymentsList() {
        paymentsList = getEm().createQuery("select p from Payments p").getResultList();
        return paymentsList;
    }

    /**
     * @param paymentsList the paymentsList to set
     */
    public void setPaymentsList(List<Payments> paymentsList) {
        this.paymentsList = paymentsList;
    }

    /**
     * @return the payments
     */
    public Payments getPayments() {
        return payments;
    }

    /**
     * @param payments the payments to set
     */
    public void setPayments(Payments payments) {
        this.payments = payments;
    }

    /**
     * @return the productcategoryList
     */
    public List<Productcategory> getProductcategoryList() {
        productcategoryList = getEm().createQuery("select p from Productcategory p").getResultList();
        return productcategoryList;
    }

    /**
     * @param productcategoryList the productcategoryList to set
     */
    public void setProductcategoryList(List<Productcategory> productcategoryList) {
        this.productcategoryList = productcategoryList;
    }

    /**
     * @return the productcategory
     */
    public Productcategory getProductcategory() {
        return productcategory;
    }

    /**
     * @param productcategory the productcategory to set
     */
    public void setProductcategory(Productcategory productcategory) {
        this.productcategory = productcategory;
    }

    /**
     * @return the transactionsList
     */
    public List<Products> getProductsList() {
        productsList = getEm().createQuery("select p from Products p").getResultList();
        return productsList;
    }

    /**
     * @param productsList the productsList to set
     */
    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    /**
     * @return the products
     */
    public Products getProducts() {
        producttransaction.setSessionDetails(user.getOutlet().getOutletname() + user.getName() + new java.util.Date());
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    /**
     * @return the producttransactionList
     */
    public List<Producttransaction> getProducttransactionList() {
        producttransactionList = getEm().createQuery("select p from Producttransaction p where p.statusID.idstatus = '2'").getResultList();
        return producttransactionList;
    }

    /**
     * @param producttransactionList the producttransactionList to set
     */
    public void setProducttransactionList(List<Producttransaction> producttransactionList) {
        this.producttransactionList = producttransactionList;
    }

    /**
     * @return the producttransaction
     */
    public Producttransaction getProducttransaction() {
        return producttransaction;
    }

    /**
     * @param producttransaction the producttransaction to set
     */
    public void setProducttransaction(Producttransaction producttransaction) {
        this.producttransaction = producttransaction;
    }

    /**
     * @return the transactionsList
     */
    public List<Transactions> getTransactionsList() {
        transactionsList = getEm().createQuery("select t from Transactions t").getResultList();
        return transactionsList;
    }

    /**
     * @param transactionsList the transactionsList to set
     */
    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public Transactions getTransactions() {
        return transactions;
    }

    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(Transactions transactions) {
        this.transactions = transactions;
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @return the remember
     */
    public boolean isRemember() {
        return remember;
    }

    /**
     * @param remember the remember to set
     */
    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    /**
     * @return the file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     * @param usergroup the usergroup to set
     */
    public void setUsergroup(Usergroup usergroup) {
        this.usergroup = usergroup;
    }

    /**
     * @return the sessionList
     */
    public List<Session> getSessionList() {
        sessionList = em.createQuery("select s from Session s").getResultList();
        return sessionList;
    }

    /**
     * @param sessionList the sessionList to set
     */
    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * @return the admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * @return the sales
     */
    public boolean isSales() {
        return sales;
    }

    /**
     * @param sales the sales to set
     */
    public void setSales(boolean sales) {
        this.sales = sales;
    }

    /**
     * @return the accountant
     */
    public boolean isAccountant() {
        return accountant;
    }

    /**
     * @param accountant the accountant to set
     */
    public void setAccountant(boolean accountant) {
        this.accountant = accountant;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        date = new java.util.Date();
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

}
