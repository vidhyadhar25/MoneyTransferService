package com.moneytransferservice.service;

import com.moneytransferservice.dao.DAOFactory;
import com.moneytransferservice.exception.CustomException;
import com.moneytransferservice.model.MoneyUtil;
import com.moneytransferservice.model.UserTransaction;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionService {
    private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);

    /**
     * Transfer fund between two accounts.
     *
     * @param transaction
     * @return
     * @throws CustomException
     */
    @POST
    public Response transferFund(UserTransaction transaction) throws CustomException {

        String currency = transaction.getCurrencyCode();
        if (MoneyUtil.INSTANCE.validateCcyCode(currency)) {
            int updateCount = daoFactory.getAccountDAO().transferAccountBalance(transaction);
            if (updateCount == 2) {
                return Response.status(Response.Status.OK).build();
            } else {
                // transaction failed
                throw new WebApplicationException("Transaction failed", Response.Status.BAD_REQUEST);
            }

        } else {
            throw new WebApplicationException("Currency Code Invalid ", Response.Status.BAD_REQUEST);
        }

    }
}
