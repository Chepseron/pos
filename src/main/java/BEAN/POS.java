/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import com.amon.db.*;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

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

    public POS() {
    }

    @PostConstruct
    public void init() {
        try {
            createAccidentModel();

        } catch (Exception e) {
        }
    }
    private MapModel advancedModel;
    private MapModel actorModel;
    private Marker marker;

//    private LineChartModel accidentModel;
    private void createAccidentModel() {
//        setAccidentList((List<Accident>) getEm().createQuery("select a from Accident a").getResultList());
//        setAccidentModel(new LineChartModel());
//        LineChartSeries Cohort = new LineChartSeries();
//        Cohort.setFill(true);
//        Cohort.setLabel("Accident/places occured");
//
//        for (Accident med : getAccidentList()) {
//            Cohort.set(med.getRoad(), med.getDeadVictims());
//        }
//
//        getAccidentModel().addSeries(Cohort);
//        getAccidentModel().setTitle("Accident");
//        getAccidentModel().setLegendPosition("ne");
//        getAccidentModel().setStacked(true);
//        getAccidentModel().setShowPointLabels(true);
//
//        Axis xAxis = new CategoryAxis("Places/Road occured");
//        getAccidentModel().getAxes().put(AxisType.X, xAxis);
//        Axis yAxis = getAccidentModel().getAxis(AxisType.Y);
//        yAxis.setLabel("Deceased victims");
//        yAxis.setMin(0);
//
//        advancedModel = new DefaultMapModel();
//        accidentList = (List<Accident>) getEm().createQuery("select a from Accident a").getResultList();
//        for (Accident c : accidentList) {
//
//            System.out.println("Latitude and location " + c.getLatitude() + " Road " + c.getRoad());
//            getAdvancedModel().addOverlay(new Marker(new LatLng(Double.parseDouble(c.getLatitude()), Double.parseDouble(c.getLongitude())), "Road Name:" + c.getRoad() + " Location " + c.getPlaceOccured() + " Deceased Victims " + c.getDeadVictims(), "/images/xml.png", "http://maps.google.com/mapfiles/ms/micons/red-dot.png"));
//        }
//
//        actorModel = new DefaultMapModel();
//        deploymentunitList = (List<Deploymentunit>) getEm().createQuery("select d FROM Deploymentunit d").getResultList();
//        for (Deploymentunit c : deploymentunitList) {
//            getAdvancedModel().addOverlay(new Marker(new LatLng(Double.parseDouble(c.getLatitude()), Double.parseDouble(c.getLongitude())), "Organisation Name:" + c.getOrgname() + " Phone Numbers " + c.getPhoneNumber(), "/images/xml.png", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
//            getActorModel().addOverlay(new Marker(new LatLng(Double.parseDouble(c.getLatitude()), Double.parseDouble(c.getLongitude())), "Organisation Name:" + c.getOrgname() + " Phone Numbers " + c.getPhoneNumber(), "/images/xml.png", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
//        }

    }

    public String login() {
        try {

            setUser((User) getEm().createQuery("select u from User u where u.username = '" + getUsername() + "' and u.pword = '" + getPassword() + "'").getSingleResult());
//            setGroup1((Usergroup) getEm().createQuery("select u from Usergroup u where u.idgroups = " + getUser().getGroupID() + "").getSingleResult());
//            if (getUsergroup().getName().equalsIgnoreCase("ADMIN")) {
//
//            } else if (getUsergroup().getName().equalsIgnoreCase("ACCOUNTANT")) {
//
//            } else if (getUsergroup().getName().equalsIgnoreCase("SELLER")) {
//
//            } else {
//
//            }

            getUtx().begin();
            getAudit().setAction("logged into the system at  " + new Date());
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getUtx().commit();

            return "index2.xhtml?faces-redirect=true";
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please provide correct credentials");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
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
            getAudit().setAction("saved outlet " + outlet.getOutletname());
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(outlet);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", outlet.getOutletname() + " saved successfully."));
            setOutlet(new Outlet());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setOutlet(new Outlet());
        return null;
    }

    public String updateOutlet() {
        try {

            Outlet outlet = getEm().find(Outlet.class, this.outlet.getIdoutlet());

            getUtx().begin();
            getAudit().setAction("updated outlet " + outlet.getOutletname());
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setCreatedby(user.getIdusers());
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

    public String createPaymentmethods() {
        try {

            getUtx().begin();
            getAudit().setAction("saved payment method " + paymentmethods.getMethod());
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(paymentmethods);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", paymentmethods.getMethod() + " saved successfully."
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
                    this.paymentmethods.getIdpaymentmethods());
            getUtx().begin();
            getAudit().setAction("updated payment method " + paymentmethod.getMethod());
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setAction("saved outlet " + payments.getTransactionID().getRetailprice());
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(payments);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", payments.getMethodcode() + " created successfully."));
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
                    this.payments.getIdpayments());

            getUtx().begin();
            getAudit().setAction("updated payments " + payments.getPaymentAmount());
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setAction("saved outlet " + payments.getTransactionID().getRetailprice());
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(payments);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", payments.getOtherdetails() + " saved successfully."));
            setProductcategory(new Productcategory());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setProductcategory(new Productcategory());
        return null;
    }

    public String updateProductcategory() {
        try {

            Productcategory productcategory = getEm().find(Productcategory.class,
                    this.productcategory.getIdproductcategory());

            getUtx().begin();
            getAudit().setAction("updated productcategory " + productcategory.getName());
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setCreatedby(user.getIdusers());
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

    public String createProducts() {
        try {
            getUtx().begin();
            getAudit().setAction("saved product " + products.getName());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(products);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", products.getName() + " saved successfully."));
            setProducts(new Products());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setUser(new User());
        return null;
    }

    public String updateProducts() {
        try {

            Products products = getEm().find(Products.class,
                    this.products.getIdproducts());
            getUtx().begin();
            getAudit().setAction("updated products " + products.getName());
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setCreatedby(user.getIdusers());
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
            getUtx().begin();
            getAudit().setAction("saved transaction " + transactions.getOtherdetails());
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(transactions);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", transactions.getOtherdetails() + " saved successfully."));
            setTransactions(new Transactions());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setTransactions(new Transactions());
        return null;
    }

    public String updateTransaction() {
        try {
            Transactions trans = getEm().find(Transactions.class, this.transactions.getIdtransactions());
            getUtx().begin();
            getAudit().setAction("updated transactions " + trans.getOtherdetails());
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(getTransactions());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", transactions.getOtherdetails() + " Updated successfully."));
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
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setAction("saved producttransaction " + transactions.getIdtransactions());
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(producttransaction);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", producttransaction.getProductID() + " saved successfully."));
            setProducttransaction(new Producttransaction());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        producttransaction = new Producttransaction();
        return null;
    }

    public String updateProducttransaction() {
        try {

            Producttransaction producttransaction = getEm().find(Producttransaction.class,
                    this.producttransaction.getIdproducttransaction());
            getUtx().begin();
            getAudit().setAction("updated producttransaction " + producttransaction.getProductID());
            getAudit().setCreatedby(user.getIdusers());
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

    public String deleteProducttransaction(Producttransaction producttransaction) {
        try {
            getUtx().begin();
            getAudit().setAction("Deleted Product transaction");
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            usergroup.setCreatedBy(new User(1));
            usergroup.setCreatedAt(new java.util.Date());
            getEm().persist(getAudit());
            getEm().persist(usergroup);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", usergroup.getName() + " saved successfully."));

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", usergroup.getName() + " could not create Usergroup successfully."));
            ex.printStackTrace();
        }
        usergroup = new Usergroup();
        return null;
    }

    public String updateUsergroup() {
        try {

            Usergroup group2 = getEm().find(Usergroup.class, usergroup.getIdgroups());
            getUtx().begin();
            getAudit().setAction("updated group");
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getUser());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getUser().getName() + " saved successfully."));
            setUser(new User());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
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
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setCreatedby(user.getIdusers());
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
            getAudit().setCreatedby(user.getIdusers());
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
        usergroupList = em.createQuery("select u from Usergroup u").getResultList();
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
        this.usergroupList = usergroupList;
    }

    public void setAdvancedModel(MapModel advancedModel) {
        this.advancedModel = advancedModel;
    }

    /**
     * @param marker the marker to set
     */
    public void setMarker(Marker marker) {
        this.marker = marker;
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

    /**
     * @return the advancedModel
     */
    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    /**
     * @return the actorModel
     */
    public MapModel getActorModel() {
        return actorModel;
    }

    /**
     * @param actorModel the actorModel to set
     */
    public void setActorModel(MapModel actorModel) {
        this.actorModel = actorModel;
    }

    /**
     * @return the marker
     */
    public Marker getMarker() {
        return marker;
    }

//    /**
//     * @return the accidentModel
//     */
//    public LineChartModel getAccidentModel() {
//        return accidentModel;
//    }
//
//    /**
//     * @param accidentModel the accidentModel to set
//     */
//    public void setAccidentModel(LineChartModel accidentModel) {
//        this.accidentModel = accidentModel;
//    }
    /**
     * @return the usergroup
     */
    public Usergroup getUsergroup() {
        return usergroup;
    }

    /**
     * @param usergroup thgetEm()usergroup to set
     */
    public void setGroup1(Usergroup usergroup) {
        this.usergroup = usergroup;
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
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    /**
     * @return the producttransactionList
     */
    public List<Producttransaction> getProducttransactionList() {
        producttransactionList = getEm().createQuery("select p from Producttransaction p").getResultList();
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

}
